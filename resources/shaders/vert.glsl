#version 330 

layout (location = 0) in vec3 position;
layout (location = 1) in vec3 color;
layout (location = 2) in vec3 normal;

out vec3 m_vertexNormal;
out vec3 m_color;

uniform mat4 projection_matrix;
uniform mat4 view_matrix;
uniform mat4 model_matrix;

void main()
{
	m_color = color;
	gl_Position = projection_matrix * view_matrix * model_matrix * vec4(position, 1.0);
}