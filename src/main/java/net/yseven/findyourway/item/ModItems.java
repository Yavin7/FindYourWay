package net.yseven.findyourway.item;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.yseven.findyourway.Config;
import net.yseven.findyourway.FindYourWay;

import static net.yseven.findyourway.FindYourWay.modId;

public class ModItems {
    public static ItemCompassBase ENDER_COMPASS;
    public static ItemCompassBase VILLAGE_COMPASS;
    public static ItemCompassBase FORTRESS_COMPASS;
    public static ItemCompassBase MONUMENT_COMPASS;
    public static ItemCompassBase MANSION_COMPASS;

    public static void init(){
        if(Config.useEnderCompass) {
            ENDER_COMPASS = new ItemCompassBase("ender_compass", "Stronghold");
        }
        if(Config.useFortressCompass) {
            FORTRESS_COMPASS = new ItemCompassBase("fortress_compass", "Fortress");
        }
        if(Config.useMansionCompass) {
            MANSION_COMPASS = new ItemCompassBase("mansion_compass", "Mansion");
        }
        if(Config.useMonumentCompass) {
            MONUMENT_COMPASS = new ItemCompassBase("monument_compass", "Monument");
        }
        if(Config.useVillageCompass) {
            VILLAGE_COMPASS = new ItemCompassBase("village_compass", "Village");
        }
    }

    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        if(Config.useEnderCompass) {
            ENDER_COMPASS.registerItemModel();
        }
        if(Config.useFortressCompass) {
            FORTRESS_COMPASS.registerItemModel();
        }
        if(Config.useMansionCompass) {
            MANSION_COMPASS.registerItemModel();
        }
        if(Config.useMonumentCompass) {
            MONUMENT_COMPASS.registerItemModel();
        }
        if(Config.useVillageCompass) {
            VILLAGE_COMPASS.registerItemModel();
        }
    }

    public static void registerItems(RegistryEvent.Register<Item> event) {
        if(Config.useEnderCompass) {
            event.getRegistry().register(ENDER_COMPASS);
        }
        if(Config.useFortressCompass) {
            event.getRegistry().register(FORTRESS_COMPASS);
        }
        if(Config.useMansionCompass) {
            event.getRegistry().register(MANSION_COMPASS);
        }
        if(Config.useMonumentCompass) {
            event.getRegistry().register(MONUMENT_COMPASS);
        }
        if(Config.useVillageCompass) {
            event.getRegistry().register(VILLAGE_COMPASS);
        }
    }
}