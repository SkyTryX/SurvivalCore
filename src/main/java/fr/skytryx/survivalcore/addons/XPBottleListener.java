package fr.skytryx.survivalcore.addons;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class XPBottleListener implements Listener {
    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        if (event.getItem()==null || event.getItem().getItemMeta()==null)return;
        if (event.getItem().getItemMeta().getDisplayName().equals("§aXP Flask") && event.getAction().equals(Action.RIGHT_CLICK_AIR)){
            event.setCancelled(true);
            event.getPlayer().giveExp(50);
            event.getItem().setAmount(event.getItem().getAmount()-1);
            event.getPlayer().sendMessage("§c[XPBottle] §bVous absorbez §650 §bpoints d'xp");
        }
    }
}