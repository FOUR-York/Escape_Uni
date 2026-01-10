#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_uv;
uniform sampler2D u_texture;

uniform vec3[10] u_vecs;
uniform int u_length;

uniform int u_active;

void main() {
    vec4 tex_in = texture2D(u_texture, v_uv);
    vec4 col = vec4(0.0, 0.0, 0.0, 1.0);
    vec4 out_col = col;

    if (u_active > 0) {
        vec2 screenPos = v_uv * vec2(640.0, 480.0);
        float least_p = 1.0;
        for (int i = 0; i < u_length; i++) {
            float len = length(screenPos - u_vecs[i].rg);
            float radius = u_vecs[i].b;
            if (len < radius && len/radius < least_p) {
                least_p = len/radius;
                out_col.rgb = mix(tex_in.rgb, col.rgb, max(0.5, smoothstep(0.5, 1.0, least_p)));
            }
        }
    } else {
        out_col = tex_in;
    }

    gl_FragColor = out_col;
}
