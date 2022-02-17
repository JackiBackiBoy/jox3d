#version 330 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec3 aNormal;

out vec3 Normal;
out vec3 FragPos;

uniform mat4 projMatrix;
uniform mat4 viewMatrix;

void main()
{
  Normal = aNormal;
  FragPos = aPos;
  gl_Position = projMatrix * vec4(aPos, 1.0);
}
