package zeezed.flappybird.util;

import org.joml.Math;

public final class Random {
	public static int getRandom(int min, int max) {
		return (int)((Math.random() * (max - min)) + min);
	}
}
