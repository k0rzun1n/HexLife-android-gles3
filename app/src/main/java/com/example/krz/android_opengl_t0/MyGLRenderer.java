package com.example.krz.android_opengl_t0;

import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.Matrix;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyGLRenderer implements GLSurfaceView.Renderer {

    private Square mSquare;
    private Cylinder mCyl;
    private Context mContext;
    public float[] mClearColor;
    float[] mEye;
    float[] mEyeRotation; //zy

    private HexLifeGame hlg = new HexLifeGame();

    public MyGLRenderer(Context context) {
        super();
        mContext = context;
    }

    public void setmClearColor(float[] newCol) {
        for (int i = 0; i < 4; i++)
            mClearColor[i] = newCol[i];
    }

    public void moveCamera(float dx, float dy) {
        double zRotRad = mEyeRotation[0] / 180 * Math.PI;
        double speed = 0.2;
        mEye[0] += speed * (-dx * Math.sin(zRotRad) - dy * Math.cos(zRotRad));
        mEye[1] += speed * (-dx * Math.cos(zRotRad) + dy * Math.sin(zRotRad));
    }

    public void rotateCamera(float dx, float dy) {
//        mEye[0] += dx / 100;
        mEyeRotation[0] += dx / 20;
        mEyeRotation[1] += dy / 20;
        mTv.post(new Runnable() {
            @Override
            public void run() {
                mTv.setText(Float.toString(mEyeRotation[0]) + " : " + Float.toString(mEyeRotation[1]));
            }
        });
    }

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        mEye = new float[]{-6.0f, 0.0f, 3.0f};
        mEyeRotation = new float[]{0.0f, 0.0f};
        mClearColor = new float[]{0.3f, 0.0f, 0.0f, 1.0f};
//        mSquare = new Square(mContext);
        mCyl = new Cylinder(mContext);
        GLES20.glClearColor(mClearColor[0], mClearColor[1], mClearColor[2], mClearColor[3]);
        hlg.step();

//        ByteBuffer bb = ByteBuffer.allocateDirect()
    }

    private float[] mRotationMatrix = new float[16];

    public void onDrawFrame(GL10 gl) {
//        GLES20.glClearColor(mClearColor[0], mClearColor[1], mClearColor[2], mClearColor[3]);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_STENCIL_BUFFER_BIT);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

//        Log.d("GLESVER",GLES20.glGetString(GLES20.GL_SHADING_LANGUAGE_VERSION));
//        Log.d("GLESVER",GLES20.glGetString(GLES20.GL_VERSION));
        int[] glmax = new int[1];
//        GLES20.glGetIntegerv(GLES20.GL_MAX_VERTEX_UNIFORM_VECTORS, glmax, 0);
        GLES20.glGetIntegerv(GLES20.GL_VERTEX_ATTRIB_ARRAY_SIZE, glmax, 0);
        Log.d("GLESVER", Integer.toString(glmax[0]));
        Log.d("GLESVER", GLU.gluErrorString(GLES20.glGetError()));

//        Matrix.setLookAtM(mViewMatrix, 0, mEye[0], mEye[1], mEye[2], mEye[0] - 1, mEye[1] - 1, mEye[2] - 3, 0f, 0f, 1.0f);
        Matrix.setLookAtM(mViewMatrix, 0,
                0f, 0f, 0f,
                1f, 0f, 0f,
                0f, 0f, 1.0f);
        Matrix.rotateM(mViewMatrix, 0, mEyeRotation[1], 0, -1, 0);
        Matrix.rotateM(mViewMatrix, 0, mEyeRotation[0], 0, 0, 1);
        Matrix.translateM(mViewMatrix, 0, -mEye[0], -mEye[1], -mEye[2]);

        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);


        //--DRAW
        GLES20.glEnable(GLES20.GL_STENCIL_TEST);
//        GLES20.glStencilFunc(GLES20.GL_NEVER,1,1);
        GLES20.glStencilFunc(GLES20.GL_ALWAYS, 1, 1);
//        GLES20.glStencilFunc();
        int[] ixx = new int[10];
        GLES20.glGetIntegerv(GLES20.GL_STENCIL_BITS, ixx, 0);
        Log.d("stencbi", Integer.toString(ixx[0]));
        mCyl.draw(mMVPMatrix);
        Matrix.translateM(mMVPMatrix, 0, -3f, 0, 0);
        mCyl.draw(mMVPMatrix);
//        mSquare.draw(scratch);
    }

    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
//        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 1, 100);
    }

    public static int loadShader(Context context, int type, String shaderFileName) {
        AssetManager am = context.getAssets();
        InputStream is;
        String shaderString = "";
        try {
            is = am.open(shaderFileName);
            shaderString = convertStreamToString(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("ShaderString", shaderString);
        return loadShader(type, shaderString);
    }

    public static int loadShader(int type, String shaderCode) {

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        Log.d("ShaderLog", GLES20.glGetShaderInfoLog(shader));
        return shader;
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private TextView mTv;

    public void setTextView(TextView tv) {
        mTv = tv;
    }
}
