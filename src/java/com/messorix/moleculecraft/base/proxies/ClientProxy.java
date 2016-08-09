package com.messorix.moleculecraft.base.proxies;

import com.messorix.moleculecraft.base.MoleculecraftBase;
import com.messorix.moleculecraft.wailaintegration.WailaConfig;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    	MoleculecraftBase.blocks.setBlockModels();
    	MoleculecraftBase.items.setItemModels();
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
    	FMLInterModComms.sendMessage("Waila", "register", WailaConfig.class.getName() + ".callbackRegister");
	}

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }
}