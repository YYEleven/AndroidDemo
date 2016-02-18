package wxw.com.androiddemo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

/**
 * Created by Eleven on 16/2/16.
 */
public class ShowFragment extends Fragment {

    WebView webview=null;
    TextView tv_value;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show, container, false);
        webview=(WebView) view.findViewById(R.id.webview);
        tv_value = (TextView) view.findViewById(R.id.tv_value);


        return view;
    }


    public WebView getWebView()
    {
        return webview;
    }
    public TextView getTextView(){
        return tv_value;
    }
}
