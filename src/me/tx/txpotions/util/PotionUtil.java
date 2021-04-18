package me.tx.txpotions.util;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;

import me.tx.txpotions.TxPotionsPlugin;
import me.tx.txpotions.api.TxPotion;
import net.md_5.bungee.api.ChatColor;

public class PotionUtil {
	
	private static final NamespacedKey txPotionTypeKey;
	
	static {
		txPotionTypeKey = new NamespacedKey(TxPotionsPlugin.getInstance(), "txpotion-type");
	}

	public static boolean isTxPotion(ItemStack item) {
		return item.getItemMeta().getPersistentDataContainer().has(txPotionTypeKey, PersistentDataType.STRING);
	}
	
	public static boolean isTxPotion(ThrownPotion potion) {
		return isTxPotion(potion.getItem());
	}
	
	public static String getPotionType(ItemStack item) {
		return item.getItemMeta().getPersistentDataContainer().get(txPotionTypeKey, PersistentDataType.STRING);
	}
	
	public static <T extends TxPotion> ItemStack createPotion(boolean splash, T potion, Color color) {
		ItemStack item = new ItemStack(splash ? Material.SPLASH_POTION : Material.POTION, 1);
		PotionMeta meta = (PotionMeta)item.getItemMeta();
		
		meta.setColor(color);
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', potion.getDisplay()));
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		
		meta.getPersistentDataContainer().set(txPotionTypeKey, PersistentDataType.STRING, potion.getId());		
		item.setItemMeta(meta);
		return item;
	}
	
	private static final NamespacedKey durKey;
	
	static {
		durKey = new NamespacedKey(TxPotionsPlugin.getInstance(), "duration");
	}
	
	public static ItemStack setDuration(ItemStack item, int duration) {
		ItemMeta meta = item.getItemMeta();

		meta.getPersistentDataContainer().set(durKey,
				PersistentDataType.INTEGER, duration);
		item.setItemMeta(meta);
		return item;
	}
	
	public static int getDuration(ItemStack potion) {
		if(!isTxPotion(potion))
			return -1;
		return potion.getItemMeta().getPersistentDataContainer().get(durKey, PersistentDataType.INTEGER);
	}
}