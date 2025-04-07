package fr.skytryx.survivalcore.addons;

import org.bukkit.Material;
import org.bukkit.entity.Boat;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;

public class BoatKill implements Listener {
    @EventHandler
    public void bbreak(VehicleExitEvent event){
        if (event.getVehicle() instanceof Boat){
            if(event.getVehicle().getPassengers().size() == 1){
                event.getVehicle().remove();
                event.getVehicle().getWorld().dropItem(event.getVehicle().getLocation(), new ItemStack(Material.OAK_BOAT));
                if(event.getExited().getType() == EntityType.PLAYER){
                    event.getExited().sendMessage("§c[BoatKill] §bTon bateau a été §6cassé");
                }
            }
        }
    }
}
