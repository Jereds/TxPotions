package me.tx.txpotions.api;

import org.bukkit.event.entity.PotionSplashEvent;

public interface Splashable {
	
	public void onSplash(PotionSplashEvent event);
}
