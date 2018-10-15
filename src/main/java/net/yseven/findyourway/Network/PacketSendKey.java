package net.yseven.findyourway.Network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.yseven.findyourway.item.ItemCompassBase;

public class PacketSendKey implements IMessage {
    private BlockPos structurePos;
    private final ItemCompassBase compass;

    @Override
    public void fromBytes(ByteBuf buf) {
        structurePos = BlockPos.fromLong(buf.readLong());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(structurePos.toLong());
    }

    public PacketSendKey(ItemCompassBase compassBase) {
        compass = compassBase;
    }

    public static class Handler implements IMessageHandler<PacketSendKey, IMessage> {
        @Override
        public IMessage onMessage(PacketSendKey message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketSendKey message, MessageContext ctx) {
            //server code
            EntityPlayerMP player = ctx.getServerHandler().player;
            WorldServer world = (WorldServer) player.world;
            if (message.compass != null) {
                message.structurePos = world.getChunkProvider().getNearestStructurePos(world, message.compass.getStructureType(), new BlockPos(player), false);
                if (message.structurePos != null) {
                    PacketHandler.INSTANCE.sendTo(message, player);
                }
            }
        }
    }
}
