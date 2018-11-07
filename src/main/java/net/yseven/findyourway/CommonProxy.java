package net.yseven.findyourway;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.yseven.findyourway.Network.MessageHandlerOnServer;
import net.yseven.findyourway.Network.MessageHandlerOnServerDummy;
import net.yseven.findyourway.Network.MessageToClient;
import net.yseven.findyourway.Network.MessageToServer;
import net.yseven.findyourway.item.ItemCompassBase;
import net.yseven.findyourway.item.ModItems;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class CommonProxy {

    public static ArrayList<ItemCompassBase> compassList = new ArrayList<ItemCompassBase>();
    public static SimpleNetworkWrapper simpleNetworkWrapper;

    public static final byte MESSAGE_TO_SERVER_ID = 71;
    public static final byte MESSAGE_TO_CLIENT_ID = 72;

    public void preInit(FMLPreInitializationEvent event) {
        ModItems.init();

        simpleNetworkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(FindYourWay.modId);
        simpleNetworkWrapper.registerMessage(MessageHandlerOnServer.class, MessageToServer.class, MESSAGE_TO_SERVER_ID, Side.SERVER);
        simpleNetworkWrapper.registerMessage(MessageHandlerOnServerDummy.class, MessageToClient.class, MESSAGE_TO_CLIENT_ID, Side.SERVER);
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(ModItems.ENDER_COMPASS);
        event.getRegistry().register(ModItems.VILLAGE_COMPASS);
        event.getRegistry().register(ModItems.FORTRESS_COMPASS);
        event.getRegistry().register(ModItems.MONUMENT_COMPASS);
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

    public static int setCompassId(ItemCompassBase compass) {
        switch (compass.getStructureType()){
            case "Stronghold": return 1;
            case "Village": return 2;
            case "Fortress": return 3;
            case "Monument": return 4;
            default: return 0;
        }
    }

    public static ItemCompassBase getCompassId(int id) {
        switch (id) {
            case 1: return ModItems.ENDER_COMPASS;
            case 2: return ModItems.VILLAGE_COMPASS;
            case 3: return ModItems.FORTRESS_COMPASS;
            case 4: return ModItems.MONUMENT_COMPASS;
            default:
                ItemCompassBase ERROR_COMPASS = new ItemCompassBase("error", "");
                return ERROR_COMPASS;
        }
    }
}
