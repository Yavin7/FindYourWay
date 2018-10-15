package net.yseven.findyourway;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.yseven.findyourway.Network.PacketHandler;
import net.yseven.findyourway.item.ItemCompassBase;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class CommonProxy {

    public static ArrayList<ItemCompassBase> compassList = new ArrayList<>();

    public void preInit(FMLPreInitializationEvent event) {
        PacketHandler.registerMessages("findyourway");
    }

    public void  init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    public void registerItemRenderer(Item item, int meta, String id) {
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
