package wxw.com.androiddemo;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v4.widget.SlidingPaneLayout.PanelSlideListener;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


public class MainActivity extends Activity implements BookMarkerFragment.BookmarkListener {
    Fragment bookmarkerFragment;
    Fragment showFragment;
    SlidingPaneLayout spl = null;
    ActionBar actionBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        actionBar = this.getActionBar();
//        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setDisplayHomeAsUpEnabled(true);
        spl = (SlidingPaneLayout) this.findViewById(R.id.slidingpanellayout);
        spl.setPanelSlideListener(new PanelSlideListener() {
            @Override
            public void onPanelClosed(View view) {
                MainActivity.this.getFragmentManager()
                        .findFragmentById(R.id.leftfragment)
                        .setHasOptionsMenu(false);
            }

            @Override
            public void onPanelOpened(View viw) {
                MainActivity.this.getFragmentManager()
                        .findFragmentById(R.id.leftfragment)
                        .setHasOptionsMenu(true);
            }

            @Override
            public void onPanelSlide(View arg0, float arg1) {

            }
        });
    }

    @Override
    public void onChangeBookmark(String bookmark) {
        ShowFragment sf = (ShowFragment)MainActivity.this.getFragmentManager().findFragmentById(R.id.rightfragment);
        WebView webView = sf.getWebView();
        TextView tv = sf.getTextView();
        tv.setText(bookmark);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        WebViewClient client = new WebViewClient();
        webView.setWebViewClient(client);
        webView.loadUrl(bookmark);

    }

}
