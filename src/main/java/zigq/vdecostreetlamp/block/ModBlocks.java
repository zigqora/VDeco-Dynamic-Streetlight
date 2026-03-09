// src/main/java/net/zigq/vdeco/block/ModBlocks.java
package zigq.vdecostreetlamp.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import zigq.vdecostreetlamp.VDecoStreetLamp;
import zigq.vdecostreetlamp.block.custom.DefaultBlockShape;
import zigq.vdecostreetlamp.block.custom.RedstoneStreetlampHeadBlock;
import zigq.vdecostreetlamp.block.custom.RemoteLeverBlock;
import zigq.vdecostreetlamp.block.custom.StreetlampBlock;
//import zigq.vdecostreetlamp.block.custom.TrafficSignalBlock;

public class ModBlocks {
        private static final VoxelShape DEFAULT_BLOCK_SHAPE = VoxelShapes.fullCube();

        // Setup common settings to reduce duplicated processing at startup
        private static final FabricBlockSettings STREETLIGHT_SETTINGS = FabricBlockSettings.copyOf(Blocks.STONE)
                        .nonOpaque();

        private static VoxelShape makeStreetlightBottomShape() {
                return VoxelShapes.union(
                                VoxelShapes.cuboid(0.3125, 0, 0.3125, 0.6875, 0.1875, 0.6875),
                                VoxelShapes.cuboid(0.375, 0, 0.375, 0.625, 1, 0.625)).simplify();
        }

        private static VoxelShape makeStreetlightPoleShape() {
                return VoxelShapes.cuboid(0.375, 0, 0.375, 0.625, 1, 0.625);
        }

        // --------------------------------------------Road and Street
        // Blocks--------------------------------------------

        public static final Block STREETLIGHT_BOTTOM = registerBlock("streetlight_bottom",
                        new DefaultBlockShape(STREETLIGHT_SETTINGS, makeStreetlightBottomShape()));

        public static final Block STREETLIGHT_POLE = registerBlock("streetlight_pole",
                        new DefaultBlockShape(STREETLIGHT_SETTINGS, makeStreetlightPoleShape()));

        public static final Block STREETLIGHT_SUPPORT = registerBlock("streetlight_support",
                        new DefaultBlockShape(STREETLIGHT_SETTINGS, DEFAULT_BLOCK_SHAPE));

        public static final Block STREETLIGHT_POLE_HORIZONTAL = registerBlock("streetlight_pole_horizontal",
                        new DefaultBlockShape(STREETLIGHT_SETTINGS, DEFAULT_BLOCK_SHAPE));

        public static final Block STREETLIGHT_LIGHT_HEAD_1 = registerBlock("streetlight_light_head_1",
                        new StreetlampBlock(FabricBlockSettings.copyOf(STREETLIGHT_SETTINGS)
                                        .luminance(state -> state.get(StreetlampBlock.LIT) ? 15 : 0),
                                        DEFAULT_BLOCK_SHAPE));

        // --------------------------------------------Redstone Street
        // Blocks--------------------------------------------

        public static final Block REDSTONE_STREETLIGHT_LIGHT_HEAD_1 = registerBlock("redstone_streetlight_light_head_1",
                        new RedstoneStreetlampHeadBlock(FabricBlockSettings.copyOf(STREETLIGHT_SETTINGS)
                                        .luminance(state -> state.get(RedstoneStreetlampHeadBlock.LIT) ? 15 : 0),
                                        DEFAULT_BLOCK_SHAPE));

        public static final Block REMOTE_LEVER = registerBlock("remote_lever",
                        new RemoteLeverBlock(FabricBlockSettings.copyOf(Blocks.LEVER)));

        // public static final Block TRAFFIC_SIGNAL = registerBlock("trafficsignal",
        // new
        // TrafficSignalBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()
        // .luminance(state -> (state.get(TrafficSignalBlock.RED) ||
        // state.get(TrafficSignalBlock.YELLOW) ||
        // state.get(TrafficSignalBlock.GREEN)) ? 10 : 0)));

        // public static final Block TRAFFIC_SIGNAL_HORIZONTAL =
        // registerBlock("trafficsignal_horizontal",
        // new
        // TrafficSignalBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()
        // .luminance(state -> (state.get(TrafficSignalBlock.RED) ||
        // state.get(TrafficSignalBlock.YELLOW) ||
        // state.get(TrafficSignalBlock.GREEN)) ? 10 : 0)));

        private static Block registerBlock(String name, Block block) {
                registerBlockItem(name, block);
                return Registry.register(Registries.BLOCK, new Identifier(VDecoStreetLamp.MOD_ID, name), block);
        }

        private static Item registerBlockItem(String name, Block block) {
                return Registry.register(Registries.ITEM, new Identifier(VDecoStreetLamp.MOD_ID, name),
                                new BlockItem(block, new FabricItemSettings()));
        }

        public static void registerModBlocks() {
                VDecoStreetLamp.LOGGER.info("Register ModBlocks for " + VDecoStreetLamp.MOD_ID);
        }

}
