package com.protonmail.hallowfiend.thegreatmaw.data;

import com.protonmail.hallowfiend.thegreatmaw.MawHelper;
import com.protonmail.hallowfiend.thegreatmaw.registry.ModItems;
import com.protonmail.hallowfiend.thegreatmaw.registry.ModTags;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;

public class MawCraftingRecipes {

  public static void register(RecipeOutput output) {
    recipesBlocks(output);
    recipesMaterials(output);
    recipesSmallBrickDyeing(output);
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
            .define('p', ModItems.PORCELAIN_BRICK_SLAB.get())
            .pattern("p")
            .pattern("p")
            .unlockedBy("has_porcelain_brick_slab", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.PORCELAIN_BRICK_SLAB.get()))
            .save(output, MawHelper.RL("shaped/porcelain_bricks_from_slab"));
    stairRecipe(output, ModItems.PORCELAIN_BRICKS.get(), ModItems.PORCELAIN_BRICK_STAIRS.get());
    slabRecipe(output, ModItems.PORCELAIN_BRICKS.get(), ModItems.PORCELAIN_BRICK_SLAB.get());
    twoByTwo(output, RecipeCategory.BUILDING_BLOCKS, ModItems.PORCELAIN_BRICK.get(), ModItems.PORCELAIN_BRICKS.get(), 1);
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.WHITE_SMALL_PORCELAIN_BRICKS.get(), 4)
            .define('p', ModItems.PORCELAIN_BRICKS.get())
            .pattern("pp ")
            .pattern("pp ")
            .unlockedBy("has_porcelain_bricks", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.PORCELAIN_BRICKS.get()))
            .save(output, MawHelper.RL("shaped/white_small_porcelain_bricks_from_large_bricks"));

  }

  private static void recipesMaterials(RecipeOutput output) {
    ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.UNFIRED_PORCELAIN.get(), 2)
            .requires(Items.CLAY_BALL)
            .requires(Items.BONE_MEAL)
            .unlockedBy("has_clay", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CLAY_BALL))
            .save(output, MawHelper.RL("shapeless/unfired_porcelain"));
  }

  private static void recipesSmallBrickDyeing(RecipeOutput output) {
    smallBrickDyeing(output, ModItems.WHITE_SMALL_PORCELAIN_BRICKS.get(), Tags.Items.DYES_WHITE);
    smallBrickDyeing(output, ModItems.LIGHT_GRAY_SMALL_PORCELAIN_BRICKS.get(), Tags.Items.DYES_LIGHT_GRAY);
    smallBrickDyeing(output, ModItems.GRAY_SMALL_PORCELAIN_BRICKS.get(), Tags.Items.DYES_GRAY);
    smallBrickDyeing(output, ModItems.BLACK_SMALL_PORCELAIN_BRICKS.get(), Tags.Items.DYES_BLACK);
    smallBrickDyeing(output, ModItems.BROWN_SMALL_PORCELAIN_BRICKS.get(), Tags.Items.DYES_BROWN);
    smallBrickDyeing(output, ModItems.RED_SMALL_PORCELAIN_BRICKS.get(), Tags.Items.DYES_RED);
    smallBrickDyeing(output, ModItems.ORANGE_SMALL_PORCELAIN_BRICKS.get(), Tags.Items.DYES_ORANGE);
    smallBrickDyeing(output, ModItems.YELLOW_SMALL_PORCELAIN_BRICKS.get(), Tags.Items.DYES_YELLOW);
    smallBrickDyeing(output, ModItems.LIME_SMALL_PORCELAIN_BRICKS.get(), Tags.Items.DYES_LIME);
    smallBrickDyeing(output, ModItems.GREEN_SMALL_PORCELAIN_BRICKS.get(), Tags.Items.DYES_GREEN);
    smallBrickDyeing(output, ModItems.CYAN_SMALL_PORCELAIN_BRICKS.get(), Tags.Items.DYES_CYAN);
    smallBrickDyeing(output, ModItems.LIGHT_BLUE_SMALL_PORCELAIN_BRICKS.get(), Tags.Items.DYES_LIGHT_BLUE);
    smallBrickDyeing(output, ModItems.BLUE_SMALL_PORCELAIN_BRICKS.get(), Tags.Items.DYES_BLUE);
    smallBrickDyeing(output, ModItems.PURPLE_SMALL_PORCELAIN_BRICKS.get(), Tags.Items.DYES_PURPLE);
    smallBrickDyeing(output, ModItems.MAGENTA_SMALL_PORCELAIN_BRICKS.get(), Tags.Items.DYES_MAGENTA);
    smallBrickDyeing(output, ModItems.PINK_SMALL_PORCELAIN_BRICKS.get(), Tags.Items.DYES_PINK);
  }

  public static void stairRecipe(RecipeOutput output, ItemLike blockInput, ItemLike blockOutput) {
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blockOutput, 4)
            .define('b', blockInput)
            .pattern("b  ")
            .pattern("bb ")
            .pattern("bbb")
            .unlockedBy("has_input_block", InventoryChangeTrigger.TriggerInstance.hasItems(blockInput))
            .save(output, MawHelper.RL("shaped/" + BuiltInRegistries.ITEM.getKey(blockOutput.asItem()).getPath()));

  }

  public static void slabRecipe(RecipeOutput output, ItemLike blockInput, ItemLike blockOutput) {
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blockOutput, 6)
            .define('b', blockInput)
            .pattern("bbb")
            .unlockedBy("has_input_block", InventoryChangeTrigger.TriggerInstance.hasItems(blockInput))
            .save(output, MawHelper.RL("shaped/" + BuiltInRegistries.ITEM.getKey(blockOutput.asItem()).getPath()));

  }

  public static void twoByTwo(RecipeOutput output, RecipeCategory category, ItemLike input, ItemLike outputItem, int amount) {
    ShapedRecipeBuilder.shaped(category, outputItem, amount)
            .define('b', input)
            .pattern("bb ")
            .pattern("bb ")
            .unlockedBy("has_input", InventoryChangeTrigger.TriggerInstance.hasItems(input))
            .save(output, MawHelper.RL("shaped/" + BuiltInRegistries.ITEM.getKey(outputItem.asItem()).getPath()));

  }


  public static void smallBrickDyeing(RecipeOutput output, ItemLike smallBrick, TagKey<Item> dyeTag) {
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, smallBrick, 8)
            .define('p', ModTags.Items.SMALL_PORCELAIN_BRICKS)
            .define('d', dyeTag)
            .pattern("ppp")
            .pattern("pdp")
            .pattern("ppp")
            .unlockedBy("has_porcelain_bricks", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.PORCELAIN_BRICKS.get()))
            .save(output, MawHelper.RL("shaped/" + BuiltInRegistries.ITEM.getKey(smallBrick.asItem()).getPath()));

  }
}
