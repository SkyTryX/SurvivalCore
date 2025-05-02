package fr.skytryx.survivalcore.commands;

import fr.skytryx.survivalcore.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.lang.Math;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.floorDiv;

public class CommandXPBottle implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String @NotNull [] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            int nb_flask = floorDiv((Math.toIntExact(Math.round(player.getTotalExperience()*0.9))), 50);
            if(nb_flask > 0) {
                player.setTotalExperience(0);
                player.setLevel(0);
                player.setExp(0);
                String[] names = {"§apetite flask d'XP", "§emoyenne flask d'XP", "§6grande flask d'XP", "§4mega flask d'XP", "Soupe de vomi"};
                int i = 0;
                int reste = 0;
                boolean tricheur = false;
                String name = names[i];
                while (nb_flask > 64) {
                    reste += (int) ((nb_flask % 9)*Math.pow(9, i));
                    nb_flask /= 9;
                    i++;
                    name = names[i];
                    if(i>=4){
                       tricheur = true;
                        break;
                    }
                }
                if(tricheur) {
                    player.sendMessage("§c[XPBottle] Sale tricheur tu ne peut pas avoir autant d'XP!");
                    player.sendMessage("§c[XPBottle] Tu me dégoute! Voilà pour toi!");
                    player.getInventory().addItem(Util.CreateItem(Material.MUSHROOM_STEW, name, 1));
                }else{
                    player.getInventory().addItem(Util.CreateItem(Material.EXPERIENCE_BOTTLE, name, nb_flask));
                    player.giveExp(reste*50);
                }
            } else{
                player.sendMessage("§c[XPBottle] Vous n'avez pas assez d'XP!");
            }
            return true;
        }
        return false;
    }
}
