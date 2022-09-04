package ru.izerus.sibgeology.geology;

import org.bukkit.GameMode;
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
import ru.izerus.sibgeology.geology.GeoUtils.PickBreakException;
import ru.izerus.sibgeology.geology.utils.ActionBarUtils;

/**
 * @author iZerus
 *
 */
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

		try {
			GeoAnalysis analysis = new GeoAnalysis(item, location);
			ActionBarUtils.send(player, analysis.getResultMessage());
		} catch (PickException exception) {
			ActionBarUtils.send(player, SibGeology.lang().get(Lang.WEAK_PICKAXE));
		}

		GeoUtils.playItemSound(player);
		if (player.getGameMode() == GameMode.SURVIVAL && SibGeology.config().isDamagePickaxe()) {
			try {
				GeoUtils.addPickaxeDamage(item);
			} catch (PickBreakException exception) {
				GeoUtils.playItemBreakSound(player);
			}
		}
	}
}
