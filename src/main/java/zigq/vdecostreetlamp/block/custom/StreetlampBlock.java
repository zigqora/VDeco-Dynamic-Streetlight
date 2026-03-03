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

public class StreetlampBlock extends DefaultBlockShape {
    public static final BooleanProperty LIT = Properties.LIT;
    public static final IntProperty FLICKER = IntProperty.of("flicker", 0, 3);
    public StreetlampBlock(Settings settings, VoxelShape shape) {
        super(settings, shape);
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
        return parentState.with(LIT, !ctx.getWorld().isDay()).with(FLICKER, 0);
    }
    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);
        if (!world.isClient) {
            world.scheduleBlockTick(pos, this, world.random.nextInt(100));
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        boolean isDay = world.isDay();
        boolean isLit = state.get(LIT);
        int flicker = state.get(FLICKER);

        if (isDay) {
            if (isLit || flicker != 0) {
                world.setBlockState(pos, state.with(LIT, false).with(FLICKER, 0), 3);
            }
            world.scheduleBlockTick(pos, this, 100 + random.nextInt(100));
        } else {
            if (!isLit && flicker == 0) {
                world.setBlockState(pos, state.with(LIT, true).with(FLICKER, 1), 3);
                world.scheduleBlockTick(pos, this, 2 + random.nextInt(4)); // Very fast 2-5 tick delay
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
                world.scheduleBlockTick(pos, this, 100 + random.nextInt(100)); // Return to long delay
            }
            else {
                world.scheduleBlockTick(pos, this, 100 + random.nextInt(100));
            }
        }
    }
}