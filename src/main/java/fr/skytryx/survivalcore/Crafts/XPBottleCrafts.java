package fr.skytryx.survivalcore.Crafts;

import fr.skytryx.survivalcore.SurvivalCore;
import fr.skytryx.survivalcore.commands.CommandXPBottle;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

public class XPBottleCrafts {

    public static ShapedRecipe flask(ItemStack result, ItemStack ingredient, String recipeName) {
        NamespacedKey key = new NamespacedKey(SurvivalCore.getInstance(), recipeName);
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("XXX", "XXX", "XXX");
        recipe.setIngredient('X', new RecipeChoice.ExactChoice(ingredient));
        return recipe;
    }

    public static ShapedRecipe moyenneFlask() {
        return flask(CommandXPBottle.CreateXPFlask(1, 1), CommandXPBottle.CreateXPFlask(0, 1), "moyenneFlaskXP");
    }

    public static ShapedRecipe grandeFlask() {
        return flask(CommandXPBottle.CreateXPFlask(2, 1), CommandXPBottle.CreateXPFlask(1, 1), "grandeFlaskXP");
    }

    public static ShapedRecipe enormeFlask() {
        return flask(CommandXPBottle.CreateXPFlask(3, 1), CommandXPBottle.CreateXPFlask(2, 1), "enormeFlaskXP");
    }
}
