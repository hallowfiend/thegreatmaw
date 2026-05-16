package com.protonmail.hallowfiend.thegreatmaw.registry;

import com.protonmail.hallowfiend.thegreatmaw.TheGreatMaw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {

  public static class Items {
    public static final TagKey<Item> SMALL_PORCELAIN_BRICKS = modItemTag("small_porcelain_bricks");
  }

  private static TagKey<Item> modItemTag(String path) {
    return ItemTags.create(ResourceLocation.fromNamespaceAndPath(TheGreatMaw.MODID, path));
  }

}
