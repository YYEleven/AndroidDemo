package wxw.com.androiddemo;

//import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Eleven on 16/2/18.
 */
public class FriendFragment extends Fragment {
    private Button friend_btn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friends_layout,null);
        friend_btn = (Button) view.findViewById(R.id.friend_btn);
        friend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"点击了",Snackbar.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
