package net.yseven.findyourway.item;

import net.minecraft.item.Item;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;
import net.yseven.findyourway.FindYourWay;
import org.apache.logging.log4j.Level;

import java.io.Console;

public class ModItems {
    @ObjectHolder("findyourway:ender_compass")
    public static ItemCompassBase ENDER_COMPASS;
    @ObjectHolder("findyourway:fortress_compass")
    public static ItemCompassBase FORTRESS_COMPASS;
    @ObjectHolder("findyourway:monument_compass")
    public static ItemCompassBase MONUMENT_COMPASS;
    @ObjectHolder("findyourway:mansion_compass")
    public static ItemCompassBase MANSION_COMPASS;
    @ObjectHolder("findyourway:village_compass")
    public static ItemCompassBase VILLAGE_COMPASS;

    public static void init(){
        /*
        if(Config.useEnderCompass) {
            ENDER_COMPASS = new ItemCompassBase("ender_compass", "Stronghold", DimensionType.OVERWORLD);
        }
        if(Config.useFortressCompass) {
            FORTRESS_COMPASS = new ItemCompassBase("fortress_compass", "Fortress", DimensionType.NETHER);
        }
        if(Config.useMansionCompass) {
            MANSION_COMPASS = new ItemCompassBase("mansion_compass", "Mansion", DimensionType.OVERWORLD);
        }
        if(Config.useMonumentCompass) {
            MONUMENT_COMPASS = new ItemCompassBase("monument_compass", "Monument", DimensionType.OVERWORLD);
        }
        if(Config.useVillageCompass) {
            VILLAGE_COMPASS = new ItemCompassBase("village_compass", "Village", DimensionType.OVERWORLD);
        }

         */
        ENDER_COMPASS = new ItemCompassBase("ender_compass", "Stronghold", DimensionType.OVERWORLD);
        FORTRESS_COMPASS = new ItemCompassBase("fortress_compass", "Fortress", DimensionType.NETHER);
        MANSION_COMPASS = new ItemCompassBase("mansion_compass", "Mansion", DimensionType.OVERWORLD);
        MONUMENT_COMPASS = new ItemCompassBase("monument_compass", "Monument", DimensionType.OVERWORLD);
        VILLAGE_COMPASS = new ItemCompassBase("village_compass", "Village", DimensionType.OVERWORLD);
    }
}