package me.tx.txpotions.potions;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Breedable;
import org.bukkit.event.entity.PotionSplashEvent;

import me.tx.txpotions.api.Brewable;
import me.tx.txpotions.api.Splashable;
import me.tx.txpotions.api.TxPotion;

public class Breeding extends TxPotion implements Splashable, Brewable {

	public Breeding() {
		super("breeding", "&dPotion of Breeding", Color.fromRGB(245, 115, 176));
	}

	@Override
	public void onSplash(PotionSplashEvent event) {
		event.getAffectedEntities().stream()
			.filter(entity -> entity instanceof Breedable && entity instanceof Animals && entity instanceof Ageable)
			.filter(entity -> ((Ageable)entity).isAdult())
			.filter(entity -> ((Breedable)entity).canBreed())
			.filter(entity -> !((Animals)entity).isLoveMode())
			.forEach(entity -> {
				entity.getWorld().spawnParticle(Particle.HEART, entity.getLocation().add(0, 1.5, 0), 8, .25, .25, .25, .25);
				((Animals)entity).setLoveModeTicks(140);
			});
	}

	@Override
	public Material getIngredient() {
		return Material.POPPY;
	}
}