package zeezed.flappybird.util;

import org.joml.AABBf;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Box {
	private Vector2f size;
	
	private AABBf aabb;
	
	private Vector3f min;
	private Vector3f max;
	
	public Box(Vector2f position, Vector2f size) {
		this.size = size;
		
		this.min = new Vector3f(
				-0.5f * size.x + position.x,
				-0.5f * size.y + position.y,
				0.0f
				);
		
		this.max = new Vector3f(
				0.5f * size.x + position.x,
				0.5f * size.y + position.y,
				0.0f
				);
		
		aabb = new AABBf(min, max);
	}
	
	public void update(Vector2f position) {
		min.x = -0.5f * size.x + position.x;
		min.y = -0.5f * size.y + position.y;
		max.x = 0.5f * size.x + position.x;
		max.y = 0.5f * size.y + position.y;
		aabb.setMin(min);
		aabb.setMax(max);
	}
	
	public boolean intersects(Box other) {
		return aabb.intersectsAABB(other.getAABB());
	}
	
	public AABBf getAABB() {
		return aabb;
	}
}
