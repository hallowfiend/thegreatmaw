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
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import org.cyclops.cyclopscore.fluid.SingleUseTank;
import org.cyclops.cyclopscore.helper.IModHelpersNeoForge;
import vectorwing.farmersdelight.common.block.entity.HeatableBlockEntity;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;
import vectorwing.farmersdelight.common.utility.ItemUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CalcinationCrucibleBlockEntity extends SyncedBlockEntity implements HeatableBlockEntity, Clearable {

  private int evaporationTime;
  private int evaporationTimeTotal;

  private final SingleUseTank tank;

  private CalcinationRecipeWrapper calcinationRecipeWrapper;
  // vaguely based on the ID drying basin

  public CalcinationCrucibleBlockEntity(BlockPos pos, BlockState state) {
    super(ModBlockEntityTypes.CALCINATIONCRUCIBLE.get(), pos, state);

    // Create tank
    this.tank = new SingleUseTank(IModHelpersNeoForge.get().getFluidHelpers().getBucketVolume());
  }

  protected boolean canCook(CalcinationCrucibleBlockEntity crucible) {
    if (!tank.isFull())
      return false;
    if (level == null)
      return false;
    return crucible.isHeated(level, worldPosition);
  }

  public static void calcinationTick(Level level, BlockPos pos, BlockState state, CalcinationCrucibleBlockEntity crucible) {
    boolean didInventoryChange = false;
    boolean isHeated = crucible.isHeated(level, pos);
    CalcinationCrucibleRecipe recipe = crucible.getRecipe(level.getRecipeManager().getAllRecipesFor(MawRecipeTypes.CALCINATING.get()), crucible.tank.getFluid());
    if (isHeated && crucible.hasInput()) {
      if (recipe != null && crucible.canCook(crucible)) {
        crucible.processCooking(crucible.tank.getFluid(), level);
      }
      else {
        crucible.evaporationTime = Mth.clamp(crucible.evaporationTime - 2, 0, crucible.evaporationTimeTotal);
      }
    }
    else if (crucible.evaporationTime > 0) {
      crucible.evaporationTime = Mth.clamp(crucible.evaporationTime - 2, 0, crucible.evaporationTimeTotal);
    }
  }

  private void processCooking(FluidStack fluid, Level level) {
    assert this.level != null;
    RecipeManager recipeManager = level.getRecipeManager();
    List<RecipeHolder<CalcinationCrucibleRecipe>> recipes = recipeManager.getAllRecipesFor(MawRecipeTypes.CALCINATING.get());
    @Nullable CalcinationCrucibleRecipe recipe = getRecipe(recipes, fluid);
    if (recipe != null && recipe.getFluidIngredient() == fluid) {
      ++evaporationTime;
      evaporationTimeTotal = recipe.getEvaporationTime();
    }
    if (evaporationTime >= evaporationTimeTotal)
    {
      if (!canCook(this))
        return;
      ItemStack recipeOutput = null;
      if (recipe != null) {
        recipeOutput = recipe.getResultItem();
      }
      evaporationTime = 0;
      Direction direction = Direction.UP;
      // spawn the result
      if (recipeOutput != null) {
        ItemUtils.spawnItemEntity(level, recipeOutput.copy(), worldPosition.getX() + 0.5, worldPosition.getY() + 0.3, worldPosition.getZ() + 0.5,
                direction.getStepX() * 0.08F, 0.25F, direction.getStepZ() * 0.08F);
      }
      // make a hiss sound
      level.playSound(null, worldPosition.getX() + 0.5F, worldPosition.getY() + 0.5F, worldPosition.getZ() + 0.5F, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 0.8F, 1.0F);
      // empty the tank
      tank.reset();
    }
  }


  private CalcinationCrucibleRecipe getRecipe(List<RecipeHolder<CalcinationCrucibleRecipe>> recipes, FluidStack fluidStack) {
    for (RecipeHolder<CalcinationCrucibleRecipe> recipeHolder : recipes) {
      CalcinationCrucibleRecipe recipe = recipeHolder.value();
      FluidStack fluidStack1 = recipe.getFluidIngredient(); {
        if (fluidStack == fluidStack1) {
          return recipe;
        }
      }
    }
    return null;
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