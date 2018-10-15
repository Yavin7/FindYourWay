package net.yseven.findyourway.Network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.yseven.findyourway.Client.ClientProxy;
import net.yseven.findyourway.item.ItemCompassBase;

public class PacketGetKey implements IMessage, IMessageHandler<PacketGetKey, IMessage> {
    private BlockPos structurePos;
    private ItemCompassBase compass;

    public PacketGetKey() {}

    public PacketGetKey(BlockPos pos, ItemCompassBase compassBase) {
        structurePos = pos;
        compass = compassBase;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(structurePos.toLong());
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        structurePos = BlockPos.fromLong(buf.readLong());
    }

    @Override
    public IMessage onMessage(PacketGetKey message, MessageContext ctx) {
        //Client Code
        ClientProxy.getMinecraft().addScheduledTask(() -> ClientProxy.setStructurePos(message.compass, message.structurePos));
        return null;
    }
}
