package zeezed.flappybird.core;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

import zeezed.flappybird.util.Input;
import zeezed.flappybird.util.Key;

public class Application {
	private boolean isRunning;
	private Window window;
	private Game game;
	
	public Application(Game game) {
		this.isRunning = false;
		this.window = new Window(game.getTitle(), 1280, 720);
		this.game = game;
	}
	
	public void start() {
		if(isRunning) return;
		
		window.init();
		Input.init();
		
		isRunning = true;
		updateLoop();
	}
	
	private void updateLoop() {
		
		double LAST_TIME = glfwGetTime();
		
		game.onStart();
		
		while(isRunning) {			
			if(window.isCloseRequested() || Input.isKeyDown(Key.KEY_ESCAPE)) stop();
			
			double CURRENT_TIME = glfwGetTime();
			double DELTA_TIME = CURRENT_TIME - LAST_TIME;
			LAST_TIME = CURRENT_TIME;
			
			game.onUpdate((float)DELTA_TIME);
			
			window.clear();
			game.onDraw();
			window.update();
		}
		
		cleanUp();
	}
	
	private void stop() {
		if(!isRunning) return;
		isRunning = false;
	}
	
	private void cleanUp() {
		game.onCleanUp();
		window.cleanUp();
	}
}
