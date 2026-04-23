package zigq.dynamicstreetlight.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import com.mojang.serialization.MapCodec;

import java.util.EnumMap;
import java.util.Map;

public class DefaultBlockShape extends HorizontalFacingBlock {
    protected final Map<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);

    public DefaultBlockShape(Settings settings, VoxelShape shape) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
        calculateShapes(Direction.NORTH, shape);
    }

    public static final MapCodec<DefaultBlockShape> CODEC = createCodec(settings -> new DefaultBlockShape(settings, VoxelShapes.fullCube()));

    @Override
    public MapCodec<? extends DefaultBlockShape> getCodec() {
        return CODEC;
    }

    protected void calculateShapes(Direction to, VoxelShape shape) {
        VoxelShape[] buffer = new VoxelShape[] { shape, VoxelShapes.empty() };

        for (int i = 0; i < 4; i++) {
            Direction direction = Direction.fromHorizontal((to.getHorizontal() + i) % 4);
            buffer[0].forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = VoxelShapes.combine(buffer[1],
                    VoxelShapes.cuboid(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX), BooleanBiFunction.OR));
            SHAPES.put(direction, buffer[0].simplify());
            buffer[0] = buffer[1];
            buffer[1] = VoxelShapes.empty();
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES.getOrDefault(state.get(FACING), VoxelShapes.fullCube());
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}


