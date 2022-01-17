package jox3d.sandbox;
import jox3d.math.*;
import jox3d.rendering.*;
import java.nio.*;
import static org.lwjgl.opengl.GL41.*;
import org.lwjgl.system.*;

public class RenderingWindow extends Window {
  float[] vertices = new float[] { -0.5f, -0.5f, -0.5f,
                                    0.5f, -0.5f, -0.5f,
                                    0.5f,  0.5f, -0.5f,
                                    0.5f,  0.5f, -0.5f,
                                    -0.5f,  0.5f, -0.5f,
                                    -0.5f, -0.5f, -0.5f,

                                    -0.5f, -0.5f,  0.5f,
                                    0.5f, -0.5f,  0.5f,
                                    0.5f,  0.5f,  0.5f,
                                    0.5f,  0.5f,  0.5f,
                                    -0.5f,  0.5f,  0.5f,
                                    -0.5f, -0.5f,  0.5f,

                                    -0.5f,  0.5f,  0.5f,
                                    -0.5f,  0.5f, -0.5f,
                                    -0.5f, -0.5f, -0.5f,
                                    -0.5f, -0.5f, -0.5f,
                                    -0.5f, -0.5f,  0.5f,
                                    -0.5f,  0.5f,  0.5f,

                                    0.5f,  0.5f,  0.5f,
                                    0.5f,  0.5f, -0.5f,
                                    0.5f, -0.5f, -0.5f,
                                    0.5f, -0.5f, -0.5f,
                                    0.5f, -0.5f,  0.5f,
                                    0.5f,  0.5f,  0.5f,

                                    -0.5f, -0.5f, -0.5f,
                                    0.5f, -0.5f, -0.5f,
                                    0.5f, -0.5f,  0.5f,
                                    0.5f, -0.5f,  0.5f,
                                    -0.5f, -0.5f,  0.5f,
                                    -0.5f, -0.5f, -0.5f,
                                    
                                    -0.5f,  0.5f, -0.5f,
                                    0.5f,  0.5f, -0.5f,
                                    0.5f,  0.5f,  0.5f,
                                    0.5f,  0.5f,  0.5f,
                                    -0.5f,  0.5f,  0.5f,
                                    -0.5f,  0.5f, -0.5f };
  
  int vao = 0;                              
  int vbo = 0;
  Shader lightingShader = new Shader();

  public RenderingWindow() {
    super(1240, 720, "Rendering Window");
  }

  @Override
  public void onStart() {
    // Vertex array object (VAO)
    vao = glGenVertexArrays();
    glBindVertexArray(vao);

    FloatBuffer verticesBuffer = MemoryUtil.memAllocFloat(vertices.length);
    verticesBuffer.put(vertices).flip();

    // Vertex buffer object (VBO)
    vbo = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, vbo);
    glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

    // Vertex attributes
    glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0); // positions (x, y, z)
    glEnableVertexAttribArray(0);

    lightingShader.loadShader(ShaderType.Vertex, "assets/shaders/lighting_shader.vert");
    lightingShader.loadShader(ShaderType.Fragment, "assets/shaders/lighting_shader.frag");
    lightingShader.createProgram();
    lightingShader.setFloat("color", 0.5f);
  }

  @Override
  public void onUpdate() {
    Matrix4x4 matrix = new Matrix4x4(1.0f, 0.0f, 0.0f, 0.0f,
                                     0.0f, 1.0f, 0.0f, 0.0f,
                                     0.0f, 0.0f, 1.0f, 0.0f,
                                     0.0f, 0.0f, 0.0f, 1.0f);

    lightingShader.setMatrix4x4("projMatrix", matrix);
  }

  @Override
  public void onRender() {
    lightingShader.bind();
    glBindVertexArray(vao);
    glDrawArrays(GL_TRIANGLES, 0, 36);
  }
}
