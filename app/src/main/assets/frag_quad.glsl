#version 300 es
precision mediump float;
//layout(location=1) in vec4 col;
in vec2 UV;
out vec3 color;
//
uniform sampler2D renderedTexture;
//uniform float time;

void main(){
//    color = texture( renderedTexture, UV + 0.005*vec2( sin(time+1024.0*UV.x),cos(time+768.0*UV.y)) ).xyz;

////      change to texelfetch and make more samples
//    color = texture( renderedTexture, UV).xyz; //works
//    vec3 bloom[4];
//    bloom[0] = texture( renderedTexture, UV+vec2(0.01,0.01) ).xyz;
//    bloom[1] = texture( renderedTexture, UV+vec2(-0.01,-0.01) ).xyz;
//    bloom[2] = texture( renderedTexture, UV+vec2(0.01,-0.01) ).xyz;
//    bloom[3] = texture( renderedTexture, UV+vec2(-0.01,0.01) ).xyz;
//    vec3 combinedBloom = vec3(0,0,0);
//    for(int i=0;i<4;i++){
//        if(length(bloom[i])>0.5){
//            combinedBloom += 0.2*bloom[i];
//        }
//    }
//    color = texture( renderedTexture, UV).xyz  + combinedBloom;

    color = texture( renderedTexture, UV).xyz;
}