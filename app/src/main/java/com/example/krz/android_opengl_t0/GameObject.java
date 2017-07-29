package com.example.krz.android_opengl_t0;

import android.content.Context;
import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import static java.nio.ByteOrder.nativeOrder;

/**
 * Created by krz on 24-Jul-17.
 */

public abstract class GameObject {
    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    protected int vertexCount;
    protected final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex
    protected float[] vertices = null;
    protected float[] normals = null;
    protected short[] drawOrder = null;// = {0, 1, 2, 0, 2, 3}; // order to draw vertices
    protected final Context mContext;
    protected final int mProgram;
    // Use to access and set the view transformation
    protected int mMVPMatrixHandle;
    protected FloatBuffer vertexBuffer;
    protected FloatBuffer normalBuffer;
    protected ShortBuffer drawListBuffer;
    protected int mPositionHandle;
    protected int mColorHandle;
    protected int mNormHandle;

    public GameObject(Context context) {

        mContext = context;
        mProgram = GLES20.glCreateProgram();


    }

    protected void init(String vsFName, String fsFName) {
        vertexCount = vertices.length / COORDS_PER_VERTEX;

        // initialize vertex byte buffer for shape coordinates
        ByteBuffer vb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 4 bytes per float)
                vertices.length * 4);
        vb.order(nativeOrder());
        vertexBuffer = vb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        ByteBuffer nb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 4 bytes per float)
                normals.length * 4);
        nb.order(nativeOrder());
        normalBuffer = nb.asFloatBuffer();
        normalBuffer.put(normals);
        normalBuffer.position(0);

        // initialize byte buffer for the draw list
//        ByteBuffer dlb = ByteBuffer.allocateDirect(
//                // (# of coordinate values * 2 bytes per short)
//                drawOrder.length * 2);
//        dlb.order(nativeOrder());
//        drawListBuffer = dlb.asShortBuffer();
//        drawListBuffer.put(drawOrder);
//        drawListBuffer.position(0);


        int vertexShader = MyGLRenderer.loadShader(mContext, GLES20.GL_VERTEX_SHADER,
                "vert_cylinder.glsl");
        int fragmentShader = MyGLRenderer.loadShader(mContext, GLES20.GL_FRAGMENT_SHADER,
                "frag_cylinder.glsl");

        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        // creates OpenGL ES program executables
        GLES20.glLinkProgram(mProgram);

        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        mNormHandle = GLES20.glGetAttribLocation(mProgram, "vNorm");
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
    }
}
