#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_uv;
uniform sampler2D u_texture;
uniform vec2 pos;

void main() {
    vec4 col = texture2D(u_texture, v_uv);

    vec2 screenPos = v_uv * vec2(640.0, 480.0);

    if (length(screenPos - pos) > 100.0) {
        col = vec4(0.0, 0.0, 0.0, 1.0);
    }

    gl_FragColor = col;
}
