package zigq.dynamicstreetlight.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeverBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.WorldChunk;
import com.mojang.serialization.MapCodec;

public class RemoteLeverBlock extends LeverBlock {
    public static final MapCodec<RemoteLeverBlock> CODEC = createCodec(RemoteLeverBlock::new);

    @Override
    public MapCodec<LeverBlock> getCodec() {
        return (MapCodec<LeverBlock>) (Object) CODEC;
    }

    private static final int SCAN_RADIUS = 64; // Distance to search for streetlamps

    public RemoteLeverBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
            BlockHitResult hit) {
        if (world.isClient) {
            BlockState newState = state.cycle(POWERED);
            if (newState.get(POWERED)) {
                // Particles are handled by LeverBlock internally on tick/use depending on
                // version, skipping manual call here to avoid access issues.
            }
            return ActionResult.SUCCESS;
        } else {
            BlockState newState = this.togglePower(state, world, pos);
            float pitch = newState.get(POWERED) ? 0.6F : 0.5F;
            world.playSound(null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3F, pitch);

            // Toggle lamps
            int toggledCount = scanAndToggleLamps(world, pos, newState.get(POWERED));

            if (toggledCount > 0) {
                player.sendMessage(Text.translatable("message.dynamic_streetlight.remote_lever.success", toggledCount), true);
            } else {
                player.sendMessage(Text.translatable("message.dynamic_streetlight.remote_lever.none_found"), true);
            }

            return ActionResult.CONSUME;
        }
    }

    public BlockState togglePower(BlockState state, World world, BlockPos pos) {
        state = state.cycle(POWERED);
        world.setBlockState(pos, state, Block.NOTIFY_ALL);
        world.updateNeighborsAlways(pos, this);
        world.updateNeighborsAlways(pos.offset(getDirection(state).getOpposite()), this);
        return state;
    }

    private int scanAndToggleLamps(World world, BlockPos center, boolean isLeverOn) {
        int count = 0;
        int radius = SCAN_RADIUS;

        int minX = center.getX() - radius;
        int minY = center.getY() - radius;
        int minZ = center.getZ() - radius;
        int maxX = center.getX() + radius;
        int maxY = center.getY() + radius;
        int maxZ = center.getZ() + radius;

        int minChunkX = minX >> 4;
        int maxChunkX = maxX >> 4;
        int minChunkZ = minZ >> 4;
        int maxChunkZ = maxZ >> 4;

        for (int cX = minChunkX; cX <= maxChunkX; cX++) {
            for (int cZ = minChunkZ; cZ <= maxChunkZ; cZ++) {
                if (!world.isChunkLoaded(cX, cZ)) {
                    continue;
                }

                WorldChunk chunk = world.getChunk(cX, cZ);
                if (chunk == null)
                    continue;

                ChunkSection[] sections = chunk.getSectionArray();
                for (int i = 0; i < sections.length; i++) {
                    ChunkSection section = sections[i];
                    if (section == null || section.isEmpty())
                        continue;

                    int sectionMinY = chunk.sectionIndexToCoord(i) * 16;
                    int sectionMaxY = sectionMinY + 15;

                    if (sectionMaxY < minY || sectionMinY > maxY)
                        continue;

                    boolean hasLamp = section.hasAny(state -> state.getBlock() instanceof RedstoneStreetlampHeadBlock);

                    if (!hasLamp)
                        continue;

                    // Now safely iterate through the section
                    int localMinY = Math.max(minY, sectionMinY);
                    int localMaxY = Math.min(maxY, sectionMaxY);

                    int chunkMinX = Math.max(minX, cX * 16);
                    int chunkMaxX = Math.min(maxX, cX * 16 + 15);
                    int chunkMinZ = Math.max(minZ, cZ * 16);
                    int chunkMaxZ = Math.min(maxZ, cZ * 16 + 15);

                    for (int x = chunkMinX; x <= chunkMaxX; x++) {
                        for (int y = localMinY; y <= localMaxY; y++) {
                            for (int z = chunkMinZ; z <= chunkMaxZ; z++) {
                                BlockPos pos = new BlockPos(x, y, z);
                                BlockState targetState = chunk.getBlockState(pos);

                                if (targetState.getBlock() instanceof RedstoneStreetlampHeadBlock) {
                                    boolean isLampRemotelyPowered = targetState
                                            .get(RedstoneStreetlampHeadBlock.REMOTELY_POWERED);
                                    if (isLampRemotelyPowered != isLeverOn) {
                                        // Update world block state
                                        world.setBlockState(pos, targetState
                                                .with(RedstoneStreetlampHeadBlock.REMOTELY_POWERED, isLeverOn), 3);
                                        world.scheduleBlockTick(pos, targetState.getBlock(), 1);
                                        count++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return count;
    }
}







