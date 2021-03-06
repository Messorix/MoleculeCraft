package com.messorix.moleculecraft.base.proxies;

import com.messorix.moleculecraft.base.MoleculecraftBase;
import com.messorix.moleculecraft.base.oredict.ModOreDictionary;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ServerProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
        MoleculecraftBase.oreDict = new ModOreDictionary();
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }
}