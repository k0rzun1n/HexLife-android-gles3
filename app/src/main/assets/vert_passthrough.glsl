#version 300 es
layout(location = 0)in vec2 vPosition;
layout(location = 1)in vec2 vUV;
out vec2 UV;
void main() {
//    vec2 uout = vec2(vPosition.x,vPosition.y);
//    vec2 uout = vec2(0.1,255);
    UV = vUV;
    gl_Position = vec4(vPosition,0,1);
}