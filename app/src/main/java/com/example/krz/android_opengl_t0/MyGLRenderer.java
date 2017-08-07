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
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_FRAMEBUFFER;

//import android.opengl.GLES30Ext;

public class MyGLRenderer implements GLSurfaceView.Renderer {

    int[] frameBufs;
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private Quad mQuad;
    private Cylinder mCyl;
    private Context mContext;
    float[] mEye;
    float[] mEyeRotation; //zy

    int[] rendBuf;

    private HexLifeGame mHLG;

    int[] rendTex;
    private int camGridX;
    private int camGridY;
    private int renderRadius = 7;

    public MyGLRenderer(Context context) {
        super();
        mHLG = new HexLifeGame();
        mContext = context;
    }

    public void gameStep() {
        //add nullchecks
        mHLG.step();
        mCyl.setInst(mHLG.getField(camGridX, camGridY, renderRadius));
//        mCyl.setInst(mHLG.getField(0, 0, 7));
//        GLES30.glClearColor(0,1f,0,1f);
    }

    public void switchCellAtPixel(final int x, final int y) {
        //probably need to lock something
        GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, frameBufs[0]);
        GLES30.glReadBuffer(GLES30.GL_COLOR_ATTACHMENT1);
        IntBuffer ret = IntBuffer.allocate(2);
        GLES30.glReadPixels(x, mHeight - y, 1, 1, GLES30.GL_RG_INTEGER, GLES30.GL_INT, ret);
        Log.d("rdpx",
                Integer.toString(ret.get(0)) + " " +
                        Integer.toString(ret.get(1)));
        mHLG.switchCell(ret.get(0), ret.get(1));
        ret.clear();
        GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, 0);
//        mCyl.setInst(mHLG.getField(0, 0, 7));
        mCyl.setInst(mHLG.getField(camGridX, camGridY, renderRadius));
    }

    public void moveCamera(float dx, float dy) {
        double zRotRad = mEyeRotation[0] / 180 * Math.PI;
        double speed = 0.2;
        mEye[0] += speed * (-dx * Math.sin(zRotRad) - dy * Math.cos(zRotRad));
        mEye[1] += speed * (-dx * Math.cos(zRotRad) + dy * Math.sin(zRotRad));

        float cellRadius = 1.1f;
        float xc = cellRadius * (float) Math.sqrt(3.0);
        float yc = cellRadius * 1.5f;
        boolean camMoved = false;
        camGridY = (int) (mEye[1] / yc);
        camGridX = (int) ((mEye[0] - (camGridY % 2) * 0.5f) / xc);
        if (camMoved) mCyl.setInst(mHLG.getField(camGridX, camGridY, renderRadius));
//        Log.d("cam",Integer.toString(camGridX)+":"+Integer.toString(camGridY));
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
//        mEye = new float[]{-6.0f, 0.0f, 3.0f};
        mEye = new float[]{-6.0f, 0.0f, 12.0f};
        mEyeRotation = new float[]{0.0f, 60.0f};
        mQuad = new Quad(mContext);
        mCyl = new Cylinder(mContext);
        GLES30.glClearColor(0.1f, 0f, 0f, 1f);
//        hlg.step();

        frameBufs = new int[1];
        GLES30.glGenFramebuffers(1, frameBufs, 0);

        rendTex = new int[2];
        GLES30.glGenTextures(2, rendTex, 0);

        rendBuf = new int[1];
        GLES30.glGenRenderbuffers(1, rendBuf, 0);
    }

    private float[] mRotationMatrix = new float[16];

    int mWidth, mHeight;

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        mWidth = width;
        mHeight = height;

        GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, frameBufs[0]);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, rendTex[0]);
        GLES30.glTexImage2D(GLES30.GL_TEXTURE_2D, 0, GLES30.GL_RGBA, width, height, 0, GLES30.GL_RGBA, GLES30.GL_UNSIGNED_BYTE, null);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE);
        GLES30.glFramebufferTexture2D(GLES30.GL_FRAMEBUFFER, GLES30.GL_COLOR_ATTACHMENT0, GLES30.GL_TEXTURE_2D, rendTex[0], 0);

        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, rendTex[1]);
        GLES30.glTexImage2D(GLES30.GL_TEXTURE_2D, 0, GLES30.GL_RG32I, width / 1, height / 1, 0, GLES30.GL_RG_INTEGER, GLES30.GL_INT, null);
//        GLES30.glTexImage2D(GLES30.GL_TEXTURE_2D, 0, GLES30.GL_RG8, width, height, 0, GLES30.GL_RG, GLES30.GL_INT, null); //rg16i is probably enough
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE);
        GLES30.glFramebufferTexture2D(GLES30.GL_FRAMEBUFFER, GLES30.GL_COLOR_ATTACHMENT1, GLES30.GL_TEXTURE_2D, rendTex[1], 0);

        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0);


        GLES30.glBindRenderbuffer(GLES30.GL_RENDERBUFFER, rendBuf[0]);
        GLES30.glRenderbufferStorage(GLES30.GL_RENDERBUFFER, GLES30.GL_DEPTH_COMPONENT16, width, height);
        GLES30.glFramebufferRenderbuffer(GLES30.GL_FRAMEBUFFER, GLES30.GL_DEPTH_ATTACHMENT, GLES30.GL_RENDERBUFFER, rendBuf[0]);

        GLES30.glDrawBuffers(2, new int[]{GLES30.GL_COLOR_ATTACHMENT0, GLES30.GL_COLOR_ATTACHMENT1}, 0);
//        GLES30.glDrawBuffers(1, new int[]{GLES30.GL_COLOR_ATTACHMENT0}, 0);

        Log.d("FBUF", Integer.toString(GLES30.glCheckFramebufferStatus(GL_FRAMEBUFFER)));
        Log.d("FBUF", Integer.toString(GLES30.GL_FRAMEBUFFER_COMPLETE));
        Log.d("FBUF", GLU.gluErrorString(GLES30.glGetError()));
        GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, 0);


        GLES30.glViewport(0, 0, mWidth, mHeight);
        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
//        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 1, 100);

//        mCyl.setInst(new int[]{
//                0, 0, 0,
//                0, 1, 1,
//                0, 2, 1,
//                1, 1, 0,
//                1, 0, 1, 2, 0, 1,
//                -1, -2, 1, 0, -2, 1
//        });
        mCyl.setInst(mHLG.getField(0, 1, 3));
    }

    public void onDrawFrame(GL10 gl) {


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
//        GLES30.glEnable(GLES30.GL_STENCIL_TEST);
//        GLES30.glStencilFunc(GLES30.GL_NEVER,1,1);
//        GLES30.glStencilFunc(GLES30.GL_ALWAYS, 1, 1);
//        GLES30.glStencilFunc();
//        int[] ixx = new int[10];
//        GLES30.glGetIntegerv(GLES30.GL_STENCIL_BITS, ixx, 0);
//        Log.d("stencbi", Integer.toString(ixx[0]));
//        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);

        GLES30.glBindFramebuffer(GL_FRAMEBUFFER, frameBufs[0]);
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        mCyl.draw(mMVPMatrix);

        GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, 0);
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);
        mQuad.draw(rendTex[0]);

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
