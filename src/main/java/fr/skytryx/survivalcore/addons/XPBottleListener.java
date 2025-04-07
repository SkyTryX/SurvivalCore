package fr.skytryx.survivalcore.addons;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class XPBottleListener implements Listener {
    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        ItemStack item=event.getItem();
        if (item==null || item.getItemMeta()==null)return;
        if (item.getItemMeta().getDisplayName().equals("§aXP Flask") && Arrays.asList(Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK).contains(event.getAction())){
            event.setCancelled(true);
            event.getPlayer().giveExp(50);
            item.setAmount(item.getAmount()-1);
            event.getPlayer().sendMessage("§c[XPBottle] §bVous absorbez 50 points d'xp");
        }
    }
}