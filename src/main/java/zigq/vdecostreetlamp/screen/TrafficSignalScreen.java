package zigq.vdecostreetlamp.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import zigq.vdecostreetlamp.network.ModMessages;

public class TrafficSignalScreen extends HandledScreen<TrafficSignalScreenHandler> {

    private boolean redTracker;
    private boolean yellowTracker;
    private boolean greenTracker;
    private boolean blinksAtNightTracker;

    public TrafficSignalScreen(TrafficSignalScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.redTracker = handler.redOn;
        this.yellowTracker = handler.yellowOn;
        this.greenTracker = handler.greenOn;
        this.blinksAtNightTracker = handler.blinksAtNight;
    }

    @Override
    protected void init() {
        super.init();
        
        int buttonWidth = 80;
        int buttonHeight = 20;

        int startX = (width - backgroundWidth) / 2 + (backgroundWidth / 2) - (buttonWidth / 2);
        int startY = (height - backgroundHeight) / 2 + 30;

        addDrawableChild(ButtonWidget.builder(Text.literal("Toggle Red").styled(s -> s.withColor(redTracker ? 0xFF5555 : 0xAAAAAA)), button -> {
            redTracker = !redTracker;
            button.setMessage(Text.literal("Toggle Red").styled(s -> s.withColor(redTracker ? 0xFF5555 : 0xAAAAAA)));
            sendToggleEvent("red");
        }).dimensions(startX, startY, buttonWidth, buttonHeight).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("Toggle Yellow").styled(s -> s.withColor(yellowTracker ? 0xFFFF55 : 0xAAAAAA)), button -> {
            yellowTracker = !yellowTracker;
            button.setMessage(Text.literal("Toggle Yellow").styled(s -> s.withColor(yellowTracker ? 0xFFFF55 : 0xAAAAAA)));
            sendToggleEvent("yellow");
        }).dimensions(startX, startY + 25, buttonWidth, buttonHeight).build());
        
        addDrawableChild(ButtonWidget.builder(Text.literal("Toggle Green").styled(s -> s.withColor(greenTracker ? 0x55FF55 : 0xAAAAAA)), button -> {
            greenTracker = !greenTracker;
            button.setMessage(Text.literal("Toggle Green").styled(s -> s.withColor(greenTracker ? 0x55FF55 : 0xAAAAAA)));
            sendToggleEvent("green");
        }).dimensions(startX, startY + 50, buttonWidth, buttonHeight).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("Blinks at Night").styled(s -> s.withColor(blinksAtNightTracker ? 0x55FFFF : 0xAAAAAA)), button -> {
            blinksAtNightTracker = !blinksAtNightTracker;
            button.setMessage(Text.literal("Blinks at Night").styled(s -> s.withColor(blinksAtNightTracker ? 0x55FFFF : 0xAAAAAA)));
            sendToggleEvent("blink_night");
        }).dimensions(startX, startY + 75, buttonWidth, buttonHeight).build());
    }

    private void sendToggleEvent(String color) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(handler.getPos());
        buf.writeString(color);
        ClientPlayNetworking.send(ModMessages.SET_TRAFFIC_SIGNAL_STATE, buf);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        // Intentionally left blank to remove the GUI background
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        // Intentionally left blank to hide the word "Inventory" and "Traffic Signal" titles
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
