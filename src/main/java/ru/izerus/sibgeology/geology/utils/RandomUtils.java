package ru.izerus.sibgeology.geology.utils;

import java.util.Random;

public class RandomUtils {

	private static final Random rand = new Random(System.currentTimeMillis());

	public static double getInaccurateValue(double value, double inaccuracity) {
		if (inaccuracity <= 0)
			return value;
		double min = value - (value * inaccuracity);
		min = min > 0 ? min : 0;
		double max = value + (value * inaccuracity);
		return min + rand.nextDouble() * (max - min);
	}

	public static boolean isChance(double chance) {
		return rand.nextDouble() <= chance;
	}

}
