package zeezed.flappybird.rendering;

import static org.lwjgl.opengl.GL30.*;

import org.joml.Vector2f;

public class Sprite {
	private int vao;
	private int vbo;
	private int ibo;
	
	public Sprite() {
		this.vao = 0;
		this.vbo = 0;
		this.ibo = 0;
	}
	
	public void init(Vector2f size) {
		float sizeX = 0.5f * size.x;
		float sizeY = 0.5f * size.y;
		
		float vertexData[] = {
				-sizeX, -sizeY, 0.0f, 0.0f,
				-sizeX, sizeY, 0.0f, 1.0f,
				sizeX, sizeY, 1.0f, 1.0f,
				sizeX, -sizeY, 1.0f, 0.0f
		};
		
		int indices[] = {
				0, 1, 2,
				2, 3, 0
		};
		
		vao = glGenVertexArrays();
		vbo = glGenBuffers();
		ibo = glGenBuffers();
		
		glBindVertexArray(vao);
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		
		glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);
	
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, 2, GL_FLOAT, false, vertexData.length, 0);
		
		glEnableVertexAttribArray(1);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, vertexData.length, 8);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
	}
	
	public void draw() {
		glBindVertexArray(vao);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
	}
	
	public void cleanUp() {
		if(vao != 0) {
			glDeleteVertexArrays(vao);
			vao = 0;
		}
		
		if(vbo != 0) {
			glDeleteBuffers(vbo);
			vbo = 0;
		}
		
		if(ibo != 0) {
			glDeleteBuffers(ibo);
			ibo = 0;
		}
	}
}