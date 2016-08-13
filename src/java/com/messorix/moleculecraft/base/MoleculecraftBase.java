package com.messorix.moleculecraft.base;

import com.anime.basic.MainModReference;
import com.anime.basic.logger.ModLogger;
import com.messorix.moleculecraft.base.classes.ModMolecules;
import com.messorix.moleculecraft.base.creativetabs.MoleculeCraftTab;
import com.messorix.moleculecraft.base.events.ItemOverlayEvent;
import com.messorix.moleculecraft.base.oredict.ModOreDictionary;
import com.messorix.moleculecraft.base.proxies.CommonProxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = MainModReference.MODID, name = MainModReference.NAME, version = MainModReference.VERSION)
public class MoleculecraftBase 
{
	
	@Instance
    public static MoleculecraftBase instance = new MoleculecraftBase();

    @SidedProxy(clientSide = MainModReference.CLIENT_PROXY_PATH, serverSide = MainModReference.SERVER_PROXY_PATH)
    public static CommonProxy proxy;

    public static ModBlocks blocks = new ModBlocks();
    public static ModItems items = new ModItems();
    public static ModRecipes recipes = new ModRecipes();
    public static MoleculeCraftTab moleculeCraftTab = new MoleculeCraftTab();
    public static ModOreDictionary oreDict;

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
    	ModLogger.logInfoMessage("Started Pre Initialization.");
        MoleculecraftBase.proxy.preInit(e);
        ModLogger.logInfoMessage("Succesfully Pre initialized.");
    }

    @EventHandler
    public void init(FMLInitializationEvent e) {
    	ModLogger.logInfoMessage("Started Initialization.");
    	MoleculecraftBase.proxy.init(e);
    	oreDict = new ModOreDictionary();
    	ModTileEntities.registerTileEntities();
    	ModMolecules.createMolecules();
    	ModMolecules.bindMolecules();
    	MinecraftForge.EVENT_BUS.register(new ItemOverlayEvent());
    	ModLogger.logInfoMessage("Succesfully Initialized.");
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
    	ModLogger.logInfoMessage("Started Post Initialization.");
    	MoleculecraftBase.proxy.postInit(e);
    	ModLogger.logInfoMessage("Succesfully Post Initialized.");
    }
    
}