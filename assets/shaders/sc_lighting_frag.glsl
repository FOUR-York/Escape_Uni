#version 120

varying vec2 v_uv;

uniform sampler2D u_texture;

uniform vec3 u_vecs[10];
uniform int u_length;
uniform int u_active;

const vec4 BLACK = vec4(0.0, 0.0, 0.0, 1.0);

void main()
{
    vec4 tex_in = texture2D(u_texture, v_uv);
    vec4 out_col = tex_in;

    if (u_active > 0)
    {
        out_col = BLACK;

        vec2 screenPos = v_uv * vec2(640.0, 480.0);
        float least_p = 1.0;

        for (int i = 0; i < 10; i++)
        {
            if (i >= u_length)
            break;

            float len = length(screenPos - u_vecs[i].rg);
            float radius = u_vecs[i].b;

            if (len < radius)
            {
                float p = len / radius;
                if (p < least_p)
                {
                    least_p = p;
                    out_col.rgb = mix(
                    tex_in.rgb,
                    BLACK.rgb,
                    max(0.5, smoothstep(0.5, 1.0, least_p))
                    );
                }
            }
        }
    }

    gl_FragColor = out_col;
}
