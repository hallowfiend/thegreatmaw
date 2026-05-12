package com.protonmail.hallowfiend.thegreatmaw.data.builders;

import com.protonmail.hallowfiend.thegreatmaw.TheGreatMaw;
import com.protonmail.hallowfiend.thegreatmaw.common.crafting.CalcinationCrucibleRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class CalcinationRecipeBuilder implements RecipeBuilder {
  private final FluidStack fluid;
  private final ItemStack resultStack;
  private final int evaporationTime;
  private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
  @Nullable
  private String namespace;

  private CalcinationRecipeBuilder(FluidStack fluid, ItemStack resultStack, int evaporationTime)
  {
    this.fluid = fluid;
    this.evaporationTime = evaporationTime;
    this.resultStack = resultStack;
  }

  @Override
  public CalcinationRecipeBuilder unlockedBy(String criterionName, Criterion<?> criterionTrigger) {
    this.criteria.put(criterionName, criterionTrigger);
    return this;
  }

  public static CalcinationRecipeBuilder calcinationRecipe(FluidStack input, ItemStack mainResult, int evaporationTime) {
    return new CalcinationRecipeBuilder(input, mainResult, evaporationTime);
  }

  @Override
  public RecipeBuilder group(@Nullable String s) {
    return this;
  }

  public CalcinationRecipeBuilder setNamespace(String namespace) {
    this.namespace = namespace;
    return this;
  }

  public void saveToMaw(RecipeOutput output) {
    this.setNamespace(TheGreatMaw.MODID).save(output);
  }

  @Override
  public Item getResult() {
    return this.resultStack.getItem();
  }

  public void save(RecipeOutput output) {
    ResourceLocation defaultLocation = RecipeBuilder.getDefaultRecipeId(resultStack.getItem());
    save(output, ResourceLocation.fromNamespaceAndPath(this.namespace != null ? namespace : defaultLocation.getNamespace(), defaultLocation.getPath()).withPrefix("calcinating/"));
  }

  @Override
  public void save(RecipeOutput output, ResourceLocation id) {
    ResourceLocation recipeId = id;
    CalcinationCrucibleRecipe recipe = new CalcinationCrucibleRecipe(
            this.fluid,
            this.resultStack,
            this.evaporationTime
    );
    output.accept(recipeId, recipe, null);
  }
}
