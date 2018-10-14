#version 330

layout (location = 0) in vec3 position;
layout (location = 2) in vec3 normals;

uniform mat4 projectionMatrix;
uniform mat4 worldMatrix;
uniform mat4 modelViewMatrix;
uniform vec3 color;
uniform vec3 sunPos;
uniform float useSun;

out vec3 outColor;
out float diff;

void main()
{
	gl_Position = projectionMatrix * worldMatrix * modelViewMatrix * vec4(position, 1.0);
	outColor = color;


    if (useSun > 0.5) {
	    diff = max(dot(normalize(sunPos - position), normalize(normals)), 0.3);
	} else {
	    diff = 1.0;
	    outColor = vec3(1.0, 0.0, 0.0);
	}
}