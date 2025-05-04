package fr.skytryx.survivalcore.addons;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class XPBottleListener implements Listener {
    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        if (event.getItem()==null || event.getItem().getItemMeta()==null)return;
        String[] names = {"§apetite flask d'XP", "§emoyenne flask d'XP", "§6grande flask d'XP", "§4enorme flask d'XP", "Soupe de vomi"};
        int[] quantite = {20, 180, 1620, 14580, 0};
        for (int i = 0; i < 5; i++) {
            if (event.getItem().getItemMeta().getDisplayName().equals(names[i]) && (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR))){
                event.setCancelled(true);
                event.getPlayer().giveExp(quantite[i]);
                if(i==4){
                    event.getItem().setAmount(0);
                    event.getPlayer().getInventory().addItem(ItemStack.of(Material.BOWL));
                    event.getPlayer().sendMessage("§c[XPBottle] Quelle idée de boire du vomi? :-(");
                    event.getPlayer().addPotionEffect((new PotionEffect(PotionEffectType.NAUSEA, 300, 1)));
                }else{
                    event.getItem().setAmount(event.getItem().getAmount()-1);
                    event.getPlayer().sendMessage("§c[XPBottle] §bVous absorbez §6"+quantite[i]+" §bpoints d'xp");
                }
                break;
            }
        }
    }
}