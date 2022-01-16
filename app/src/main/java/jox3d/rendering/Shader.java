package jox3d.rendering;

import jox3d.math.*;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

import static org.lwjgl.opengl.GL46.*;

public class Shader {
  private Map<ShaderType, Integer> shaderIDs = new HashMap<ShaderType, Integer>();
  private int m_ID = 0;

  public void bind() {
    glUseProgram(m_ID);
  }

  public void loadShader(ShaderType shaderType, String filePath) {
    // Check if file extension is valid for the given shader file, else ignore loading the shader
    try {
      File shaderFile = new File(filePath);
      Scanner fileReader = new Scanner(shaderFile);
      String fileContents = "";

      // read each line from the shader file
      while (fileReader.hasNextLine()) {
        String line = fileReader.nextLine();
        fileContents += line;
      }
      
      shaderIDs.put(shaderType, glCreateShader(shaderType.typeID));
      glShaderSource(shaderIDs.get(shaderType), fileContents);
      glCompileShader(shaderIDs.get(shaderType));

      fileReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("Error: file at path " + filePath + " not found!");
      e.printStackTrace();
    }
  }      

  public void createProgram() {
    m_ID = glCreateProgram();

    //shaderIDs.foreach((k, v) => glAttachShader(id, v)) // attach all shaders
    glLinkProgram(m_ID); // unite all attached shaders into one program
    //shaderIDs.foreach((k, v) => glDeleteShader(v)) // deallocate the standalone shaders
  }

  public void setFloat(String uniformName, float value) {
    bind();
    glUniform1f(glGetUniformLocation(m_ID, uniformName), value);
  }

  public void setMatrix4x4(String uniformName, Matrix4x4 value) {
    bind();
    //glUniformMatrix4fv(glGetUniformLocation(m_ID, uniformName), false, value.data)
  }
}