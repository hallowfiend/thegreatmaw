package com.protonmail.hallowfiend.thegreatmaw.common.block.entity;
import com.protonmail.hallowfiend.thegreatmaw.common.crafting.CalcinationCrucibleRecipe;
import com.protonmail.hallowfiend.thegreatmaw.common.util.AcceptabilityCache;
import com.protonmail.hallowfiend.thegreatmaw.common.util.recipes.RecipeCaches;
import com.protonmail.hallowfiend.thegreatmaw.registry.MawRecipeTypes;
import com.protonmail.hallowfiend.thegreatmaw.registry.ModBlockEntityTypes;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Clearable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.cyclops.cyclopscore.fluid.SingleUseTank;
import vectorwing.farmersdelight.common.block.entity.HeatableBlockEntity;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;
import vectorwing.farmersdelight.common.utility.ItemUtils;

import java.util.Objects;
import java.util.Optional;

public class CalcinationCrucibleBlockEntity extends SyncedBlockEntity implements HeatableBlockEntity, Clearable {

  private int evaporationTime;
  private int evaporationTimeTotal;

  private final SingleUseTank tank;

  private boolean searchRecipes = true;
  private CalcinationCrucibleRecipe currentRecipe;
  private String currentRecipeIdSynced = "";

  private final Object2IntOpenHashMap<ResourceLocation> usedRecipeTracker;

  private static final AcceptabilityCache<Fluid> fluidCache = new AcceptabilityCache<>();

  // vaguely based on the ID drying basin

  public CalcinationCrucibleBlockEntity(BlockPos pos, BlockState state) {
    super(ModBlockEntityTypes.CALCINATIONCRUCIBLE.get(), pos, state);
    // Create tank
    this.tank = new SingleUseTank(1000);
    this.usedRecipeTracker = new Object2IntOpenHashMap<>();
  }

  public static void clearFluidCache() {
    fluidCache.clear();
  }

  protected boolean canCook(CalcinationCrucibleRecipe recipe, CalcinationCrucibleBlockEntity crucible) {
    if (!tank.isFull())
      return false;
    if (level == null)
      return false;
    if (!recipe.matches(crucible.tank.getFluid()))
      return false; // make sure the fluid is the same
    return crucible.isHeated(level, worldPosition);
  }

  public int genIngredientHash() {
    return Objects.hash(
            FluidStack.hashFluidAndComponents(tank.getFluid())
    );
  }

  public static void calcinationTick(Level level, BlockPos pos, BlockState state, CalcinationCrucibleBlockEntity crucible) {
    boolean isHeated = crucible.isHeated(level, pos);
    if (isHeated && crucible.hasInput()) {
      if (crucible.searchRecipes) {
        RecipeCaches.CALCINATION_CRUCIBLE.getCachedRecipe(crucible::getRecipe, crucible::genIngredientHash).ifPresentOrElse(holder -> {
          crucible.currentRecipe = holder.value();
          crucible.usedRecipeTracker.addTo(holder.id(),1);
          crucible.currentRecipeIdSynced = holder.id().toString();
        }, () -> {
          crucible.currentRecipe = null;
          crucible.usedRecipeTracker.clear();
          crucible.currentRecipeIdSynced = "";
        });
        crucible.searchRecipes = false;
        if (crucible.currentRecipe.matches(crucible.tank.getFluid()) && crucible.canCook(crucible.currentRecipe, crucible)) {
          crucible.processCooking(crucible.currentRecipe, level, crucible);
        }
        else {
          crucible.evaporationTime = Math.max(0, crucible.evaporationTime);
        }
      }
    }
  }

  private boolean processCooking(CalcinationCrucibleRecipe recipe, Level level, CalcinationCrucibleBlockEntity crucible) {
    if (level == null)
      return false;

    ++evaporationTime;
    evaporationTimeTotal = recipe.getEvaporationTime();
    if (evaporationTime < evaporationTimeTotal) {
      setChanged();
      return false;
    }
    else {
      ItemStack recipeOutput = null;
      if (recipe.getInputFluid().isPresent()) {
        recipeOutput = recipe.getOutputItem();
      }
      Direction direction = Direction.UP;
      // spawn the result
      if (recipeOutput != null) {
        ItemUtils.spawnItemEntity(level, recipeOutput.copy(), worldPosition.getX() + 0.5, worldPosition.getY() + 0.3, worldPosition.getZ() + 0.5,
                direction.getStepX() * 0.08F, 0.25F, direction.getStepZ() * 0.08F);
      }
      // make a hiss sound
      level.playSound(null, worldPosition.getX() + 0.5F, worldPosition.getY() + 0.5F, worldPosition.getZ() + 0.5F, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 0.8F, 1.0F);
      // empty the tank
      crucible.tank.reset();
      evaporationTime = 0;
    }
    return true;
  }


  private Optional<RecipeHolder<CalcinationCrucibleRecipe>> getRecipe() {
    for (RecipeHolder<CalcinationCrucibleRecipe> holder : MawRecipeTypes.getRecipes(level, MawRecipeTypes.CALCINATING)) {
      CalcinationCrucibleRecipe recipe = holder.value();
      if (recipe.matches(tank.getFluid())) {
        return Optional.of(holder);
      }
    }
    return Optional.empty();
  }

  @Override
  public void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
    super.saveAdditional(compound, registries);
    compound.putInt("evaporationTime", evaporationTime);
    compound.putInt("evaporationTimeTotal", evaporationTimeTotal);
    compound.putString("currentRecipeIdSynced", currentRecipeIdSynced);
    CompoundTag compoundRecipes = new CompoundTag();
    usedRecipeTracker.forEach((recipeId, craftedAmount) -> compoundRecipes.putInt(recipeId.toString(), craftedAmount));
    compound.put("RecipesUsed", compoundRecipes);
  }

  @Override
  public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
    super.loadAdditional(tag, provider);

    currentRecipeIdSynced = tag.getString("currentRecipeIdSynced");
    evaporationTime = tag.getInt("evaporationTime");
    evaporationTimeTotal = tag.getInt("evaporationTimeTotal");
    CompoundTag compoundRecipes = tag.getCompound("RecipesUsed");
    for (String key : compoundRecipes.getAllKeys()) {
      usedRecipeTracker.put(ResourceLocation.parse(key), compoundRecipes.getInt(key));
    }
  }

  public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
    CompoundTag tag = new CompoundTag();
    saveAdditional(tag, registries);
    return tag;
  }

  public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider registries) {
    super.handleUpdateTag(tag, registries);
  }

  private boolean hasInput () {
      return tank.isFull();
  }

  public SingleUseTank getTank() {
    return tank;
  }

    @Override
    public void clearContent () {
      tank.reset();
    }
}