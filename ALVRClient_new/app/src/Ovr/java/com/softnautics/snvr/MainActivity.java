package com.softnautics.snvr;
import android.app.Activity;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import com.softnautics.snvr.R;
public class MainActivity extends Activity implements SurfaceHolder.Callback {
    private final static String TAG = "MainActivity ";
    private MainThread mThread = null;
    //private Activity mActivity;

    //private OvrContext mOvrContext = new OvrContext();
    //private Handler mHandler;
    //private HandlerThread mHandlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SurfaceView surfaceView = findViewById(R.id.surfaceview);

        SurfaceHolder holder = surfaceView.getHolder();
        holder.addCallback(this);

        Utils.logi(TAG, () -> "onCreate: Starting MainThread");
        mThread = new MainThread();
        mThread.start();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mThread != null) {
            mThread.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(mThread != null) {
            mThread.onPause();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        Utils.logi(TAG, () -> "onDestroy: Stopping MainThread.");
        if(mThread != null) {
            mThread.quit();
            mThread = null;
        }
        Utils.logi(TAG, () -> "onDestroy: MainThread has stopped.");
    }

    public void surfaceCreated(final SurfaceHolder holder) {
        mThread.onSurfaceCreated(holder.getSurface());
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mThread.onSurfaceChanged(holder.getSurface());
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        mThread.onSurfaceDestroyed();
    }
}
