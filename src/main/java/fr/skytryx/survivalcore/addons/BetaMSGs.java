package fr.skytryx.survivalcore.addons;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.PluginEnableEvent;

public class BetaMSGs implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        event.getPlayer().sendMessage("§f----------------------------\n"+
                                         "§bBienvenue sur §6???SMP\n"+
                                         "§cCeci est une §c§lBETA, §cveuillez\n"+
                                         "§creport les bugs sur discord!\n"+
                                          "§f----------------------------");
    }

    @EventHandler
    public void onStart(PluginEnableEvent event){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(event.getPlugin(), () -> Bukkit.broadcast(Component.text("[BETA]").color(TextColor.color(255, 0, 0)).append(Component.text(" Le serveur est en béta, veuillez report les bugs sur le discord!").color(TextColor.color(20, 100, 255)))), 12000L, 12000L);
    }
}
