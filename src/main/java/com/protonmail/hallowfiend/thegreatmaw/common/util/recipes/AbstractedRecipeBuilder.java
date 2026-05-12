package com.protonmail.hallowfiend.thegreatmaw.common.util.recipes;

import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import javax.annotation.Nullable;

public abstract class AbstractedRecipeBuilder implements RecipeBuilder {

  @Override
  public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
    return this;
  }

  @Override
  public RecipeBuilder group(@Nullable String group) {
    return this;
  }

  @Override
  public Item getResult() {
    return Items.AIR;
  }

}
