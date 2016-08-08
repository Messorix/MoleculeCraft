package com.messorix.moleculecraft.base.proxies;

import com.messorix.moleculecraft.base.MoleculecraftBase;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) 
    {
    	MoleculecraftBase.blocks.registerBlocks();
    	MoleculecraftBase.items.registerItems();
    }

    public void init(FMLInitializationEvent e) 
    {
    	MoleculecraftBase.blocks.setItemModels();
    	MoleculecraftBase.items.setItemModels();
    }

    public void postInit(FMLPostInitializationEvent e) 
    {
    	MoleculecraftBase.recipes.registerRecipes();
    }
}