package com.example.krz.android_opengl_t0;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by krz on 24-Jul-17.
 */
class MyGLSurfaceView extends GLSurfaceView {

    private MyGLRenderer mRenderer;
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;
    private TextView mTv;

    //    @Override
//    protected void onFinishInflate(){
//        super();
//
//    }
    public void moveCamera(float dx, float dy) {
        mRenderer.moveCamera(dx, dy);
        requestRender();
    }

    public void setTextView(TextView tv) {
        mRenderer.setTextView(tv);
    }

    public void setBGColor(float[] newCol) {
        queueEvent(new Runnable() {
            @Override
            public void run() {
                GLES30.glClearColor(0, 0.4f, 0.2f, 1);
            }
        });
        requestRender();
    }

    private void init(Context context) {
        // Create an OpenGL ES 3.0 context
        setEGLContextClientVersion(3);

        mRenderer = new MyGLRenderer(context);

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    public MyGLSurfaceView(Context context) {
        super(context);
        init(context);
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    //    public MyGLSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs);
//        init(context);
//    }
    private long tt;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();
//        e.getpo
//        Log.d("buttz","press");
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                tt = System.currentTimeMillis();
                Log.d("buttst", Long.toString(tt));
                break;
            case MotionEvent.ACTION_UP:
                if (System.currentTimeMillis() - tt < 2000)
                    Log.d("butt", "clicked__");
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = x - mPreviousX;
                float dy = y - mPreviousY;
                mRenderer.rotateCamera(dx, dy);
                requestRender();
        }
        mPreviousX = x;
        mPreviousY = y;
        return true;
    }

}
