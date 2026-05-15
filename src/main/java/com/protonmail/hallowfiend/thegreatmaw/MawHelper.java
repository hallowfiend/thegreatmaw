package com.protonmail.hallowfiend.thegreatmaw;

import net.minecraft.resources.ResourceLocation;

public interface MawHelper {

  public static ResourceLocation RL(String path) {
    return ResourceLocation.fromNamespaceAndPath(TheGreatMaw.MODID, path);
  }
}
