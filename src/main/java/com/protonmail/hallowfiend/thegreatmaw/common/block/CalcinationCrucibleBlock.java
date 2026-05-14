package com.protonmail.hallowfiend.thegreatmaw.common.block;

import com.mojang.serialization.MapCodec;
import com.protonmail.hallowfiend.thegreatmaw.common.block.entity.CalcinationCrucibleBlockEntity;
import com.protonmail.hallowfiend.thegreatmaw.common.crafting.CalcinationCrucibleRecipe;
import com.protonmail.hallowfiend.thegreatmaw.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.block.state.CookingPotSupport;
import vectorwing.farmersdelight.common.tag.ModTags;

import static org.spongepowered.asm.util.Annotations.setValue;

public class CalcinationCrucibleBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {

  public static final MapCodec<CalcinationCrucibleBlock> CODEC = simpleCodec(CalcinationCrucibleBlock::new);

  public static final BooleanProperty SUPPORT = BooleanProperty.create("support");
  public static final BooleanProperty FULL = BooleanProperty.create("full");
  public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

  protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D);
  protected static final VoxelShape SHAPE_WITH_TRAY = Shapes.or(SHAPE, Block.box(0.0D, -1.0D, 0.0D, 16.0D, 0.0D, 16.0D));

  public CalcinationCrucibleBlock(BlockBehaviour.Properties properties) {
    super(Properties.of()
            .mapColor(MapColor.TERRACOTTA_WHITE)
            .requiresCorrectToolForDrops()
            .strength(1.0F));

    this.registerDefaultState(this.stateDefinition.any()
            .setValue(SUPPORT, false)
            .setValue(WATERLOGGED, false)
            .setValue(FULL, false));
  }

  @Override
  public InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHit) {
    if (pLevel.getBlockEntity(pPos) instanceof CalcinationCrucibleBlockEntity crucible) {
      InteractionHand pHand = pPlayer.getUsedItemHand();
      ItemStack stack = pPlayer.getItemInHand(pHand);
      var bucket = stack.getCapability(Capabilities.FluidHandler.ITEM);
      if (bucket != null) {
        if (bucket.getFluidInTank(1).getFluid() == Fluids.WATER) {
          if (!pLevel.isClientSide) {
            bucket.drain(1000, IFluidHandler.FluidAction.EXECUTE);
            pLevel.setBlockAndUpdate(pPos, pState.setValue(FULL, true));
            pLevel.playSound(null, pPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
              }
            }
          }
          return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
      return super.useWithoutItem(pState, pLevel, pPos, pPlayer, pHit);
  }

  @SuppressWarnings("deprecation")
  @Override
  public boolean hasAnalogOutputSignal(BlockState blockState) {
    return true;
  }


  @Override
  protected MapCodec<? extends BaseEntityBlock> codec() {
    return CODEC;
  }


  public @NotNull VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
    return state.getValue(SUPPORT).equals(true) ? SHAPE_WITH_TRAY : SHAPE;
  }

  public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
    return SHAPE;
  }

  @Override
  public @NotNull FluidState getFluidState(BlockState state) {
    return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
  }

  @Override
  public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
    return RenderShape.MODEL;
  }

  @Nullable
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntity) {
    return createTickerHelper(blockEntity, ModBlockEntityTypes.CALCINATIONCRUCIBLE.get(), CalcinationCrucibleBlockEntity::calcinationTick);
  }

  @Nullable
  protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> serverType, BlockEntityType<E> clientType, BlockEntityTicker<? super E> ticker) {
    return clientType == serverType ? (BlockEntityTicker<A>) ticker : null;
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    super.createBlockStateDefinition(builder);
    builder.add(SUPPORT, WATERLOGGED, FULL);
  }

  @Override
  public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
    if (state.getValue(WATERLOGGED)) {
      level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
    }
    if (facing.getAxis().equals(Direction.Axis.Y)) {
      return state.setValue(SUPPORT, getTrayState(level, currentPos));
    }
    return state;
  }

  private boolean getTrayState(LevelAccessor world, BlockPos pos) {
    return world.getBlockState(pos.below()).is(ModTags.Blocks.TRAY_HEAT_SOURCES);
  }

  @Override
  public BlockState getStateForPlacement(BlockPlaceContext context) {
    BlockPos pos = context.getClickedPos();
    Level level = context.getLevel();
    FluidState fluid = level.getFluidState(context.getClickedPos());

    BlockState state = this.defaultBlockState()
            .setValue(FULL, false)
            .setValue(WATERLOGGED, fluid.getType() == Fluids.WATER);

    return state.setValue(SUPPORT, getTrayState(level, pos));
  }


  @Override
  public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
    return ModBlockEntityTypes.CALCINATIONCRUCIBLE.get().create(blockPos, blockState);
  }
}
