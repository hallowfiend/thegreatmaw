package com.protonmail.hallowfiend.thegreatmaw;

import com.protonmail.hallowfiend.thegreatmaw.common.util.DummyRecipeWrapper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public interface MawHelper {

  public static ResourceLocation RL(String path) {
    return ResourceLocation.fromNamespaceAndPath(TheGreatMaw.MODID, path);
  }


}
