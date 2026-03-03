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
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import zigq.vdecostreetlamp.VDecoStreetLamp;
import zigq.vdecostreetlamp.block.custom.DefaultBlockShape;
import zigq.vdecostreetlamp.block.custom.StreetlampBlock;

public class ModBlocks {
    private static final VoxelShape DEFAULT_BLOCK_SHAPE = VoxelShapes.fullCube();


    // --------------------------------------------Road and Street Blocks--------------------------------------------

    public static final Block STREETLIGHT_BOTTOM = registerBlock("streetlight_bottom",
            new DefaultBlockShape(FabricBlockSettings.copyOf(Blocks.STONE).nonOpaque(), DEFAULT_BLOCK_SHAPE));

    public static final Block STREETLIGHT_POLE = registerBlock("streetlight_pole",
            new DefaultBlockShape(FabricBlockSettings.copyOf(Blocks.STONE).nonOpaque(), DEFAULT_BLOCK_SHAPE));

    public static final Block STREETLIGHT_SUPPORT = registerBlock("streetlight_support",
            new DefaultBlockShape(FabricBlockSettings.copyOf(Blocks.STONE).nonOpaque(), DEFAULT_BLOCK_SHAPE));

    public static final Block STREETLIGHT_POLE_HORIZONTAL = registerBlock("streetlight_pole_horizontal",
            new DefaultBlockShape(FabricBlockSettings.copyOf(Blocks.STONE).nonOpaque(), DEFAULT_BLOCK_SHAPE));

    public static final Block STREETLIGHT_LIGHT_HEAD_1 = registerBlock("streetlight_light_head_1",
            new StreetlampBlock(FabricBlockSettings.copyOf(Blocks.STONE)
                    .nonOpaque()
                    .luminance(state -> state.get(StreetlampBlock.LIT) ? 15 : 0),
                    DEFAULT_BLOCK_SHAPE));

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
