package net.yseven.findyourway.Network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.yseven.findyourway.CommonProxy;
import net.yseven.findyourway.item.ItemCompassBase;

public class PacketSendKey implements IMessage {
    private BlockPos structurePos;
    private final ItemCompassBase compass;

    @Override
    public void fromBytes(ByteBuf buf) {}

    @Override
    public void toBytes(ByteBuf buf) {}

    public PacketSendKey(ItemCompassBase compassBase) {
        compass = compassBase;
    }

    public static class Handler implements IMessageHandler<PacketSendKey, IMessage> {
        @Override
        public IMessage onMessage(PacketSendKey message, MessageContext ctx) {
            final EntityPlayerMP player = ctx.getServerHandler().player;
            if (CommonProxy.containsCompass(player.inventory, message.compass)) {
                final WorldServer world = (WorldServer) player.world;
                final int compassId = CommonProxy.setCompassId(message.compass);
                final String structureType = message.compass.getStructureType();
                world.addScheduledTask(new Runnable() {
                    @Override
                    public void run() {
                        BlockPos pos = world.getChunkProvider().getNearestStructurePos(world, structureType, new BlockPos(player), true);
                        if (pos != null) {
                            PacketHandler.INSTANCE.sendTo(new PacketGetKey(message.structurePos, compassId), player);
                        }
                    }
                });
            }
            return null;
        }
    }
}
