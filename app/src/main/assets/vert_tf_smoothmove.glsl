#version 300 es

layout(location = 0) in int vState;
layout(location = 1) in float vHeight;
out float newHeight;

void main() {
    newHeight = vHeight+0.01*(float(vState));
}