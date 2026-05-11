package com.protonmail.hallowfiend.thegreatmaw.data;

import com.protonmail.hallowfiend.thegreatmaw.data.builders.CalcinationRecipeBuilder;
import com.protonmail.hallowfiend.thegreatmaw.registry.ModItems;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidStack;

public class CalcinationRecipes {

  public static final int FAST_COOKING = 50;
  public static final int NORMAL_COOKING = 100;
  public static final int SLOW_COOKING = 200;

  public static void register(RecipeOutput output) {
    calcinate(output);
  }

  private static void calcinate(RecipeOutput output)
  {
    CalcinationRecipeBuilder.calcinationRecipe(Fluids.WATER, SLOW_COOKING, ModItems.SALT.get().getDefaultInstance());
  }

}
