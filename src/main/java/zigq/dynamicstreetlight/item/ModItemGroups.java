package zigq.dynamicstreetlight.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import zigq.dynamicstreetlight.DynamicStreetlights;
import zigq.dynamicstreetlight.block.ModBlocks;

public class ModItemGroups {

    public static final ItemGroup STREET_LIGHTS_AND_LAMPS = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(DynamicStreetlights.MOD_ID, "1"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup_test.streetlights_and_lamps"))
                    .icon(() -> new ItemStack(ModBlocks.STREETLIGHT_BOTTOM)).entries((displayContext, entries) -> {
                        entries.add(ModBlocks.STREETLIGHT_BOTTOM);
                        entries.add(ModBlocks.STREETLIGHT_POLE);
                        entries.add(ModBlocks.STREETLIGHT_SUPPORT);
                        entries.add(ModBlocks.STREETLIGHT_POLE_HORIZONTAL);
                        entries.add(ModBlocks.STREETLIGHT_LIGHT_HEAD_1);
                        // entries.add(ModBlocks.TRAFFIC_SIGNAL);
                        // entries.add(ModBlocks.TRAFFIC_SIGNAL_HORIZONTAL);

                        // Redstone Variants
                        entries.add(ModBlocks.REDSTONE_STREETLIGHT_LIGHT_HEAD_1);

                        // Tools and Other Items
                        entries.add(ModBlocks.REMOTE_LEVER);
                    }).build());

    public static void registerItemGroups() {
    }
}




