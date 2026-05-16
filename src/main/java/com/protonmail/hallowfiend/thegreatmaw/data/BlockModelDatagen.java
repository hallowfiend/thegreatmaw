package com.protonmail.hallowfiend.thegreatmaw.data;

import com.google.common.collect.Sets;
import com.protonmail.hallowfiend.thegreatmaw.TheGreatMaw;
import com.protonmail.hallowfiend.thegreatmaw.registry.ModBlocks;
import com.protonmail.hallowfiend.thegreatmaw.registry.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static vectorwing.farmersdelight.data.ItemModels.MUG;

public class BlockModelDatagen extends BlockModelProvider {

  public static final String CUBEALL = "block/cube_all";

  public BlockModelDatagen(PackOutput output, ExistingFileHelper existingFileHelper) {
    super(output, TheGreatMaw.MODID, existingFileHelper);
  }

  @Override
  protected void registerModels() {
    Set<Block> blocks = BuiltInRegistries.BLOCK.stream().filter(i -> TheGreatMaw.MODID.equals(BuiltInRegistries.BLOCK.getKey(i).getNamespace()))
            .collect(Collectors.toSet());
    // Cube/all (all four sides the same)
    Set<Block> cubeAll = Sets.newHashSet(
            ModBlocks.PORCELAIN_BRICKS.get(),
            ModBlocks.WHITE_SMALL_PORCELAIN_BRICKS.get(),
            ModBlocks.LIGHT_GRAY_SMALL_PORCELAIN_BRICKS.get(),
            ModBlocks.GRAY_SMALL_PORCELAIN_BRICKS.get(),
            ModBlocks.BLACK_SMALL_PORCELAIN_BRICKS.get(),
            ModBlocks.BROWN_SMALL_PORCELAIN_BRICKS.get(),
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
            ModBlocks.PINK_SMALL_PORCELAIN_BRICKS.get()
    );
    takeAll(blocks, cubeAll.toArray(new Block[0])).forEach(block -> cubeAllModel(block, resourceBlock(blockName(block))));
  }

  private String blockName(Block block) {
    return BuiltInRegistries.BLOCK.getKey(block).getPath();
  }

  public void cubeAllModel(Block block, ResourceLocation texture) {
    withExistingParent(blockName(block), CUBEALL).texture("layer0", texture);
  }

  public ResourceLocation resourceBlock(String path) {
    return ResourceLocation.fromNamespaceAndPath(TheGreatMaw.MODID, "block/" + path);
  }

  @SafeVarargs
  @SuppressWarnings("varargs")
  public static <T> Collection<T> takeAll(Set<? extends T> src, T... blocks) {
    List<T> ret = Arrays.asList(blocks);
    for (T block : blocks) {
      if (!src.contains(block)) {
        TheGreatMaw.LOGGER.warn("Block {} not found in set", block);
      }
    }
    if (!src.removeAll(ret)) {
      TheGreatMaw.LOGGER.warn("takeAll array didn't yield anything ({})", Arrays.toString(blocks));
    }
    return ret;
  }

  public static <T> Collection<T> takeAll(Set<T> src, Predicate<T> pred) {
    List<T> ret = new ArrayList<>();

    Iterator<T> iter = src.iterator();
    while (iter.hasNext()) {
      T item = iter.next();
      if (pred.test(item)) {
        iter.remove();
        ret.add(item);
      }
    }

    if (ret.isEmpty()) {
      TheGreatMaw.LOGGER.warn("takeAll predicate yielded nothing", new Throwable());
    }
    return ret;
  }
}
