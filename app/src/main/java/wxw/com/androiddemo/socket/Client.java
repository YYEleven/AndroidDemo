package wxw.com.androiddemo.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Eleven on 16/3/15.
 */
public class Client {
    private String serverIp;
    private int port;
    private Socket socket;
    private boolean running = false;
    private long lastSendTime;
    private ConcurrentHashMap<Class, ObjectAction> actionMapping = new ConcurrentHashMap<Class, ObjectAction>();

    public Client(String serverIP, int port) {
        this.serverIp = serverIP;
        this.port = port;

    }

    public void start() {
        if (running) return;
        try {
            socket = new Socket(serverIp, port);
            lastSendTime = System.currentTimeMillis();
            running = true;
            new Thread(new KeepAliveWatchDog()).start();
            new Thread(new ReceiveWatchDog()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    class KeepAliveWatchDog implements Runnable {


        long checkDelay = 10;
        long keepAliveDelay = 2000;

        @Override
        public void run() {
            while (running) {
                if (System.currentTimeMillis() - lastSendTime > keepAliveDelay) {
                    try {
                        Client.this.sendObject(new KeepAlive());
                    } catch (IOException e) {
                        e.printStackTrace();
                        Client.this.stop();
                    }
                    lastSendTime = System.currentTimeMillis();
                } else {
                    try {
                        Thread.sleep(checkDelay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Client.this.stop();
                    }
                }
            }
        }

    }

    class ReceiveWatchDog implements Runnable {

        @Override
        public void run() {

            while (running) {
                try {
                    InputStream in = socket.getInputStream();
                    if (in.available() > 0) {
                        ObjectInputStream ois = new ObjectInputStream(in);
                        Object obj = ois.readObject();
                        System.out.println("接收：\t" + obj);
                        ObjectAction oa = actionMapping.get(obj.getClass());
                        oa = oa == null ? new DefaultObjectAction() : oa;
                        oa.doAction(obj, Client.this);
                    } else {
                        Thread.sleep(10);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Client.this.stop();
                }
            }


        }
    }

    public void sendObject(Object obj) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(
                socket.getOutputStream());
        OutputStream os = socket.getOutputStream();
//        oos.writeObject(obj);
        OutputStreamWriter writer = new OutputStreamWriter(os);
        String str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                + "\t维持连接包";
        writer.write("A");
//        System.out.println("发送：\t" + obj);
        oos.flush();
        os.flush();
    }

    public void stop() {
        if (running)
            running = false;
    }

    public  interface ObjectAction {
        void doAction(Object obj, Client client);
    }

    public static final class DefaultObjectAction implements ObjectAction {
        public void doAction(Object obj, Client client) {
            System.out.println("处理：\t" + obj.toString());
        }
    }
}
