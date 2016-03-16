package wxw.com.androiddemo;

//import android.app.Fragment;

import android.app.DownloadManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import wxw.com.androiddemo.socket.Client;

/**
 * Created by Eleven on 16/2/18.
 */
public class FriendFragment extends Fragment {
    private Button friend_btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friends_layout, null);
        friend_btn = (Button) view.findViewById(R.id.friend_btn);
        friend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "点击了", Snackbar.LENGTH_SHORT).show();
                new Thread(){
                    @Override
                    public void run() {
                        startClient();
                    }
                }.start();

            }
        });
        return view;
    }

    public void okHttpTest() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("").build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

            }
        });

    }


    public void startClient() {
        String serverIp = "192.168.4.23";
        int port = 65430;
        Client client = new Client(serverIp,port);
        client.start();

    }

}
