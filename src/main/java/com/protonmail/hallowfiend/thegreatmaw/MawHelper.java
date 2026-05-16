package com.protonmail.hallowfiend.thegreatmaw;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public interface MawHelper {

  public static ResourceLocation RL(String path) {
    return ResourceLocation.fromNamespaceAndPath(TheGreatMaw.MODID, path);
  }

}
