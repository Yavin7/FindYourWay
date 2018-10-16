package net.yseven.findyourway;


import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid = FindYourWay.modId, name = FindYourWay.name, version = FindYourWay.version)
public class FindYourWay {
    public static final String modId = "findyourway";
    public static final String name = "Find Your Way";
    public static final String version = "1.0.0-beta";

    @Mod.Instance(modId)
    public static FindYourWay instance;

    @SidedProxy(serverSide = "net.yseven.findyourway.CommonProxy", clientSide = "net.yseven.findyourway.Client.ClientProxy")
    public static CommonProxy proxy;
    public static SimpleNetworkWrapper network;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        System.out.println(name + " is loading!");
        System.out.println("FIND YOUR WAY TEST LINE -------------------------------------- TEST");

        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
