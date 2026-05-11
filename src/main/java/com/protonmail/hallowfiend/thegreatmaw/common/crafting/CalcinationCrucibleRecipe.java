package com.protonmail.hallowfiend.thegreatmaw.common.crafting;

import com.protonmail.hallowfiend.thegreatmaw.common.util.CalcinationRecipeWrapper;
import com.protonmail.hallowfiend.thegreatmaw.registry.MawRecipeSerializers;
import com.protonmail.hallowfiend.thegreatmaw.registry.MawRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class CalcinationCrucibleRecipe implements Recipe<CalcinationRecipeWrapper> {
  private final Optional<Fluid> fluidIngredient;
  private final ItemStack output;
  private final int evaporationTime;

  public CalcinationCrucibleRecipe(Optional<Fluid> fluidIngredient, ItemStack output, int evaporationTime) {
    this.fluidIngredient = fluidIngredient;
    this.output = output;
    this.evaporationTime = evaporationTime;
  }

  @Override
  public boolean matches(CalcinationRecipeWrapper calcinationRecipeWrapper, Level level) {
    return (this.fluidIngredient.isPresent() && this.fluidIngredient.get().isSame(calcinationRecipeWrapper.getFluid().getFluid()));
  }

  @Override
  public ItemStack assemble(CalcinationRecipeWrapper calcinationRecipeWrapper, HolderLookup.Provider provider) {
    return this.output.copy();
  }

  public ItemStack assemble() {
    return assemble(null, null);
  }

  @Override
  public boolean canCraftInDimensions(int i, int i1) {
    return true;
  }

  public Optional<Fluid> getFluidIngredient() {
    return fluidIngredient;
  }

  public int getEvaporationTime()
  {
    return evaporationTime;
  }

  @Override
  public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider provider) {
    return output;
  }

  public ItemStack getResultItem() {
    return getResultItem(null);
  }

  @Override
  public RecipeSerializer<?> getSerializer() {
    return MawRecipeSerializers.CALCINATING.get();
  }

  @Override
  public RecipeType<?> getType() {
    return MawRecipeTypes.CALCINATING.get();
  }
}
