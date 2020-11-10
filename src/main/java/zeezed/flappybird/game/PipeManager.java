package zeezed.flappybird.game;

import zeezed.flappybird.rendering.Shader;

public class PipeManager {
	private Pipe[] pipes;
	private int lastPipeIndex;
	private float gap;
	
	public void init() {
		gap = 10.0f;
		
		pipes = new Pipe[5];
		
		for(int i = 0; i < pipes.length; i++) {
			pipes[i] = new Pipe();
			pipes[i].init();
			pipes[i].setPosition(20.0f + i * gap, 0);
			pipes[i].setRandomOffset();
		}
		
		lastPipeIndex = pipes.length - 1;
	}
	
	public void reset() {
		for(int i = 0; i < pipes.length; i++) {
			Pipe pipe = pipes[i];
			pipe.setPosition(20.0f + i * gap, 0.0f);
			pipe.setRandomOffset();
			pipe.setScoreGained(false);
			pipe.reset();
		}
		
		lastPipeIndex = pipes.length - 1;
	}
	
	public void update(float delta) {
		for(int i = 0; i < pipes.length; i++) {
			Pipe pipe = pipes[i];
			pipe.update(delta);
			
			if(pipe.getPosition().x < -20.0f)
				moveToBack(pipe, i);
		}
	}
	
	public void draw(Shader shader) {
		for(int i = 0; i < pipes.length; i++) {
			pipes[i].draw(shader);
		}
	}
	
	public void cleanUp() {
		for(int i = 0; i < pipes.length; i++) {
			pipes[i].cleanUp();
		}
	}
	
	public boolean checkGateCollisions(Player player) {
		for(int i = 0; i < pipes.length; i++) {
			Pipe pipe = pipes[i];
			if(pipe.isScoreGained()) continue;
			if(pipe.checkGateCollision(player)) {
				pipe.setScoreGained(true);
				return true;
			}
		}
		
		return false;
	}
	
	public boolean checkCollision(Player player) {
		for(int i = 0; i < pipes.length; i++) {
			if(pipes[i].checkCollision(player))
				return true;
		}
		
		return false;
	}
	
	private void moveToBack(Pipe pipe, int index) {
		Pipe last = pipes[lastPipeIndex];
		pipe.setPosition(last.getPosition().x + gap, 0.0f);
		pipe.setRandomOffset();
		pipe.setScoreGained(false);
		
		lastPipeIndex = index;
	}
}