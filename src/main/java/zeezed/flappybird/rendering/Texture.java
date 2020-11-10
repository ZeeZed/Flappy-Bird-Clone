package zeezed.flappybird.rendering;

import static org.lwjgl.opengl.GL30.*;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class Texture {
	private int id;
	private int width;
	private int height;
	
	public Texture() {
		this.id = 0;
	}
	
	public void init(String filePath) {
		ByteBuffer data = loadTexture(filePath);
		id = glGenTextures();
		bind();
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
	}
	
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, id);
	}
	
	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public void cleanUp() {
		if(id != 0) {
			glDeleteTextures(id);
			id = 0;
		}
	}
	
	private ByteBuffer loadTexture(String filePath) {
		ByteBuffer buffer = null;
		
		try {
			BufferedImage image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(filePath));
			int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
			buffer = BufferUtils.createByteBuffer(image.getTileWidth() * image.getHeight() * 4);
			
			boolean hasAlpha = image.getColorModel().hasAlpha();
			
			for(int y = image.getHeight() - 1; y >= 0; y--) {
				for(int x = 0; x < image.getWidth(); x++) {
					int pixel = pixels[y * image.getWidth() + x];
					buffer.put((byte)((pixel >> 16) & 0xFF));
					buffer.put((byte)((pixel >> 8) & 0xFF));
					buffer.put((byte)(pixel & 0xFF));
					
					if(hasAlpha)
						buffer.put((byte)((pixel >> 24) & 0xFF));
					else
						buffer.put((byte)(0xFF));
				}
			}
			
			width = image.getWidth();
			height = image.getHeight();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		buffer.flip();
		return buffer;
	}
}