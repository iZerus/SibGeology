package ru.izerus.sibgeology.geology.utils;

import java.util.Random;

import org.bukkit.inventory.ItemStack;

public class Utils {

	private static final Random rand = new Random(System.currentTimeMillis());

	public static float getInaccurateValue(float value, float inaccuracity) {
		if (inaccuracity <= 0)
			return value;
		float min = value - (value * inaccuracity);
		min = min > 0 ? min : 0;
		float max = value + (value * inaccuracity);
		return min + rand.nextFloat() * (max - min);
	}

	public static String getItemStackName(ItemStack itemStack) {
		String materialName = itemStack.getType().getKey().asString().toLowerCase();
		return materialName;
	}

	public static boolean isChance(float chance) {
		return rand.nextFloat() <= chance;
	}

	public static float randomFloat() {
		return rand.nextFloat();
	}

	public static int randomInt(int size) {
		return rand.nextInt(size);
	}

	public static int randomInt(int min, int max) {
		return rand.nextInt((max - min) + 1) + min;
	}
	
}
