#version 330

layout (location = 0) in vec3 position;
layout (location = 2) in vec3 normals;

uniform mat4 projectionMatrix;
uniform mat4 worldMatrix;
uniform mat4 modelViewMatrix;
uniform vec3 color;
uniform vec3 sunPos;
uniform float useSun;
uniform float useFog;

out vec3 outColor;
out float diff;
out float visibility;

const float fogDensity = 0.007;
const float gradient = 1.5;

void main()
{
    vec4 posRelativeToCam = worldMatrix * modelViewMatrix * vec4(position, 1.0);
	gl_Position = projectionMatrix * posRelativeToCam;
	outColor = color;


    if (useSun > 0.5) {
	    diff = max(dot(normalize(sunPos - position), normalize(normals)), 0.3);
	} else {
	    diff = 1.0;
	    outColor = vec3(1.0, 0.0, 0.0);
	}

	if (useFog > 0.5) {
        float distance = length(posRelativeToCam.xyz);
        visibility = exp(-pow((distance * fogDensity), gradient));
        visibility = clamp(visibility, 0.0, 1.0);
	}
}