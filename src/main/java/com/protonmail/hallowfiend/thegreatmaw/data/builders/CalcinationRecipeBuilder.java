package com.protonmail.hallowfiend.thegreatmaw.data.builders;

import com.protonmail.hallowfiend.thegreatmaw.TheGreatMaw;
import com.protonmail.hallowfiend.thegreatmaw.common.crafting.CalcinationCrucibleRecipe;
import com.protonmail.hallowfiend.thegreatmaw.common.util.FluidInputRecipeWrapper;
import com.protonmail.hallowfiend.thegreatmaw.common.util.recipes.AbstractedRecipeBuilder;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class CalcinationRecipeBuilder extends AbstractedRecipeBuilder {
  private final CalcinationCrucibleRecipe.Inputs inputs;
  private final CalcinationCrucibleRecipe.Outputs outputs;
  private final int evaporationTime;
  private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
  @Nullable
  private String namespace;

  private CalcinationRecipeBuilder(CalcinationCrucibleRecipe.Inputs inputs, CalcinationCrucibleRecipe.Outputs outputs, int evaporationTime)
  {
    this.inputs = inputs;
    this.outputs = outputs;
    this.evaporationTime = evaporationTime;
  }

  public static CalcinationRecipeBuilder calcinationRecipe(CalcinationCrucibleRecipe.Inputs inputs, CalcinationCrucibleRecipe.Outputs outputs, int evaporationTime) {
    return new CalcinationRecipeBuilder(inputs, outputs, evaporationTime);
  }

  public static ResourceLocation getDefaultRecipeId(ItemLike itemLike) {
    return Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(itemLike.asItem()));
  }

  public CalcinationRecipeBuilder setNamespace(String namespace) {
    this.namespace = namespace;
    return this;
  }

  public void save(RecipeOutput output) {
    ResourceLocation defaultLocation = getDefaultRecipeId(outputs.outputItem().getItem());
    save(output, ResourceLocation.fromNamespaceAndPath(this.namespace != null ? namespace : defaultLocation.getNamespace(), defaultLocation.getPath()).withPrefix("calcinating/"));
  }

  @Override
  public void save(RecipeOutput output, ResourceLocation id) {
    output.accept(id, new CalcinationCrucibleRecipe(inputs, outputs, evaporationTime),
            null);
  }
}
