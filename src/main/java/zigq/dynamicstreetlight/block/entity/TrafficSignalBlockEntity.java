// package zigq.dynamicstreetlight.block.entity;

// import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
// import net.minecraft.block.BlockState;
// import net.minecraft.block.entity.BlockEntity;
// import net.minecraft.entity.player.PlayerEntity;
// import net.minecraft.entity.player.PlayerInventory;
// import net.minecraft.nbt.NbtCompound;
// import net.minecraft.network.PacketByteBuf;
// import net.minecraft.network.listener.ClientPlayPacketListener;
// import net.minecraft.network.packet.Packet;
// import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
// import net.minecraft.screen.ScreenHandler;
// import net.minecraft.server.network.ServerPlayerEntity;
// import net.minecraft.world.World;
// import net.minecraft.text.Text;
// import net.minecraft.util.math.BlockPos;
// import org.jetbrains.annotations.Nullable;
// import zigq.dynamicstreetlight.block.custom.TrafficSignalBlock;
// import zigq.dynamicstreetlight.screen.TrafficSignalScreenHandler;

// public class TrafficSignalBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {

//     private boolean redOn = false;
//     private boolean yellowOn = false;
//     private boolean greenOn = false;
//     private boolean blinksAtNight = false;

//     public TrafficSignalBlockEntity(BlockPos pos, BlockState state) {
//         super(ModBlockEntities.TRAFFIC_SIGNAL_BLOCK_ENTITY, pos, state);
//     }

//     public boolean isRedOn() { return redOn; }
//     public boolean isYellowOn() { return yellowOn; }
//     public boolean isGreenOn() { return greenOn; }
//     public boolean isBlinkingAtNight() { return blinksAtNight; }

//     public void setRedOn(boolean redOn) {
//         this.redOn = redOn;
//         markDirtyAndSync();
//     }

//     public void setYellowOn(boolean yellowOn) {
//         this.yellowOn = yellowOn;
//         markDirtyAndSync();
//     }

//     public void setGreenOn(boolean greenOn) {
//         this.greenOn = greenOn;
//         markDirtyAndSync();
//     }

//     public void setBlinksAtNight(boolean blinksAtNight) {
//         this.blinksAtNight = blinksAtNight;
//         markDirtyAndSync();
//     }

//     private void markDirtyAndSync() {
//         markDirty();
//         if (world != null && !world.isClient()) {
//             world.updateListeners(pos, getCachedState(), getCachedState(), 3);
//         }
//     }

//     @Override
//     protected void writeNbt(NbtCompound nbt) {
//         super.writeNbt(nbt);
//         nbt.putBoolean("RedOn", redOn);
//         nbt.putBoolean("YellowOn", yellowOn);
//         nbt.putBoolean("GreenOn", greenOn);
//         nbt.putBoolean("BlinksAtNight", blinksAtNight);
//     }

//     @Override
//     public void readNbt(NbtCompound nbt) {
//         super.readNbt(nbt);
//         this.redOn = nbt.getBoolean("RedOn");
//         this.yellowOn = nbt.getBoolean("YellowOn");
//         this.greenOn = nbt.getBoolean("GreenOn");
//         this.blinksAtNight = nbt.getBoolean("BlinksAtNight");
//     }

//     @Override
//     public Packet<ClientPlayPacketListener> toUpdatePacket() {
//         return BlockEntityUpdateS2CPacket.create(this);
//     }

//     @Override
//     public NbtCompound toInitialChunkDataNbt() {
//         return createNbt();
//     }

//     @Override
//     public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
//         buf.writeBlockPos(this.pos);
//         buf.writeBoolean(redOn);
//         buf.writeBoolean(yellowOn);
//         buf.writeBoolean(greenOn);
//         buf.writeBoolean(blinksAtNight);
//     }

//     @Override
//     public Text getDisplayName() {
//         return Text.translatable("block.DynamicStreetlights.trafficsignal");
//     }

//     @Nullable
//     @Override
//     public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
//         return new TrafficSignalScreenHandler(syncId, playerInventory, this.pos, redOn, yellowOn, greenOn, blinksAtNight);
//     }

//     public static void tick(World world, BlockPos pos, BlockState state, TrafficSignalBlockEntity entity) {
//         if (world.isClient()) {
//             return;
//         }

//         if (entity.blinksAtNight && !world.isDay()) {
//             boolean globalBlinkState = (world.getTime() % 40) < 20;
//             
//             if (entity.yellowOn != globalBlinkState || entity.redOn || entity.greenOn) {
//                 entity.yellowOn = globalBlinkState;
//                 entity.redOn = false;
//                 entity.greenOn = false;
//                 
//                 entity.markDirtyAndSync();
//                 
//                 world.setBlockState(pos, state
//                         .with(TrafficSignalBlock.RED, false)
//                         .with(TrafficSignalBlock.YELLOW, entity.yellowOn)
//                         .with(TrafficSignalBlock.GREEN, false), 3);
//             }
//         } else if (entity.blinksAtNight && world.isDay() && (entity.yellowOn || entity.redOn || entity.greenOn)) {
//            entity.yellowOn = false;
//            entity.redOn = false;
//            entity.greenOn = false;
//            entity.markDirtyAndSync();
//            
//            world.setBlockState(pos, state
//                 .with(TrafficSignalBlock.RED, false)
//                 .with(TrafficSignalBlock.YELLOW, false)
//                 .with(TrafficSignalBlock.GREEN, false), 3);
//         }
//     }
// }



