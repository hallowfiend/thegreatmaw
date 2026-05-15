package com.protonmail.hallowfiend.thegreatmaw.common.block.entity;

import com.protonmail.hallowfiend.thegreatmaw.registry.ModBlockEntityTypes;
import com.protonmail.hallowfiend.thegreatmaw.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Clearable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vectorwing.farmersdelight.common.block.entity.HeatableBlockEntity;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;
import vectorwing.farmersdelight.common.utility.ItemUtils;

import static com.protonmail.hallowfiend.thegreatmaw.common.block.CalcinationCrucibleBlock.FULL;

public class CalcinationCrucibleBlockEntity extends SyncedBlockEntity implements HeatableBlockEntity, Clearable {

  private int evaporationTime;
  private int evaporationTimeTotal;

  // vaguely based on the ID drying basin

  public CalcinationCrucibleBlockEntity(BlockPos pos, BlockState state) {
    super(ModBlockEntityTypes.CALCINATIONCRUCIBLE.get(), pos, state);
  }

  protected boolean canCook(CalcinationCrucibleBlockEntity crucible) {
    if (!crucible.getBlockState().getValue(FULL))
      return false;
    if (level == null)
      return false;
    return crucible.isHeated(level, worldPosition);
  }

  public static void calcinationTick(Level level, BlockPos pos, BlockState state, CalcinationCrucibleBlockEntity crucible) {
    if (crucible.canCook(crucible)) {
          crucible.processCooking(level, crucible);
        }
      }

  private void processCooking(Level level, CalcinationCrucibleBlockEntity crucible) {
    if (level == null)
      return;

    ++evaporationTime;
    evaporationTimeTotal = 200;
    if (evaporationTime < evaporationTimeTotal) {
      setChanged();
    }
    else {
      ItemStack recipeOutput = new ItemStack(ModItems.SALT.get(), 1);
      Direction direction = Direction.UP;
      // spawn the result
      ItemUtils.spawnItemEntity(level, recipeOutput.copy(), worldPosition.getX() + 0.5, worldPosition.getY() + 0.3, worldPosition.getZ() + 0.5,
              direction.getStepX() * 0.08F, 0.25F, direction.getStepZ() * 0.08F);
      // make a hiss sound
      level.playSound(null, worldPosition.getX() + 0.5F, worldPosition.getY() + 0.5F, worldPosition.getZ() + 0.5F, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 0.8F, 1.0F);
      // empty the "tank"
      BlockState pState = crucible.getBlockState();
      level.setBlockAndUpdate(crucible.getBlockPos(), pState.setValue(FULL, false));
      evaporationTime = 0;
    }
  }

  @Override
  public void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
    super.saveAdditional(compound, registries);
    compound.putInt("EvaporationTime", evaporationTime);
    compound.putInt("EvaporationTimeTotal", evaporationTimeTotal);
  }

  @Override
  public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
    super.loadAdditional(tag, provider);

    evaporationTime = tag.getInt("EvaporationTime");
    evaporationTimeTotal = tag.getInt("EvaporationTimeTotal");
  }

  public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
    CompoundTag tag = new CompoundTag();
    saveAdditional(tag, registries);
    return tag;
  }

  public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider registries) {
    super.handleUpdateTag(tag, registries);
  }

  @Override
    public void clearContent () {
      this.getBlockState().setValue(FULL, false);
  }

  @Override
  public void setRemoved() {
    super.setRemoved();
  }
}