package fr.skytryx.survivalcore.commands;

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
        if (!(commandSender instanceof Player)) return false;
        Player player=(Player) commandSender;
        int nb_flask = floorDiv((Math.toIntExact(Math.round(player.getTotalExperience()*0.9))), 50);
        AtomicInteger item_nb = new AtomicInteger();
        if(nb_flask != 0){
            Arrays.stream(player.getInventory().getStorageContents()).forEach(item ->{
                if(item == null) item_nb.getAndIncrement();
            });
            if(item_nb.get()*64 < nb_flask) player.sendMessage("§c[XPBottle] §cTu n'as pas assez de place dans ton inventaire");
            else{
                player.setTotalExperience(0);
                player.setLevel(0);
                player.setExp(0);
                ItemStack xpcontainer=new ItemStack(Material.FLOWER_POT, nb_flask);
                xpcontainer.addUnsafeEnchantment(Enchantment.LURE, 1);
                ItemMeta xpcontainer_meta=xpcontainer.getItemMeta();
                xpcontainer_meta.setDisplayName("§aXP Flask");
                xpcontainer_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                xpcontainer.setItemMeta(xpcontainer_meta);
                player.getInventory().addItem(xpcontainer);
                player.sendMessage("§c[XPBottle] §bVous recevez " + nb_flask + "flask d'xp.");
            }
        } else {
            player.sendMessage("§c[XPBottle] §bTu n'as pas assez d'xp!");
        }


        return true;
    }
}
