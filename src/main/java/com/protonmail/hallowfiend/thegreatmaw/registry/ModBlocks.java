package com.protonmail.hallowfiend.thegreatmaw.registry;

import com.klikli_dev.modonomicon.registry.RegistryObject;
import com.protonmail.hallowfiend.thegreatmaw.TheGreatMaw;
import com.protonmail.hallowfiend.thegreatmaw.common.block.CalcinationCrucibleBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModBlocks {

  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, TheGreatMaw.MODID);

  //Manual workstations
  public static final Supplier<Block> CALCINATIONCRUCIBLE = BLOCKS.register("calcination_crucible",
          () -> new CalcinationCrucibleBlock(Block.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).strength(0.5F, 6.0F).sound(SoundType.DECORATED_POT)));
  //Porcelain

  public static BlockBehaviour.Properties PorcelainProperties(){
    return BlockBehaviour.Properties.of()
            .mapColor(MapColor.TERRACOTTA_WHITE)
            .requiresCorrectToolForDrops()
            .instrument(NoteBlockInstrument.CHIME)
            .strength(1.5F, 6.0F)
            .sound(SoundType.STONE);
  }
  public static final Supplier<Block> PORCELAIN_BRICKS = BLOCKS.register("porcelain_bricks",
          () -> new Block(PorcelainProperties()));
  public static final Supplier<Block> PORCELAIN_BRICK_SLAB = BLOCKS.register("porcelain_brick_slab",
          () -> new SlabBlock(PorcelainProperties()));
  public static final Supplier<Block> PORCELAIN_BRICK_STAIRS = BLOCKS.register("porcelain_brick_stairs",
          () -> new StairBlock(ModBlocks.PORCELAIN_BRICKS.get().defaultBlockState(), PorcelainProperties()));
  public static final Supplier<Block> WHITE_SMALL_PORCELAIN_BRICKS = BLOCKS.register("white_small_porcelain_bricks",
          () -> new Block(PorcelainProperties()));
  public static final Supplier<Block> LIGHT_GRAY_SMALL_PORCELAIN_BRICKS = BLOCKS.register("light_gray_small_porcelain_bricks",
          () -> new Block(PorcelainProperties()));
  public static final Supplier<Block> GRAY_SMALL_PORCELAIN_BRICKS = BLOCKS.register("gray_small_porcelain_bricks",
          () -> new Block(PorcelainProperties()));
  public static final Supplier<Block> BLACK_SMALL_PORCELAIN_BRICKS = BLOCKS.register("black_small_porcelain_bricks",
          () -> new Block(PorcelainProperties()));
  public static final Supplier<Block> BROWN_SMALL_PORCELAIN_BRICKS = BLOCKS.register("brown_small_porcelain_bricks",
          () -> new Block(PorcelainProperties()));
  public static final Supplier<Block> RED_SMALL_PORCELAIN_BRICKS = BLOCKS.register("red_small_porcelain_bricks",
          () -> new Block(PorcelainProperties()));
  public static final Supplier<Block> ORANGE_SMALL_PORCELAIN_BRICKS = BLOCKS.register("orange_small_porcelain_bricks",
          () -> new Block(PorcelainProperties()));
  public static final Supplier<Block> YELLOW_SMALL_PORCELAIN_BRICKS = BLOCKS.register("yellow_small_porcelain_bricks",
          () -> new Block(PorcelainProperties()));
  public static final Supplier<Block> LIME_SMALL_PORCELAIN_BRICKS = BLOCKS.register("lime_small_porcelain_bricks",
          () -> new Block(PorcelainProperties()));
  public static final Supplier<Block> GREEN_SMALL_PORCELAIN_BRICKS = BLOCKS.register("green_small_porcelain_bricks",
          () -> new Block(PorcelainProperties()));
  public static final Supplier<Block> CYAN_SMALL_PORCELAIN_BRICKS = BLOCKS.register("cyan_small_porcelain_bricks",
          () -> new Block(PorcelainProperties()));
  public static final Supplier<Block> LIGHT_BLUE_SMALL_PORCELAIN_BRICKS = BLOCKS.register("light_blue_small_porcelain_bricks",
          () -> new Block(PorcelainProperties()));
  public static final Supplier<Block> BLUE_SMALL_PORCELAIN_BRICKS = BLOCKS.register("blue_small_porcelain_bricks",
          () -> new Block(PorcelainProperties()));
  public static final Supplier<Block> PURPLE_SMALL_PORCELAIN_BRICKS = BLOCKS.register("purple_small_porcelain_bricks",
          () -> new Block(PorcelainProperties()));
  public static final Supplier<Block> MAGENTA_SMALL_PORCELAIN_BRICKS = BLOCKS.register("magenta_small_porcelain_bricks",
          () -> new Block(PorcelainProperties()));
  public static final Supplier<Block> PINK_SMALL_PORCELAIN_BRICKS = BLOCKS.register("pink_small_porcelain_bricks",
          () -> new Block(PorcelainProperties()));
}
