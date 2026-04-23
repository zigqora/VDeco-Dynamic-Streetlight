package zigq.dynamicstreetlight.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import zigq.dynamicstreetlight.DynamicStreetlights;
import zigq.dynamicstreetlight.block.custom.DefaultBlockShape;
import zigq.dynamicstreetlight.block.custom.RedstoneStreetlampHeadBlock;
import zigq.dynamicstreetlight.block.custom.RemoteLeverBlock;
import zigq.dynamicstreetlight.block.custom.StreetlampBlock;

public class StreetlightBlocks {
        private static final VoxelShape DEFAULT_BLOCK_SHAPE = VoxelShapes.fullCube();

        private static AbstractBlock.Settings baseSettings() {
            return AbstractBlock.Settings.create()
                    .mapColor(net.minecraft.block.MapColor.STONE_GRAY)
                    .instrument(net.minecraft.block.enums.NoteBlockInstrument.BASEDRUM)
                    .strength(1.5f, 6.0f)
                    .nonOpaque();
        }

        private static VoxelShape makeStreetlightBottomShape() {
                return VoxelShapes.union(
                                VoxelShapes.cuboid(0.3125, 0, 0.3125, 0.6875, 0.1875, 0.6875),
                                VoxelShapes.cuboid(0.375, 0, 0.375, 0.625, 1, 0.625)).simplify();
        }

        private static VoxelShape makeStreetlightPoleShape() {
                return VoxelShapes.cuboid(0.375, 0, 0.375, 0.625, 1, 0.625);
        }

        // --------------------------------------------Road and Street Blocks--------------------------------------------

        public static Block STREETLIGHT_BOTTOM;
        public static Block STREETLIGHT_POLE;
        public static Block STREETLIGHT_SUPPORT;
        public static Block STREETLIGHT_POLE_HORIZONTAL;
        public static Block STREETLIGHT_LIGHT_HEAD_1;
        public static Block REDSTONE_STREETLIGHT_LIGHT_HEAD_1;
        public static Block REMOTE_LEVER;

        public static void registerModBlocks() {
                DynamicStreetlights.LOGGER.info("Registering ModBlocks for " + DynamicStreetlights.MOD_ID);

                STREETLIGHT_BOTTOM = registerBlock("streetlight_bottom",
                                new DefaultBlockShape(baseSettings(), makeStreetlightBottomShape()));

                STREETLIGHT_POLE = registerBlock("streetlight_pole",
                                new DefaultBlockShape(baseSettings(), makeStreetlightPoleShape()));

                STREETLIGHT_SUPPORT = registerBlock("streetlight_support",
                                new DefaultBlockShape(baseSettings(), DEFAULT_BLOCK_SHAPE));

                STREETLIGHT_POLE_HORIZONTAL = registerBlock("streetlight_pole_horizontal",
                                new DefaultBlockShape(baseSettings(), DEFAULT_BLOCK_SHAPE));

                STREETLIGHT_LIGHT_HEAD_1 = registerBlock("streetlight_light_head_1",
                                new StreetlampBlock(baseSettings().luminance(state -> state.get(StreetlampBlock.LIT) ? 15 : 0),
                                                DEFAULT_BLOCK_SHAPE));

                REDSTONE_STREETLIGHT_LIGHT_HEAD_1 = registerBlock("redstone_streetlight_light_head_1",
                                new RedstoneStreetlampHeadBlock(baseSettings().luminance(state -> state.get(RedstoneStreetlampHeadBlock.LIT) ? 15 : 0),
                                                DEFAULT_BLOCK_SHAPE));

                REMOTE_LEVER = registerBlock("remote_lever",
                                new RemoteLeverBlock(AbstractBlock.Settings.create()
                                                .noCollision()
                                                .strength(0.5f)
                                                .pistonBehavior(net.minecraft.block.piston.PistonBehavior.DESTROY)));
        }

        private static Block registerBlock(String name, Block block) {
                registerBlockItem(name, block);
                return Registry.register(Registries.BLOCK, Identifier.of(DynamicStreetlights.MOD_ID, name), block);
        }

        private static Item registerBlockItem(String name, Block block) {
                return Registry.register(Registries.ITEM, Identifier.of(DynamicStreetlights.MOD_ID, name),
                                new BlockItem(block, new Item.Settings()));
        }


}
