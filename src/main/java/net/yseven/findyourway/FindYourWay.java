package net.yseven.findyourway;


import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.yseven.findyourway.setup.ClientProxy;
import net.yseven.findyourway.setup.IProxy;
import net.yseven.findyourway.setup.ModSetup;
import net.yseven.findyourway.setup.ServerProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("findyourway")
public class FindYourWay {
    //TODO Fix the ServerProxy and ClientProxy and add both to the setup package
    public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy())

    public static final Logger LOGGER = LogManager.getLogger();

    public static ModSetup setup = new ModSetup();

    public FindYourWay() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        setup.init();
        proxy.init();
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
            Item.Properties properties = new Item.Properties().group(setup.itemGroup);
        }
    }
}
