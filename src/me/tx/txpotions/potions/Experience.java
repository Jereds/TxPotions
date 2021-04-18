package me.tx.txpotions.potions;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataType;

import me.tx.txpotions.TxPotionsPlugin;
import me.tx.txpotions.api.Drinkable;
import me.tx.txpotions.api.TxPotion;

public class Experience extends TxPotion implements Drinkable {

	public Experience() {
		super("experience", "&aPotion Of Experience", Color.fromRGB(0, 0, 0));
	}

	private static final NamespacedKey key;
	
	static {
		key = new NamespacedKey(TxPotionsPlugin.getInstance(), "experience-multiplier");
	}
	
	@Override
	public void onDrink(PlayerItemConsumeEvent event) {
		event.getPlayer().getPersistentDataContainer().set(key, PersistentDataType.STRING, "true");
		UUID uuid = event.getPlayer().getUniqueId();
		
		try {
			Bukkit.getScheduler().runTaskLater(TxPotionsPlugin.getInstance(), () -> {}, 20 * 3 * 60);
		} catch(Exception e) {
			TxPotionsPlugin.queueRemove(uuid, key.getNamespace());
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if(TxPotionsPlugin.isQueued(event.getPlayer(), key.getNamespace()))
			event.getPlayer().getPersistentDataContainer().remove(key);
	}
	
	@EventHandler
	public void onPlayerGetExp(PlayerExpChangeEvent event) {
		if(event.getPlayer().getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
			event.setAmount(event.getAmount() * 3);
		}
	}
}
