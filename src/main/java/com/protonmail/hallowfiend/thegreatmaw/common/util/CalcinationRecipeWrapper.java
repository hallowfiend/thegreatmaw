package com.protonmail.hallowfiend.thegreatmaw.common.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;

public interface CalcinationRecipeWrapper extends RecipeInput {
  FluidStack getFluid();
  long getTankCapacity();

  @Override
  default boolean isEmpty() {
    if (getFluid().isEmpty())
      return false;
    return RecipeInput.super.isEmpty();
  }
}
