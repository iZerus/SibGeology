package ru.izerus.sibgeology.geology;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import ru.izerus.sibgeology.SibGeology;
import ru.izerus.sibgeology.config.Lang;
import ru.izerus.sibgeology.geology.GeoAnalysis.PickException;
import ru.izerus.sibgeology.geology.utils.ActionBarUtils;

public class GeologyListener implements Listener {

	@EventHandler
	void onUseItemEvent(PlayerInteractEvent e) {
		if (!SibGeology.config().isEnabled())
			return;
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		ItemStack item = e.getItem();
		if (item == null || !GeoUtils.isPickaxe(item.getType()))
			return;
		if (!GeoUtils.isStone(e.getClickedBlock().getType()))
			return;

		Player player = e.getPlayer();
		Location location = e.getInteractionPoint();

		GeoUtils.playItemSound(player);
		try {
			GeoAnalysis analysis = new GeoAnalysis(item, location);
			ActionBarUtils.send(player, analysis.getResultMessage());
		} catch (PickException exception) {
			ActionBarUtils.send(player, SibGeology.lang().get(Lang.WEAK_PICKAXE));
		}
	}
}
