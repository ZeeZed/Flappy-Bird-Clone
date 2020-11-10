package zeezed.flappybird.core;

import org.joml.Matrix4f;
import org.joml.Vector2f;

public class Camera {
	private Matrix4f projectionMatrix;
	private float zoom;
	
	private Vector2f position;
	
	public Camera(float zoom) {
		this.zoom = zoom;
		this.position = new Vector2f(0.0f, 0.0f);
		createProjection();
	}
	
	private void createProjection() {
		float aspect = Window.getInstance().getWidth() / (float)Window.getInstance().getHeight();
		projectionMatrix = new Matrix4f().ortho(-zoom, zoom, -zoom / aspect, zoom / aspect, -1.0f, 1.0f);
	}
	
	public Matrix4f getViewProjection() {
		Matrix4f t = new Matrix4f().translate(-position.x, -position.y, -1.0f);
		Matrix4f view = new Matrix4f().identity();
		return projectionMatrix.mul(t, view);
	}
}