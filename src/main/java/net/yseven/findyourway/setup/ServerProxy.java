package net.yseven.findyourway.setup;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.yseven.findyourway.Config;
import net.yseven.findyourway.FindYourWay;
import net.yseven.findyourway.Network.MessageHandlerOnServer;
import net.yseven.findyourway.Network.MessageHandlerOnServerDummy;
import net.yseven.findyourway.Network.MessageToClient;
import net.yseven.findyourway.Network.MessageToServer;
import net.yseven.findyourway.item.ItemCompassBase;
import net.yseven.findyourway.item.ModItems;

import java.io.File;
import java.util.ArrayList;

@Mod.EventBusSubscriber
public class ServerProxy {

    public static ArrayList<ItemCompassBase> compassList = new ArrayList<ItemCompassBase>();
    public static SimpleNetworkWrapper simpleNetworkWrapper;
    public static Configuration config;

    public static final byte MESSAGE_TO_SERVER_ID = 71;
    public static final byte MESSAGE_TO_CLIENT_ID = 72;

    public void preInit(FMLPreInitializationEvent event) {
        File directory = event.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "findyourway.cfg"));
        Config.readConfig();

        ModItems.init();

        simpleNetworkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(FindYourWay.modId);
        simpleNetworkWrapper.registerMessage(MessageHandlerOnServer.class, MessageToServer.class, MESSAGE_TO_SERVER_ID, Side.SERVER);
        simpleNetworkWrapper.registerMessage(MessageHandlerOnServerDummy.class, MessageToClient.class, MESSAGE_TO_CLIENT_ID, Side.SERVER);
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {
        if(config.hasChanged()){
            config.save();
        }

    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        ModItems.registerItems(event);
    }

    public static boolean containsCompass(IInventory inventory, ItemCompassBase compass) {
        for(int slot = 0; slot < inventory.getSizeInventory(); slot++) {
            ItemStack stack = inventory.getStackInSlot(slot);
            if(!stack.isEmpty() && stack.getItem() == compass) {
                return true;
            }
        }
        return false;
    }
}
