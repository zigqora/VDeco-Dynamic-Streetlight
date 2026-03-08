package zigq.vdecostreetlamp.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;

public class RedstoneStreetlampHeadBlock extends AbstractFlickeringBlock {
    public static final BooleanProperty POWERED = Properties.POWERED; // Used to track received power
    public static final BooleanProperty REMOTELY_POWERED = BooleanProperty.of("remotely_powered"); // Track remote toggles

    public RedstoneStreetlampHeadBlock(Settings settings, VoxelShape shape) {
        super(settings, shape);
        this.setDefaultState(this.getDefaultState().with(POWERED, false).with(REMOTELY_POWERED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(POWERED, REMOTELY_POWERED);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState parentState = super.getPlacementState(ctx);
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        boolean isPowered = isBlockPowered(world, pos);

        return parentState.with(POWERED, isPowered).with(REMOTELY_POWERED, false);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!world.isClient) {
            boolean isPowered = isBlockPowered(world, pos);
            if (state.get(POWERED) != isPowered) {
                world.setBlockState(pos, state.with(POWERED, isPowered), 3);
                // Schedule a tick immediately when power changes so it can start/stop the flicker effect
                world.scheduleBlockTick(pos, this, 1);
            }
        }
    }

    @Override
    protected boolean shouldBeLit(BlockState state, ServerWorld world, BlockPos pos) {
        return state.get(POWERED) || state.get(REMOTELY_POWERED);
    }

    @Override
    protected void scheduleInactiveTick(ServerWorld world, BlockPos pos, Random random) {
        // Redstone blocks don't need persistent periodic ticking. 
        // Neighbor updates handle changes.
    }

    @Override
    protected void scheduleActiveTick(ServerWorld world, BlockPos pos, Random random) {
        // Redstone blocks don't need persistent periodic ticking.
        // Neighbor updates handle changes.
    }

    private boolean isBlockPowered(World world, BlockPos pos) {
        // Redstone head turns on from its own redstone power
        return world.isReceivingRedstonePower(pos);
    }
}
