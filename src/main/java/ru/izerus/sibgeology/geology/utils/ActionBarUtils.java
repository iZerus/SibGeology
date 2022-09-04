package ru.izerus.sibgeology.geology.utils;

import org.bukkit.entity.Player;

public class ActionBarUtils {

	@SuppressWarnings("deprecation")
	public static void send(Player player, String message) {
		// XXX deprecated method
		player.sendActionBar(message);
	}

}
