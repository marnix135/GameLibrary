#version 330

in vec3 outColor;
in float diff;

out vec4 fragColor;

void main()
{
    fragColor = vec4(outColor, 1.0) * diff;
}