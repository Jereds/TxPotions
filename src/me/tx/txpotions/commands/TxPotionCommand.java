package me.tx.txpotions.commands;

import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.tx.txpotions.TxPotionsPlugin;
import me.tx.txpotions.api.TxPotion;
import me.tx.txpotions.util.PotionUtil;
import net.md_5.bungee.api.ChatColor;

public class TxPotionCommand implements CommandExecutor {

	private int duration = 0;
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player && sender.hasPermission("TxPotions.txpotioncommand"))) {
			sender.sendMessage(ChatColor.RED + "You lack permission to run this command!");
			return true;
		}
		Player player = (Player) sender;

		if (args.length < 1 || args.length > 2) {
			player.sendMessage(ChatColor.RED + "Incorrect format! /potion <potion>");
			return true;
		}

		
		if (args.length > 1) {
			try {
				duration = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				player.sendMessage(ChatColor.RED + "Invalid number for duration, duration is set to 0.");
				duration = 0;
			}
		}

		TxPotionsPlugin.getPotion(args[0]).ifPresentOrElse(potion -> {
			ItemStack item = PotionUtil.setDuration(potion.getItem(), duration);

			player.getInventory().addItem(item);
			player.sendMessage(ChatColor.GREEN + "You successfully got a potion of: " + args[0]);
		}, () -> {
			player.sendMessage(ChatColor.RED + "Sorry! I couldn't find a potion of that type.\n"
					+ "Please try one of these:\n"
					+ TxPotionsPlugin.getPotions().stream().map(TxPotion::getId).collect(Collectors.joining(",")));

		});
		duration = 0;
		return true;
	}
}
