package fr.skytryx.survivalcore.commands;

import fr.skytryx.survivalcore.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

public class CommandSkill implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if(!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        final File skillfile = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("SurvivalCore")).getDataFolder(), "skills.yml");
        final YamlConfiguration skillconfig = YamlConfiguration.loadConfiguration(skillfile);
        Inventory skill_inv = Bukkit.createInventory(null, 36, "§7Menu Skill");
        Util.StainedGlass(0,9, skill_inv);
        skill_inv.setItem(11, Util.CreateItem(Material.DIAMOND_HOE, "§6Farming", Arrays.asList("§bLevel: §6"+ skillconfig.getString(player.getUniqueId()+".farming.level")+"§b/20",
                "§bXP: §6" + skillconfig.getString(player.getUniqueId()+".farming.xp")+"§b/§6"+(Math.pow(skillconfig.getInt(player.getUniqueId() + ".farming.level") * 15, 2) + 100),
                " ",
                "§bCapacité Passive (§6More Health§b): ",
                "§bCapacité Active (§6Replenish§b): ")));

        skill_inv.setItem(12, Util.CreateItem(Material.DIAMOND_PICKAXE, "§6Mining", Arrays.asList("§bLevel: §6"+ skillconfig.getString(player.getUniqueId()+".mining.level")+"§b/20",
                "§bXP: §6" + skillconfig.getString(player.getUniqueId()+".mining.xp")+"§b/§6"+(Math.pow(skillconfig.getInt(player.getUniqueId() + ".mining.level"), 3)*15+100),
                " ",
                "§bCapacité Passive (§6More Ores§b): ",
                "§bCapacité Active (§6InstaMine§b): ")));

        skill_inv.setItem(13, Util.CreateItem(Material.DIAMOND_SHOVEL, "§6Excavating", Arrays.asList("§bLevel: §6"+ skillconfig.getString(player.getUniqueId()+".excavating.level")+"§b/20",
                "§bXP: §6" + skillconfig.getString(player.getUniqueId()+".excavating.xp")+"§b/§6"+(Math.pow(skillconfig.getInt(player.getUniqueId() + ".excavating.level") * 15, 2) + 100),
                " ",
                "§bCapacité Passive (§6Lucky Drops§b): ",
                "§bCapacité Active (§6Area Destroyer§b): ")));

        skill_inv.setItem(14, Util.CreateItem(Material.DIAMOND_AXE, "§6WoodCutting", Arrays.asList("§bLevel: §6"+skillconfig.getString(player.getUniqueId()+".woodcutting.level")+"§b/20",
                "§bXP: §6" + skillconfig.getString(player.getUniqueId()+".woodcutting.xp")+"§b/§6"+(Math.pow(skillconfig.getInt(player.getUniqueId() + ".woodcutting.level") * 15, 2) + 100),
                " ",
                "§bCapacité Passive (§6Reduced Fall§b): ",
                "§bCapacité Active (§6Timber§b): ")));

        skill_inv.setItem(15, Util.CreateItem(Material.FISHING_ROD, "§6Fishing", Arrays.asList("§bLevel: §6"+skillconfig.getString(player.getUniqueId()+".fishing.level")+"§b/20",
                "§bXP: §6" + skillconfig.getString(player.getUniqueId()+".fishing.xp")+"§b/§6"+(Math.pow(skillconfig.getInt(player.getUniqueId() + ".fishing.level") * 15, 2) + 100),
                " ",
                "§bCapacité Passive (§6Swimming Speed§b): ",
                "§bCapacité Active (§6Old Fishing§b): ")));

        skill_inv.setItem(20, Util.CreateItem(Material.DIAMOND_SWORD, "§6Fighting", Arrays.asList("§bLevel: §6"+skillconfig.getString(player.getUniqueId()+".fighting.level")+"§b/20",
                "§bXP: §6" + skillconfig.getString(player.getUniqueId()+".fighting.xp")+"§b/§6"+(Math.pow(skillconfig.getInt(player.getUniqueId() + ".fighting.level") * 15, 2) + 100),
                " ",
                "§bCapacité Passive (§6Damage Buff§b): ",
                "§bCapacité Active (§6Lightning Strike§b): ")));

        skill_inv.setItem(21, Util.CreateItem(Material.BOW, "§6Bow-ing", Arrays.asList("§bLevel: §6"+skillconfig.getString(player.getUniqueId()+".bow-ing.level")+"§b/20",
                "§bXP: §6" + skillconfig.getString(player.getUniqueId()+".bow-ing.xp")+"§b/§6"+(Math.pow(skillconfig.getInt(player.getUniqueId() + ".bow-ing.level") * 15, 2) + 100),
                " ",
                "§bCapacité Passive: (§6Arrow Damage§b): ",
                "§bCapacité Active (§6Boom Arrow§b): ")));

        skill_inv.setItem(22, Util.CreateItem(Material.ENCHANTING_TABLE, "§6Enchanting", Arrays.asList("§bLevel: §6"+ skillconfig.getString(player.getUniqueId()+".enchanting.level")+"§b/20",
                "§bXP: §6" + skillconfig.getString(player.getUniqueId()+".enchanting.xp")+"§b/§6"+(Math.pow(skillconfig.getInt(player.getUniqueId() + ".enchanting.level") * 15, 2) + 100),
                " ",
                "§bCapacité Passive (§6XP Gain buff§b): ",
                "§bCapacité Active (§6MobFreeze§b): ")));

        skill_inv.setItem(23, Util.CreateItem(Material.ANVIL, "§6Forging", Arrays.asList("§bLevel: §6"+ skillconfig.getString(player.getUniqueId()+".forging.level")+"§b/20",
                "§bXP: §6" + skillconfig.getString(player.getUniqueId()+".forging.xp")+"§b/§6"+(Math.pow(skillconfig.getInt(player.getUniqueId() + ".forging.level") * 15, 2) + 100),
                " ",
                "§bCapacité Passive (§6Reduced Cost§b): ",
                "§bCapacité Active (§6XPBoom§b): ")));

        skill_inv.setItem(24, Util.CreateItem(Material.BREWING_STAND, "§6Brewing", Arrays.asList("§bLevel: §6"+ skillconfig.getString(player.getUniqueId()+".brewing.level")+"§b/20",
                "§bXP: §6" + skillconfig.getString(player.getUniqueId()+".brewing.xp")+"§b/§6"+(Math.pow(skillconfig.getInt(player.getUniqueId() + ".brewing.level") * 15, 2) + 100),
                " ",
                "§bCapacité Passive (§6Longer Potions§b): ",
                "§bCapacité Active (§6SuperPot§b): ")));

        Util.StainedGlass(27, 36, skill_inv);
        player.openInventory(skill_inv);
        return true;
    }
}
