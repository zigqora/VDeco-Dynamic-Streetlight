package zigq.vdecostreetlamp.block.custom;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;

public class StreetlampBlock extends AbstractFlickeringBlock {
    
    public StreetlampBlock(Settings settings, VoxelShape shape) {
        super(settings, shape);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState parentState = super.getPlacementState(ctx);
        return parentState.with(LIT, !ctx.getWorld().isDay()).with(FLICKER, 0);
    }

    @Override
    protected boolean shouldBeLit(BlockState state, ServerWorld world, BlockPos pos) {
        return !world.isDay();
    }

    @Override
    protected void scheduleInactiveTick(ServerWorld world, BlockPos pos, Random random) {
        // Daylight sensor blocks need constant checking when they are off
        world.scheduleBlockTick(pos, this, 100 + random.nextInt(100));
    }

    @Override
    protected void scheduleActiveTick(ServerWorld world, BlockPos pos, Random random) {
        // ...and when they are on.
        world.scheduleBlockTick(pos, this, 100 + random.nextInt(100));
    }
}