#version 330

in vec3 m_color;
in vec3 m_vertexNormal;

out vec4 fragColor;

void main()
{
	fragColor = vec4(m_color, 1.0);
}