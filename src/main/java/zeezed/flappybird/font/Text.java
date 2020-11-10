package zeezed.flappybird.font;

import org.joml.Matrix4f;
import org.joml.Vector2f;

import zeezed.flappybird.rendering.Shader;
import zeezed.flappybird.rendering.Sprite;
import zeezed.flappybird.rendering.Texture;

/*
 * Too lazy to properly do text rendering
 */

public class Text {
	private Texture[] textures;
	private Sprite sprite;
	
	public void init() {
		textures = new Texture[10];
		for(int i = 0; i < textures.length; i++) {
			textures[i] = new Texture();
			textures[i].init("fonts/char_" + i + ".png");
		}
		
		sprite = new Sprite();
		sprite.init(new Vector2f(1.0f));
	}
	
	public void draw(int amount, Shader shader) {
		String intString = "" + amount;
		
		if(intString.length() == 1) {
			draw(shader, new Vector2f(0.0f, 8.0f), amount);	
		}else if(intString.length() == 2) {
			draw(shader, new Vector2f(-0.3f, 8.0f), Integer.parseInt(String.valueOf(intString.charAt(0))));
			draw(shader, new Vector2f(0.3f, 8.0f), Integer.parseInt(String.valueOf(intString.charAt(1))));
		}else if(intString.length() == 3) {
			draw(shader, new Vector2f(-0.6f, 8.0f), Integer.parseInt(String.valueOf(intString.charAt(0))));
			draw(shader, new Vector2f(0.0f, 8.0f), Integer.parseInt(String.valueOf(intString.charAt(1))));
			draw(shader, new Vector2f(0.6f, 8.0f), Integer.parseInt(String.valueOf(intString.charAt(2))));
		}
	}
	
	private void draw(Shader shader, Vector2f position, int amount) {
		textures[amount].bind();
		shader.setMat4("_model", getModelMatrix(position));
		sprite.draw();
		textures[amount].unbind();
	}
	
	public void cleanUp() {
		for(int i = 0; i < textures.length; i++) {
			textures[i].cleanUp();
		}
		
		sprite.cleanUp();
	}
	
	private Matrix4f getModelMatrix(Vector2f position) {
		return new Matrix4f().translate(position.x, position.y, 0.0f);
	}
}
