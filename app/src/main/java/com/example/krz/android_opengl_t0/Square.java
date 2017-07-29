package com.example.krz.android_opengl_t0;

import android.content.Context;
import android.opengl.GLES20;

public class Square extends GameObject {

    public Square(Context context) {
        super(context);
//        vertices = new float[]{
//                -0.5f, 0.5f, 0.0f,   // top left
//                -0.5f, -0.5f, 0.0f,   // bottom left
//                0.5f, -0.5f, 0.0f,   // bottom right
//                0.5f, 0.5f, 0.0f};
//        vertices = new float[]{
//                -0.5f, 0.5f, 0.0f,   // top left
//                -0.5f, -0.5f, 0.0f,   // bottom left
//                0.5f, -0.5f, 0.0f,   // bottom right
//                -0.5f, 0.5f, 0.0f,   // top left
//                0.5f, -0.5f, 0.0f,   // bottom right
//                0.5f, 0.5f, 0.0f
//        };
        vertices = new float[]{
                -0.5f, 0.5f, 0.5f,   // top left
                -0.5f, -0.5f, 0.5f,   // bottom left
                0.5f, -0.5f, 0.5f,   // bottom right
                -0.5f, 0.5f, 0.5f,   // top left
                0.5f, -0.5f, 0.5f,   // bottom right
                0.5f, 0.5f, 0.5f,

                0.5f, 0.5f, 0.5f,   // top left
                0.5f, 0.5f, -0.5f,   // bottom left
                0.5f, -0.5f, -0.5f,   // bottom right
                0.5f, 0.5f, 0.5f,   // top left
                0.5f, -0.5f, -0.5f,   // bottom right
                0.5f, -0.5f, 0.5f
        };
//        drawOrder = new short[]{0, 1, 2, 0, 2, 3};

        init("vert_cylinder.glsl","frag_cylinder.glsl");


    }

    public void draw(float[] mvpMatrix) {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram);

        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        float[] color = {1.0f, 0.0f, 0.0f, 1.0f};

        GLES20.glUniform4fv(mColorHandle, 1, color, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0, vertexCount);
//        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        GLES20.glDisableVertexAttribArray(mPositionHandle);

    }
}