package ru.izerus.sibgeology.geology;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import ru.izerus.sibgeology.SibGeology;
import ru.izerus.sibgeology.geology.utils.RandomUtils;

/**
 * @author iZerus
 *
 */
public class GeoUtils {

	static class PickBreakException extends Exception {
		private static final long serialVersionUID = -6528828777901930677L;
	}

	public static void addPickaxeDamage(ItemStack pickaxe) throws PickBreakException {
		float level = pickaxe.getEnchantmentLevel(Enchantment.DURABILITY);
		if (!RandomUtils.isChance(1.0f / (level + 1.0f))) {
			return;
		}
		ItemMeta meta = pickaxe.getItemMeta();
		int damage = ((Damageable) meta).getDamage() + 1;
		int maxDamage = pickaxe.getType().getMaxDurability();
		if (damage < maxDamage) {
			((Damageable) meta).setDamage(damage);
			pickaxe.setItemMeta(meta);
		} else {
			pickaxe.setAmount(0);
			throw new PickBreakException();
		}
	}

	public static int getPickaxeRadius(Material material) {
		switch (material) {
		case WOODEN_PICKAXE:
			return SibGeology.config().getWoodenPickaxeRadius();

		case STONE_PICKAXE:
			return SibGeology.config().getStonePickaxeRadius();

		case IRON_PICKAXE:
			return SibGeology.config().getIronPickaxeRadius();

		case GOLDEN_PICKAXE:
			return SibGeology.config().getGoldenPickaxeRadius();

		case DIAMOND_PICKAXE:
			return SibGeology.config().getDiamondPickaxeRadius();

		case NETHERITE_PICKAXE:
			return SibGeology.config().getNetheritePickaxeRadius();

		default:
			throw new IllegalArgumentException("Unknown pickaxe");
		}
	}

	public static boolean isOreBlock(Material material) {
		switch (material) {
		case COAL_ORE:
		case COPPER_ORE:
		case DEEPSLATE_COAL_ORE:
		case DEEPSLATE_COPPER_ORE:
		case DEEPSLATE_DIAMOND_ORE:
		case DEEPSLATE_EMERALD_ORE:
		case DEEPSLATE_GOLD_ORE:
		case DEEPSLATE_IRON_ORE:
		case DEEPSLATE_LAPIS_ORE:
		case DEEPSLATE_REDSTONE_ORE:
		case DIAMOND_ORE:
		case EMERALD_ORE:
		case GOLD_ORE:
		case IRON_ORE:
		case LAPIS_ORE:
		case NETHER_GOLD_ORE:
		case NETHER_QUARTZ_ORE:
		case REDSTONE_ORE:
			return true;
		default:
			return false;
		}

	}

	public static boolean isPickaxe(Material material) {
		switch (material) {
		case WOODEN_PICKAXE:
		case STONE_PICKAXE:
		case IRON_PICKAXE:
		case GOLDEN_PICKAXE:
		case DIAMOND_PICKAXE:
		case NETHERITE_PICKAXE:
			return true;
		default:
			return false;
		}
	}

	public static boolean isStone(Material material) {
		switch (material) {
		case STONE:
		case GRANITE:
		case DIORITE:
		case ANDESITE:
		case DEEPSLATE:
		case TUFF:
		case CALCITE:
		case DRIPSTONE_BLOCK:
		case NETHERRACK:
		case BASALT:
		case BLACKSTONE:
			return true;
		default:
			return false;
		}
	}

	public static void playItemBreakSound(Player player) {
		player.playSound(player, Sound.ITEM_SHIELD_BREAK, 1.0f, 1.0f);
	}

	public static void playItemSound(Player player) {
		player.playSound(player, Sound.BLOCK_STONE_BREAK, 1.0f, (float) RandomUtils.getInaccurateValue(4.0f, 0.05f));
	}

}
