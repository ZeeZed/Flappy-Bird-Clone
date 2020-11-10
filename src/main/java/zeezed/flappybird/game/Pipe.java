package zeezed.flappybird.game;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import zeezed.flappybird.rendering.Shader;
import zeezed.flappybird.rendering.Sprite;
import zeezed.flappybird.rendering.Texture;
import zeezed.flappybird.util.Box;
import zeezed.flappybird.util.Random;

public class Pipe {
	private static final int[] OFFSETS = {
			-1, 0, 1, 2, 3, 4
	};
	
	private Vector2f position;
	private float rotation;
	private Sprite topSprite;
	private Sprite bottomSprite;
	private Texture texture;
	private int offsetIndex;
	
	private Box topBox;
	private Box bottomBox;
	
	private Box gateBox;
	private boolean scoreGained;
	
	public void init() {
		position = new Vector2f(0.0f);
		rotation = 0.0f;
		topSprite = new Sprite();
		topSprite.init(new Vector2f(2, 8));
		
		bottomSprite = new Sprite();
		bottomSprite.init(new Vector2f(2, 8));
		
		texture = new Texture();
		texture.init("textures/pipe.png");
		
		setRandomOffset();
		
		topBox = new Box(position, new Vector2f(2, 8));
		bottomBox = new Box(position, new Vector2f(2, 8));
		
		gateBox = new Box(position, new Vector2f(1, 4));
		scoreGained = false;
	}
	
	public void update(float delta) {
		position.x -= delta * 6.0f;
		int offset = OFFSETS[offsetIndex];
		topBox.update(new Vector2f(position.x, 5.5f + offset));
		bottomBox.update(new Vector2f(position.x, -5.5f + offset));
		
		gateBox.update(new Vector2f(position.x, offset));
	}
	
	public void draw(Shader shader) {
		texture.bind();
		
		int offset = OFFSETS[offsetIndex];
		
		rotation = 0.0f;
		position.y = -5.5f + offset;
		shader.setMat4("_model", getModelMatrix());
		bottomSprite.draw();
		
		position.y = 5.5f + offset;
		rotation = 180.0f;
		shader.setMat4("_model", getModelMatrix());
		topSprite.draw();
		
		texture.unbind();
		
		position.y = offset;
		rotation = 0.0f;
		shader.setMat4("_model", getModelMatrix());
	}
	
	public void cleanUp() {
		topSprite.cleanUp();
		bottomSprite.cleanUp();
		texture.cleanUp();
	}
	
	public void reset() {
		int offset = OFFSETS[offsetIndex];
		topBox.update(new Vector2f(position.x, 5.5f + offset));
		bottomBox.update(new Vector2f(position.x, -5.5f + offset));
		
		gateBox.update(new Vector2f(position.x, offset));
	}
	
	public boolean checkGateCollision(Player player) {
		return gateBox.intersects(player.getBox());
	}
	
	public boolean checkCollision(Player player) {
		return topBox.intersects(player.getBox()) || bottomBox.intersects(player.getBox());
	}
	
	public void setRandomOffset() {
		offsetIndex = Random.getRandom(0, OFFSETS.length);
	}
	
	public void setPosition(float x, float y) {
		position.x = x;
		position.y = y;
	}
	
	public Vector2f getPosition() {
		return position;
	}
	
	public void setScoreGained(boolean gained) {
		scoreGained = gained;
	}
	
	public boolean isScoreGained() {
		return scoreGained;
	}
	
	private Matrix4f getModelMatrix() {
		Matrix4f model = new Matrix4f().translate(position.x, position.y, 0.0f);
		return model.mul(new Matrix4f().rotate(Math.toRadians(rotation), new Vector3f(0, 0, 1)));
	}
}