package com.protonmail.hallowfiend.thegreatmaw.common.util;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.level.Level;

import java.util.List;

public abstract class DummyRecipeWrapper implements CraftingRecipe {

  protected DummyRecipeWrapper() {
  }

  @Override
  public boolean matches(CraftingInput container, Level level) {
    return true;
  }

  @Override
  public ItemStack assemble(CraftingInput container, HolderLookup.Provider registryAccess) {
    return ItemStack.EMPTY;
  }

  @Override
  public boolean canCraftInDimensions(int width, int height) {
    return true;
  }

  @Override
  public ItemStack getResultItem(HolderLookup.Provider registryAccess) {
    return ItemStack.EMPTY;
  }

  @Override
  public boolean isSpecial() {
    return true;
  }

  @Override
  public CraftingBookCategory category() {
    return CraftingBookCategory.MISC;
  }

  /**
   * Just to keep vanilla happy...
   */
  public static class DummyIInventory implements CraftingContainer {
    private static final DummyIInventory INSTANCE = new DummyIInventory();

    public static DummyIInventory getInstance() {
      return INSTANCE;
    }

    @Override
    public int getContainerSize() {
      return 0;
    }

    @Override
    public boolean isEmpty() {
      return true;
    }

    @Override
    public ItemStack getItem(int index) {
      return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int index, int count) {
      return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
      return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int index, ItemStack stack) {
    }

    @Override
    public void setChanged() {
    }

    @Override
    public boolean stillValid(Player player) {
      return false;
    }

    @Override
    public void clearContent() {
    }

    @Override
    public int getWidth() {
      return 3;
    }

    @Override
    public int getHeight() {
      return 3;
    }

    @Override
    public List<ItemStack> getItems() {
      return List.of();
    }

    @Override
    public void fillStackedContents(StackedContents p_40281_) {

    }
  }
}
