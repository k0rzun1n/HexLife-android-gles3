package com.example.krz.android_opengl_t0;

import android.content.Context;
import android.opengl.GLES20;

public class Cylinder extends GameObject {

    public Cylinder(Context context) {
        super(context);

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


        init("vert_cylinder.glsl", "frag_cylinder.glsl");


    }

    public void draw(float[] mvpMatrix) {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram);

        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        GLES20.glEnableVertexAttribArray(mNormHandle);
        GLES20.glVertexAttribPointer(mNormHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, normalBuffer);

        float[] color = {1.0f, 0.0f, 0.0f, 1.0f};

        GLES20.glUniform4fv(mColorHandle, 1, color, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
//        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        

        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mNormHandle);

    }
}