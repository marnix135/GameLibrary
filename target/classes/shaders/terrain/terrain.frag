#version 330

in vec3 outColor;
in float diff;
in float visibility;

uniform vec3 fogColor;

out vec4 fragColor;

void main()
{
    fragColor = mix(vec4(fogColor, 1.0), vec4(outColor, 1.0) * diff, visibility);
}