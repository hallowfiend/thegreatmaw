package com.protonmail.hallowfiend.thegreatmaw.data;

import com.protonmail.hallowfiend.thegreatmaw.TheGreatMaw;
import com.protonmail.hallowfiend.thegreatmaw.registry.ModItems;
import com.protonmail.hallowfiend.thegreatmaw.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class MawItemTags extends ItemTagsProvider {

  public MawItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagsProvider.TagLookup<Block>> blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
    super(output, provider, blockTagProvider, TheGreatMaw.MODID, existingFileHelper);
  }

  @Override
  protected void addTags(HolderLookup.@NotNull Provider provider) {
    // this.registerMinecraftTags();
    this.registerModTags();
  }

  private void registerModTags() {
    tag(ModTags.Items.SMALL_PORCELAIN_BRICKS).add(
            ModItems.WHITE_SMALL_PORCELAIN_BRICKS.get(),
            ModItems.LIGHT_GRAY_SMALL_PORCELAIN_BRICKS.get(),
            ModItems.GRAY_SMALL_PORCELAIN_BRICKS.get(),
            ModItems.BLACK_SMALL_PORCELAIN_BRICKS.get(),
            ModItems.RED_SMALL_PORCELAIN_BRICKS.get(),
            ModItems.ORANGE_SMALL_PORCELAIN_BRICKS.get(),
            ModItems.YELLOW_SMALL_PORCELAIN_BRICKS.get(),
            ModItems.LIME_SMALL_PORCELAIN_BRICKS.get(),
            ModItems.GREEN_SMALL_PORCELAIN_BRICKS.get(),
            ModItems.CYAN_SMALL_PORCELAIN_BRICKS.get(),
            ModItems.LIGHT_BLUE_SMALL_PORCELAIN_BRICKS.get(),
            ModItems.BLUE_SMALL_PORCELAIN_BRICKS.get(),
            ModItems.PURPLE_SMALL_PORCELAIN_BRICKS.get(),
            ModItems.MAGENTA_SMALL_PORCELAIN_BRICKS.get(),
            ModItems.PINK_SMALL_PORCELAIN_BRICKS.get()
    );
  }

}
