package jox3d.input; 

public enum MouseButton {
  Left(0),
  Right(1),
  Scroll(2),
  Mouse4(3),
  Mouse5(4),
  Mouse6(5),
  Mouse7(6),
  Mouse8(7);

  public final int code;

  private MouseButton(int code) {
    this.code = code;
  }
}
