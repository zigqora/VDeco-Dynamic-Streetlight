package zigq.vdecostreetlamp.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import zigq.vdecostreetlamp.VDecoStreetLamp;
import zigq.vdecostreetlamp.block.ModBlocks;

public class ModItemGroups {

    public static final ItemGroup STREET_LIGHTS_AND_LAMPS = Registry.register(Registries.ITEM_GROUP,
            new Identifier(VDecoStreetLamp.MOD_ID, "2"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup_test.streetlights_and_lamps"))
                    .icon(() -> new ItemStack(ModBlocks.STREETLIGHT_BOTTOM)).entries((displayContext, entries) -> {
                        entries.add(ModBlocks.STREETLIGHT_BOTTOM);
                        entries.add(ModBlocks.STREETLIGHT_POLE);
                        entries.add(ModBlocks.STREETLIGHT_SUPPORT);
                        entries.add(ModBlocks.STREETLIGHT_POLE_HORIZONTAL);
                        entries.add(ModBlocks.STREETLIGHT_LIGHT_HEAD_1);
                    }).build());



    public static void registerItemGroups() {
        VDecoStreetLamp.LOGGER.info("Registering Mod Item Groups for " + VDecoStreetLamp.MOD_ID);
    }
}
