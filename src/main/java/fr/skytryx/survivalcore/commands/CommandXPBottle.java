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
            int nb_flask = floorDiv((Math.toIntExact(Math.round(player.getTotalExperience()*0.9))), 20);
            if(nb_flask > 0) {
                player.setTotalExperience(0);
                player.setLevel(0);
                player.setExp(0);
                int i = 0;
                int reste = 0;
                boolean tricheur = false;
                while (nb_flask > 64) {
                    reste += (int) ((nb_flask % 9)*Math.pow(9, i));
                    nb_flask /= 9;
                    i++;
                    if(i>=4){
                       tricheur = true;
                       break;
                    }
                }
                if(tricheur) {
                    player.sendMessage("§c[XPBottle] Sale tricheur tu ne peut pas avoir autant d'XP!");
                    player.sendMessage("§c[XPBottle] Tu me dégoute! Voilà pour toi!");
                    player.getInventory().addItem(Util.CreateItem(Material.MUSHROOM_STEW, "Soupe de vomi", 1));
                }else{
                    player.getInventory().addItem(CreateXPFlask(i, nb_flask));
                    player.giveExp(reste*20);
                }
            } else{
                player.sendMessage("§c[XPBottle] Vous n'avez pas assez d'XP!");
            }
            return true;
        }
        return false;
    }

    public static ItemStack CreateXPFlask(int i, int amount) {
        if(i>=0 && i<=3){
            ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE, amount);
            String[] names = {"§apetite flask d'XP", "§emoyenne flask d'XP", "§6grande flask d'XP", "§4enorme flask d'XP"};
            ItemMeta meta = item.getItemMeta();
            meta.displayName(Component.text(names[i]));
            int[] customModelData = {6001,6002,6003,6004};
            meta.setCustomModelData(customModelData[i]);
            item.setItemMeta(meta);
            return item;
        }else{
            throw new RuntimeException("L'indice \"i\" dois être entre 0 et 3 inclus.");
        }
    }
}
