package ru.izerus.sibgeology;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import ru.izerus.sibgeology.config.Config;
import ru.izerus.sibgeology.config.Lang;
import ru.izerus.sibgeology.config.LangConfig;
import ru.izerus.sibgeology.geology.GeologyListener;

/**
 * @author iZerus
 *
 */
public class SibGeology extends JavaPlugin {

	private static SibGeology instance;
	private static Config config;
	private static LangConfig lang;

	public static Config config() {
		return config;
	}

	public static SibGeology instance() {
		return instance;
	}

	public static LangConfig lang() {
		return lang;
	}

	public static Logger logger() {
		return instance.getLogger();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("sibgeology")) {
			config.load();
			lang.load();
			sender.sendMessage(SibGeology.lang().get(Lang.RELOADED));
			return true;
		}
		return false;
	}

	@Override
	public void onEnable() {
		instance = this;
		config = new Config();
		lang = new LangConfig();
		getServer().getPluginManager().registerEvents(new GeologyListener(), this);
	}

}
