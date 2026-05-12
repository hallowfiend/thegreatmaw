package com.protonmail.hallowfiend.thegreatmaw.data;

import com.protonmail.hallowfiend.thegreatmaw.TheGreatMaw;
import com.protonmail.hallowfiend.thegreatmaw.common.crafting.CalcinationCrucibleRecipe;
import com.protonmail.hallowfiend.thegreatmaw.common.util.FluidInputRecipeWrapper;
import com.protonmail.hallowfiend.thegreatmaw.data.builders.CalcinationRecipeBuilder;
import com.protonmail.hallowfiend.thegreatmaw.registry.ModItems;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.WaterFluid;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

public class CalcinationRecipes {

  public static void register(RecipeOutput output) {
    calcinate(output);
  }

  private static void calcinate(RecipeOutput output)
  {
    CalcinationRecipeBuilder.calcinationRecipe(
                    CalcinationCrucibleRecipe.Inputs.of(new SizedFluidIngredient(FluidIngredient.of(Fluids.WATER), 1000)),
                    new FluidInputRecipeWrapper.Outputs(new ItemStack(ModItems.SALT.get(), 1)),
                            200)
                            .save(output);
  }

}
