package fr.skytryx.survivalcore.staff;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class XrayAlerts implements Listener {

    // Buffer des alertes de XRay
    public static Map<Player, Integer> XrayList = new HashMap<>();

    @EventHandler
    public void onMine(BlockBreakEvent event){
        if(event.getBlock().getType() != Material.DIAMOND_ORE)
            return;
        if(XrayList.containsKey(event.getPlayer()))
            XrayList.put(event.getPlayer(), XrayList.get(event.getPlayer())+1);
        else
            XrayList.put(event.getPlayer(), 1);

        // Alerte Staff
        if(XrayList.get(event.getPlayer()) > 8)
            Bukkit.getOnlinePlayers().forEach(player -> {
                if(player.isOp())
                    player.sendMessage("§c[Xray] §6"+event.getPlayer().getName()+" §ba miné §6"+XrayList.get(event.getPlayer())+" §bdiamants!");
            });

        // Suppression du décompte
        Bukkit.getScheduler().scheduleSyncDelayedTask(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("SurvivalCore")), () -> {
            XrayList.put(event.getPlayer(), XrayList.get(event.getPlayer())-1);
            if(XrayList.get(event.getPlayer()) == 0)
                XrayList.remove(event.getPlayer());
        }, 1200);
    }
}
