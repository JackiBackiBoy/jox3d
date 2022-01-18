#version 330 core
in vec3 Normal;

out vec4 FragColor;

uniform float color;

void main()
{
  FragColor = vec4(color, 0.0f, 0.0f, 1.0f);
}
