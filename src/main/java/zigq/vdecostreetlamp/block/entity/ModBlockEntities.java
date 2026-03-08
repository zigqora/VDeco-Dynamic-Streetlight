package zigq.vdecostreetlamp.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import zigq.vdecostreetlamp.VDecoStreetLamp;
import zigq.vdecostreetlamp.block.ModBlocks;

public class ModBlockEntities {
    public static BlockEntityType<TrafficSignalBlockEntity> TRAFFIC_SIGNAL_BLOCK_ENTITY;

    public static void registerBlockEntities() {
        TRAFFIC_SIGNAL_BLOCK_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                new Identifier(VDecoStreetLamp.MOD_ID, "traffic_signal_be"),
                FabricBlockEntityTypeBuilder.create(TrafficSignalBlockEntity::new, ModBlocks.TRAFFIC_SIGNAL, ModBlocks.TRAFFIC_SIGNAL_HORIZONTAL).build(null)
        );
    }
}
