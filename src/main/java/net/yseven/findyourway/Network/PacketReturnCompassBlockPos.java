package net.yseven.findyourway.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;
import net.yseven.findyourway.FindYourWay;
import net.yseven.findyourway.item.ItemCompassBase;

import java.util.function.Supplier;

public class PacketReturnCompassBlockPos {
    private BlockPos pos;
    private ItemCompassBase compass;

    public PacketReturnCompassBlockPos() {

    }

    public PacketReturnCompassBlockPos(BlockPos pos, ItemCompassBase compass) {
        this.pos = pos;
        this.compass = compass;
    }

    public PacketReturnCompassBlockPos(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        compass = ItemCompassBase.getCompassFromId(buf.readInt());
    }

    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        buf.writeInt(compass.getItemCompassID());
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            compass.setStructurePos(pos);
        });
        ctx.get().setPacketHandled(true);
    }
}
