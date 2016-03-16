package wxw.com.androiddemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Eleven on 16/3/15.
 */
public class NetWorkService extends Service {

    private static final String TAG = NetWorkService.class.getName();
    private static final long HEART_BEAT = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
