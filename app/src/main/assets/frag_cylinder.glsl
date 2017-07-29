#version 300 es
precision mediump float;
uniform vec4 vColor;
//layout(location=1) in vec4 col;
in float light_intensity;
out vec4 col;
void main() {
//  gl_FragColor = vColor;
//  col = vec4(1,1,1,1)*0.1;
//  col = vec4(vertcol,1);
  col = vColor * light_intensity;
}