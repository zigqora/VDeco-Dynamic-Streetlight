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
import zigq.dynamicstreetlight.block.StreetlightBlocks;

public class ModItemGroups {

    public static ItemGroup STREET_LIGHTS_AND_LAMPS;

    public static void registerItemGroups() {
        STREET_LIGHTS_AND_LAMPS = Registry.register(Registries.ITEM_GROUP,
                Identifier.of(DynamicStreetlights.MOD_ID, "1"),
                FabricItemGroup.builder().displayName(Text.translatable("itemgroup_test.streetlights_and_lamps"))
                        .icon(() -> new ItemStack(StreetlightBlocks.STREETLIGHT_BOTTOM)).entries((displayContext, entries) -> {
                            entries.add(StreetlightBlocks.STREETLIGHT_BOTTOM);
                            entries.add(StreetlightBlocks.STREETLIGHT_POLE);
                            entries.add(StreetlightBlocks.STREETLIGHT_SUPPORT);
                            entries.add(StreetlightBlocks.STREETLIGHT_POLE_HORIZONTAL);
                            entries.add(StreetlightBlocks.STREETLIGHT_LIGHT_HEAD_1);
                            // entries.add(StreetlightBlocks.TRAFFIC_SIGNAL);
                            // entries.add(StreetlightBlocks.TRAFFIC_SIGNAL_HORIZONTAL);

                            // Redstone Variants
                            entries.add(StreetlightBlocks.REDSTONE_STREETLIGHT_LIGHT_HEAD_1);

                            // Tools and Other Items
                            entries.add(StreetlightBlocks.REMOTE_LEVER);
                        }).build());
    }
}




