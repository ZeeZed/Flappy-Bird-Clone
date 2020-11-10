package zeezed.flappybird.core;

public abstract class Game {
	public void onStart() {}
	public void onUpdate(float delta) {}
	public void onDraw() {}
	public void onCleanUp() {}
	
	public abstract String getTitle();
}