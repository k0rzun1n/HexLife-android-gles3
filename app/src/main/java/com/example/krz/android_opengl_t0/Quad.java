package com.example.krz.android_opengl_t0;

import android.content.Context;
import android.opengl.GLES30;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static java.nio.ByteOrder.nativeOrder;

public class Quad {
    private final Context mContext;
    protected float[] vertices = null;
    protected int mProgram;
    protected FloatBuffer vertexBuffer;
    protected int mPositionHandle;

    protected int[] bufs;

    public Quad(Context context) {
        mContext = context;

        vertices = new float[]{
                -1.0f, -1.0f, 0.0f,
                1.0f, -1.0f, 0.0f,
                -1.0f,  1.0f, 0.0f,
                -1.0f,  1.0f, 0.0f,
                1.0f, -1.0f, 0.0f,
                1.0f,  1.0f, 0.0f,
        };

        init("vert_passthrough.glsl", "frag_quad.glsl");

    }

    protected void init(String vsFName, String fsFName) {

        // initialize vertex byte buffer for shape coordinates
        ByteBuffer vb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 4 bytes per float)
                vertices.length * 4);
        vb.order(nativeOrder());
        vertexBuffer = vb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        bufs = new int[1];
        GLES30.glGenBuffers(1, bufs, 0);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, bufs[0]);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, vertexBuffer.capacity()*4, vertexBuffer, GLES30.GL_STATIC_DRAW);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);
        vertexBuffer = null;

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
    }

    public void draw() {
        // Add program to OpenGL ES environment
        GLES30.glUseProgram(mProgram);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, bufs[0]);
        GLES30.glEnableVertexAttribArray(mPositionHandle);
        GLES30.glVertexAttribPointer(mPositionHandle, 3,
                GLES30.GL_FLOAT, false,
//                3*4, vertexBuffer);
                3*4, 0);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 6);

        GLES30.glDisableVertexAttribArray(mPositionHandle);

    }

}