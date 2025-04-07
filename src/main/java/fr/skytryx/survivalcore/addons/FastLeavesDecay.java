package fr.skytryx.survivalcore.addons;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;

import java.util.Arrays;
import java.util.List;

public class FastLeavesDecay implements Listener {

    List<Material> ListLeaves =
            Arrays.asList(Material.ACACIA_LEAVES, Material.AZALEA_LEAVES, Material.BIRCH_LEAVES,
                    Material.CHERRY_LEAVES, Material.JUNGLE_LEAVES, Material.SPRUCE_LEAVES,
                    Material.MANGROVE_LEAVES, Material.OAK_LEAVES, Material.FLOWERING_AZALEA_LEAVES,
                    Material.DARK_OAK_LEAVES);

    @EventHandler
    public void OnLeaveDecay(LeavesDecayEvent event){
        Block brokenblock = event.getBlock();
        Location blockloc = brokenblock.getLocation();
        for(int i = -10; i <= 10; i++){
            for(int j = -10; j <= 10; j++) {
                for(int k = -10; k <= 10; k++){
                    Block featuredblock = brokenblock.getWorld().getBlockAt(
                            new Location(blockloc.getWorld(), blockloc.getBlockX()+i, blockloc.getBlockY()+j, blockloc.getBlockZ()+k));
                    if(ListLeaves.contains(featuredblock.getType())){
                        featuredblock.breakNaturally();
                    }
                }
            }
        }
    }
}
