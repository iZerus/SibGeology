package ru.izerus.sibgeology.config;

import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;

import ru.izerus.sibgeology.SibGeology;

public class Config {

	public static final class Fields {
		public static final String enabled = "enabled";
		public static final String language = "language";
		public static final String woodenPickaxeRadius = "woodenPickaxeRadius";
		public static final String stonePickaxeRadius = "stonePickaxeRadius";
		public static final String ironPickaxeRadius = "ironPickaxeRadius";
		public static final String goldenPickaxeRadius = "goldenPickaxeRadius";
		public static final String diamondPickaxeRadius = "diamondPickaxeRadius";
		public static final String netheritePickaxeRadius = "netheritePickaxeRadius";
	}

	private boolean enabled;
	private String language;
	private int woodenPickaxeRadius;
	private int stonePickaxeRadius;
	private int ironPickaxeRadius;
	private int goldenPickaxeRadius;
	private int diamondPickaxeRadius;
	private int netheritePickaxeRadius;

	public Config() {
		SibGeology.instance().saveDefaultConfig();
		load();
		save();
	}

	public int getDiamondPickaxeRadius() {
		return diamondPickaxeRadius;
	}

	public int getGoldenPickaxeRadius() {
		return goldenPickaxeRadius;
	}

	public int getIronPickaxeRadius() {
		return ironPickaxeRadius;
	}

	public String getLanguage() {
		return language;
	}

	public int getNetheritePickaxeRadius() {
		return netheritePickaxeRadius;
	}

	public int getStonePickaxeRadius() {
		return stonePickaxeRadius;
	}

	public int getWoodenPickaxeRadius() {
		return woodenPickaxeRadius;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void load() {
		SibGeology.instance().reloadConfig();
		FileConfiguration file = SibGeology.instance().getConfig();

		enabled = file.getBoolean(Fields.enabled);
		language = file.getString(Fields.language);
		woodenPickaxeRadius = file.getInt(Fields.woodenPickaxeRadius);
		stonePickaxeRadius = file.getInt(Fields.stonePickaxeRadius);
		ironPickaxeRadius = file.getInt(Fields.ironPickaxeRadius);
		goldenPickaxeRadius = file.getInt(Fields.goldenPickaxeRadius);
		diamondPickaxeRadius = file.getInt(Fields.diamondPickaxeRadius);
		netheritePickaxeRadius = file.getInt(Fields.netheritePickaxeRadius);
	}

	public void save() {
		FileConfiguration file = SibGeology.instance().getConfig();
		file.set(Fields.enabled, enabled);
		file.set(Fields.language, language);
		file.set(Fields.woodenPickaxeRadius, woodenPickaxeRadius);
		file.set(Fields.stonePickaxeRadius, stonePickaxeRadius);
		file.set(Fields.ironPickaxeRadius, ironPickaxeRadius);
		file.set(Fields.goldenPickaxeRadius, goldenPickaxeRadius);
		file.set(Fields.diamondPickaxeRadius, diamondPickaxeRadius);
		file.set(Fields.netheritePickaxeRadius, netheritePickaxeRadius);

		String path = SibGeology.instance().getDataFolder() + "/config.yml";
		try {
			file.save(path);
		} catch (IOException e) {
			SibGeology.logger().log(Level.WARNING, "Failed to save config", e);
		}
		load();
	}

}
