package com.protonmail.hallowfiend.thegreatmaw.registry;

import com.protonmail.hallowfiend.thegreatmaw.TheGreatMaw;
import com.protonmail.hallowfiend.thegreatmaw.common.block.entity.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

public class ModBlockEntityTypes {

  public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, TheGreatMaw.MODID);

  public static final Supplier<BlockEntityType<CalcinationCrucibleBlockEntity>> CALCINATIONCRUCIBLE = TILES.register("calcination_crucible",
          () -> BlockEntityType.Builder
                  .of(CalcinationCrucibleBlockEntity::new, ModBlocks.CALCINATIONCRUCIBLE.get())
                  .build(null));
}
