package wxw.com.androiddemo;


//import android.app.AlertDialog;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Eleven on 16/2/16.
 */
public class MainFragment extends Fragment {
    private LocalBroadcastManager localBroadcastManager;//
    private BroadcastReceiver broadcastReceiver;
    private Button btn;
    private TextView tv;
    private Context context;
    private IntentFilter intentFilter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        context = getActivity();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, null);
        intentFilter = new IntentFilter();
        intentFilter.addAction("test");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if ("test".equals(intent.getAction())) {
                    tv.setText("广播接收到了");
                }
            }
        };
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);
        btn = (Button) view.findViewById(R.id.btn);
        tv = (TextView) view.findViewById(R.id.tv);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                context.sendBroadcast(new Intent("test"));


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Dialog");
                builder.setMessage("少数派客户端");
                builder.setPositiveButton("OK", null);
                builder.setNegativeButton("Cancel", null);
                builder.show();
            }

        });
        return view;
    }

    @Override
    public void onDestroy() {
        localBroadcastManager.unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }
}
