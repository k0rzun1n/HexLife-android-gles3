package com.example.krz.android_opengl_t0;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by krz on 25-Jul-17.
 */

//public class MyJoystick extends LinearLayout {
public class MyJoystick extends View {
    private MyGLSurfaceView mGLSurfaceView;


    public MyJoystick(Context context) {
        super(context);
    }

    public MyJoystick(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    boolean verticalMovement;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
//        this.setBackgroundColor(0xFF00FF00);
//        if(e.getAction()==1 || e.getAction()==2)
//            return true;
//        MotionEvent.ACTION_BUTTON_PRESS
        float dx, dy;
        dx = e.getX() / ((float) getWidth()) - 0.5f;//norm
        dy = e.getY() / ((float) getHeight()) - 0.5f;//norm

        if (e.getAction() == MotionEvent.ACTION_DOWN)
            if (dy > 0.3f || dy < -0.3f)
                verticalMovement = true;
            else
                verticalMovement = false;
        if (mGLSurfaceView != null)
            if (verticalMovement)
                mGLSurfaceView.moveCamera(0, 0, -dy);
            else
                mGLSurfaceView.moveCamera(dx, dy, 0);
        return true;
    }


    public void setmGLSurfaceView(MyGLSurfaceView mGLSurfaceView) {
        this.mGLSurfaceView = mGLSurfaceView;
    }
}
