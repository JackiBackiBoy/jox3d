package jox3d.sandbox;
import static org.lwjgl.opengl.GL41.*;
import jox3d.math.*;
import jox3d.rendering.*;
import jox3d.components.*;
import jox3d.input.*;

import java.nio.*;
import org.lwjgl.system.*;

public class RenderingWindow extends Window {
  int vao = 0;                              
  int vbo = 0;
  Shader lightingShader = new Shader();
  Mesh cube = new Mesh();
  Camera camera = new Camera(new Vector3D(0, 0, 0));

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
    // --- Camera controller ---
    // Vertical movement
    if (Keyboard.isKeyDown(KeyCode.Space))
      camera.position.y += 5.0f * deltaTime;
    if (Keyboard.isKeyDown(KeyCode.LeftControl))
      camera.position.y -= 5.0f * deltaTime;
    
    // Horizontal movement
    if (Keyboard.isKeyDown(KeyCode.D)) 
      camera.position = Vector3D.add(camera.position,
                                     Vector3D.multiply(5.0f * deltaTime, camera.getRight()));

    if (Keyboard.isKeyDown(KeyCode.A)) 
      camera.position = Vector3D.add(camera.position,
                                     Vector3D.multiply(-5.0f * deltaTime, camera.getRight()));
    t += deltaTime;

    Matrix4x4 translMatrix = new Matrix4x4(1.0f, 0.0f, 0.0f, 0.0f,
                                           0.0f, 1.0f, 0.0f, 0.0f,
                                           0.0f, 0.0f, 1.0f, 4.0f,
                                           0.0f, 0.0f, 0.0f, 1.0f);
    // Rotation matrix 
    Matrix4x4 rotationMatrix = Matrix4x4.rotateY(Matrix4x4.identity, t);

    // World matrix
    Matrix4x4 worldMatrix = Matrix4x4.multiply(translMatrix, rotationMatrix);

    // View matrix
    Matrix4x4 viewMatrix = camera.getViewMatrix();

    // Projection matrix
    float nearZ = 0.8f;
    float farZ = 100.0f;
    float a = (-farZ - nearZ) / (nearZ - farZ);
    float b = (2 * farZ * nearZ) / (nearZ - farZ);

    float d = 1.0f / (float)Math.tan(Math.PI / 4);
    Matrix4x4 projMatrix = new Matrix4x4(d / getAspectRatio(), 0.0f, 0.0f, 0.0f,
                                         0.0f, d,    0.0f, 0.0f,
                                         0.0f, 0.0f, a,    b,
                                         0.0f, 0.0f, 1.0f, 0.0f);
    // Combine projection, view and world
    Matrix4x4 pvm = Matrix4x4.multiply(projMatrix, Matrix4x4.multiply(viewMatrix, worldMatrix));

    lightingShader.setMatrix4x4("projMatrix", pvm);
  }

  @Override
  public void onRender() {
    lightingShader.bind();
    glBindVertexArray(vao);
    glDrawArrays(GL_TRIANGLES, 0, 36);
  }
}
