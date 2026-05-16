package com.protonmail.hallowfiend.thegreatmaw.data;

import com.google.common.collect.Sets;
import com.protonmail.hallowfiend.thegreatmaw.TheGreatMaw;
import com.protonmail.hallowfiend.thegreatmaw.registry.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Set;

public class BlockStateDatagen extends BlockStateProvider {

  public BlockStateDatagen(PackOutput output, ExistingFileHelper exFileHelper) {
    super(output, TheGreatMaw.MODID, exFileHelper);
  }

  public ModelFile existingModel(Block block) {
    return new ModelFile.ExistingModelFile(resourceMawBlock(blockName(block)), models().existingFileHelper);
  }

  public ModelFile existingModel(String path) {
    return new ModelFile.ExistingModelFile(resourceMawBlock(path), models().existingFileHelper);
  }

  public ModelFile assumedModel(Block block) {
    return new ModelFile.UncheckedModelFile(resourceMawBlock(blockName(block)));
  }

  public ResourceLocation resourceMawBlock(String path) {
    return ResourceLocation.fromNamespaceAndPath(TheGreatMaw.MODID, ModelProvider.BLOCK_FOLDER + "/" + path);
  }

  private String blockName(Block block) {
    return BuiltInRegistries.BLOCK.getKey(block).getPath();
  }

  @Override
  protected void registerStatesAndModels() {
    Set<Block> smallBricks = Sets.newHashSet(
            // Standard
            ModBlocks.WHITE_SMALL_PORCELAIN_BRICKS.get(),
            ModBlocks.BLACK_SMALL_PORCELAIN_BRICKS.get(),
            ModBlocks.BLUE_SMALL_PORCELAIN_BRICKS.get(),
            ModBlocks.BROWN_SMALL_PORCELAIN_BRICKS.get(),
            ModBlocks.CYAN_SMALL_PORCELAIN_BRICKS.get(),
            ModBlocks.GRAY_SMALL_PORCELAIN_BRICKS.get(),
            ModBlocks.GREEN_SMALL_PORCELAIN_BRICKS.get(),
            ModBlocks.LIGHT_BLUE_SMALL_PORCELAIN_BRICKS.get(),
            ModBlocks.LIGHT_GRAY_SMALL_PORCELAIN_BRICKS.get(),
            ModBlocks.LIME_SMALL_PORCELAIN_BRICKS.get(),
            ModBlocks.MAGENTA_SMALL_PORCELAIN_BRICKS.get(),
            ModBlocks.ORANGE_SMALL_PORCELAIN_BRICKS.get(),
            ModBlocks.PINK_SMALL_PORCELAIN_BRICKS.get()
    );
    for (Block brick : smallBricks) {
      simpleBlock(brick, assumedModel(brick));
    }

  }
}
