package com.protonmail.hallowfiend.thegreatmaw.data;

import com.protonmail.hallowfiend.thegreatmaw.TheGreatMaw;
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

  public static void register(RecipeOutput output) {
    calcinate(output);
  }

  private static void calcinate(RecipeOutput output)
  {
    CalcinationRecipeBuilder.calcinationRecipe(new FluidStack(Fluids.WATER, 1000), ModItems.SALT.get().getDefaultInstance(), 200)
            .save(output);
  }

}
