package zeezed.flappybird.util;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;

import zeezed.flappybird.core.Window;

public final class Input {
	private static boolean[] keysDown = new boolean[Key.KEY_LAST];
	
	public static void init() {
		glfwSetKeyCallback(Window.getInstance().getWindowId(), Input::keyCallback);
	}
	
	private static void keyCallback(long window, int key, int scancode, int action, int mods) {
		if(action == GLFW_PRESS) {
			keysDown[key] = true;
		}
		
		if(action == GLFW_RELEASE) {
			keysDown[key] = false;
		}
	}
	
	public static boolean isKeyPressed(int keyCode) {
		return glfwGetKey(Window.getInstance().getWindowId(), keyCode) == GLFW_PRESS;
	}
	
	public static boolean isKeyDown(int keyCode) {
		boolean result = keysDown[keyCode];
		keysDown[keyCode] = false;
		return result;
	}
}