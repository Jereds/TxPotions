package me.tx.txpotions.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;

public class BrewingListener implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void onBrew(InventoryClickEvent event) {

		Player player = (Player) event.getWhoClicked();

		if (!(event.getInventory() instanceof BrewerInventory)) {
			return;
		}

		BrewerInventory inv = (BrewerInventory) event.getInventory();

		player.sendMessage(Integer.toString(event.getSlot()) + " " + event.getSlotType());

		if (event.getSlotType() != SlotType.FUEL) {
			return;
		}

		event.setCancelled(true);
		
		if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) {
			inv.setIngredient(event.getCursor());
			player.setItemOnCursor(null);
			return;
		}
		
		ItemStack i = inv.getIngredient();
		inv.setIngredient(event.getCursor());
		player.setItemOnCursor(i);

		/*
		 * if (event.getSlot() != ingredientSlot) {
		 * event.getWhoClicked().sendMessage("not ingredient slot"); return; } if
		 * (inv.getIngredient() != null) { inv.setIngredient(event.getCursor());
		 * event.setCursor(null); return; } ItemStack ing = inv.getIngredient();
		 * inv.setIngredient(event.getCursor()); event.setCursor(ing);
		 */
	}
}
