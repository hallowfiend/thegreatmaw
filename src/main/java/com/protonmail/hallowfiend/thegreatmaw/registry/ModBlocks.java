package com.protonmail.hallowfiend.thegreatmaw.registry;

import com.protonmail.hallowfiend.thegreatmaw.TheGreatMaw;

import java.util.function.Supplier;

import com.protonmail.hallowfiend.thegreatmaw.common.block.CalcinationCrucibleBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {

  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, TheGreatMaw.MODID);

  //Manual workstations
  public static final Supplier<Block> CALCINATIONCRUCIBLE = BLOCKS.register("calcination_crucible",
          () -> new CalcinationCrucibleBlock(Block.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).strength(0.5F, 6.0F).sound(SoundType.DECORATED_POT)));

}
