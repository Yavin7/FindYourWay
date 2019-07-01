package net.yseven.findyourway.setup;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ClientProxy implements IProxy {
    @Override
    public void init(FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }
    /*
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        for(int i = 0; i < ServerProxy.compassList.size(); i++) {
            if(hasCompass(ServerProxy.compassList.get(i))) {
                if(getWorld() != ServerProxy.compassList.get(i).getStructureWorld()) {
                    resetStructurePos(ServerProxy.compassList.get(i));
                }
            }
        }
    }
    */
}