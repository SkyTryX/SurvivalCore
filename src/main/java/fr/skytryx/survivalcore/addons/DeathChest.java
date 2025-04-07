package fr.skytryx.survivalcore.addons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.data.type.Chest.Type;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class DeathChest implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        final List<ItemStack> content = new ArrayList<>(event.getDrops());
        if(content.isEmpty())return;
        org.bukkit.block.data.type.Chest chestData1;
        org.bukkit.block.data.type.Chest chestData2;
        Player player = event.getPlayer();
        Location loc = player.getLocation();
        final ItemStack[] items = content.toArray(new ItemStack[0]);

        final double x = loc.getX();
        final Location x1 = loc.clone();
        final Location x2 = loc.clone();
        x2.setX(x + 1);
        Block block1 = x1.getBlock();
        Block block2 = x2.getBlock();

        block1.setType(Material.CHEST);
        if (event.getDrops().size() >= 27) {
            block2.setType(Material.CHEST);

            Chest chest1 = (Chest) block1.getState();
            Chest chest2 = (Chest) block2.getState();

            chestData1 = (org.bukkit.block.data.type.Chest) chest1.getBlockData();
            chestData2 = (org.bukkit.block.data.type.Chest) chest2.getBlockData();

            chestData1.setType(Type.LEFT);
            block1.setBlockData(chestData1, true);
            chestData2.setType(Type.RIGHT);
            block2.setBlockData(chestData2, true);
        }

            //Updating the chest
            Chest bChest = (org.bukkit.block.Chest) x1.getBlock().getState();
            bChest.setCustomName("§7Death Chest de "+player.getName());
            bChest.update();

            //Adding items to chest
        int i = 0;
        for (final ItemStack item : items) {
            Chest bChest1 = (Chest) x1.getBlock().getState();
            bChest1.getInventory().setItem(i, item);
            i++;
        }
            event.getDrops().clear();
            event.getPlayer().sendMessage("§c[DeathChest] §bTu es mort au coordonnées suivantes: "+ loc.getBlockX()+" "+loc.getBlockY()+" "+loc.getBlockZ());
        }

        @EventHandler
        public void ChestDisappear(InventoryCloseEvent event){
            if(event.getInventory().getType() != InventoryType.CHEST) return;
            if(event.getView().getTitle().contains("§7Death Chest de")){
                if(event.getInventory().isEmpty()) {
                    Objects.requireNonNull(event.getInventory().getLocation()).getBlock().setType(Material.AIR);
                    Arrays.asList(new Location(event.getInventory().getLocation().getWorld(),
                            event.getInventory().getLocation().getBlockX()-1,
                            event.getInventory().getLocation().getBlockY(),
                            event.getInventory().getLocation().getBlockZ()),
                            new Location(event.getInventory().getLocation().getWorld(),
                                    event.getInventory().getLocation().getBlockX()+1,
                                    event.getInventory().getLocation().getBlockY(),
                                    event.getInventory().getLocation().getBlockZ())).forEach(loc ->{
                            if(loc.getBlock().getType() == Material.CHEST) {
                                Chest chest = (Chest) loc.getBlock().getState();
                                if (chest.getInventory().isEmpty()) {
                                    loc.getBlock().setType(Material.AIR);
                                }
                            }
                    });
                }
            }
        }
    }
