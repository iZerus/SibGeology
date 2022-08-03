package ru.izerus.plugintemplate;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginTemplate extends JavaPlugin {
	
	static String message;
    
	@Override
	public void onEnable() {
		saveDefaultConfig();
		reloadConfig();
		FileConfiguration config = getConfig();
		message = config.getString(Fields.message);
		getLogger().info(message);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("plugintemplate")) {
			sender.sendMessage(message);
			return true;
		}
		return false;
	}
	
	public static final class Fields {
        public static final String message = "message";
	}
	
}
