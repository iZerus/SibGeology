package ru.izerus.sibgeology;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import ru.izerus.sibgeology.config.Config;

public class SibGeology extends JavaPlugin {

	private static SibGeology instance;

	public static SibGeology instance() {
		return instance;
	}

	private Config config;

	public Config config() {
		return this.config;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("sibgeology")) {
			config.load();
			sender.sendMessage("SibGeology reloaded: " + config.enabled());
			return true;
		}
		return false;
	}

	@Override
	public void onEnable() {
		instance = this;
		config = new Config(this);
	}

}
