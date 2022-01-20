package jox3d.input;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.*;
import jox3d.*;

public class Keyboard {
  public static boolean isKeyDown(KeyCode keyCode) {
    int state = glfwGetKey(App.window.getWindowID(), keyCode.code);

    return state == GLFW_PRESS;
  }
}
