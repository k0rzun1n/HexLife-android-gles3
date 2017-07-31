#version 300 es
precision mediump float;
//layout(location=1) in vec4 col;
//in vec2 UV;
out vec3 color;
//
//uniform sampler2D renderedTexture;
//uniform float time;

void main(){
//    color = texture( renderedTexture, UV + 0.005*vec2( sin(time+1024.0*UV.x),cos(time+768.0*UV.y)) ).xyz;
    color = vec3(1,1,1);
}