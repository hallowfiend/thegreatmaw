package com.protonmail.hallowfiend.thegreatmaw.common.block;

import com.mojang.serialization.MapCodec;
import com.protonmail.hallowfiend.thegreatmaw.common.block.entity.CalcinationCrucibleBlockEntity;
import com.protonmail.hallowfiend.thegreatmaw.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.neoforged.neoforge.fluids.FluidActionResult;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.cyclops.cyclopscore.Capabilities;
import org.cyclops.cyclopscore.fluid.SingleUseTank;
import org.cyclops.cyclopscore.helper.IFluidHelpersNeoForge;
import org.cyclops.cyclopscore.helper.IModHelpers;
import org.cyclops.cyclopscore.helper.IModHelpersNeoForge;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.block.state.CookingPotSupport;
import vectorwing.farmersdelight.common.tag.ModTags;

import static org.spongepowered.asm.util.Annotations.setValue;

public class CalcinationCrucibleBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {

  public static final MapCodec<CalcinationCrucibleBlock> CODEC = simpleCodec(CalcinationCrucibleBlock::new);

  public static final BooleanProperty SUPPORT = BooleanProperty.create("support");
  public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
  public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

  protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D);
  protected static final VoxelShape SHAPE_WITH_TRAY = Shapes.or(SHAPE, Block.box(0.0D, -1.0D, 0.0D, 16.0D, 0.0D, 16.0D));

  public CalcinationCrucibleBlock(BlockBehaviour.Properties properties) {
    super(Properties.of()
            .mapColor(MapColor.TERRACOTTA_WHITE)
            .requiresCorrectToolForDrops()
            .strength(1.0F));

    this.registerDefaultState(this.stateDefinition.any().setValue(SUPPORT, false).setValue(WATERLOGGED, false));
  }

  @Override
  public InteractionResult useWithoutItem(BlockState blockState, Level world, BlockPos blockPos, Player player,
          BlockHitResult rayTraceResult) {
    return IModHelpers.get().getBlockEntityHelpers().get(world, blockPos, CalcinationCrucibleBlockEntity.class)
            .map(tile -> {
              ItemStack itemStack = player.getInventory().getSelected();
              IFluidHandler itemFluidHandler = FluidUtil.getFluidHandler(itemStack).orElse(null);
              SingleUseTank tank = tile.getTank();

              if (itemStack.isEmpty()) {
                return InteractionResult.PASS;
                }
              else if (itemFluidHandler != null && !tank.isFull()
                      && !itemFluidHandler.drain(Integer.MAX_VALUE, IFluidHandler.FluidAction.SIMULATE).isEmpty()) {
                FluidActionResult fluidAction = FluidUtil.tryEmptyContainer(itemStack, tank, Integer.MAX_VALUE, player, true);
                if (fluidAction.isSuccess()) {
                  ItemStack newItemStack = fluidAction.getResult();
                  IModHelpers.get().getInventoryHelpers().tryReAddToStack(player, itemStack, newItemStack, player.getUsedItemHand());
                  world.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
                }
                return InteractionResult.SUCCESS;
              } else if (itemFluidHandler != null && !tank.isEmpty() &&
                      itemFluidHandler.fill(tank.getFluid(), IFluidHandler.FluidAction.SIMULATE) > 0) {
                FluidActionResult fluidAction = FluidUtil.tryFillContainer(itemStack, tank, Integer.MAX_VALUE, player, true);
                if (fluidAction.isSuccess()) {
                  ItemStack newItemStack = fluidAction.getResult();
                  IModHelpers.get().getInventoryHelpers().tryReAddToStack(player, itemStack, newItemStack, player.getUsedItemHand());
                }
                return InteractionResult.SUCCESS;
              }
              return InteractionResult.PASS;
            })
            .orElse(InteractionResult.PASS);
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
    builder.add(FACING, SUPPORT, WATERLOGGED);
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
            .setValue(FACING, context.getHorizontalDirection().getOpposite())
            .setValue(WATERLOGGED, fluid.getType() == Fluids.WATER);

    return state.setValue(SUPPORT, getTrayState(level, pos));
  }


  @Override
  public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
    return ModBlockEntityTypes.CALCINATIONCRUCIBLE.get().create(blockPos, blockState);
  }
}
