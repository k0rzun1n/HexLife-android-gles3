#version 300 es
precision mediump float;
//uniform vec4 vColor;
//layout(location=1) in vec4 col;
in float light_intensity;
in vec4 fColor;
layout (location=0) out vec4 col;
//layout (location=1) out ivec2 gridCoord;
void main() {
//  gl_FragColor = vColor;
//  col = vec4(1,1,1,1)*0.1;
//  col = vec4(vertcol,1);
  col = fColor * light_intensity;
}