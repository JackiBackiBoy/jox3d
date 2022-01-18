#version 330 core
in vec3 Normal;
in vec3 FragPos;

out vec4 FragColor;

uniform vec3 color;

vec3 lightDir = vec3(0.2f, 0.8f, 0.5f);
float ambient = 0.2f;

float calcDiffuse()
{
  vec3 normLightDir = normalize(lightDir);
  float intensity = max(dot(Normal, normLightDir), 0.0);
  
  return intensity;
}

void main()
{
  vec3 result = (ambient + calcDiffuse()) * color;
  FragColor = vec4(result, 1.0f);
}
