package fr.skytryx.survivalcore.staff;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Objects;

public class VillagerAlerts implements Listener {

    @EventHandler
    public void VillagerDeath(EntityDeathEvent event){
        if(event.getEntity().getType() != EntityType.VILLAGER) return;
        if(Objects.requireNonNull(event.getEntity().getKiller()).getType() != EntityType.PLAYER) return;
        Bukkit.getOnlinePlayers().forEach(player -> {
            if(player.isOp()) player.sendMessage("§c[Villager] §6"+event.getEntity().getKiller().getName()+" §ba tué un §6villageois");
        });
    }
}
