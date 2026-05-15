package com.protonmail.hallowfiend.thegreatmaw.data;

import com.protonmail.hallowfiend.thegreatmaw.MawHelper;
import com.protonmail.hallowfiend.thegreatmaw.registry.ModItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;

public class MawStonecuttingRecipes {

  public static void register(RecipeOutput output) {
    recipesStonecutting(output);
  }

  private static void recipesStonecutting(RecipeOutput output) {
    SingleItemRecipeBuilder
            .stonecutting(Ingredient.of(ModItems.PORCELAIN_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS, ModItems.PORCELAIN_BRICK_SLAB.get(), 2)
            .unlockedBy("has_porcelain_bricks", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.PORCELAIN_BRICKS.get()))
            .save(output, MawHelper.RL("stonecutting/porcelain_brick_slab"));
  }

}
