package net.yseven.findyourway.Network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.yseven.findyourway.setup.ServerProxy;

public class MessageHandlerOnServer implements IMessageHandler<MessageToServer, IMessage> {
    public IMessage onMessage(final MessageToServer message, MessageContext ctx) {
        if(ctx.side != Side.SERVER) {
            System.err.println("MessageToServer received on wrong side: " + ctx.side);
            return null;
        }
        if(!message.isMessageIsValid()) {
            System.err.println("MessageToServer is not valid: " + message.toString());
        }

        final EntityPlayerMP sendingPlayer = ctx.getServerHandler().player;
        if(sendingPlayer == null) {
            System.err.println("EntityPlayerMP was null MessageToServer was received!");
            return null;
        }
        final WorldServer playerWorldServer = sendingPlayer.getServerWorld();
        playerWorldServer.addScheduledTask(() -> processMessage(message, sendingPlayer));

        return null;
    }

    private void processMessage(MessageToServer message, EntityPlayerMP sendingPlayer) {
        int dimension = sendingPlayer.dimension;
        WorldServer world = (WorldServer)sendingPlayer.world;
        BlockPos structurePos;

        if(world.getChunkProvider().getNearestStructurePos(world, message.getStructureType(), sendingPlayer.getPosition(), true) != null) {
            structurePos = world.getChunkProvider().getNearestStructurePos(world, message.getStructureType(), sendingPlayer.getPosition(), true);
        } else {
            structurePos = new BlockPos(0, 0, 0);
        }

        MessageToClient msg = new MessageToClient(structurePos, message.getCompass());
        if (dimension == sendingPlayer.dimension) {
            ServerProxy.simpleNetworkWrapper.sendTo(msg, sendingPlayer);
        }
    }
}
