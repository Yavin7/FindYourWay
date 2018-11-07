package net.yseven.findyourway.Network;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.yseven.findyourway.item.ItemCompassBase;

public class MessageHandlerOnClient implements IMessageHandler<MessageToClient, IMessage> {

    public IMessage onMessage(final MessageToClient message, MessageContext ctx) {
        if (ctx.side != Side.CLIENT) {
            System.err.println("MessageToClient received on wrong side: " + ctx.side);
            return null;
        }
        if(!message.isMessageIsValid()) {
            System.err.println("MessageToClient is not valid: " + message.toString());
            return null;
        }

        Minecraft minecraft = Minecraft.getMinecraft();
        minecraft.addScheduledTask(() -> processMessage(message.getCompass(), message.getStructurePos()));
        return null;
    }

    private void processMessage(ItemCompassBase compass, BlockPos pos) {
        compass.setStructurePos(pos);
    }
}