package net.yseven.findyourway.Client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.yseven.findyourway.CommonProxy;
import net.yseven.findyourway.FindYourWay;
import net.yseven.findyourway.Network.PacketHandler;
import net.yseven.findyourway.Network.PacketSendKey;
import net.yseven.findyourway.item.ItemCompassBase;
import net.yseven.findyourway.item.ModItems;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

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

    //TO-DO fill out the following methods
    public static boolean hasCompass(ItemCompassBase compass) {
        return getPlayer() != null && CommonProxy.containsCompass(getPlayer().inventory, compass);
    }

    public static void setStructurePos(ItemCompassBase compassBase, BlockPos pos) {
        compassBase.setStructurePos(pos);
    }

    public static void resetStructurePos(ItemCompassBase compass) {
        compass.setStructurePos(null);
        compass.setStructureWorld(getWorld());
        PacketHandler.INSTANCE.sendToServer(new PacketSendKey(compass));
    }

    //Proxy Info
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent event) {
            ModItems.registerModels();
        }
    }

    @SubscribeEvent
    public void onModelRegistry(ModelRegistryEvent event) {
        registerModel(ModItems.ENDER_COMPASS);
        registerModel(ModItems.VILLAGE_COMPASS);
    }

    private void registerModel(ItemCompassBase compass) {
        compass.addPropertyOverride(new ResourceLocation(compass.assetTag), new AngleGetter(compass));
        ModelLoader.setCustomModelResourceLocation(compass, 0, new ModelResourceLocation(FindYourWay.modId + ":" + compass.getUnlocalizedName(), "inventory"));
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        for(int i = 0; i < CommonProxy.compassList.size(); i++) {
            if(hasCompass(CommonProxy.compassList.get(i))) {
                if(getWorld() != CommonProxy.compassList.get(i).getStructureWorld()) {
                    resetStructurePos(CommonProxy.compassList.get(i));
                }
            }
        }
    }
}
