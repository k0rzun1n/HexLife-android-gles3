package com.example.krz.android_opengl_t0;

import android.content.Context;
import android.opengl.GLES30;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
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
    protected int[] instance = null;
    protected short[] drawOrder = null;// = {0, 1, 2, 0, 2, 3}; // order to draw vertices
    protected final Context mContext;
    protected int mProgram;
    // Use to access and set the view transformation
    protected int mMVPMatrixHandle;
    protected FloatBuffer vertexBuffer;
    protected FloatBuffer normalBuffer;
    protected IntBuffer instBuffer;
    protected ShortBuffer drawListBuffer;
    protected int mPositionHandle;
    protected int mColorHandle;
    protected int mNormHandle;
    protected int mInstHandle;

    protected int[] bufs;

    public GameObject(Context context) {
        mContext = context;
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

        ByteBuffer ib = ByteBuffer.allocateDirect(
                // (# of coordinate values * 4 bytes per int)
                normals.length * 4);
        ib.order(nativeOrder());
        instBuffer = ib.asIntBuffer();
        instBuffer.put(instance);
        instBuffer.position(0);

        bufs = new int[3];
        GLES30.glGenBuffers(3, bufs, 0);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, bufs[0]);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, vertexBuffer.capacity()*4, vertexBuffer, GLES30.GL_STATIC_DRAW);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, bufs[1]);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, normalBuffer.capacity()*4, normalBuffer, GLES30.GL_STATIC_DRAW);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, bufs[2]);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, instBuffer.capacity()*4, instBuffer, GLES30.GL_STATIC_DRAW); //todo dyndraw?
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);
        vertexBuffer = null;
        normalBuffer = null;
        instBuffer = null;
        vertices = null;
//        GLES30.glDeleteBuffers(2, bufs, 0);
        // initialize byte buffer for the draw list
//        ByteBuffer dlb = ByteBuffer.allocateDirect(
//                // (# of coordinate values * 2 bytes per short)
//                drawOrder.length * 2);
//        dlb.order(nativeOrder());
//        drawListBuffer = dlb.asShortBuffer();
//        drawListBuffer.put(drawOrder);
//        drawListBuffer.position(0);


        int vertexShader = MyGLRenderer.loadShader(mContext, GLES30.GL_VERTEX_SHADER,
                vsFName);
        int fragmentShader = MyGLRenderer.loadShader(mContext, GLES30.GL_FRAGMENT_SHADER,
                fsFName);

        mProgram = GLES30.glCreateProgram();
        GLES30.glAttachShader(mProgram, vertexShader);
        GLES30.glAttachShader(mProgram, fragmentShader);
        // creates OpenGL ES program executables
        GLES30.glLinkProgram(mProgram);

        mPositionHandle = GLES30.glGetAttribLocation(mProgram, "vPosition");
        mNormHandle = GLES30.glGetAttribLocation(mProgram, "vNorm");
        mInstHandle = GLES30.glGetAttribLocation(mProgram, "vInstance");
        mColorHandle = GLES30.glGetUniformLocation(mProgram, "vColor");
        mMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");
    }
}
