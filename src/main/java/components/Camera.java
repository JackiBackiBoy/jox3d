package jox3d.components;
import jox3d.math.*;

public class Camera {
  public Vector3D position;

  // Euler angles
  public float pitch = 0.0f;
  public float yaw = 0.0f;
  public float roll = 0.0f;

  // UVN rotation vectors
  private Vector3D right = new Vector3D(1, 0, 0);
  private Vector3D up = new Vector3D(0, 1, 0);
  private Vector3D forward = new Vector3D(0, 0, 1);

  public Vector3D getRight() { return right; }

  public Matrix4x4 getViewMatrix() {
    return new Matrix4x4(right.x, right.y, right.z, -position.x,
                         up.x, up.y, up.z, -position.y,
                         forward.x, forward.y, forward.z, -position.z,
                         0.0f, 0.0f, 0.0f, 1.0f);
  }

  public Camera(Vector3D position) {
    this.position = position;
  }
}
