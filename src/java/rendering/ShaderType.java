package jox3d.rendering;
import static org.lwjgl.opengl.GL41.*;
import org.lwjgl.opengl.ARBComputeShader;

public enum ShaderType {
  Vertex(GL_VERTEX_SHADER),
  Fragment(GL_FRAGMENT_SHADER),
  Geometry(GL_GEOMETRY_SHADER),
  Compute(ARBComputeShader.GL_COMPUTE_SHADER);

  public final int typeID;

  private ShaderType(int typeID) { this.typeID = typeID; }
}
