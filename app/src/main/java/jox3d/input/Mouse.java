package jox3d.input;
import jox3d.*;
import jox3d.math.*;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.BufferUtils;
import java.nio.DoubleBuffer;

public class Mouse {
  public static boolean isButtonDown(MouseButton button) {
    int state = glfwGetMouseButton(App.window.getWindowID(), button.code);

    return state == GLFW_PRESS;
  }

  public static Vector2D getPosition() {
    DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
    DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);

    glfwGetCursorPos(App.window.getWindowID(), xBuffer, yBuffer);

    return new Vector2D((float)xBuffer.get(0), (float)yBuffer.get(0));
  }
}
