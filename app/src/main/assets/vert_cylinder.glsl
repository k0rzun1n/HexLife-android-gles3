#version 300 es
uniform mat4 uMVPMatrix;

layout(location = 0)in vec3 vPosition;
layout(location = 1)in vec3 vNorm;
layout(location = 2)in ivec3 vInstance;//x,y,state
layout(location = 3)in float vHeight;
//attribute vec4 vPosition;
out float light_intensity;
vec3 lightPos = vec3(-5,-5,10);
float cellRadius = 1.1;

void main() {
    float xc = cellRadius * sqrt(3.0);
    float yc = cellRadius * 1.5;
    // the matrix must be included as a modifier of gl_Position
    // Note that the uMVPMatrix factor *must be first* in order
    // for the matrix multiplication product to be correct.
//    vertcol = vec4(1,0,1,1);
    light_intensity = dot(vNorm , normalize(lightPos));// + vec4(0,0,0,1);
//    light_intensity = vInstance;// + vec4(0,0,0,1);
//    gl_Position = uMVPMatrix *(vInstance[0]*xTranslate)*(vInstance[1]*yTranslate)* vec4(vPosition,1);
    gl_Position = uMVPMatrix * (vec4(vPosition,1)
                              + vec4(xc*(float(vInstance[0]) + 0.5*float(abs(vInstance[1])%2))
                                    ,yc*float(vInstance[1])
                                    ,vHeight
                                    ,0));
//    gl_Position = uMVPMatrix *(vec4(vPosition,1)+vec4(float(vInstance.x),float(vInstance.y),0,0));
//    gl_Position = uMVPMatrix * (vec4(vPosition,1) + vec4(0,gl_InstanceID,0,0));
//    gl_Position = uMVPMatrix * vec4(vPosition,1);
//    pos = uMVPMatrix * vPosition;
}