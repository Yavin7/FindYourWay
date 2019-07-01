package net.yseven.findyourway;


import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.yseven.findyourway.item.ItemCompassBase;
import net.yseven.findyourway.item.ModItems;
import net.yseven.findyourway.network.PacketHandler;
import net.yseven.findyourway.setup.ClientProxy;
import net.yseven.findyourway.setup.IProxy;
import net.yseven.findyourway.setup.ServerProxy;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

@Mod("findyourway")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class FindYourWay {
    public static final String MOD_NAME = "Find Your Way";
    public static final String MODID = "findyourway";
    public static final String VERSION = "2.0.0-beta";

    public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

    public static final Logger LOGGER = LogManager.getLogger();

    public static ItemGroup creativeTab = new ItemGroup("FindYourWay") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.ENDER_COMPASS);
        }
    };

    public static ArrayList<ItemCompassBase> compassList = new ArrayList<ItemCompassBase>();

    public static SimpleChannel networkHandler;

    public FindYourWay() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        PacketHandler.registerMessages(FindYourWay.MODID);
        proxy.init(event);
    }

    @SubscribeEvent
    public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
        ModItems.init();

        LOGGER.log(Level.INFO, "Registering items");

        event.getRegistry().register(ModItems.ENDER_COMPASS);
        event.getRegistry().register(ModItems.FORTRESS_COMPASS);
        event.getRegistry().register(ModItems.MANSION_COMPASS);
        event.getRegistry().register(ModItems.MONUMENT_COMPASS);
        event.getRegistry().register(ModItems.VILLAGE_COMPASS);
    }
}
