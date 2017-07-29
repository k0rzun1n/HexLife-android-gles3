package com.example.krz.android_opengl_t0;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by krz on 25-Jul-17.
 */

public class MyJoystick extends View {
    private TextView mTv;
    private MyGLSurfaceView mGLSurfaceView;


    public MyJoystick(Context context) {
        super(context);
    }

    public MyJoystick(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
//        this.setBackgroundColor(0xFF00FF00);
//        if(e.getAction()==1 || e.getAction()==2)
//            return true;
//        MotionEvent.ACTION_BUTTON_PRESS
        float dx,dy;
        dx = e.getX()/((float)getWidth()) - 0.5f;//norm
        dy = e.getY()/((float)getHeight()) - 0.5f;//norm
        if( mGLSurfaceView != null)
            mGLSurfaceView.moveCamera(dx,dy);
        mTv.setText(
                Float.toString(e.getX()) + ":" + Float.toString(e.getY())
                        + "|" + Integer.toString(this.getWidth()) + ":" + Integer.toString(this.getHeight())
                        + "|" + Float.toString(getX()) + ":" + Float.toString(getY())
                        + "|" + Float.toString(dx) + ":" + Float.toString(dy)
                        + "_" + Integer.toString(e.getPointerCount())
                        + "_" + Long.toString(System.currentTimeMillis())
        );
//        Log.d("lol",Float.toString((getWidth()*1.0f+1)/2));

        return true;
    }

    public void setTextView(TextView tv) {
        mTv = tv;
    }

    public void setmGLSurfaceView(MyGLSurfaceView mGLSurfaceView) {
        this.mGLSurfaceView = mGLSurfaceView;
    }
}
