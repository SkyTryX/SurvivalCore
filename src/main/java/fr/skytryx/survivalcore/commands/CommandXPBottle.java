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
            Player player=(Player) commandSender;
            int nb_flask = floorDiv((Math.toIntExact(Math.round(player.getTotalExperience()*0.9))), 50);
            if(nb_flask > 0) {
                player.setTotalExperience(0);
                player.setLevel(0);
                player.setExp(0);
                player.getInventory().addItem(Util.CreateItem(Material.EXPERIENCE_BOTTLE, "§aXP Flask", nb_flask));
                player.sendMessage("§c[XPBottle] §bVous recevez " + nb_flask + "flask d'xp.");
            } else{
                player.sendMessage("§c[XPBottle] Vous n'avez pas assez d'XP!");
            }
            return true;
        }
        return false;
    }
}
