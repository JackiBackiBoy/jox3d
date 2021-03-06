package jox3d.rendering;

import jox3d.math.*;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

import static org.lwjgl.opengl.GL41.*;

public class Shader {
  private Map<ShaderType, Integer> m_ShaderIDs = new HashMap<ShaderType, Integer>();
  private int m_ID = 0;

  public void bind() {
    glUseProgram(m_ID);
  }

  public void loadShader(ShaderType shaderType, String filePath) {
    // Check if file extension is valid for the given shader file, else ignore loading the shader
    try {
      File shaderFile = new File(filePath).getAbsoluteFile();
      Scanner fileReader = new Scanner(shaderFile);
      String fileContents = "";

      // read each line from the shader file
      while (fileReader.hasNextLine()) {
        String line = fileReader.nextLine() + "\n";
        fileContents += line;
      }
      
      m_ShaderIDs.put(shaderType, glCreateShader(shaderType.typeID));
      glShaderSource(m_ShaderIDs.get(shaderType), fileContents);
      glCompileShader(m_ShaderIDs.get(shaderType));

      fileReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("Error: file at path " + filePath + " not found!");
      e.printStackTrace();
    }
  }      

  public void createProgram() {
    m_ID = glCreateProgram();

    for (var shader : m_ShaderIDs.entrySet()) { glAttachShader(m_ID, shader.getValue()); } // attach all shaders
    glLinkProgram(m_ID); // unite all attached shaders into one program
    for (var shader : m_ShaderIDs.entrySet()) { shader.getValue(); } // delete all shaders
  }

  public void setFloat(String uniformName, float value) {
    bind();
    glUniform1f(glGetUniformLocation(m_ID, uniformName), value);
  }

  public void setVec3(String uniformName, Vector3D vector) {
    bind();
    glUniform3f(glGetUniformLocation(m_ID, uniformName), vector.x, vector.y, vector.z);
  }

  public void setMatrix4x4(String uniformName, Matrix4x4 value) {
    bind();
    glUniformMatrix4fv(glGetUniformLocation(m_ID, uniformName), false, value.getFlatData());
  }
}
