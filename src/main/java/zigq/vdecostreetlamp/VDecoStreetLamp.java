package zigq.vdecostreetlamp;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zigq.vdecostreetlamp.block.ModBlocks;
import zigq.vdecostreetlamp.item.ModItemGroups;

public class VDecoStreetLamp implements ModInitializer {
	public static final String MOD_ID = "vdecostreetlamp";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();
		ModBlocks.registerModBlocks();
		LOGGER.info("Hello Fabric world!");
	}
}