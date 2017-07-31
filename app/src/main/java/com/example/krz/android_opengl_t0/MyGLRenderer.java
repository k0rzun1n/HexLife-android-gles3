package com.example.krz.android_opengl_t0;

import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.Matrix;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.nio.ByteBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

//import android.opengl.GLES30Ext;

public class MyGLRenderer implements GLSurfaceView.Renderer {

    private Quad mQuad;
    private Cylinder mCyl;
    private Context mContext;
    public float[] mClearColor;
    float[] mEye;
    float[] mEyeRotation; //zy
    int[] frameBufs;
    int[] rendTex;

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
        mQuad = new Quad(mContext);
        mCyl = new Cylinder(mContext);
        GLES30.glClearColor(mClearColor[0], mClearColor[1], mClearColor[2], mClearColor[3]);
        hlg.step();

        frameBufs = new int[2];
        GLES30.glGenFramebuffers(2, frameBufs, 0);

        rendTex = new int[2];
        GLES30.glGenTextures(2, rendTex, 0);

        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, rendTex[0]);
//        Buffer tex = ByteBuffer.allocateDirect(100*100*4);
        Buffer tex = ByteBuffer.allocateDirect(1);
        GLES30.glTexImage2D(GLES30.GL_TEXTURE_2D, 0, GLES30.GL_RGBA, 100, 100, 0, GLES30.GL_RGBA, GLES30.GL_UNSIGNED_BYTE, tex);

//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
//        GLES2
//        GLES30.glCheckFramebufferStatus()
//        GLES30.glFramebufferTexture2D(GLES30.GL_COLOR_ATTACHMENT0,);

//        ByteBuffer bb = ByteBuffer.allocateDirect()
    }

    private float[] mRotationMatrix = new float[16];

    public void onDrawFrame(GL10 gl) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_STENCIL_BUFFER_BIT);
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);

//        Log.d("GLESVER",GLES30.glGetString(GLES30.GL_SHADING_LANGUAGE_VERSION));
//        Log.d("GLESVER",GLES30.glGetString(GLES30.GL_VERSION));
        int[] glmax = new int[1];
//        GLES30.glGetIntegerv(GLES30.GL_MAX_VERTEX_UNIFORM_VECTORS, glmax, 0);
        GLES30.glGetIntegerv(GLES30.GL_VERTEX_ATTRIB_ARRAY_SIZE, glmax, 0);
        Log.d("GLESVER", Integer.toString(glmax[0]));
        Log.d("GLESVER", GLU.gluErrorString(GLES30.glGetError()));

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
        GLES30.glEnable(GLES30.GL_STENCIL_TEST);
//        GLES30.glStencilFunc(GLES30.GL_NEVER,1,1);
        GLES30.glStencilFunc(GLES30.GL_ALWAYS, 1, 1);
//        GLES30.glStencilFunc();
        int[] ixx = new int[10];
        GLES30.glGetIntegerv(GLES30.GL_STENCIL_BITS, ixx, 0);
        Log.d("stencbi", Integer.toString(ixx[0]));
//        GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER,frameBufs[0]);
//        mCyl.draw(mMVPMatrix);
//        Matrix.translateM(mMVPMatrix, 0, -3f, 0, 0);
//        mCyl.draw(mMVPMatrix);
//        GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER,0);
        mQuad.draw();
//        mQuad.draw(scratch);
    }

    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
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

        // create a vertex shader type (GLES30.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES30.GL_FRAGMENT_SHADER)
        int shader = GLES30.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES30.glShaderSource(shader, shaderCode);
        GLES30.glCompileShader(shader);
        Log.d("ShaderLog", GLES30.glGetShaderInfoLog(shader));
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
