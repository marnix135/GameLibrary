#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texCoords;
layout (location = 2) in vec3 normals;

uniform mat4 projectionMatrix;
uniform mat4 worldMatrix;
uniform mat4 modelViewMatrix;
uniform vec3 color;

out vec3 outColor;
out vec2 outTexCoords;
out vec3 outNormals;

void main()
{
	gl_Position = projectionMatrix * worldMatrix * modelViewMatrix * vec4(position, 1.0);

	outColor = color;
	outTexCoords = texCoords;
	outNormals = normals;
}