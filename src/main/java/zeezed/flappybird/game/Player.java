package zeezed.flappybird.game;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import zeezed.flappybird.rendering.Shader;
import zeezed.flappybird.rendering.Sprite;
import zeezed.flappybird.rendering.Texture;
import zeezed.flappybird.util.Box;
import zeezed.flappybird.util.Input;
import zeezed.flappybird.util.Key;

public class Player {
	private Vector2f position;
	private float rotation;
	private Vector2f velocity;
	private float gravity;
	private float jumpForce;
	
	private Sprite sprite;
	private Texture texture;
	
	private Box box;
	
	public void init() {
		position = new Vector2f(-10.0f, 0.0f);
		rotation = 0.0f;
		velocity = new Vector2f(0.0f);
		gravity = 20.0f;
		jumpForce = 8.0f;
		
		sprite = new Sprite();
		sprite.init(new Vector2f(1.0f));
		
		texture = new Texture();
		texture.init("textures/bird.png");
		
		box = new Box(position, new Vector2f(1.0f, 0.8f));
	}
	
	public void input() {
		if(Input.isKeyDown(Key.KEY_SPACE))
			velocity.y = jumpForce;
	}
	
	public void update(float delta) {
		velocity.y -= delta * gravity;
		
		position.x += velocity.x * delta;
		position.y += velocity.y * delta;
		rotation = velocity.y * 3.0f;
		
		if(position.y > 8.0f) {
			position.y = 8.0f;
			velocity.y = 0.0f;
		}
		if(position.y < -5.2f) {
			position.y = -5.2f;
			velocity.y = 0.0f;
		}
		
		box.update(position);
	}
	
	public void draw(Shader shader) {
		texture.bind();
		shader.setMat4("_model", getModelMatrix());
		sprite.draw();
		texture.unbind();
	}
	
	public void cleanUp() {
		sprite.cleanUp();
		texture.bind();
	}
	
	public void reset() {
		position.y = 0.0f;
		velocity = new Vector2f(0, 0);
		box.update(position);
	}
	
	public boolean checkCollisionWithGround(Ground ground) {
		return box.intersects(ground.getBox());
	}
	
	public Box getBox() {
		return box;
	}
	
	private Matrix4f getModelMatrix() {
		Matrix4f model = new Matrix4f().translate(position.x, position.y, 0.0f);
		return model.mul(new Matrix4f().rotate(Math.toRadians(rotation), new Vector3f(0, 0, 1)));
	}
}