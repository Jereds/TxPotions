package me.tx.txpotions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.tx.txpotions.api.TxPotion;
import me.tx.txpotions.commands.TxPotionCommand;
import me.tx.txpotions.listeners.BrewingListener;
import me.tx.txpotions.listeners.PotionListener;
import me.tx.txpotions.potions.Breeding;

public class TxPotionsPlugin extends JavaPlugin {

	private final List<Supplier<Listener>> listeners = Arrays.asList(
			PotionListener::new,
			BrewingListener::new,
			Breeding::new
			);
	
	private static final List<TxPotion> potions = new ArrayList<>();
	
	public static List<TxPotion> getPotions() {
		return potions;
	}
	
	public static Optional<TxPotion> getPotion(String id) {
		return getPotions().stream().filter(potion -> potion.getId().equals(id)).findFirst();
	}
	
	public static void addPotion(TxPotion potion) {
		potions.add(potion);
	}
	
	private static TxPotionsPlugin inst;
	
	public static TxPotionsPlugin getInstance() {
		return inst;
	}
	
	@Override
	public void onEnable() {
		inst = this;
		listeners.forEach(listener -> Bukkit.getPluginManager().registerEvents(listener.get(), this));
		
		getCommand("txpotion").setExecutor(new TxPotionCommand());
	}
	
	public static void queueRemove(UUID playerUUID, String namespace) {
		getInstance().getConfig().set(namespace, playerUUID.toString());
	}
	
	public static boolean isQueued(Player player, String namespace) {
		return getInstance().getConfig().getString(namespace) != null;
	}
}
