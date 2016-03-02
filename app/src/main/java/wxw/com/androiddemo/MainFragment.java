package wxw.com.androiddemo;


//import android.app.AlertDialog;

//import android.app.Fragment;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
    private ImageView image_view;
    private Animator anim;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        context = getActivity();
        super.onCreate(savedInstanceState);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
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


//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle("Dialog");
//                builder.setMessage("少数派客户端");
//                builder.setPositiveButton("OK", null);
//                builder.setNegativeButton("Cancel", null);
//                builder.show();
                image_view.setVisibility(View.GONE);
                int cx = (image_view.getLeft() + image_view.getRight()) / 2;
                int cy = (image_view.getTop() + image_view.getBottom()) / 2;
                int finalRadius = Math.max(image_view.getWidth(), image_view.getHeight());
                anim = ViewAnimationUtils.createCircularReveal(image_view, cx, cy, finalRadius, 0);
                anim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        image_view.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
//                image_view.setVisibility(View.VISIBLE);
                anim.start();
            }

        });
        image_view = (ImageView) view.findViewById(R.id.image_view);



        return view;
    }

    @Override
    public void onDestroy() {
        localBroadcastManager.unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }
}
