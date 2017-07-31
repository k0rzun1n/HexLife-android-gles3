package com.example.krz.android_opengl_t0;

import android.content.Context;
import android.opengl.GLES30;

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

        instance = new int[sides * sideVertAmount / 3];
        for (int i = 0; i < sides * sideVertAmount / 3; i++) {
            instance[i] = 5;
        }

        init("vert_cylinder.glsl", "frag_cylinder.glsl");


    }

    public void setInst(int ins) {
        for (int i = 0; i < instance.length; i++) {
            instance[i] = ins;
        }
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
        GLES30.glVertexAttribPointer(mInstHandle, 1,
                GLES30.GL_INT, false,
//                vertexStride, normalBuffer);
                1*4, 0);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);
        float[] color = {1.0f, 0.0f, 0.0f, 1.0f};


        GLES30.glUniform4fv(mColorHandle, 1, color, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vertexCount);
//        GLES30.glDrawElements(GLES30.GL_TRIANGLES, drawOrder.length, GLES30.GL_UNSIGNED_SHORT, drawListBuffer);


        GLES30.glDisableVertexAttribArray(mPositionHandle);
        GLES30.glDisableVertexAttribArray(mNormHandle);
        GLES30.glDisableVertexAttribArray(mInstHandle);

    }
}