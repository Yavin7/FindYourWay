package net.yseven.findyourway.item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {
    public static ItemCompassBase ENDER_COMPASS;
    public static ItemCompassBase VILLAGE_COMPASS;

    public static void init(){
        ENDER_COMPASS = new ItemCompassBase("ender_compass", "Stronghold");
        VILLAGE_COMPASS = new ItemCompassBase("village_compass", "Village");
    }

    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        ENDER_COMPASS.registerItemModel();
        VILLAGE_COMPASS.registerItemModel();
    }
}
