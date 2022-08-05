package ru.izerus.sibgeology.config;

import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;

import ru.izerus.sibgeology.SibGeology;

public class Config {

	public static final class Fields {
		public static final String enabled = "enabled";
	}

	private final SibGeology plugin;

	private boolean enabled;

	public Config(SibGeology instance) {
		plugin = instance;
		plugin.saveDefaultConfig();
		load();
	}

	public boolean enabled() {
		return enabled;
	}

	public void load() {
		plugin.reloadConfig();
		FileConfiguration file = plugin.getConfig();

		enabled = file.getBoolean(Fields.enabled);
	}

	public void save() {
		FileConfiguration file = plugin.getConfig();
		file.set(Fields.enabled, enabled);
		String path = plugin.getDataFolder() + "/config.yml";
		try {
			file.save(path);
		} catch (IOException e) {
			plugin.getLogger().log(Level.WARNING, "Failed to save config", e);
		}
		load();
	}

}
