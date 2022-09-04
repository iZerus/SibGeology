package ru.izerus.sibgeology.geology;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import ru.izerus.sibgeology.SibGeology;
import ru.izerus.sibgeology.config.Lang;
import ru.izerus.sibgeology.geology.utils.RandomUtils;

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
					if (!GeoUtils.isOreBlock(material))
						continue;

					int distance = (int) loc.distance(location);
					if (map.containsKey(material)) {
						distance = Integer.min(distance, map.get(material));
					}
					map.put(material, distance);
				}
			}
		}
		return sortByValue(map);
	}

	// TODO Refactor
	public String getResultMessage() {
		Set<String> addedOre = new HashSet<>();
		if (!oreMap.isEmpty()) {
			String message = "";
			for (Map.Entry<Material, Integer> entry : oreMap.entrySet()) {
				Material ore = entry.getKey();
				if (!RandomUtils.isChance(SibGeology.config().getOreChance(ore.name())))
					continue;

				String oreName = SibGeology.lang().get(Lang.valueOf(ore.name()));
				if (addedOre.contains(oreName))
					continue;

				Integer count = entry.getValue();
				message += oreName + "{" + count + "} ";
				addedOre.add(oreName);
			}
			return message.equals("") ? SibGeology.lang().get(Lang.ORE_NOT_FOUND) : message;
		} else {
			return SibGeology.lang().get(Lang.ORE_NOT_FOUND);
		}
	}

}
