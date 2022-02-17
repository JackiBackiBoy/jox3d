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

  public float length() { return (float)Math.sqrt(x * x + y * y + z * z); }

  public static Vector3D multiply(float factor, Vector3D vector) {
    return new Vector3D(factor * vector.x, factor * vector.y, factor * vector.z);
  }

  public static Vector3D add(Vector3D a, Vector3D b) {
    return new Vector3D(a.x + b.x, a.y + b.y, a.z + b.z);
  }

  public static Vector3D normalize(Vector3D vector) {
    float length = vector.length();

    return new Vector3D(vector.x / length, vector.y / length, vector.z / length);
  }

  public static Vector3D cross(Vector3D a, Vector3D b) {
    return new Vector3D(a.y * b.z - a.z * b.y,
                        a.z * b.x - a.x * b.z,
                        a.x * b.y - a.y * b.x);
  }

  public static Vector3D up = new Vector3D(0.0f, 1.0f, 0.0f);
}
