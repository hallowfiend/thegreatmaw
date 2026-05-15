package com.protonmail.hallowfiend.thegreatmaw.data;

import com.protonmail.hallowfiend.thegreatmaw.MawHelper;
import com.protonmail.hallowfiend.thegreatmaw.registry.ModItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
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
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.PORCELAIN_BRICKS.get(), 1)
            .define('p', ModItems.PORCELAIN_BRICK.get())
            .pattern("pp")
            .pattern("pp")
            .unlockedBy("has_porcelain_brick", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.PORCELAIN_BRICK.get()))
            .save(output, MawHelper.RL("shaped/porcelain_bricks"));
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.PORCELAIN_BRICKS.get(), 1)
            .define('p', ModItems.PORCELAIN_BRICK_SLAB.get())
            .pattern("p")
            .pattern("p")
            .unlockedBy("has_porcelain_brick_slab", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.PORCELAIN_BRICK_SLAB.get()))
            .save(output, MawHelper.RL("shaped/porcelain_bricks_from_slab"));
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.PORCELAIN_BRICK_SLAB.get(), 6)
            .define('p', ModItems.PORCELAIN_BRICKS.get())
            .pattern("ppp")
            .unlockedBy("has_porcelain_bricks", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.PORCELAIN_BRICKS.get()))
            .save(output, MawHelper.RL("shaped/porcelain_brick_slab"));

  }

  private static void recipesMaterials(RecipeOutput output) {
    ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.UNFIRED_PORCELAIN.get(), 2)
            .requires(Items.CLAY_BALL)
            .requires(Items.BONE_MEAL)
            .unlockedBy("has_clay", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CLAY_BALL))
            .save(output, MawHelper.RL("shapeless/unfired_porcelain"));
  }

}
