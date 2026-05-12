package com.protonmail.hallowfiend.thegreatmaw.registry;

import com.protonmail.hallowfiend.thegreatmaw.TheGreatMaw;
import com.protonmail.hallowfiend.thegreatmaw.common.crafting.CalcinationCrucibleRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static vectorwing.farmersdelight.common.registry.ModRecipeTypes.registerRecipeType;

public class MawRecipeTypes {
  public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
          DeferredRegister.create(Registries.RECIPE_TYPE, TheGreatMaw.MODID);

  public static final Supplier<RecipeType<CalcinationCrucibleRecipe>> CALCINATING = RECIPE_TYPES.register("calcinating", () -> registerRecipeType("calcinating"));

}
