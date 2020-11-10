package zeezed.flappybird.game;

import zeezed.flappybird.core.Application;
import zeezed.flappybird.core.Camera;
import zeezed.flappybird.core.Game;
import zeezed.flappybird.font.Text;
import zeezed.flappybird.rendering.Shader;
import zeezed.flappybird.util.Input;
import zeezed.flappybird.util.Key;

public class FlappyBirdGame extends Game {
	private Camera camera;
	private Shader shader;
	private Player player;
	private Ground ground;
	private PipeManager pipeManager;
	private boolean gameStarted;
	private boolean gameOver;
	private int score;
	private Text text;
	
	@Override
	public void onStart() {
		camera = new Camera(15.0f);
		shader = new Shader();
		shader.init("shaders/spriteShader");
		
		player = new Player();
		player.init();
		
		ground = new Ground();
		ground.init();
		
		pipeManager = new PipeManager();
		pipeManager.init();
		
		gameStarted = false;
		gameOver = false;
		score = 0;
		text = new Text();
		text.init();
	}
	
	private void startGame() {
		gameStarted = true;
	}
	
	@Override
	public void onUpdate(float delta) {
		if(gameStarted && !gameOver) {
			player.input();
			
			if(player.checkCollisionWithGround(ground) || pipeManager.checkCollision(player)) {
				gameOver = true;
				System.out.println("Score: " + score);
			}
			
			pipeManager.update(delta);
			
			if(pipeManager.checkGateCollisions(player))
				score++;
		}
		
		if(gameOver) {
			if(Input.isKeyDown(Key.KEY_SPACE)) {
				player.reset();
				pipeManager.reset();
				gameOver = false;
				score = 0;
				System.out.println("Reseting...");
			}
		}
		
		if(gameStarted) {
			player.update(delta);
		}else if(!gameStarted) {
			if(Input.isKeyDown(Key.KEY_SPACE)) {
				startGame();
			}
		}
	}
	
	@Override
	public void onDraw() {
		shader.bind();
		shader.setMat4("_viewProjection", camera.getViewProjection());
		pipeManager.draw(shader);
		
		player.draw(shader);
		
		ground.draw(shader);
		
		text.draw(score, shader);
		shader.unbind();
	}
	
	@Override
	public void onCleanUp() {
		pipeManager.cleanUp();
		player.cleanUp();
		ground.cleanUp();
		shader.cleanUp();
	}
	
	@Override
	public String getTitle() {
		return "Flappy Bird Test";
	}
	
	public static void main(String[] args) {
		Application app = new Application(new FlappyBirdGame());
		app.start();
	}
}
