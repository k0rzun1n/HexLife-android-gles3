package com.example.krz.android_opengl_t0;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private MyGLSurfaceView mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        HexLifeGame hlg = new HexLifeGame();
//        hlg.getField(0,0,3);
//        hlg.getField(0,1,3);
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
//        mGLView = new MyGLSurfaceView(this);
//        setContentView(mGLView);
        setContentView(R.layout.activity_main);
        mGLView = (MyGLSurfaceView) findViewById(R.id.myGLView);
        MyJoystick mJoy = (MyJoystick) findViewById(R.id.mJoystick);

        mGLView.setTextView((TextView) findViewById(R.id.debugText));
        mJoy.setTextView((TextView) findViewById(R.id.debugText2));
        mJoy.setmGLSurfaceView(mGLView);
    }

    public void changeBGColor(View v) {
        mGLView.setBGColor(new float[]{0f, 0.3f, 0.7f, 1f});
    }
}

