package ru.izerus.sibgeology.geology;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import ru.izerus.sibgeology.SibGeology;
import ru.izerus.sibgeology.config.Lang;

public class GeoAnalysis {

	class PickException extends Exception {
		private static final long serialVersionUID = 2979789394014147174L;
	}

	private static Map<Material, Integer> sortByValue(final Map<Material, Integer> map) {
		return map.entrySet().stream().sorted((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}

	private Map<Material, Integer> oreMap;

	public GeoAnalysis(ItemStack item, Location location) throws PickException {
		int radius = GeoUtils.getPickaxeRadius(item.getType());
		if (radius == 0) {
			throw new PickException();
		}
		oreMap = getOreAreaMap(location, radius);
	}

	private Map<Material, Integer> getOreAreaMap(Location loc, int rad) {
		Map<Material, Integer> map = new HashMap<>();
		for (int x = loc.getBlockX() - rad; x <= loc.getBlockX() + rad; x++) {
			for (int y = loc.getBlockY() - rad; y <= loc.getBlockY() + rad; y++) {
				for (int z = loc.getBlockZ() - rad; z <= loc.getBlockZ() + rad; z++) {
					Location location = new Location(loc.getWorld(), x, y, z);
					Material material = location.getBlock().getType();
					if (GeoUtils.isOreBlock(material)) {
						int distance = (int) loc.distance(location);
						if (map.containsKey(material)) {
							distance = Integer.min(distance, map.get(material));
						}
						map.put(material, distance);
					}
				}
			}
		}
		return sortByValue(map);
	}

	public String getResultMessage() {
		if (!oreMap.isEmpty()) {
			String message = "";
			for (Map.Entry<Material, Integer> entry : oreMap.entrySet()) {
				Material ore = entry.getKey();
				Integer count = entry.getValue();
				message += SibGeology.lang().get(Lang.valueOf(ore.name())) + "{" + count + "} ";
			}
			return message;
		} else {
			return SibGeology.lang().get(Lang.ORE_NOT_FOUND);
		}
	}

}
