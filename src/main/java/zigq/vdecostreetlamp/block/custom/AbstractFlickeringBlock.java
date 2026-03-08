package zigq.vdecostreetlamp.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;

public abstract class AbstractFlickeringBlock extends DefaultBlockShape {
    public static final BooleanProperty LIT = Properties.LIT;
    public static final IntProperty FLICKER = IntProperty.of("flicker", 0, 3);

    public AbstractFlickeringBlock(Settings settings, VoxelShape shape) {
        super(settings, shape);
        // By default, not lit and not flickering
        this.setDefaultState(this.getDefaultState().with(LIT, false).with(FLICKER, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(LIT, FLICKER);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState parentState = super.getPlacementState(ctx);
        if (parentState == null) {
            parentState = this.getDefaultState();
        }
        
        // Let subclasses determine the initial powering logic
        return parentState.with(LIT, false).with(FLICKER, 0);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);
        if (!world.isClient) {
            world.scheduleBlockTick(pos, this, world.random.nextInt(10));
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        boolean shouldBeLit = shouldBeLit(state, world, pos);
        boolean isLit = state.get(LIT);
        int flicker = state.get(FLICKER);

        if (!shouldBeLit) {
            if (isLit || flicker != 0) {
                world.setBlockState(pos, state.with(LIT, false).with(FLICKER, 0), 3);
            }
            scheduleInactiveTick(world, pos, random);
        } else {
            if (!isLit && flicker == 0) {
                world.setBlockState(pos, state.with(LIT, true).with(FLICKER, 1), 3);
                world.scheduleBlockTick(pos, this, 2 + random.nextInt(4));
            }
            else if (flicker == 1) {
                world.setBlockState(pos, state.with(LIT, false).with(FLICKER, 2), 3);
                world.scheduleBlockTick(pos, this, 2 + random.nextInt(4));
            }
            else if (flicker == 2) {
                world.setBlockState(pos, state.with(LIT, true).with(FLICKER, 3), 3);
                world.scheduleBlockTick(pos, this, 2 + random.nextInt(4));
            }
            else if (flicker == 3) {
                world.setBlockState(pos, state.with(LIT, true).with(FLICKER, 0), 3);
                scheduleActiveTick(world, pos, random);
            }
            else {
                scheduleActiveTick(world, pos, random);
            }
        }
    }

    /**
     * Determines whether the block SHOULD currently be on/lit based on its custom logic.
     * Needs to be implemented by child classes (e.g. is it night? is it powered?).
     */
    protected abstract boolean shouldBeLit(BlockState state, ServerWorld world, BlockPos pos);

    /**
     * Scheduling ticks based on the nature of the child block.
     * e.g. Daylight block needs constant ticks, Redstone block may only need neighbor updates.
     */
    protected abstract void scheduleInactiveTick(ServerWorld world, BlockPos pos, Random random);
    protected abstract void scheduleActiveTick(ServerWorld world, BlockPos pos, Random random);
}
