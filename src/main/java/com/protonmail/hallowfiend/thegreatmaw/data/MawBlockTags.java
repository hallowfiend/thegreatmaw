package com.protonmail.hallowfiend.thegreatmaw.data;

import com.protonmail.hallowfiend.thegreatmaw.TheGreatMaw;
import com.protonmail.hallowfiend.thegreatmaw.registry.ModBlocks;
import com.protonmail.hallowfiend.thegreatmaw.registry.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class MawBlockTags extends BlockTagsProvider {

  public MawBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
    super(output, lookupProvider, TheGreatMaw.MODID, existingFileHelper);
  }

  @Override
  protected void addTags(HolderLookup.@NotNull Provider provider) {
    this.registerBlockMineables();
  }

  protected void registerBlockMineables() {
    tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE).add(
            ModBlocks.WHITE_SMALL_PORCELAIN_BRICKS.get(),
            ModBlocks.LIGHT_GRAY_SMALL_PORCELAIN_BRICKS.get(),
            ModBlocks.GRAY_SMALL_PORCELAIN_BRICKS.get(),
            ModBlocks.BLACK_SMALL_PORCELAIN_BRICKS.get(),
            ModBlocks.RED_SMALL_PORCELAIN_BRICKS.get(),
            ModBlocks.ORANGE_SMALL_PORCELAIN_BRICKS.get(),
            ModBlocks.YELLOW_SMALL_PORCELAIN_BRICKS.get(),
            ModBlocks.LIME_SMALL_PORCELAIN_BRICKS.get(),
            ModBlocks.GREEN_SMALL_PORCELAIN_BRICKS.get(),
            ModBlocks.CYAN_SMALL_PORCELAIN_BRICKS.get(),
            ModBlocks.LIGHT_BLUE_SMALL_PORCELAIN_BRICKS.get(),
            ModBlocks.BLUE_SMALL_PORCELAIN_BRICKS.get(),
            ModBlocks.PURPLE_SMALL_PORCELAIN_BRICKS.get(),
            ModBlocks.MAGENTA_SMALL_PORCELAIN_BRICKS.get(),
            ModBlocks.PINK_SMALL_PORCELAIN_BRICKS.get(),
            ModBlocks.PORCELAIN_BRICK_SLAB.get(),
            ModBlocks.PORCELAIN_BRICK_STAIRS.get()
    );
  }

}
