package jox3d.sandbox;
import jox3d.math.*;
import jox3d.rendering.*;
import java.nio.*;
import static org.lwjgl.opengl.GL41.*;
import org.lwjgl.system.*;

public class RenderingWindow extends Window {
  int vao = 0;                              
  int vbo = 0;
  Shader lightingShader = new Shader();
  Mesh cube = new Mesh();

  public RenderingWindow() {
    super(1240, 720, "Rendering Window");
  }

  @Override
  public void onStart() {
    cube.loadFromFile("assets/models/cube.obj");

    // Vertex array object (VAO)
    vao = glGenVertexArrays();
    glBindVertexArray(vao);

    FloatBuffer verticesBuffer = MemoryUtil.memAllocFloat(cube.getVertexData().length);
    verticesBuffer.put(cube.getVertexData()).flip();

    // Vertex buffer object (VBO)
    vbo = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, vbo);
    glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

    // --- Vertex attributes ---
    // Positions (x, y, z)
    glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * Float.BYTES, 0);
    glEnableVertexAttribArray(0);

    // Normals (x, y, z) 
    glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * Float.BYTES, 3 * Float.BYTES);
    glEnableVertexAttribArray(1);

    // Load assets
    lightingShader.loadShader(ShaderType.Vertex, "assets/shaders/lighting_shader.vert");
    lightingShader.loadShader(ShaderType.Fragment, "assets/shaders/lighting_shader.frag");
    lightingShader.createProgram();
    lightingShader.setVec3("color", new Vector3D(0.5f, 0.9f, 1.0f));
  }

  float t = 0.0f;

  @Override
  public void onUpdate(final float deltaTime) {
    t += deltaTime;

    Matrix4x4 translMatrix = new Matrix4x4(1.0f, 0.0f, 0.0f, 0.0f,
                                     0.0f, 1.0f, 0.0f, 0.0f,
                                     0.0f, 0.0f, 1.0f, 4.0f,
                                     0.0f, 0.0f, 0.0f, 1.0f);

    translMatrix = Matrix4x4.rotateY(translMatrix, t);

    float nearZ = 0.8f;
    float farZ = 100.0f;
    float a = (-farZ - nearZ) / (nearZ - farZ);
    float b = (2 * farZ * nearZ) / (nearZ - farZ);

    float d = 1.0f / (float)Math.tan(Math.PI / 4);
    Matrix4x4 projMatrix = new Matrix4x4(d / getAspectRatio(), 0.0f, 0.0f, 0.0f,
                                         0.0f, d,    0.0f, 0.0f,
                                         0.0f, 0.0f, a,    b,
                                         0.0f, 0.0f, 1.0f, 0.0f);

    lightingShader.setMatrix4x4("projMatrix", Matrix4x4.multiply(projMatrix, translMatrix));
  }

  @Override
  public void onRender() {
    lightingShader.bind();
    glBindVertexArray(vao);
    glDrawArrays(GL_TRIANGLES, 0, 36);
  }
}
