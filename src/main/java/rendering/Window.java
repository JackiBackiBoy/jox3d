package jox3d.rendering;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL41.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.*;

public class Window {
  protected String m_Title;
  protected int m_Width;
  protected int m_Height;
  protected long m_WindowID;

  public Window(int width, int height, String title) {
    m_Title = title;
    m_Width = width;
    m_Height = height;
  }
  
  public long getWindowID() { return m_WindowID; }

  public float getAspectRatio() {
    return (float)m_Width / m_Height;
  }

  public void onStart() {}

  public void onUpdate(final float deltaTime) {}

  public void onRender() {}

  public void onExit() {}

  public void run() {
    initialize();

    float deltaTime = 0.0f;
    glEnable(GL_DEPTH_TEST);

    // Start
    onStart();

    // Game loop
    while ( !glfwWindowShouldClose(m_WindowID) ) {
      double t0 = glfwGetTime();

      // onUpdate
      onUpdate(deltaTime);

      // onRender
      glClearColor(0.9f, 0.9f, 0.9f, 0.0f);
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
      onRender();

      glfwSwapBuffers(m_WindowID);
      glfwPollEvents();

      deltaTime = (float)(glfwGetTime() - t0);
    }

    // Exit
    onExit();
    glfwFreeCallbacks(m_WindowID);
    glfwDestroyWindow(m_WindowID);
    glfwTerminate();
    glfwSetErrorCallback(null).free();
  }

  private void initialize() {
    GLFWErrorCallback.createPrint(System.err).set();

    // Initialize GLFW. Most GLFW functions will not work before doing this.
    if (!glfwInit())
      throw new IllegalStateException("Unable to initialize GLFW");

    // Configure GLFW
    glfwDefaultWindowHints(); // optional, the current window hints are already the default
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

    m_WindowID = glfwCreateWindow(m_Width, m_Height, m_Title, NULL, NULL);

    if (m_WindowID == NULL)
      throw new RuntimeException("Failed to create window!");
    
    glfwSetKeyCallback(m_WindowID, (window, key, scancode, action, mods) -> {
      if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
        glfwSetWindowShouldClose(window, true);
    });

    // Get the thread stack and push a new frame
    try ( MemoryStack stack = stackPush() ) {
      IntBuffer pWidth = stack.mallocInt(1); // int*
      IntBuffer pHeight = stack.mallocInt(1); // int*

      // Get the window size passed to glfwCreateWindow
      glfwGetWindowSize(m_WindowID, pWidth, pHeight);

      // Get the resolution of the primary monitor
      GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

      // Center the window
      glfwSetWindowPos(
        m_WindowID,
        (vidmode.width() - pWidth.get(0)) / 2,
        (vidmode.height() - pHeight.get(0)) / 2
      );
    } // the stack frame is popped automatically

    // Make the OpenGL context current
    glfwMakeContextCurrent(m_WindowID);
    // Enable v-sync
    glfwSwapInterval(1);

    // Make the window visible
    glfwShowWindow(m_WindowID);

    GL.createCapabilities();
  }
}
