#version 120

varying vec2 v_uv;

uniform sampler2D u_texture;
uniform float u_time;
uniform float u_invulnerable;

const vec3 GOLD = vec3(1.0, 0.843, 0.0);
const vec3 PLATINUM = vec3(0.898, 0.894, 0.886);

void main()
{
    vec4 col = texture2D(u_texture, v_uv);

    vec3 v_col = mix(
    GOLD,
    PLATINUM,
    sin(u_time * 6.0) * 0.5 + 0.5
    );

    col.rgb = mix(col.rgb, v_col, 0.75 * u_invulnerable);

    gl_FragColor = col;
}
