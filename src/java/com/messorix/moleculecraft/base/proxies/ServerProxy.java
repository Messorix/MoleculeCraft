package com.messorix.moleculecraft.base.proxies;

import com.anime.basic.MainModReference;
import com.anime.basic.network.GuiHandler;
import com.messorix.moleculecraft.base.MoleculecraftBase;
import com.messorix.moleculecraft.base.init.ModTileEntities;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class ServerProxy {

    public void preInit(FMLPreInitializationEvent e) {
    	MoleculecraftBase.blocks.registerBlocks();
    	MoleculecraftBase.items.registerItems();
    }

    public void init(FMLInitializationEvent e) {
    	MoleculecraftBase.oreDict.applyOreDictionary();
    	ModTileEntities.registerTileEntities();
    	NetworkRegistry.INSTANCE.registerGuiHandler(MainModReference.MODID, new GuiHandler());
    }

    public void postInit(FMLPostInitializationEvent e) {
    	MoleculecraftBase.recipes.registerRecipes();
    }
}