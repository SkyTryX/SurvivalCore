package fr.skytryx.survivalcore.addons;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.GrindstoneInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class SkillListener implements Listener {

    public void GetXP(String skill, YamlConfiguration config, Player player, Float xp, File file){
        config.set(player.getUniqueId() + "."+skill+".xp", config.getInt(player.getUniqueId() + "."+skill+".xp") + xp);
        if (config.getInt(player.getUniqueId() + "." + skill + ".xp") >= Math.pow(config.getInt(player.getUniqueId() + "." + skill + ".level") * 15, 2) + 100 && config.getInt(player.getUniqueId() + "." + skill + ".level") < 20) {
            config.set(player.getUniqueId() + "." + skill + ".xp", 0);
            config.set(player.getUniqueId() + "." + skill + ".level", config.getInt(player.getUniqueId() + "." + skill + ".level") + 1);
            player.sendMessage("§6§lNIVEAU SUPERIEUR!\n" +
                    "§bTu es maintenant niveau §6" + config.getInt(player.getUniqueId() + "." + skill + ".level") + " §bin §6" + skill);
            if(Arrays.asList(10, 20).contains(config.getInt(player.getUniqueId() + "." + skill + ".level"))){
                config.set(player.getUniqueId() + "." + skill + ".active", config.getInt(player.getUniqueId() + "." + skill + ".level")/10);
            }
        }
        BossBar xp_bar = BossBar.bossBar(Component.text("Gain de "+ xp + "XP en "+skill+" ("+ config.getInt(player.getUniqueId() + "."+skill+".xp") +"/" + (Math.pow(config.getInt(player.getUniqueId() + "." + skill + ".level") * 15, 2) + 100) + ")"), 0f, BossBar.Color.GREEN, BossBar.Overlay.PROGRESS);
        player.showBossBar(xp_bar);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("SurvivalCore")), () -> player.hideBossBar(xp_bar), 100L);
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @EventHandler
    public void ProfileCreation(PlayerJoinEvent event){
        final File skillfile = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("SurvivalCore")).getDataFolder(), "skills.yml");
        final YamlConfiguration skillconfig = YamlConfiguration.loadConfiguration(skillfile);
        if(skillconfig.get(event.getPlayer().getUniqueId().toString()) == null){
            Arrays.asList("farming", "mining", "excavating", "woodcutting", "fishing", "fighting", "bow-ing", "enchanting", "forging", "brewing").forEach(sk ->{
                skillconfig.set(event.getPlayer().getUniqueId()+"."+sk+".xp", 0);
                skillconfig.set(event.getPlayer().getUniqueId()+"."+sk+".level", 0);
                skillconfig.set(event.getPlayer().getUniqueId()+"."+sk+".passive", 0);
                skillconfig.set(event.getPlayer().getUniqueId()+"."+sk+".active", 0);
            });
            try {
                skillconfig.save(skillfile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    Map<Material, Float> Miner = new HashMap<Material, Float>() {{
        put(Material.STONE , 1f);
        put(Material.COAL_ORE, 5f);
        put(Material.DEEPSLATE, 1.5f);
        put(Material.DEEPSLATE_COAL_ORE, 6f);
        put(Material.IRON_ORE, 9f);
        put(Material.DEEPSLATE_IRON_ORE, 10f);
        put(Material.GOLD_ORE, 15f);
        put(Material.DEEPSLATE_GOLD_ORE, 17.5f);
        put(Material.DIAMOND_ORE, 30f);
        put(Material.DEEPSLATE_DIAMOND_ORE, 35f);
        put(Material.REDSTONE_ORE, 10f);
        put(Material.DEEPSLATE_REDSTONE_ORE, 12f);
        put(Material.LAPIS_ORE, 20f);
        put(Material.DEEPSLATE_LAPIS_ORE, 25f);
        put(Material.ANCIENT_DEBRIS, 300f);
        put(Material.COPPER_ORE, 8f);
        put(Material.DEEPSLATE_COPPER_ORE, 9f);
        put(Material.NETHER_QUARTZ_ORE, 10f);
        put(Material.NETHER_GOLD_ORE, 7f);
        put(Material.EMERALD_ORE, 500f);
        put(Material.DEEPSLATE_EMERALD_ORE, 700f);
    }};
    Map<Material, Float> Farmer = new HashMap<Material, Float>() {{
        put(Material.POTATOES, 10f);
        put(Material.CARROTS, 10f);
        put(Material.BEETROOTS, 15f);
        put(Material.SUGAR_CANE, 10f);
    }};
    Map<Material, Float> WoodCutter = new HashMap<Material, Float>() {{
       put(Material.ACACIA_LOG, 10f);
       put(Material.BIRCH_LOG, 10f);
       put(Material.CHERRY_LOG, 10f);
       put(Material.JUNGLE_LOG, 10f);
       put(Material.DARK_OAK_LOG, 10f);
       put(Material.MANGROVE_LOG, 10f);
       put(Material.SPRUCE_LOG, 10f);
       put(Material.OAK_LOG, 10f);
       put(Material.AZALEA, 20f);
    }};
    Map<Material, Float> Excavater = new HashMap<Material, Float>() {{
        put(Material.DIRT, 1f);
        put(Material.GRASS_BLOCK, 2f);
        put(Material.MYCELIUM, 10f);
        put(Material.PODZOL, 8f);
        put(Material.GRAVEL, 3f);
        put(Material.SAND, 3f);
        put(Material.SUSPICIOUS_GRAVEL, 100f);
        put(Material.SUSPICIOUS_SAND, 100f);
    }};
    Map<EntityType, Float> EntityXP = new HashMap<EntityType, Float>(){{
        put(EntityType.PLAYER, 100f);
        put(EntityType.ZOMBIE, 10f);
        put(EntityType.PIG, 5f);
        put(EntityType.COW, 5f);
        put(EntityType.SHEEP, 5f);
        put(EntityType.HORSE, 5f);
        put(EntityType.SKELETON, 10f);
        put(EntityType.CREEPER, 10f);
        put(EntityType.SPIDER, 10f);
        put(EntityType.CAVE_SPIDER, 15f);
        put(EntityType.DONKEY, 20f);
        put(EntityType.MULE, 50f);
        put(EntityType.ENDER_DRAGON, 1000f);
        put(EntityType.WITHER, 1000f);
        put(EntityType.DROWNED, 15f);
        put(EntityType.PHANTOM, 20f);
        put(EntityType.SHULKER, 50f);
        put(EntityType.ENDERMAN, 25f);
        put(EntityType.ENDERMITE, 100f);
        put(EntityType.SILVERFISH, 5f);
        put(EntityType.PIGLIN, 10f);
        put(EntityType.PIGLIN_BRUTE, 250f);
        put(EntityType.ZOMBIFIED_PIGLIN, 10f);
        put(EntityType.HOGLIN, 25f);
        put(EntityType.ZOMBIE_HORSE, 100f);
        put(EntityType.VILLAGER, 100f);
        put(EntityType.ZOMBIE_VILLAGER, 10f);
        put(EntityType.SKELETON_HORSE, 100f);
        put(EntityType.WITHER_SKELETON, 20f);
        put(EntityType.BLAZE, 15f);
        put(EntityType.MAGMA_CUBE, 15f);
        put(EntityType.SLIME, 10f);
        put(EntityType.WANDERING_TRADER, 100f);
        put(EntityType.LLAMA, 10f);
        put(EntityType.SNIFFER, 100f);
        put(EntityType.ALLAY, 100f);
        put(EntityType.AXOLOTL, 10f);
        put(EntityType.BAT, 500f);
        put(EntityType.BEE, 10f);
        put(EntityType.CAMEL, 50f);
        put(EntityType.CAT, 10f);
        put(EntityType.CHICKEN, 10f);
        put(EntityType.COD, 10f);
        put(EntityType.DOLPHIN, 25f);
        put(EntityType.GUARDIAN, 100f);
        put(EntityType.ELDER_GUARDIAN, 1000f);
        put(EntityType.EVOKER, 25f);
        put(EntityType.FOX, 15f);
        put(EntityType.FROG, 15f);
        put(EntityType.GHAST, 75f);
        put(EntityType.GLOW_SQUID, 15f);
        put(EntityType.GOAT, 15f);
        put(EntityType.HUSK, 20f);
        put(EntityType.IRON_GOLEM, 100f);
        put(EntityType.PARROT, 25f);
        put(EntityType.PANDA, 25f);
        put(EntityType.OCELOT, 25f);
        put(EntityType.PILLAGER, 20f);
        put(EntityType.POLAR_BEAR, 100f);
        put(EntityType.RABBIT, 10f);
        put(EntityType.RAVAGER, 200f);
        put(EntityType.PUFFERFISH, 10f);
        put(EntityType.SALMON, 10f);
        put(EntityType.SNOW_GOLEM, 20f);
        put(EntityType.SQUID, 10f);
        put(EntityType.STRAY, 20f);
        put(EntityType.STRIDER, 20f);
        put(EntityType.TRADER_LLAMA, 50f);
        put(EntityType.TROPICAL_FISH, 10f);
        put(EntityType.TURTLE, 20f);
        put(EntityType.VEX, 30f);
        put(EntityType.VINDICATOR, 40f);
        put(EntityType.WARDEN, 4000f);
        put(EntityType.WITCH, 30f);
        put(EntityType.WOLF, 15f);





    }};
    @EventHandler
    public void XPGain(BlockBreakEvent event){
        if(event.getPlayer().getGameMode() != GameMode.SURVIVAL) return;
        final File skillfile = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("SurvivalCore")).getDataFolder(), "skills.yml");
        final YamlConfiguration skillconfig = YamlConfiguration.loadConfiguration(skillfile);
        if(event.getBlock().getMetadata("PLACED").isEmpty()) {
            if (Miner.containsKey(event.getBlock().getType())) {
                GetXP("mining", skillconfig, event.getPlayer(), Miner.get(event.getBlock().getType()), skillfile);
            } else if (Farmer.containsKey(event.getBlock().getType())) {
                GetXP("farming", skillconfig, event.getPlayer(), Farmer.get(event.getBlock().getType()), skillfile);
            } else if (WoodCutter.containsKey(event.getBlock().getType())) {
                GetXP("woodcutting", skillconfig, event.getPlayer(), WoodCutter.get(event.getBlock().getType()), skillfile);
            } else if (Excavater.containsKey(event.getBlock().getType())){
                GetXP("excavating", skillconfig, event.getPlayer(), Excavater.get(event.getBlock().getType()), skillfile);
            }
        }
    }

    @EventHandler
    public void AntiDupe(BlockPlaceEvent event){
        if(event.getPlayer().getGameMode() != GameMode.SURVIVAL) return;
        final File skillfile = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("SurvivalCore")).getDataFolder(), "skills.yml");
        final YamlConfiguration skillconfig = YamlConfiguration.loadConfiguration(skillfile);
        if (Farmer.containsKey(event.getBlock().getType())) {
            GetXP("farming", skillconfig, event.getPlayer(), Farmer.get(event.getBlock().getType()), skillfile);
        event.getBlock().setMetadata("PLACED", new FixedMetadataValue(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("SurvivalCore")), "PLACED"));
        }
    }

    @EventHandler
    public void onEnchant(EnchantItemEvent event){
        final File skillfile = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("SurvivalCore")).getDataFolder(), "skills.yml");
        final YamlConfiguration skillconfig = YamlConfiguration.loadConfiguration(skillfile);
        AtomicInteger lvl_total = new AtomicInteger();
        event.getEnchantsToAdd().forEach((ench, lvl) -> lvl_total.addAndGet(lvl));
        GetXP("enchanting", skillconfig, event.getEnchanter(), (float) (event.getExpLevelCost()+(lvl_total.get()*10)), skillfile);
    }

    @EventHandler
    public void onInvSkill(InventoryClickEvent event){
        if(event.getClickedInventory() == null) return;
        final File skillfile = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("SurvivalCore")).getDataFolder(), "skills.yml");
        final YamlConfiguration skillconfig = YamlConfiguration.loadConfiguration(skillfile);
        if(Objects.requireNonNull(event.getClickedInventory()).getType() == InventoryType.ANVIL){
            if(event.getSlot() == 2 && Objects.requireNonNull(event.getCurrentItem()).getType() != Material.AIR){
                AnvilInventory inv = (AnvilInventory) event.getClickedInventory();
                GetXP("forging", skillconfig, (Player)event.getWhoClicked(), (float) (inv.getRepairCost()*10), skillfile);
            }
        } else if (event.getClickedInventory().getType() == InventoryType.GRINDSTONE) {
            GrindstoneInventory inv = (GrindstoneInventory) event.getClickedInventory();
            if(event.getSlot() == 2 && Objects.requireNonNull(event.getCurrentItem()).getType() != Material.AIR){
                AtomicInteger lvl_total = new AtomicInteger();
                Arrays.stream(inv.getStorageContents()).filter(Objects::nonNull).forEach(item -> item.getEnchantments().forEach((ench, lvl) -> lvl_total.addAndGet(lvl)));
                GetXP("forging", skillconfig, (Player)event.getWhoClicked(), (float) lvl_total.get()*10, skillfile);
            }
        }
    }

    @EventHandler
    public void onFishing(PlayerFishEvent event){
        final File skillfile = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("SurvivalCore")).getDataFolder(), "skills.yml");
        final YamlConfiguration skillconfig = YamlConfiguration.loadConfiguration(skillfile);
        if(event.getState() != PlayerFishEvent.State.CAUGHT_FISH || event.getCaught() == null) return;
        Item itementity = (Item) event.getCaught();
        ItemStack item = new ItemStack(itementity.getItemStack());
        if(item.getType() == Material.ENCHANTED_BOOK){
            GetXP("fishing", skillconfig, event.getPlayer(), 1000f, skillfile);
        } else if(Arrays.asList(Material.NAUTILUS_SHELL, Material.NAME_TAG, Material.SADDLE).contains(item.getType())){
            GetXP("fishing", skillconfig, event.getPlayer(), 300f, skillfile);
        } else GetXP("fishing", skillconfig, event.getPlayer(), 50f, skillfile);
    }

    @EventHandler
    public void EntityKill(EntityDeathEvent event){
        if(event.getEntity().getKiller() == null) return;
        if(Objects.requireNonNull(event.getEntity().getKiller()).getType() == EntityType.PLAYER){
            if(EntityXP.containsKey(event.getEntity().getType())){
                final File skillfile = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("SurvivalCore")).getDataFolder(), "skills.yml");
                final YamlConfiguration skillconfig = YamlConfiguration.loadConfiguration(skillfile);
                if(Objects.requireNonNull(event.getEntity().getLastDamageCause()).getCause() == EntityDamageEvent.DamageCause.PROJECTILE){
                    GetXP("bow-ing", skillconfig, event.getEntity().getKiller(), EntityXP.get(event.getEntity().getType())*10, skillfile);
                } else{
                    GetXP("fighting", skillconfig, event.getEntity().getKiller(), EntityXP.get(event.getEntity().getType()), skillfile);
                }
            }
        }
    }

    @EventHandler
    public void DrinkPotion(PlayerItemConsumeEvent event){
        if(Material.POTION == event.getItem().getType()){
            final File skillfile = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("SurvivalCore")).getDataFolder(), "skills.yml");
            final YamlConfiguration skillconfig = YamlConfiguration.loadConfiguration(skillfile);
            PotionMeta potionMeta = (PotionMeta) event.getItem().getItemMeta();
            float xp = 25f;
            if(potionMeta.getBasePotionData().isUpgraded()) xp+= 50;
            if(potionMeta.getBasePotionData().isExtended()) xp+= 50;
            GetXP("brewing", skillconfig, event.getPlayer(), xp, skillfile);
        }
    }

    @EventHandler
    public void PotionClick(PlayerInteractEvent event){
        if(!Arrays.asList(Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK).contains(event.getAction())) return;
        if(event.getItem() == null) return;
        if(Arrays.asList(Material.LINGERING_POTION, Material.SPLASH_POTION).contains(Objects.requireNonNull(event.getItem()).getType())){
            final File skillfile = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("SurvivalCore")).getDataFolder(), "skills.yml");
            final YamlConfiguration skillconfig = YamlConfiguration.loadConfiguration(skillfile);
            PotionMeta potionMeta = (PotionMeta) event.getItem().getItemMeta();
            float xp = 75f;
            if(potionMeta.getBasePotionData().isUpgraded()) xp+= 50;
            if(potionMeta.getBasePotionData().isExtended()) xp+= 50;
            if(event.getItem().getType() == Material.LINGERING_POTION) xp += 50;
            GetXP("brewing", skillconfig, event.getPlayer(), xp, skillfile);
        }
    }
}
