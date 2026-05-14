package com.protonmail.hallowfiend.thegreatmaw.data;

import com.protonmail.hallowfiend.thegreatmaw.MawHelper;
import com.protonmail.hallowfiend.thegreatmaw.registry.ModItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;

public class MawCraftingRecipes {

  public static void register(RecipeOutput output) {
    recipesBlocks(output);
    recipesMaterials(output);
  }
  private static void recipesBlocks(RecipeOutput output) {
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.UNFIRED_CALCINATIONCRUCIBLE.get(), 1)
            .define('p', ModItems.UNFIRED_PORCELAIN.get())
            .pattern("p p")
            .pattern("p p")
            .pattern("ppp")
            .unlockedBy("has_porcelain", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.UNFIRED_PORCELAIN.get()))
            .save(output, MawHelper.RL("shaped/unfired_calcination_crucible"));

  }

  private static void recipesMaterials(RecipeOutput output) {
    ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.UNFIRED_PORCELAIN.get(), 2)
            .requires(Items.CLAY_BALL)
            .requires(Items.BONE_MEAL)
            .unlockedBy("has_clay", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CLAY_BALL))
            .save(output, MawHelper.RL("shapeless/unfired_porcelain"));
  }

}
