package com.protonmail.hallowfiend.thegreatmaw.data;

import com.protonmail.hallowfiend.thegreatmaw.MawHelper;
import com.protonmail.hallowfiend.thegreatmaw.registry.ModItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;

public class MawSmeltingRecipes {

  public static void register(RecipeOutput output) {
    recipesSmelting(output);
  }

  private static void recipesSmelting (RecipeOutput output) {
    SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.UNFIRED_CALCINATIONCRUCIBLE.get()), RecipeCategory.MISC, ModItems.CALCINATIONCRUCIBLE.get(), 0.1f, 300)
            .unlockedBy("has_unfired_crucible", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.UNFIRED_CALCINATIONCRUCIBLE.get()))
            .save(output, MawHelper.RL("smelting/crucible_firing"));
    SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.UNFIRED_PORCELAIN.get()), RecipeCategory.MISC, ModItems.PORCELAIN_BRICK.get(), 0.15f, 300)
            .unlockedBy("has_porcelain", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.UNFIRED_PORCELAIN.get()))
            .save(output, MawHelper.RL("smelting/porcelain_brick"));
  }

}
