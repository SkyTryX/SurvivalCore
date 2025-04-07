package fr.skytryx.survivalcore;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Util {

    // Fonction qui remplit un inventaire avec des stained glass (du slot min au slot max)
    public static void StainedGlass(int min, int max, Inventory inv){
        ItemStack StainedGlass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta IMStainedGlass = StainedGlass.getItemMeta();
        IMStainedGlass.displayName(Component.text(" "));
        StainedGlass.setItemMeta(IMStainedGlass);
        for(int i = min; i < max; i++)
            inv.setItem(i, StainedGlass);
    }

    // Fonction qui créé un item avec en paramètre un materiau, un nom
    public static ItemStack CreateItem(Material mat, String name) {
        ItemStack CreatedItem = new ItemStack(mat);
        ItemMeta IMCreatedItem = CreatedItem.getItemMeta();
        IMCreatedItem.displayName(Component.text(name));
        CreatedItem.setItemMeta(IMCreatedItem);
        return CreatedItem;
    }

    // Fonction qui créé un item avec en paramètre un materiau, un nom
    public static ItemStack CreateItem(Material mat, String name, int number_items) {
        ItemStack CreatedItem = new ItemStack(mat);
        CreatedItem.setAmount(number_items);
        ItemMeta IMCreatedItem = CreatedItem.getItemMeta();
        IMCreatedItem.displayName(Component.text(name));
        CreatedItem.setItemMeta(IMCreatedItem);
        return CreatedItem;
    }

    // Fonction qui créé un item avec en paramètre un materiau, un nom et du lore
    public static ItemStack CreateItem(Material mat, String name, List<String> lore) {
        ItemStack CreatedItem = new ItemStack(mat);
        ItemMeta IMCreatedItem = CreatedItem.getItemMeta();
        IMCreatedItem.displayName(Component.text(name));
        if(!lore.isEmpty())
            IMCreatedItem.setLore(lore);
        CreatedItem.setItemMeta(IMCreatedItem);
        return CreatedItem;
    }

    // Fonction qui créé une recette de craft
    public static void CreateRecipe(ItemStack item, List<String> shape, Map<Character, Material> map, String name){
        NamespacedKey key = new NamespacedKey(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("SurvivalCore")), name);
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape(shape.get(0), shape.get(1), shape.get(2));
        map.forEach(recipe::setIngredient);
        Bukkit.getServer().addRecipe(recipe);
    }
    // Fonction qui créé un item sous forme de tête
    public static ItemStack CreateHead(String name, String owner){
        ItemStack Head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta IMHead = (SkullMeta) Head.getItemMeta();
        IMHead.setOwningPlayer(Objects.requireNonNull(Bukkit.getOfflinePlayer(UUID.fromString(owner))));
        IMHead.displayName(Component.text("§6"+name+" §bLucky Block"));
        Head.setItemMeta(IMHead);
        return Head;
    }
}
