package net.yseven.findyourway.Network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.yseven.findyourway.item.ItemCompassBase;

public class MessageToServer implements IMessage {
    private ItemCompassBase compass;
    private String structureType;
    private boolean messageIsValid;

    public MessageToServer(ItemCompassBase compassItem) {
        compass = compassItem;
        messageIsValid = true;
    }

    public String getStructureType() {
        return structureType;
    }

    public ItemCompassBase getCompass() {
        return compass;
    }

    public boolean isMessageIsValid() {
        return messageIsValid;
    }

    public MessageToServer() {
        messageIsValid = false;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        try {
            compass = ItemCompassBase.fromBytes(buf);
        } catch (IndexOutOfBoundsException ioe) {
            System.err.println("Exception while reading MessageToServer: " + ioe);
            return;
        }
        structureType = compass.getStructureType();
        messageIsValid = true;
    }

    @Override
    public void toBytes(ByteBuf buf){
        if(!isMessageIsValid()) return;
        compass.toBytes(buf);
    }

    @Override
    public String toString() {
        return "MessageToServer[compass=" + String.valueOf(compass) + ", structurePos=]";
    }
}
