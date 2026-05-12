package com.protonmail.hallowfiend.thegreatmaw;

import com.protonmail.hallowfiend.thegreatmaw.common.util.DummyRecipeWrapper;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.fluids.FluidStack;

public interface MawHelper {

  public static ResourceLocation RL(String path) {
    return ResourceLocation.fromNamespaceAndPath(TheGreatMaw.MODID, path);
  }
}
