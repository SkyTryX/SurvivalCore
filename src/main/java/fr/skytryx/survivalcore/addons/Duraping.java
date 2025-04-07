package fr.skytryx.survivalcore.addons;

import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.meta.Damageable;


public class Duraping implements Listener {
    @EventHandler
    public void ping(PlayerItemDamageEvent event){
        Damageable im = (Damageable) event.getItem().getItemMeta();
        if(event.getItem().getType().getMaxDurability() - im.getDamage() == 15){
            event.getPlayer().sendMessage("§c[Outils] §4ATTENTION! §bTon outil va bientôt §6casser");
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.BLOCK_ANVIL_USE,50.0f,1.0f);
        }

    }
}
