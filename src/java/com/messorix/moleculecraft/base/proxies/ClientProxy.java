package com.messorix.moleculecraft.base.proxies;

import com.messorix.moleculecraft.base.MoleculecraftBase;
import com.messorix.moleculecraft.base.events.ItemOverlayEvent;
import com.messorix.moleculecraft.base.events.RenderSelectionEvent;
import com.messorix.moleculecraft.wailaintegration.WailaConfig;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends ServerProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    	MoleculecraftBase.blocks.setBlockModels();
    	MoleculecraftBase.items.setItemModels();
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
        MinecraftForge.EVENT_BUS.register(new ItemOverlayEvent());
        MinecraftForge.EVENT_BUS.register(new RenderSelectionEvent());
    	FMLInterModComms.sendMessage("Waila", "register", WailaConfig.class.getName() + ".callbackRegister");
	}

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }
}