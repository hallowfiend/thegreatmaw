package com.protonmail.hallowfiend.thegreatmaw.registry;

import com.protonmail.hallowfiend.thegreatmaw.TheGreatMaw;
import com.protonmail.hallowfiend.thegreatmaw.common.crafting.CalcinationCrucibleRecipe;
import com.protonmail.hallowfiend.thegreatmaw.common.util.DummyRecipeWrapper;
import com.protonmail.hallowfiend.thegreatmaw.common.util.recipes.AbstractedRecipeType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

public class MawRecipeTypes {

  public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
          DeferredRegister.create(Registries.RECIPE_TYPE, TheGreatMaw.MODID);

  public static final Supplier<AbstractedRecipeType<CalcinationCrucibleRecipe>> CALCINATING
          = register(RecipeTypeStrings.CALCINATING, AbstractedRecipeType::new);

  private static <T extends AbstractedRecipeType<?>> Supplier<T> register(String name, Function<String, T> factory) {
    return RECIPE_TYPES.register(name, () -> factory.apply(name));
  }

  public static <T extends DummyRecipeWrapper> Collection<RecipeHolder<T>> getRecipes(Level level, Supplier<AbstractedRecipeType<T>> sup) {
    return sup.get().allRecipeHolders(level);
  }
}
