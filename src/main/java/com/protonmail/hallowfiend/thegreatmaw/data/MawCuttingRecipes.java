package com.protonmail.hallowfiend.thegreatmaw.data;

import com.protonmail.hallowfiend.thegreatmaw.MawHelper;
import com.protonmail.hallowfiend.thegreatmaw.registry.ModItems;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.crafting.CompoundIngredient;
import vectorwing.farmersdelight.common.crafting.ingredient.ItemAbilityIngredient;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;
import vectorwing.farmersdelight.integration.crafttweaker.handlers.CuttingBoardRecipeHandler;

public class MawCuttingRecipes {

  public static Ingredient PICKAXES = matchesTool(ItemAbilities.PICKAXE_DIG, ItemTags.PICKAXES);

  private static Ingredient matchesTool(ItemAbility toolAction, TagKey<Item> fallbackTag) {
    return CompoundIngredient.of(new ItemAbilityIngredient(toolAction).toVanilla(), Ingredient.of(fallbackTag));
  }

  public static void register(RecipeOutput output) {
    blockSalvaging(output);
  }

  private static void blockSalvaging(RecipeOutput output) {
    CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.PORCELAIN_BRICKS.get()), PICKAXES, ModItems.PORCELAIN_BRICK.get(), 4)
            .salvaging()
            .save(output, MawHelper.RL("cutting/salvaging_porcelain_bricks"));
  }

}
