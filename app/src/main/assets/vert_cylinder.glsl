#version 300 es
uniform mat4 uMVPMatrix;

layout(location = 0)in vec3 vPosition;
layout(location = 1)in vec3 vNorm;
layout(location = 2)in ivec3 vInstance;//x,y,state
//attribute vec4 vPosition;
uniform uint vTime;
out float light_intensity;
out vec4 fColor;
flat out ivec2 fGridCoord;
vec3 lightPos = vec3(-5,-5,10);
float cellRadius = 1.1;

float cubicInOut(float t) {
  return t < 0.5
    ? 4.0 * t * t * t
    : 0.5 * pow(2.0 * t - 2.0, 3.0) + 1.0;
}
float cubicIn(float t) {
  return t * t * t;
}
float cubicOut(float t) {
  float f = t - 1.0;
  return f * f * f + 1.0;
}

void main() {
    fGridCoord = ivec2(vInstance[0],vInstance[1]);//pack alive somewhere to add fx in post?
//    float alive = float(vInstance[2]&1);
    bool alive = (vInstance[2]&1)==1;

    uint timeState = uint(((vInstance[2])>>8) & ((1<<24) - 1));
    uint timeDiff = vTime - timeState;
    float vTransition = float(clamp(timeDiff,0U,1000U))/1000.0;//[0,1]

//    vTransition = cubicIn(vTransition);
    vTransition = cubicInOut(vTransition);
//    vTransition = cubicOut(vTransition);

    vec4 deadColor = vec4(0.0,0.0,0.5,1.0);
    fColor = alive?
//    mix(deadColor,vec4(0.0,1.0,0.0,1.0),vTransition)
    mix(vec4(0.0,0.1,0.2,1.0),vec4(0.0,1.0,0.0,1.0),vTransition)
    :mix(vec4(1.0,0.0,0.0,1.0),deadColor,vTransition);
    float xc = cellRadius * sqrt(3.0);
    float yc = cellRadius * 1.5;
    // the matrix must be included as a modifier of gl_Position
    // Note that the uMVPMatrix factor *must be first* in order
    // for the matrix multiplication product to be correct.
    bool mark = (
    vInstance[0]%5 == 0
    ||vInstance[1]%5 == 0);
    light_intensity = (mark ? 0.6 : 1.0) *dot(vNorm , normalize(lightPos));// + vec4(0,0,0,1);
//    light_intensity = vInstance;// + vec4(0,0,0,1);
    gl_Position = uMVPMatrix * (vec4(vPosition,1)
                              + vec4(
                                    //xc*(float(vInstance[0]) + 0.5*float(abs(vInstance[1])%2))
                                     xc*(float(vInstance[0]) + 0.5*float(abs(vInstance[1])%2))
                                    ,yc*float(vInstance[1])
                                    ,alive ? vTransition : (1.0 - vTransition)
                                    ,0));
}