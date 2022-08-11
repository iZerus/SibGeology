package ru.izerus.sibgeology.geology.utils;

import java.util.Random;

public class RandomUtils {

	private static final Random rand = new Random(System.currentTimeMillis());

	public static float getInaccurateValue(float value, float inaccuracity) {
		if (inaccuracity <= 0)
			return value;
		float min = value - (value * inaccuracity);
		min = min > 0 ? min : 0;
		float max = value + (value * inaccuracity);
		return min + rand.nextFloat() * (max - min);
	}

	public static boolean isChance(float chance) {
		return rand.nextFloat() <= chance;
	}

}
