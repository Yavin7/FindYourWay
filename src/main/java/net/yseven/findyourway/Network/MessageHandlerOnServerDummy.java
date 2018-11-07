package net.yseven.findyourway.Network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageHandlerOnServerDummy implements IMessageHandler<MessageToClient, IMessage> {
    public IMessage onMessage(final MessageToClient message, MessageContext ctx) {
        System.err.println("TargetEffectMessageToClient received on wrong side:" + ctx.side);
        return null;
    }
}
