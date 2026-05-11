package com.protonmail.hallowfiend.thegreatmaw.common.block.entity;
import com.protonmail.hallowfiend.thegreatmaw.common.block.CalcinationCrucibleBlock;
import com.protonmail.hallowfiend.thegreatmaw.common.crafting.CalcinationCrucibleRecipe;
import com.protonmail.hallowfiend.thegreatmaw.common.util.CalcinationRecipeWrapper;
import com.protonmail.hallowfiend.thegreatmaw.registry.MawRecipeTypes;
import com.protonmail.hallowfiend.thegreatmaw.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Clearable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import org.cyclops.cyclopscore.fluid.SingleUseTank;
import org.cyclops.cyclopscore.helper.IModHelpersNeoForge;
import vectorwing.farmersdelight.common.block.entity.HeatableBlockEntity;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;
import vectorwing.farmersdelight.common.utility.ItemUtils;

import java.util.List;
import java.util.Optional;

public class CalcinationCrucibleBlockEntity extends SyncedBlockEntity implements HeatableBlockEntity, Clearable {

  private int evaporationTime;
  private int evaporationTimeTotal;
  private Fluid containedFluid;

  private final SingleUseTank tank;

  private CalcinationRecipeWrapper calcinationRecipeWrapper;
  // vaguely based on the ID drying basin

  public CalcinationCrucibleBlockEntity(BlockPos pos, BlockState state) {
    super(ModBlockEntityTypes.CALCINATIONCRUCIBLE.get(), pos, state);

    // Create tank
    this.tank = new SingleUseTank(IModHelpersNeoForge.get().getFluidHelpers().getBucketVolume());
    // Read the fluid inside (none by default)
    this.containedFluid = null;
  }

  protected boolean canCook(CalcinationCrucibleRecipe recipe, CalcinationCrucibleBlockEntity crucible) {
    if (!tank.isFull())
      return false;
    if (level == null)
      return false;
    if (!crucible.isHeated(level, worldPosition))
      return false;
    return tank.getFluid().getFluid().equals(recipe.getFluidIngredient().get()); // make sure the fluid is the same
  }

  public static void calcinationTick(Level level, BlockPos pos, BlockState state, CalcinationCrucibleBlockEntity crucible) {
    boolean didInventoryChange = false;
    boolean isHeated = crucible.isHeated(level, pos);
    RecipeManager recipeManager = level.getRecipeManager();
    List<RecipeHolder<CalcinationCrucibleRecipe>> recipes = recipeManager.getAllRecipesFor(MawRecipeTypes.CALCINATING.get());
    Optional<CalcinationCrucibleRecipe> recipe = Optional.ofNullable(crucible.getRecipe(recipes, crucible.tank.getFluid()));
    boolean canCraft = recipe.isPresent() && crucible.canCook(recipe.get(), crucible);
    if (isHeated && crucible.hasInput()) {
      if (canCraft) {
        didInventoryChange = true;
        crucible.processCooking(crucible.tank.getFluid(), level);
      }
      else {
        crucible.evaporationTime = Mth.clamp(crucible.evaporationTime - 2, 0, crucible.evaporationTimeTotal);
      }
    }
    else if (crucible.evaporationTime > 0) {
      crucible.evaporationTime = Mth.clamp(crucible.evaporationTime - 2, 0, crucible.evaporationTimeTotal);
    }

    if (didInventoryChange) {
      crucible.inventoryChanged();
    }
  }

  private void processCooking(FluidStack fluid, Level level) {
    assert this.level != null;
    RecipeManager recipeManager = level.getRecipeManager();
    List<RecipeHolder<CalcinationCrucibleRecipe>> recipes = recipeManager.getAllRecipesFor(MawRecipeTypes.CALCINATING.get());
    Optional<CalcinationCrucibleRecipe> recipe = Optional.ofNullable(getRecipe(recipes, tank.getFluid()));
    boolean canCraft = recipe.isPresent() && canCook(recipe.get(), this);
    if (canCraft) {
      if (evaporationTimeTotal <= 0) {
        evaporationTimeTotal = recipe.get().getEvaporationTime();
        evaporationTime = 0;
      }
      else {
        evaporationTime++;
        if (evaporationTime >= evaporationTimeTotal) {
          evaporationTime = 0;
          craft(recipe.get());
        }
      }
    }
    else {
      this.evaporationTime = 0;
    }
  }

  private CalcinationCrucibleRecipe getRecipe(List<RecipeHolder<CalcinationCrucibleRecipe>> recipes, FluidStack inventory) {
    for (RecipeHolder<CalcinationCrucibleRecipe> recipeHolder : recipes) {
      CalcinationCrucibleRecipe recipe = recipeHolder.value();
      boolean ingredientFound = false;
      Optional<Fluid> fluidIngredient = recipe.getFluidIngredient();
        if (fluidIngredient.isPresent() && fluidIngredient.get() == tank.getFluid().getFluid()) {
          ingredientFound = true;
          break;
        }
        if (!ingredientFound) {
          continue;
        }
        return recipe;
    }
    return null;
  }

  private void craft (CalcinationCrucibleRecipe recipe){
      if (!canCook(recipe, this))
        return;
      ItemStack recipeOutput = recipe.assemble();
      assert level != null;
      Direction direction = Direction.SOUTH;
      // spawn the result
      ItemUtils.spawnItemEntity(level, recipeOutput.copy(), worldPosition.getX() + 0.5, worldPosition.getY() + 0.3, worldPosition.getZ() + 0.5,
              direction.getStepX() * 0.08F, 0.25F, direction.getStepZ() * 0.08F);
      // make a hiss sound
      level.playSound(null, worldPosition.getX() + 0.5F, worldPosition.getY() + 0.5F, worldPosition.getZ() + 0.5F, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 0.8F, 1.0F);
      // empty the tank and reset the evaporation time
      evaporationTime = 0;
      tank.reset();
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