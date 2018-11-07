package net.yseven.findyourway.Network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.yseven.findyourway.item.ItemCompassBase;

public class MessageToClient implements IMessage {
    private ItemCompassBase compass;
    private BlockPos structurePos;
    private boolean messageIsValid;

    public boolean isMessageIsValid() {
        return messageIsValid;
    }

    public ItemCompassBase getCompass() {
        return compass;
    }

    public BlockPos getStructurePos() {
        return structurePos;
    }

    public MessageToClient() {
        messageIsValid = false;
    }

    public MessageToClient(BlockPos pos, ItemCompassBase compassBase) {
        structurePos = pos;
        compass = compassBase;
        messageIsValid = true;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        try {
            double x = buf.readDouble();
            double y = buf.readDouble();
            double z = buf.readDouble();
            structurePos = new BlockPos(x, y, z);
            compass = ItemCompassBase.fromBytes(buf);
        } catch (IndexOutOfBoundsException ioe) {
            System.err.println("Error while reading MessageToClient: " + ioe);
            return;
        }
        messageIsValid = true;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        if(!isMessageIsValid()) return;
        buf.writeDouble(structurePos.getX());
        buf.writeDouble(structurePos.getY());
        buf.writeDouble(structurePos.getZ());
        compass.toBytes(buf);
    }

    @Override
    public String toString() {
        return "Structure Position is: " + structurePos.toString() + "!";
    }
}
