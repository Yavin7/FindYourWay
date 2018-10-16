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
import net.yseven.findyourway.Network.PacketHandler;
import net.yseven.findyourway.item.ItemCompassBase;
import net.yseven.findyourway.item.ModItems;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class CommonProxy {

    public static ArrayList<ItemCompassBase> compassList = new ArrayList<>();

    public void preInit(FMLPreInitializationEvent event) {
        ModItems.init();
        PacketHandler.registerMessages(FindYourWay.modId);
    }

    public void  init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }


    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                ModItems.ENDER_COMPASS
        );
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
            default: return 0;
        }
    }

    public static ItemCompassBase getCompassId(int id) {
        switch (id) {
            case 1: return ModItems.ENDER_COMPASS;
            case 2: return ModItems.VILLAGE_COMPASS;
            default:
                ItemCompassBase ERROR_COMPASS = new ItemCompassBase("error", "");
                return ERROR_COMPASS;
        }
    }
}
