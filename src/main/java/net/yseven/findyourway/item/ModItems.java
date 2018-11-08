package net.yseven.findyourway.item;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.yseven.findyourway.FindYourWay;

import static net.yseven.findyourway.FindYourWay.modId;

public class ModItems {
    public static ItemCompassBase ENDER_COMPASS;
    public static ItemCompassBase VILLAGE_COMPASS;
    public static ItemCompassBase FORTRESS_COMPASS;
    public static ItemCompassBase MONUMENT_COMPASS;
    public static ItemCompassBase MANSION_COMPASS;

    public static void init(){
        ENDER_COMPASS = new ItemCompassBase("ender_compass", "Stronghold");
        VILLAGE_COMPASS = new ItemCompassBase("village_compass", "Village");
        FORTRESS_COMPASS = new ItemCompassBase("fortress_compass", "Fortress");
        MONUMENT_COMPASS = new ItemCompassBase("monument_compass", "Monument");
        MANSION_COMPASS = new ItemCompassBase("mansion_compass", "Mansion");
    }

    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        ENDER_COMPASS.registerItemModel();
        VILLAGE_COMPASS.registerItemModel();
        FORTRESS_COMPASS.registerItemModel();
        MONUMENT_COMPASS.registerItemModel();
        MANSION_COMPASS.registerItemModel();
    }

    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(ENDER_COMPASS);
        event.getRegistry().register(VILLAGE_COMPASS);
        event.getRegistry().register(FORTRESS_COMPASS);
        event.getRegistry().register(MONUMENT_COMPASS);
        event.getRegistry().register(MANSION_COMPASS);
    }
}