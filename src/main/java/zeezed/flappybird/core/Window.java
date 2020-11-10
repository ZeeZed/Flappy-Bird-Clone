package zeezed.flappybird.core;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.opengl.GL11.*;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

public class Window {
	private static Window instance = null;
	
	private final String TITLE;
	private int width;
	private int height;
	private long windowId;
	
	public Window(String title, int width, int height) {
		if(instance == null) instance = this;
		
		this.TITLE = title;
		this.width = width;
		this.height = height;
		this.windowId = 0;
	}
	
	public void init() {
		GLFWErrorCallback.createPrint(System.err);
		if(!glfwInit())
			throw new IllegalStateException("Failed to initialize GLFW!");
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		
		windowId = glfwCreateWindow(width, height, TITLE, 0, 0);
		if(windowId == 0)
			throw new IllegalStateException("Failed to create window!");
		
		try(MemoryStack stack = stackPush()){
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			
			glfwGetWindowSize(windowId, w, h);
			GLFWVidMode mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			
			glfwSetWindowPos(windowId, (mode.width() - w.get(0)) / 2, (mode.height() - h.get(0)) / 2);
		}
		
		glfwMakeContextCurrent(windowId);
		glfwSwapInterval(1);
		glfwShowWindow(windowId);
		
		GL.createCapabilities();
		
		glfwSetWindowSizeCallback(windowId, this::onWindowResize);
		
		glClearColor(0.33f, 0.75f, 0.79f, 1.0f);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT);
	}
	
	public void update() {
		glfwSwapBuffers(windowId);
		glfwPollEvents();
	}
	
	public void cleanUp() {
		glfwFreeCallbacks(windowId);
		glfwDestroyWindow(windowId);
		glfwTerminate();
	}
	
	private void onWindowResize(long window, int width, int height) {
		this.width = width;
		this.height = height;
		glViewport(0, 0, width, height);
	}
	
	public boolean isCloseRequested() {
		return glfwWindowShouldClose(windowId);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public long getWindowId() {
		return windowId;
	}
	
	public static Window getInstance() {
		return instance;
	}
}