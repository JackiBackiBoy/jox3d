package jox3d.math;

public class Vector2D {
  public float x;
  public float y;

  public Vector2D(float x, float y) {
    this.x = x;
    this.y = y;
  }

  public static Vector2D subtract(Vector2D a, Vector2D b) {
    return new Vector2D(a.x - b.x, a.y - b.y);
  }
}
