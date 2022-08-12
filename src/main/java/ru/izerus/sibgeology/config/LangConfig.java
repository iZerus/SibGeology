package ru.izerus.sibgeology.config;

import java.io.File;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import ru.izerus.sibgeology.SibGeology;

public class LangConfig {

	private final Map<Lang, String> messages = new EnumMap<>(Lang.class);

	public LangConfig() {
		load();
	}

	public String get(Lang key) {
		try {
			return Objects.requireNonNull(messages.get(key));
		} catch (NullPointerException e) {
			SibGeology.logger().warning("The message '" + key + "' is missing from your lang file");
			return "";
		}
	}

	public void load() {
		File f = new File(
				SibGeology.instance().getDataFolder() + "/lang/" + SibGeology.config().getLanguage() + ".yml");
		if (!f.exists()) {
			try {
				SibGeology.instance().saveResource("lang/" + SibGeology.config().getLanguage() + ".yml", true);
			} catch (IllegalArgumentException ignored) {
				SibGeology.logger().warning("Invalid/missing language file name");
			}
		}
		FileConfiguration config = YamlConfiguration.loadConfiguration(f);
		messages.clear();
		config.getKeys(false).forEach(message -> {
			messages.put(Lang.valueOf(message), config.getString(message));
		});
	}

}
