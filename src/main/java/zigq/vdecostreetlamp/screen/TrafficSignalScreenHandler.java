package zigq.vdecostreetlamp.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;

public class TrafficSignalScreenHandler extends ScreenHandler {
    private final BlockPos pos;
    public final boolean redOn;
    public final boolean yellowOn;
    public final boolean greenOn;
    public final boolean blinksAtNight;

    public TrafficSignalScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        this(syncId, playerInventory, buf.readBlockPos(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean());
    }

    public TrafficSignalScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos pos, boolean redOn, boolean yellowOn, boolean greenOn, boolean blinksAtNight) {
        super(ModScreenHandlers.TRAFFIC_SIGNAL_SCREEN_HANDLER, syncId);
        this.pos = pos;
        this.redOn = redOn;
        this.yellowOn = yellowOn;
        this.greenOn = greenOn;
        this.blinksAtNight = blinksAtNight;
    }

    public BlockPos getPos() {
        return pos;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY; // No slots to shift-click
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        // Simple distance check
        return player.squaredDistanceTo(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
    }
}
