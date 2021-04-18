package me.tx.txpotions.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import me.tx.txpotions.TxPotionsPlugin;
import me.tx.txpotions.api.Drinkable;
import me.tx.txpotions.api.Splashable;
import me.tx.txpotions.util.PotionUtil;

public class PotionListener implements Listener {

	@EventHandler
	public void onSplash(PotionSplashEvent event) {
		if(!PotionUtil.isTxPotion(event.getPotion().getItem()))
			return;
		
		TxPotionsPlugin.getPotions().stream()
					.filter(potion -> PotionUtil.getPotionType(event.getPotion().getItem()).equals(potion.getId()))
					.filter(potion -> potion instanceof Splashable)
					.map(potion -> (Splashable)potion)
					.forEach(potion -> potion.onSplash(event));
	}
	
	@EventHandler
	public void onDrink(PlayerItemConsumeEvent event) {
		if(!PotionUtil.isTxPotion(event.getItem()))
			return;
		
		TxPotionsPlugin.getPotions().stream()
					.filter(potion -> potion instanceof Drinkable)
					.map(potion -> (Drinkable)potion)
					.forEach(potion -> potion.onDrink(event));
	}
}
