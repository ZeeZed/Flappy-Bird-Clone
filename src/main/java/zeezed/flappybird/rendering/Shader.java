package zeezed.flappybird.rendering;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL30.*;

public class Shader {
	private int id;
	
	public Shader() {
		this.id = 0;
	}
	
	public void init(String filePath) {
		id = glCreateProgram();
		
		int vertex = createShader(GL_VERTEX_SHADER, loadShaderFile(filePath + ".v"));
		int fragment = createShader(GL_FRAGMENT_SHADER, loadShaderFile(filePath + ".f"));

		glAttachShader(id, vertex);
		glAttachShader(id, fragment);
		
		glLinkProgram(id);
		if(glGetProgrami(id, GL_LINK_STATUS) == 0) {
			throw new IllegalStateException("Failed to link shader:\n" + glGetProgramInfoLog(id, 512));
		}
		
		glValidateProgram(id);
		if(glGetProgrami(id, GL_VALIDATE_STATUS) == 0) {
			throw new IllegalStateException("Failed to validate shader:\n" + glGetProgramInfoLog(id, 512));
		}
		
		glDetachShader(id, vertex);
		glDetachShader(id, fragment);
		glDeleteShader(vertex);
		glDeleteShader(fragment);
	}
	
	private int createShader(int shaderType, String src) {
		int shaderId = glCreateShader(shaderType);
		glShaderSource(shaderId, src);
		glCompileShader(shaderId);
		
		if(glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
			throw new IllegalStateException("Failed to compile shader:\n" + glGetProgramInfoLog(id, 512));
		}
		
		return shaderId;
	}
	
	public void bind() {
		glUseProgram(id);
	}
	
	public void unbind() {
		glUseProgram(0);
	}
	
	public void cleanUp() {
		if(id != 0) {
			glDeleteProgram(id);
			id = 0;
		}
	}
	
	private String loadShaderFile(String filePath) {
		String result = "";
		try(InputStreamReader streamReader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream(filePath))){
			BufferedReader reader = new BufferedReader(streamReader);
			
			String line;
			while((line = reader.readLine()) != null)
				result += line + "\n";
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	// Uniforms
	public void setMat4(String name, Matrix4f value) {
		float[] mat = new float[16];
		mat = value.get(mat);
		glUniformMatrix4fv(getUniformLocation(name), false, mat);
	}
	
	private int getUniformLocation(String name) {
		return glGetUniformLocation(id, name);
	}
}