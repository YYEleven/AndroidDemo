package wxw.com.androiddemo;

//import android.app.Fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.facebook.common.references.CloseableReference;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

import wxw.com.androiddemo.adapter.DiscussionTextWatcher;

/**
 * Created by Eleven on 16/2/18.
 */
public class DiscussionFragment extends Fragment {
    private TextInputLayout inputLayout;
    private EditText et;
    private SimpleDraweeView id_drawe,id_drawe2,id_drawe3;
    ControllerListener controllerListener = new ControllerListener() {
        @Override
        public void onSubmit(String id, Object callerContext) {
            Log.i("DiscussionFragment", "onSubmit");
        }

        @Override
        public void onFinalImageSet(String id, @Nullable Object imageInfo, @Nullable Animatable
                animatable) {
            Log.i("DiscussionFragment", "onFinalImageSet");
        }

        @Override
        public void onIntermediateImageSet(String id, @Nullable Object imageInfo) {
            Log.i("DiscussionFragment", "onIntermediateImageSet");
        }

        @Override
        public void onIntermediateImageFailed(String id, Throwable throwable) {
            Log.i("DiscussionFragment", "onIntermediateImageFailed");
        }

        @Override
        public void onFailure(String id, Throwable throwable) {
            Log.i("DiscussionFragment", "onFailure");
        }

        @Override
        public void onRelease(String id) {
            Log.i("DiscussionFragment", "onRelease");
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.discussion_layout, null);
        inputLayout = (TextInputLayout) view.findViewById(R.id.inputLayout);
        et = (EditText) view.findViewById(R.id.et);
        inputLayout.getEditText().addTextChangedListener(new DiscussionTextWatcher(inputLayout,
                "用户名长度不能小于6位"));
        id_drawe = (SimpleDraweeView) view.findViewById(R.id.id_drawe);
        id_drawe2 = (SimpleDraweeView) view.findViewById(R.id.id_drawe2);
        id_drawe3 = (SimpleDraweeView) view.findViewById(R.id.id_drawe3);
        Uri uri = Uri.parse("https://raw.githubusercontent" +
                ".com/facebook/fresco/gh-pages/static/fresco-logo.png");
//        id_drawe.setImageURI(uri);
        int width = 150;
        int height = 150;

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri).setResizeOptions(new
                ResizeOptions(width, height)).setAutoRotateEnabled(true).build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco
                .newDraweeControllerBuilder()
                .setOldController(id_drawe.getController())
                .setImageRequest(request)
                .build();
        id_drawe.setController(controller);
        id_drawe2.setImageURI(Uri.parse("http://img0.bdstatic.com/img/image/77a483d72a2d16264e65858374ab70151409117959.jpg"));
        id_drawe3.setImageURI(Uri.parse("http://img0.bdstatic.com/img/image/a4602ed3a2c98399cd48917370f00d1e1408098579.jpg"));
        return view;
    }
}
