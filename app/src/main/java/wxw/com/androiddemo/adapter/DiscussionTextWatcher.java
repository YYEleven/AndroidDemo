package wxw.com.androiddemo.adapter;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Eleven on 16/2/19.
 */
public class DiscussionTextWatcher implements TextWatcher {
    private TextInputLayout inputLayout;
    private String errorInfo;
    public DiscussionTextWatcher(TextInputLayout inputLayout,String errorInfo){
        this.inputLayout = inputLayout;
        this.errorInfo = errorInfo;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(inputLayout.getEditText().getText().toString().length()<6){
            inputLayout.setErrorEnabled(true);
            inputLayout.setError(errorInfo);
        }else{
            inputLayout.setErrorEnabled(false);
        }
    }
}
