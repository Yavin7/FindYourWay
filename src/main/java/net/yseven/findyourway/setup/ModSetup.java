package net.yseven.findyourway.setup;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.yseven.findyourway.item.ModItems;

public class ModSetup {
    public ItemGroup itemGroup = new ItemGroup("findyourway") {
        @java.lang.Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.ENDER_COMPASS);
        }
    }

    public void init() {

    }
}
