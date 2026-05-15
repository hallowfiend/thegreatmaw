package com.protonmail.hallowfiend.thegreatmaw.common.util.recipes;

import com.protonmail.hallowfiend.thegreatmaw.MawHelper;
import com.protonmail.hallowfiend.thegreatmaw.common.util.DummyRecipeWrapper;
import com.protonmail.hallowfiend.thegreatmaw.registry.MawRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jline.utils.Log;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class AbstractedRecipeType<T extends DummyRecipeWrapper> implements RecipeType<T> {

  private final Map<ResourceLocation, RecipeHolder<T>> cachedRecipes = new HashMap<>();
  private final String typeName;

  public AbstractedRecipeType(String name) {
    this.typeName = "MawRecipeType[" + MawHelper.RL(name) + "]";
  }

  @Override
  public String toString() {
    return typeName;
  }

  public Map<ResourceLocation, RecipeHolder<T>> getRecipeMap(Level level) {
    if (level == null) {
      // we should pretty much always have a world, but use the overworld as a fallback
      MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
      if (server != null) {
        level = server.getLevel(Level.OVERWORLD);
      }
      if (level == null) {
        Log.error("detected someone trying to get recipes for {} with no world available - returning empty recipe list", this);
        return Collections.emptyMap();
      }
    }

    if (cachedRecipes.isEmpty()) {
      RecipeManager recipeManager = level.getRecipeManager();
      List<RecipeHolder<T>> recipes = recipeManager.getRecipesFor(this, CraftingInput.EMPTY, level);
      recipes.forEach(recipe -> cachedRecipes.put(recipe.id(), recipe));
      }
    return cachedRecipes;
  }

  public Collection<RecipeHolder<T>> allRecipeHolders(Level level) {
    return Collections.unmodifiableCollection(getRecipeMap(level).values());
  }

  public Collection<T> allRecipes(Level level) {
    return getRecipeMap(level).values().stream().map(RecipeHolder::value).toList();
  }

  public Stream<RecipeHolder<T>> stream(Level level) {
    return getRecipeMap(level).values().stream();
  }

  public Optional<RecipeHolder<T>> findFirst(Level level, Predicate<T> predicate) {
    return stream(level)
            .filter(holder -> predicate.test(holder.value()))
            .findFirst();
  }

  public Optional<RecipeHolder<T>> getRecipe(Level level, ResourceLocation recipeId) {
    return Optional.ofNullable(getRecipeMap(level).get(recipeId));
  }

  public static void clearCachedRecipes() {
    for (var type : MawRecipeTypes.RECIPE_TYPES.getEntries()) {
      if (type.get() instanceof AbstractedRecipeType<?> mawType) {
        mawType.cachedRecipes.clear();
      }
    }
    RecipeCaches.clearAll();
  }

}
