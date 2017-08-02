package com.example.krz.android_opengl_t0;

import android.content.Context;
import android.opengl.GLES30;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static java.nio.ByteOrder.nativeOrder;

public class Cylinder {

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    protected final int vertexStride = Cylinder.COORDS_PER_VERTEX * 4; // 4 bytes per vertex
    protected int vertexCount;
    // Use to access and set the view transformation
    protected int mMVPMatrixHandle;
    protected FloatBuffer vertexBuffer;
    protected FloatBuffer normalBuffer;
    protected int mPositionHandle;
    protected int mColorHandle;
    protected int mNormHandle;
    protected int mInstHandle;
    protected int[] bufs;
    protected final Context mContext;
    protected float[] vertices = null;
    protected float[] normals = null;
    protected int[] instance = null;
    protected int mProgram;

    public Cylinder(Context context) {
        mContext = context;

        int sides = 6;
        float height = 1.0f;
        float width = 1.0f;
        double angle = 2 * Math.PI / sides;
        int sideVertAmount = 4 * 3 * 3;
//        int sideVertAmount = 3 * 3;
        vertices = new float[sides * sideVertAmount];
        normals = new float[sides * sideVertAmount];
        for (int i = 0; i < 6; i++) {
            vertices[i * sideVertAmount + 0] = (float) Math.cos(i * angle) * width;
            vertices[i * sideVertAmount + 1] = (float) Math.sin(i * angle) * width;
            vertices[i * sideVertAmount + 2] = height;
            vertices[i * sideVertAmount + 3] = (float) Math.cos((i + 1) * angle) * width;
            vertices[i * sideVertAmount + 4] = (float) Math.sin((i + 1) * angle) * width;
            vertices[i * sideVertAmount + 5] = height;
            vertices[i * sideVertAmount + 6] = 0;
            vertices[i * sideVertAmount + 7] = 0;
            vertices[i * sideVertAmount + 8] = height;

            normals[i * sideVertAmount + 0] = 0;
            normals[i * sideVertAmount + 1] = 0;
            normals[i * sideVertAmount + 2] = 1;
            normals[i * sideVertAmount + 3] = 0;
            normals[i * sideVertAmount + 4] = 0;
            normals[i * sideVertAmount + 5] = 1;
            normals[i * sideVertAmount + 6] = 0;
            normals[i * sideVertAmount + 7] = 0;
            normals[i * sideVertAmount + 8] = 1;

            vertices[i * sideVertAmount + 9 * 1 + 0] = (float) Math.cos(i * angle) * width;
            vertices[i * sideVertAmount + 9 * 1 + 1] = (float) Math.sin(i * angle) * width;
            vertices[i * sideVertAmount + 9 * 1 + 2] = height;
            vertices[i * sideVertAmount + 9 * 1 + 3] = (float) Math.cos((i + 1) * angle) * width;
            vertices[i * sideVertAmount + 9 * 1 + 4] = (float) Math.sin((i + 1) * angle) * width;
            vertices[i * sideVertAmount + 9 * 1 + 5] = -height;
            vertices[i * sideVertAmount + 9 * 1 + 6] = (float) Math.cos((i + 1) * angle) * width;
            vertices[i * sideVertAmount + 9 * 1 + 7] = (float) Math.sin((i + 1) * angle) * width;
            vertices[i * sideVertAmount + 9 * 1 + 8] = height;

            normals[i * sideVertAmount + 9 * 1 + 0] = (float) Math.cos((0.5 + (double) i) * angle);
            normals[i * sideVertAmount + 9 * 1 + 1] = (float) Math.sin((0.5 + (double) i) * angle);
            normals[i * sideVertAmount + 9 * 1 + 2] = 0;
            normals[i * sideVertAmount + 9 * 1 + 3] = (float) Math.cos((0.5 + (double) i) * angle);
            normals[i * sideVertAmount + 9 * 1 + 4] = (float) Math.sin((0.5 + (double) i) * angle);
            normals[i * sideVertAmount + 9 * 1 + 5] = 0;
            normals[i * sideVertAmount + 9 * 1 + 6] = (float) Math.cos((0.5 + (double) i) * angle);
            normals[i * sideVertAmount + 9 * 1 + 7] = (float) Math.sin((0.5 + (double) i) * angle);
            normals[i * sideVertAmount + 9 * 1 + 8] = 0;

            vertices[i * sideVertAmount + 9 * 2 + 0] = (float) Math.cos(i * angle) * width;
            vertices[i * sideVertAmount + 9 * 2 + 1] = (float) Math.sin(i * angle) * width;
            vertices[i * sideVertAmount + 9 * 2 + 2] = height;
            vertices[i * sideVertAmount + 9 * 2 + 3] = (float) Math.cos(i * angle) * width;
            vertices[i * sideVertAmount + 9 * 2 + 4] = (float) Math.sin(i * angle) * width;
            vertices[i * sideVertAmount + 9 * 2 + 5] = -height;
            vertices[i * sideVertAmount + 9 * 2 + 6] = (float) Math.cos((i + 1) * angle) * width;
            vertices[i * sideVertAmount + 9 * 2 + 7] = (float) Math.sin((i + 1) * angle) * width;
            vertices[i * sideVertAmount + 9 * 2 + 8] = -height;

            normals[i * sideVertAmount + 9 * 2 + 0] = (float) Math.cos((0.5 + (double) i) * angle);
            normals[i * sideVertAmount + 9 * 2 + 1] = (float) Math.sin((0.5 + (double) i) * angle);
            normals[i * sideVertAmount + 9 * 2 + 2] = 0;
            normals[i * sideVertAmount + 9 * 2 + 3] = (float) Math.cos((0.5 + (double) i) * angle);
            normals[i * sideVertAmount + 9 * 2 + 4] = (float) Math.sin((0.5 + (double) i) * angle);
            normals[i * sideVertAmount + 9 * 2 + 5] = 0;
            normals[i * sideVertAmount + 9 * 2 + 6] = (float) Math.cos((0.5 + (double) i) * angle);
            normals[i * sideVertAmount + 9 * 2 + 7] = (float) Math.sin((0.5 + (double) i) * angle);
            normals[i * sideVertAmount + 9 * 2 + 8] = 0;

            vertices[i * sideVertAmount + 9 * 3 + 0] = (float) Math.cos(i * angle) * width;
            vertices[i * sideVertAmount + 9 * 3 + 1] = (float) Math.sin(i * angle) * width;
            vertices[i * sideVertAmount + 9 * 3 + 2] = -height;
            vertices[i * sideVertAmount + 9 * 3 + 3] = 0;
            vertices[i * sideVertAmount + 9 * 3 + 4] = 0;
            vertices[i * sideVertAmount + 9 * 3 + 5] = -height;
            vertices[i * sideVertAmount + 9 * 3 + 6] = (float) Math.cos((i + 1) * angle) * width;
            vertices[i * sideVertAmount + 9 * 3 + 7] = (float) Math.sin((i + 1) * angle) * width;
            vertices[i * sideVertAmount + 9 * 3 + 8] = -height;

            normals[i * sideVertAmount + 9 * 3 + 0] = 0;
            normals[i * sideVertAmount + 9 * 3 + 1] = 0;
            normals[i * sideVertAmount + 9 * 3 + 2] = -1;
            normals[i * sideVertAmount + 9 * 3 + 3] = 0;
            normals[i * sideVertAmount + 9 * 3 + 4] = 0;
            normals[i * sideVertAmount + 9 * 3 + 5] = -1;
            normals[i * sideVertAmount + 9 * 3 + 6] = 0;
            normals[i * sideVertAmount + 9 * 3 + 7] = 0;
            normals[i * sideVertAmount + 9 * 3 + 8] = -1;
        }

        //ct -st 0   x
        //st  ct 0 * y
        //0   0  1   z
        float ct = (float) Math.cos(Math.PI / 6);
        float st = (float) Math.sin(Math.PI / 6);

        float nx,ny;
        for (int i = 0; i < vertices.length; i += 3) {
            nx = vertices[i] * ct - vertices[i + 1] * st;
            ny = vertices[i] * st + vertices[i + 1] * ct;
            vertices[i] = nx;
            vertices[i + 1] = ny;
            nx = normals[i] * ct - normals[i + 1] * st;
            ny = normals[i] * st + normals[i + 1] * ct;
            normals[i] = nx;
            normals[i + 1] = ny;
        }
        init("vert_cylinder.glsl", "frag_cylinder.glsl");


    }


    private ByteBuffer ib;
    protected IntBuffer instBuffer;

    public void setInst(int[] ins) {
//        instance = ins;

        if (instBuffer == null || instBuffer.capacity() < ins.length) {
            // (# of coordinate values * 4 bytes per int)
            ib = ByteBuffer.allocateDirect(ins.length * 4);
            ib.order(nativeOrder());
            instBuffer = ib.asIntBuffer();
        }
        instBuffer.put(ins);
        instBuffer.position(0);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, bufs[2]);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, instBuffer.capacity() * 4, instBuffer, GLES30.GL_STATIC_DRAW); //todo dyndraw?
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);
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

        bufs = new int[3];
        GLES30.glGenBuffers(3, bufs, 0);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, bufs[0]);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, vertexBuffer.capacity() * 4, vertexBuffer, GLES30.GL_STATIC_DRAW);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, bufs[1]);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, normalBuffer.capacity() * 4, normalBuffer, GLES30.GL_STATIC_DRAW);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);
        vertexBuffer = null;
        normalBuffer = null;
        instBuffer = null;
        vertices = null;
//        GLES30.glDeleteBuffers(2, bufs, 0);

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


    public void draw(float[] mvpMatrix) {
        // Add program to OpenGL ES environment
        GLES30.glUseProgram(mProgram);

        GLES30.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, bufs[0]);
        GLES30.glEnableVertexAttribArray(mPositionHandle);
        GLES30.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES30.GL_FLOAT, false,
//                vertexStride, vertexBuffer);
                vertexStride, 0);


        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, bufs[1]);
        GLES30.glEnableVertexAttribArray(mNormHandle);
        GLES30.glVertexAttribPointer(mNormHandle, COORDS_PER_VERTEX,
                GLES30.GL_FLOAT, false,
//                vertexStride, normalBuffer);
                vertexStride, 0);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, bufs[2]);
        GLES30.glEnableVertexAttribArray(mInstHandle);
        GLES30.glVertexAttribPointer(mInstHandle, 3,
                GLES30.GL_INT, false,
//                vertexStride, normalBuffer);
                3 * 4, 0);

        GLES30.glVertexAttribDivisor(mInstHandle, 1);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);
        float[] color = {1.0f, 0.0f, 0.0f, 1.0f};


        GLES30.glUniform4fv(mColorHandle, 1, color, 0);
//        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vertexCount);
        GLES30.glDrawArraysInstanced(GLES30.GL_TRIANGLES, 0, vertexCount, instBuffer.capacity() / 3);
//        GLES30.glDrawElements(GLES30.GL_TRIANGLES, drawOrder.length, GLES30.GL_UNSIGNED_SHORT, drawListBuffer);


        GLES30.glDisableVertexAttribArray(mPositionHandle);
        GLES30.glDisableVertexAttribArray(mNormHandle);
        GLES30.glDisableVertexAttribArray(mInstHandle);

    }
}