package jox3d;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());

        Window window = new Window(1240, 720, "3D Renderer");
        window.run();
    }
}
