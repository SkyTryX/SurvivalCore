package fr.skytryx.survivalcore.commands;

import fr.skytryx.survivalcore.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CommandLuckyBlock implements CommandExecutor {
    public static Map<String, String> LB_Texture = new HashMap<String, String>(){{
        put("Coal", "ee937a06-a126-4532-b59d-58fbb10f7a2e");
        put("Iron", "94505953-278d-48d0-8e94-b6d6f6efc965");
        put("Gold", "136030c8-2bb2-47d6-bcde-bef42f5efecb");
        put("Diamond", "6261daee-5ad2-4fb8-b63a-90112b4b2793");
        put("Netherite", "2952a358-732e-49dc-ab2a-64d736a92119");
        put("Redstone", "4362b97c-aa3c-4035-bede-a71468721744");
        put("Emerald", "fce6054d-f84a-4cfc-ac46-be5d0c9e7954");
    }};
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if(strings[0].isEmpty() || !(commandSender instanceof Player)) return false;
        if(strings[0].equalsIgnoreCase("give")){
            if(strings[1].isEmpty()) return false;
            Player player = (Player) commandSender;
            if(LB_Texture.containsKey(strings[1])){
                player.getInventory().addItem(Util.CreateHead(strings[1], LB_Texture.get(strings[1])));
                player.sendMessage("§c[LuckyBlock] §bTu as recu 1 §6" + strings[1] + " §blucky block!");
            } else return false;
        }
        return true;
    }
}
