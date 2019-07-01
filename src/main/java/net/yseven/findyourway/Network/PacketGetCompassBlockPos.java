package net.yseven.findyourway.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorld;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.yseven.findyourway.item.ItemCompassBase;

import java.util.function.Supplier;

public class PacketGetCompassBlockPos {
    private ItemCompassBase compass;

    public PacketGetCompassBlockPos() {

    }

    public PacketGetCompassBlockPos(ByteBuf buf) {
        compass = ItemCompassBase.getCompassFromId(buf.readInt());
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(compass.getItemCompassID());
    }

    public PacketGetCompassBlockPos(ItemCompassBase compass) {
        this.compass = compass;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerWorld world = DimensionManager.getWorld(ctx.get().getSender().server, compass.getStructureDimension(), true, false);
            if(world.getDimension().getType() == compass.getStructureDimension()) {
                BlockPos structurePos = world.getWorld().getChunkProvider().getChunkGenerator().findNearestStructure(world, compass.getStructureType(), ctx.get().getSender().getPosition(), 10000, true);
                PacketHandler.INSTANCE.sendTo(new PacketReturnCompassBlockPos(structurePos, compass), ctx.get().getSender().connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
