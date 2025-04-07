package fr.skytryx.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CommandLeaderboard implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if(!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        final File skillfile = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("SurvivalCore")).getDataFolder(), "skills.yml");
        final YamlConfiguration skillconfig = YamlConfiguration.loadConfiguration(skillfile);
        Inventory lb = Bukkit.createInventory(null, 54, "§8Classement");
        HashMap<String, Integer>lb_list = new HashMap<>();
        Objects.requireNonNull(skillconfig.getConfigurationSection("")).getValues(false).forEach((path, pl) -> lb_list.put(path, 0));
        lb_list.forEach((ign, list_xp)-> Objects.requireNonNull(skillconfig.getConfigurationSection(ign)).getValues(false).forEach((path, skill) -> lb_list.put(ign, skillconfig.getInt(ign+"."+path+".level")+lb_list.get(ign))));
        Map<String, Integer> result = lb_list.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        AtomicInteger place = new AtomicInteger();
        place.set(lb_list.size());
        result.forEach((ign, list_xp)->{
            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
            skullMeta.setOwner(Objects.requireNonNull(Bukkit.getOfflinePlayer(UUID.fromString(ign))).getName());
            skullMeta.setDisplayName("§6"+ Objects.requireNonNull(Bukkit.getOfflinePlayer(UUID.fromString(ign))).getName() + " §b| §6#" + place.get());
            skullMeta.setLore(Collections.singletonList("§bNiveau: §6" + list_xp));
            head.setItemMeta(skullMeta);
            lb.setItem(place.get()-1, head);
            place.addAndGet(-1);
        });
        player.openInventory(lb);
        return true;
    }
}
