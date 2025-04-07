package fr.skytryx.survivalcore.addons;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Phantom;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.Random;

public class PhantomSize implements Listener {

    @EventHandler
    public void onPhantomSpawn(EntitySpawnEvent event) {
        if (event.getEntityType() == EntityType.PHANTOM) {
            Phantom phantom = (Phantom) event.getEntity();
            phantom.setSize(new Random().nextInt(8));
        }
    }
}
