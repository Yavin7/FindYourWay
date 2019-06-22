package net.yseven.findyourway.setup;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.yseven.findyourway.setup.ServerProxy;
import net.yseven.findyourway.Network.MessageHandlerOnClient;
import net.yseven.findyourway.Network.MessageToClient;
import net.yseven.findyourway.Network.MessageToServer;
import net.yseven.findyourway.item.ItemCompassBase;
import net.yseven.findyourway.item.ModItems;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends ServerProxy {

    //Get information about the player and world
    public static Minecraft getMinecraft() {
        return FMLClientHandler.instance().getClient();
    }
    public static WorldClient getWorld() {
        return getMinecraft().world;
    }
    public static EntityPlayerSP getPlayer() {
        return getMinecraft().player;
    }
    private static boolean angleError = false;

    public static boolean hasAngleErrrored() {
        return angleError;
    }

    public static void AngleHasErrored() {
        angleError = true;
    }

    public static boolean hasCompass(ItemCompassBase compass) {
        return getPlayer() != null && ServerProxy.containsCompass(getPlayer().inventory, compass);
    }

    public static void resetStructurePos(ItemCompassBase compass) {
        compass.setStructurePos(null);
        compass.setStructureWorld(getWorld());
        ServerProxy.simpleNetworkWrapper.sendToServer(new MessageToServer(compass));
    }

    //Proxy Info
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        MinecraftForge.EVENT_BUS.register(this);
        ServerProxy.simpleNetworkWrapper.registerMessage(MessageHandlerOnClient.class, MessageToClient.class, ServerProxy.MESSAGE_TO_CLIENT_ID, Side.CLIENT);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
            ModItems.registerModels();
        }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        for(int i = 0; i < ServerProxy.compassList.size(); i++) {
            if(hasCompass(ServerProxy.compassList.get(i))) {
                if(getWorld() != ServerProxy.compassList.get(i).getStructureWorld()) {
                    resetStructurePos(ServerProxy.compassList.get(i));
                }
            }
        }
    }
}