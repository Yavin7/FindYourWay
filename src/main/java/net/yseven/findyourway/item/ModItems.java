package net.yseven.findyourway.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {

    public static ItemCompassBase ENDER_COMPASS = new ItemCompassBase("ender_compass", "Stronghold", CreativeTabs.TOOLS);

    public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll(
                ENDER_COMPASS
        );
    }

    public static void registerModels() {
        ENDER_COMPASS.registerItemModel();
    }
}
