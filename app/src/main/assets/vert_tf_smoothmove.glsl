#version 300 es

layout(location = 0) in int vState;
layout(location = 1) in float vTransition;
out float newHeight;

//float lerp(){}

void main() {
    int alive = vState & 1;

//    newHeight = vTransition+0.01*(float(vState));
    newHeight = mix(vTransition,float(alive),0.05);
//    newHeight = (float(alive));
}