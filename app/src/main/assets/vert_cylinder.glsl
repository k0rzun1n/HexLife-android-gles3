#version 300 es
uniform mat4 uMVPMatrix;

layout(location = 0)in vec3 vPosition;
layout(location = 1)in vec3 vNorm;
layout(location = 2)in int vInstance;
//attribute vec4 vPosition;
out float light_intensity;
vec3 lightPos = vec3(-5,-5,10);
void main() {
    // the matrix must be included as a modifier of gl_Position
    // Note that the uMVPMatrix factor *must be first* in order
    // for the matrix multiplication product to be correct.
//    vertcol = vec4(1,0,1,1);
//    light_intensity = dot(vNorm , normalize(lightPos));// + vec4(0,0,0,1);
    light_intensity = float(vInstance);// + vec4(0,0,0,1);
    gl_Position = uMVPMatrix * vec4(vPosition,1);
//    pos = uMVPMatrix * vPosition;
}