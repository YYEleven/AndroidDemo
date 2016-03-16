package wxw.com.androiddemo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Eleven on 16/3/2.
 */
public class InternetFragment extends Fragment implements View.OnClickListener {
    private static String TAG = InternetFragment.class.getName();
    private static String NAMESPACE = "http://WebXml.com.cn/";
    private static String URL = "http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx";
    private Button btn_internet;
    private Button webservice;
    private Button btn_okhttp;
    @Bind(R.id.btn_okhttp_asypost)
    Button btn_okhttp_asypost;
    @Bind(R.id.btn_okhttp_asyget)
    Button btn_okhttp_asyget;
    private TextView tv_internet;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.internet_layout, null);
        ButterKnife.bind(this, view);
        btn_internet = (Button) view.findViewById(R.id.btn_internet);
        tv_internet = (TextView) view.findViewById(R.id.tv_internet);
        webservice = (Button) view.findViewById(R.id.webservice);
        btn_okhttp = (Button) view.findViewById(R.id.btn_okhttp);
        webservice.setOnClickListener(this);
        btn_internet.setOnClickListener(this);
        btn_okhttp.setOnClickListener(this);
        return view;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    String value = msg.getData().getString("msg");
                    tv_internet.setText(value);
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_internet:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("1.创建一个URL对象\n" +
                        " 2.利用HttpURLConnection对象从网络中获取网页数据\n" +
                        " 3.设置超时\n" +
                        " 4.响应吗判断\n" +
                        " 5.取得返回的输入流，获取数据");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.create().show();
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            //1.创建一个URL对象
                            //2.利用HttpURLConnection对象从网络中获取网页数据
                            //3.设置超时
                            //4.响应吗判断
                            //5.取得返回的输入流，获取数据
                            URL url = new URL("http://192.168.0.30:8001/ClinicalService" +
                                    ".asmx/DEVICE_GetRegInfo?strXML=<IN><ID>868029025006792</ID><SYSTEM>002</SYSTEM></IN>");
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setConnectTimeout(3000);
                            if (conn.getResponseCode() == 200) {
                                InputStream in = conn.getInputStream();
                                BufferedReader bf = new BufferedReader(new InputStreamReader(in));
                                String response = null;
                                String readLine = null;
                                while ((readLine = bf.readLine()) != null) {
                                    response = response + readLine;
                                }
//                                tv_internet.setText(response);
                                Log.i(TAG, response);
                                Message message = new Message();
                                message.what = 1;
                                Bundle bundle = new Bundle();
                                bundle.putString("msg", response);
                                message.setData(bundle);
                                handler.sendMessage(message);
                                bf.close();
                                in.close();
                                conn.disconnect();
                            }

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

                break;
            case R.id.webservice:

                final String func = "getRegionProvince";
                new Thread() {
                    @Override
                    public void run() {

                        String nameSpace = "http://www.zlsoft.com";
                        String method = "UserLogin";
                        String endPoint = "http://192.168.0.30:8001/ClinicalService.asmx";
                        String soapAction = "http://www.zlsoft.com/DEVICE_GetRegInfo";
                        SoapObject object = new SoapObject(nameSpace, method);
                        object.addProperty("strXML", "<IN><ID>868029025006792</ID><SYSTEM>002</SYSTEM></IN>");
                        object.addProperty("userName","ZLHIS");
                        object.addProperty("password","AQA");
                        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        envelope.bodyOut = object;
                        envelope.dotNet = true;
                        envelope.setOutputSoapObject(object);
                        HttpTransportSE se = new HttpTransportSE(endPoint);
                        try {
                            se.call(soapAction, envelope);
                            SoapObject result = (SoapObject) envelope.bodyIn;
                            String string = null;
                            if (result != null) {
                                string = object.getProperty(0).toString();
                                Log.i(TAG, string);
                                Message message = new Message();
                                message.what = 1;
                                Bundle bundle = new Bundle();
                                bundle.putString("msg", string);
                                message.setData(bundle);
                                handler.sendMessage(message);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        }


                    }
                }.start();


                break;
            case R.id.btn_okhttp:
                OkHttpClient client = new OkHttpClient();

                FormEncodingBuilder bu = new FormEncodingBuilder();
                bu.add("strXML","<IN><ID>868029025006792</ID><SYSTEM>002</SYSTEM></IN>");
                Request request = new Request.Builder().url("http://192.168.0.30:8001/ClinicalService.asmx/DEVICE_GetRegInfo").post(bu.build()).build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        Log.i(TAG, response.body().string());
                        if(response.isSuccessful()){
                            Message message = new Message();
                            message.what = 1;
                            Bundle bundle = new Bundle();
                            bundle.putString("msg", response.body().string()+"AAA");
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }

                    }
                });
//                new Thread() {
//                    @Override
//                    public void run() {
//                        OkHttpClient client = new OkHttpClient();
//                        Request request = new Request.Builder().url("https://publicobject.com/helloworld.txt").build();
//                        try {
//                            Response response = client.newCall(request).execute();
//                            if (response.isSuccessful()) {
//                                Message message = new Message();
//                                message.what = 1;
//                                Bundle bundle = new Bundle();
//                                bundle.putString("msg", response.body().string() + "AAA");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                            } else {
//
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }.start();


                break;
        }
    }

    @OnClick(R.id.btn_okhttp_asyget)
    public void asy_get() {
        OkHttpClient client = new OkHttpClient();
        String url = "http://192.168.0.30:8001/ClinicalService" +
                ".asmx?op=DEVICE_GetRegInfo?strXML=<IN><ID>868029025006792</ID><SYSTEM>002</SYSTEM></IN>";
        Request request = null;
        try {
            request = new Request.Builder().url(URLEncoder.encode(url, "utf-8").toString()).build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                System.out.println(response.body().string());
                Message message = new Message();
                message.what = 1;
                Bundle bundle = new Bundle();
                bundle.putString("msg", response.body().string());
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
    }

    @OnClick(R.id.btn_okhttp_asypost)
    public void asy_post() {
//        //创建OkHttpClient对象
//        OkHttpClient client = new OkHttpClient();
////通过FormEncodingBuilder对象添加多个请求参数键值对
//        FormEncodingBuilder builder = new FormEncodingBuilder();
//        builder.add("method", "post").add("strXML", "<IN><ID>868029025006792</ID><SYSTEM>002</SYSTEM></IN>");
////通过FormEncodingBuilder对象构造Post请求体
//        RequestBody body = builder.build();
////通过请求地址和请求体构造Post请求对象Request
//        Request request = new Request.Builder().url("http://192.168.0.30:8001/ClinicalService.asmx?op=DEVICE_GetRegInfo").post
//                (body).build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                Message message = new Message();
//                message.what = 1;
//                Bundle bundle = new Bundle();
//                bundle.putString("msg", response.body().string());
//                message.setData(bundle);
//                handler.sendMessage(message);
//            }
//        });


    }
}
