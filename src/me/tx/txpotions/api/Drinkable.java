package me.tx.txpotions.api;

import org.bukkit.event.player.PlayerItemConsumeEvent;

public interface Drinkable {

	public void onDrink(PlayerItemConsumeEvent event);
}
