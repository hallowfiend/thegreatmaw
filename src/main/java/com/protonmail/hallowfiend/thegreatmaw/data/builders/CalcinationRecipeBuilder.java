package com.protonmail.hallowfiend.thegreatmaw.data.builders;

import com.protonmail.hallowfiend.thegreatmaw.TheGreatMaw;
import com.protonmail.hallowfiend.thegreatmaw.common.crafting.CalcinationCrucibleRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class CalcinationRecipeBuilder implements RecipeBuilder {
  private Optional<Fluid> fluid;
  private final ItemStack resultStack;
  private final int evaporationTime;
  private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
  private String namespace = "thegreatmaw";

  private CalcinationRecipeBuilder(Optional<Fluid> fluid, int evaporationTime, ItemStack resultStack)
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

  public static CalcinationRecipeBuilder calcinationRecipe(Fluid input, int evaporationTime, ItemStack mainResult) {
    return new CalcinationRecipeBuilder(Optional.ofNullable(input), evaporationTime, mainResult);
  }

  @Override
  public RecipeBuilder group(@Nullable String s) {
    return this;
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
    Advancement.Builder advancementBuilder = output.advancement()
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
            .rewards(AdvancementRewards.Builder.recipe(recipeId))
            .requirements(AdvancementRequirements.Strategy.OR);
    this.criteria.forEach(advancementBuilder::addCriterion);
    CalcinationCrucibleRecipe recipe = new CalcinationCrucibleRecipe(
            this.fluid,
            this.resultStack,
            this.evaporationTime
    );
    output.accept(recipeId, recipe, advancementBuilder.build(id.withPrefix("recipes/")));
  }

  public void build(RecipeOutput consumerIn, String save) {
    if (resultStack == null)
      throw new NullPointerException("Calcination Recipe does not specify a result.");

    if (!resultStack.isEmpty()) {
      if (fluid.isPresent()) {
        ResourceLocation baseFluidLocation = BuiltInRegistries.FLUID.getKey(fluid.get());
        ResourceLocation resultItemLocation = BuiltInRegistries.ITEM.getKey(resultStack.getItem());
        build(consumerIn, TheGreatMaw.MODID + ":calcination/" + resultItemLocation.getPath() + "_from_" + baseFluidLocation.getPath());
        return;
      }
      ResourceLocation resultItemLocation = BuiltInRegistries.ITEM.getKey(resultStack.getItem());
      build(consumerIn, TheGreatMaw.MODID + ":calcination/" + resultItemLocation.getPath());
    }
  }
}
