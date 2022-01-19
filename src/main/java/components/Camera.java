package jox3d.components;
import jox3d.math.*;

class Camera {
  private Vector3D position;
  private Vector3D rotation; // euler angles

  public Camera(Vector3D position) {
    this.position = position;
  }
}
