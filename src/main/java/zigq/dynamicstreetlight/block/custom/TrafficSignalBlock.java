// package zigq.dynamicstreetlight.block.custom;

// import net.minecraft.block.Block;
// import net.minecraft.block.BlockEntityProvider;
// import net.minecraft.block.BlockState;
// import net.minecraft.block.entity.BlockEntity;
// import net.minecraft.block.entity.BlockEntityTicker;
// import net.minecraft.block.entity.BlockEntityType;
// import net.minecraft.entity.player.PlayerEntity;
// import net.minecraft.item.ItemPlacementContext;
// import net.minecraft.state.StateManager;
// import net.minecraft.state.property.BooleanProperty;
// import net.minecraft.state.property.DirectionProperty;
// import net.minecraft.state.property.Properties;
// import net.minecraft.util.ActionResult;
// import net.minecraft.util.Hand;
// import net.minecraft.util.hit.BlockHitResult;
// import net.minecraft.util.math.BlockPos;
// import net.minecraft.util.math.Direction;
// import net.minecraft.world.World;
// import org.jetbrains.annotations.Nullable;
// import zigq.dynamicstreetlight.block.entity.ModBlockEntities;
// import zigq.dynamicstreetlight.block.entity.TrafficSignalBlockEntity;

// public class TrafficSignalBlock extends Block implements BlockEntityProvider
// {
// public static final DirectionProperty FACING = Properties.FACING;
// public static final BooleanProperty RED = BooleanProperty.of("red");
// public static final BooleanProperty YELLOW = BooleanProperty.of("yellow");
// public static final BooleanProperty GREEN = BooleanProperty.of("green");

// public TrafficSignalBlock(Settings settings) {
// super(settings);
// this.setDefaultState(this.getDefaultState()
// .with(FACING, Direction.NORTH)
// .with(RED, false)
// .with(YELLOW, false)
// .with(GREEN, false));
// }

// @Override
// protected void appendProperties(StateManager.Builder<Block, BlockState>
// builder) {
// builder.add(FACING, RED, YELLOW, GREEN);
// }

// @Nullable
// @Override
// public BlockState getPlacementState(ItemPlacementContext ctx) {
// Direction direction = ctx.getPlayerLookDirection().getOpposite();
// if (direction.getAxis() == Direction.Axis.Y) {
// direction = ctx.getHorizontalPlayerFacing().getOpposite(); // Keep it
// horizontal if placed looking up/down
// }
// return this.getDefaultState()
// .with(FACING, direction)
// .with(RED, false)
// .with(YELLOW, false)
// .with(GREEN, false);
// }

// @Nullable
// @Override
// public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
// return new TrafficSignalBlockEntity(pos, state);
// }

// @Nullable
// @Override
// public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world,
// BlockState state, BlockEntityType<T> type) {
// return checkType(type, ModBlockEntities.TRAFFIC_SIGNAL_BLOCK_ENTITY,
// TrafficSignalBlockEntity::tick);
// }

// @SuppressWarnings("unchecked")
// @Nullable
// protected static <E extends BlockEntity, A extends BlockEntity>
// BlockEntityTicker<A> checkType(BlockEntityType<A> givenType,
// BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker) {
// return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
// }

// @Override
// public ActionResult onUse(BlockState state, World world, BlockPos pos,
// PlayerEntity player, Hand hand, BlockHitResult hit) {
// if (!world.isClient) {
// BlockEntity blockEntity = world.getBlockEntity(pos);
// if (blockEntity instanceof TrafficSignalBlockEntity trafficSignal) {
// player.openHandledScreen(trafficSignal);
// }
// }
// return ActionResult.SUCCESS;
// }
// }



