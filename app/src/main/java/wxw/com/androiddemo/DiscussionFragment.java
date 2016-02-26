package wxw.com.androiddemo;

//import android.app.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import wxw.com.androiddemo.adapter.DiscussionTextWatcher;

/**
 * Created by Eleven on 16/2/18.
 */
public class DiscussionFragment extends Fragment {
    private TextInputLayout inputLayout;
    private EditText et;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.discussion_layout, null);
        inputLayout = (TextInputLayout) view.findViewById(R.id.inputLayout);
        et = (EditText) view.findViewById(R.id.et);
        inputLayout.getEditText().addTextChangedListener(new DiscussionTextWatcher(inputLayout, "用户名长度不能小于6位"));

        return view;
    }
}
