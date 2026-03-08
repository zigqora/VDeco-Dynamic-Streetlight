package zigq.vdecostreetlamp;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import zigq.vdecostreetlamp.screen.ModScreenHandlers;
import zigq.vdecostreetlamp.screen.TrafficSignalScreen;

public class VDecoStreetLampClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.TRAFFIC_SIGNAL_SCREEN_HANDLER, TrafficSignalScreen::new);
	}
}
