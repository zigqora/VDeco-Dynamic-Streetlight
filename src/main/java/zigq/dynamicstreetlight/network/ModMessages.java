package zigq.dynamicstreetlight.network;

// import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
// import net.minecraft.block.BlockState;
// import net.minecraft.util.Identifier;
// import zigq.dynamicstreetlight.DynamicStreetlights;
// import zigq.dynamicstreetlight.block.custom.TrafficSignalBlock;
// import zigq.dynamicstreetlight.block.entity.TrafficSignalBlockEntity;

public class ModMessages {
    // public static final Identifier SET_TRAFFIC_SIGNAL_STATE = new
    // Identifier(DynamicStreetlights.MOD_ID, "set_traffic_signal_state");

    public static void registerC2SPackets() {
        // ServerPlayNetworking.registerGlobalReceiver(SET_TRAFFIC_SIGNAL_STATE,
        // (server, player, handler, buf, responseSender) -> {
        // var pos = buf.readBlockPos();
        // var colorToToggle = buf.readString();

        // server.execute(() -> {
        // if (player.getWorld().getBlockEntity(pos) instanceof TrafficSignalBlockEntity
        // blockEntity) {
        // boolean redOn = blockEntity.isRedOn();
        // boolean yellowOn = blockEntity.isYellowOn();
        // boolean greenOn = blockEntity.isGreenOn();

        // switch (colorToToggle) {
        // case "red": blockEntity.setRedOn(!redOn); redOn = !redOn; break;
        // case "yellow": blockEntity.setYellowOn(!yellowOn); yellowOn = !yellowOn;
        // break;
        // case "green": blockEntity.setGreenOn(!greenOn); greenOn = !greenOn; break;
        // case "blink_night":
        // blockEntity.setBlinksAtNight(!blockEntity.isBlinkingAtNight()); break;
        // }

        // if (!colorToToggle.equals("blink_night")) {
        // BlockState blockState = player.getWorld().getBlockState(pos);
        // player.getWorld().setBlockState(pos, blockState
        // .with(TrafficSignalBlock.RED, redOn)
        // .with(TrafficSignalBlock.YELLOW, yellowOn)
        // .with(TrafficSignalBlock.GREEN, greenOn), 3);
        // }
        // }
        // });
        // });
    }

    // public static void registerS2CPackets() {
    // }
}



