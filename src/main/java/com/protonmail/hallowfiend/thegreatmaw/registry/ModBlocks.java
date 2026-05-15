package com.protonmail.hallowfiend.thegreatmaw.registry;

import com.protonmail.hallowfiend.thegreatmaw.TheGreatMaw;
import com.protonmail.hallowfiend.thegreatmaw.common.block.CalcinationCrucibleBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredRegister;

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
}
