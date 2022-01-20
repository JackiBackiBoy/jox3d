package jox3d.math;

public class Vector3D {
  public float x;
  public float y;
  public float z;
  public float w = 1.0f;

  public Vector3D(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public static Vector3D multiply(float factor, Vector3D vector) {
    return new Vector3D(factor * vector.x, factor * vector.y, factor * vector.z);
  }

  public static Vector3D add(Vector3D a, Vector3D b) {
    return new Vector3D(a.x + b.x, a.y + b.y, a.z + b.z);
  }
}
