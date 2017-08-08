package com.example.krz.android_opengl_t0;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    private MyGLSurfaceView mGLView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        // mGLView = new MyGLSurfaceView(this);
        // setContentView(mGLView);

        //inflate instead
        setContentView(R.layout.activity_main);
        mGLView = (MyGLSurfaceView) findViewById(R.id.myGLView);
        MyJoystick mJoy = (MyJoystick) findViewById(R.id.mJoystick);

        mJoy.setmGLSurfaceView(mGLView);
    }

    public void gameStep(View v) {
        mGLView.gameStep();
    }
}

