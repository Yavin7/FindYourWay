package net.yseven.findyourway.network;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.yseven.findyourway.FindYourWay;

public class PacketHandler {
    public static SimpleChannel INSTANCE;
    private static int ID = 0;

    public static int nextPacketId() {
        return ID++;
    }

    public static void registerMessages(String channelName) {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(FindYourWay.MODID, channelName), () -> "1.0", s -> true, s -> true);

        //Server Side
        INSTANCE.registerMessage(nextPacketId(), PacketReturnCompassBlockPos.class,
                PacketReturnCompassBlockPos::toBytes,
                PacketReturnCompassBlockPos::new,
                PacketReturnCompassBlockPos::handle);

        //Client Side
        INSTANCE.registerMessage(nextPacketId(), PacketGetCompassBlockPos.class,
                PacketGetCompassBlockPos::toBytes,
                PacketGetCompassBlockPos::new,
                PacketGetCompassBlockPos::handle);
    }
}
