package com.protonmail.hallowfiend.thegreatmaw.registry;

import com.protonmail.hallowfiend.thegreatmaw.TheGreatMaw;
import com.protonmail.hallowfiend.thegreatmaw.common.crafting.CalcinationCrucibleRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MawRecipeSerializers {

  public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS
          = DeferredRegister.create(Registries.RECIPE_SERIALIZER, TheGreatMaw.MODID);

  public static final Supplier<RecipeSerializer<CalcinationCrucibleRecipe>> CALCINATING
          = RECIPE_SERIALIZERS.register(RecipeTypeStrings.CALCINATING,
          () -> new CalcinationCrucibleRecipe.Serializer<>(CalcinationCrucibleRecipe::new));

}
