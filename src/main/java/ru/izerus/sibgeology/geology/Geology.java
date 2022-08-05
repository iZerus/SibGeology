package ru.izerus.sibgeology.geology;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import ru.izerus.sibgeology.geology.utils.Utils;

public class Geology implements Listener {

	@EventHandler
	void onUseItemEvent(PlayerInteractEvent e) {
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if (!isPickaxe(e.getMaterial())) return;
		if (!isStone(e.getClickedBlock().getType())) return;
		
		doGeoAnalysis(e.getPlayer(), e.getItem(), e.getInteractionPoint());
	}
	
	@SuppressWarnings("deprecation")
	private void doGeoAnalysis(Player player, ItemStack item, Location location) {
		Material material = item.getType();
		int radius = getRadius(material);
		
		if (radius == 0) {
			player.sendActionBar("Кирка не подходит");
			return;
		} else {
			player.playSound(player, Sound.BLOCK_STONE_BREAK, 1.0f, Utils.getInaccurateValue(4.0f, 0.05f));
		}
		
		Map<Material, Integer> oreMap = getOreAreaMap(location, radius);
		if (oreMap.isEmpty()) {
			player.sendActionBar("Ничего не найдено");
			return;
		}
		showMap(player, oreMap);
	}
	
	@SuppressWarnings("deprecation")
	private void showMap(Player player, Map<Material, Integer> showMap) {
		if (!showMap.isEmpty()) {
			String message = "";
			showMap = sortByValue(showMap);
			for (Map.Entry<Material, Integer> entry : showMap.entrySet()) {
				Material ore = entry.getKey();
				Integer count = entry.getValue();
				message += ore.name() + "{" + count + "} ";
			}
			player.sendActionBar(message);
		}
	}
	
	private static Map<Material, Integer> sortByValue(final Map<Material, Integer> map) {
		return map.entrySet().stream().sorted((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}
	
	private int getRadius(Material material) {
		switch (material) {
		case WOODEN_PICKAXE:
			return 0;

		case STONE_PICKAXE:
			return 3;

		case IRON_PICKAXE:
			return 4;

		case GOLDEN_PICKAXE:
			return 4;

		case DIAMOND_PICKAXE:
			return 5;

		case NETHERITE_PICKAXE:
			return 5;

		default:
			return 0;
		}
	}
	
	private boolean isPickaxe(Material material) {
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
	
	private boolean isStone(Material material) {
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
	
	private Map<Material, Integer> getOreAreaMap(Location loc, int rad) {
		Map<Material, Integer> map = new HashMap<>();
		for (int x = loc.getBlockX() - rad; x <= loc.getBlockX() + rad; x++) {
			for (int y = loc.getBlockY() - rad; y <= loc.getBlockY() + rad; y++) {
				for (int z = loc.getBlockZ() - rad; z <= loc.getBlockZ() + rad; z++) {
					Location location = new Location(loc.getWorld(), x, y, z);
					Material material = location.getBlock().getType();
					if (isOreBlock(material)) {
						int distance = (int) loc.distance(location);
						if (map.containsKey(material)) {
							distance = Integer.min(distance, map.get(material));
						}
						map.put(material, distance);
					}
				}
			}
		}
		return map;
	}
	
	private boolean isOreBlock(Material material) {
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
	
}
