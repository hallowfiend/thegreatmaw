package com.protonmail.hallowfiend.thegreatmaw.common.util.recipes;

import com.protonmail.hallowfiend.thegreatmaw.common.crafting.CalcinationCrucibleRecipe;

public class RecipeCaches {
  public static final RecipeCache<CalcinationCrucibleRecipe> CALCINATION_CRUCIBLE = new RecipeCache<>();

  public static void clearAll() {
    CALCINATION_CRUCIBLE.clear();
  }
}
