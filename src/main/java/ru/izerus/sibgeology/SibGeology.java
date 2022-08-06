package ru.izerus.sibgeology;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import ru.izerus.sibgeology.config.Config;
import ru.izerus.sibgeology.geology.GeologyListener;

public class SibGeology extends JavaPlugin {

	private static SibGeology instance;

	private static Config config;

	public static Config config() {
		return config;
	}

	public static SibGeology instance() {
		return instance;
	}

	public static Logger logger() {
		return instance.getLogger();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("sibgeology")) {
			config.load();
			sender.sendMessage("SibGeology reloaded");
			return true;
		}
		return false;
	}

	@Override
	public void onEnable() {
		instance = this;
		config = new Config();
		getServer().getPluginManager().registerEvents(new GeologyListener(), this);
	}

}
