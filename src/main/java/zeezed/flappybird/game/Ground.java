package zeezed.flappybird.game;

import org.joml.Matrix4f;
import org.joml.Vector2f;

import zeezed.flappybird.rendering.Shader;
import zeezed.flappybird.rendering.Sprite;
import zeezed.flappybird.rendering.Texture;
import zeezed.flappybird.util.Box;

public class Ground {
	private Vector2f position;
	private Sprite sprite;
	private Texture texture;
	
	private Box box;
	
	public void init() {
		position = new Vector2f(0, -8.0f);
		sprite = new Sprite();
		sprite.init(new Vector2f(30, 5));
		
		texture = new Texture();
		texture.init("textures/ground.png");
		
		box = new Box(position, new Vector2f(30.0f, 5.0f));
	}
	
	public void draw(Shader shader) {
		shader.setMat4("_model", getModelMatrix());
		texture.bind();
		sprite.draw();
		texture.unbind();
	}
	
	public void cleanUp() {
		sprite.cleanUp();
		texture.cleanUp();
	}
	
	private Matrix4f getModelMatrix() {
		return new Matrix4f().translate(position.x, position.y, 0.0f);
	}
	
	public Box getBox() {
		return box;
	}
}