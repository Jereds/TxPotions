package me.tx.txpotions.api;

import org.bukkit.Color;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import me.tx.txpotions.TxPotionsPlugin;
import me.tx.txpotions.util.PotionUtil;

public abstract class TxPotion implements Listener {
	
	private final String id;
	private final String display;
	private final Color color;
	public TxPotion(String id, String display, Color color) {
		this.id = id;
		this.display = display;
		this.color = color;
		TxPotionsPlugin.addPotion(this);
	}
	
	public String getId() {
		return id;
	}
	
	public String getDisplay() {
		return display;
	}
	
	public Color getColor() {
		return color;
	}
	
	public ItemStack getItem() {
		return PotionUtil.createPotion(this instanceof Splashable, this, color);
	}
}
