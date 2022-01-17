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

	float t = 0.0f;

  @Override
  public void onUpdate(final float deltaTime) {
		t += deltaTime;

    Matrix4x4 translMatrix = new Matrix4x4(1.0f, 0.0f, 0.0f, 0.0f,
                                     0.0f, 1.0f, 0.0f, 0.0f,
                                     0.0f, 0.0f, 1.0f, 4.0f,
                                     0.0f, 0.0f, 0.0f, 1.0f);
		float nearZ = 0.8f;
    float farZ = 100.0f;
    float a = (-farZ - nearZ) / (nearZ - farZ);
    float b = (2 * farZ * nearZ) / (nearZ - farZ);

    float d = 1.0f / (float)Math.tan(Math.PI / 4);
    Matrix4x4 projMatrix = new Matrix4x4(d / getAspectRatio(), 0.0f, 0.0f, 0.0f,
																				 0.0f, d,    0.0f, 0.0f,
																				 0.0f, 0.0f, a,    b,
																				 0.0f, 0.0f, 1.0f, 0.0f);
		if (t == 0.0f)
			System.out.println(translMatrix.toString());


		translMatrix = Matrix4x4.rotateY(translMatrix, t);

		if (t == 0.0f)
			System.out.println(translMatrix.toString());

    lightingShader.setMatrix4x4("projMatrix", Matrix4x4.multiply(projMatrix, translMatrix));
  }

  @Override
  public void onRender() {
    lightingShader.bind();
    glBindVertexArray(vao);
    glDrawArrays(GL_TRIANGLES, 0, 36);
  }
}
