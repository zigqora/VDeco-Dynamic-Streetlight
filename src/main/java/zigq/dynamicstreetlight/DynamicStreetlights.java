package zigq.dynamicstreetlight;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zigq.dynamicstreetlight.block.StreetlightBlocks;
import zigq.dynamicstreetlight.item.ModItemGroups;
//import zigq.dynamicstreetlight.network.ModMessages;
import zigq.dynamicstreetlight.screen.ModScreenHandlers;
import zigq.dynamicstreetlight.block.entity.ModBlockEntities;

public class DynamicStreetlights implements ModInitializer {
	public static final String MOD_ID = "dynamic_streetlight";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		StreetlightBlocks.registerModBlocks();
		ModItemGroups.registerItemGroups();
		ModBlockEntities.registerBlockEntities();
        ModScreenHandlers.registerScreenHandlers();
        //ModMessages.registerC2SPackets();
	}
}
