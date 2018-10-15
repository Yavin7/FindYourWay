package net.yseven.findyourway;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Util {
    public static boolean checkInventoryForCompass(InventoryPlayer inventory, Item compass) {
        for(int x = 0; x < inventory.getSizeInventory(); x++) {
            ItemStack stack = inventory.getStackInSlot(x);
            if(!stack.isEmpty() && stack.getItem() == compass) {
                return true;
            }
        }
        return false;
    }
}
