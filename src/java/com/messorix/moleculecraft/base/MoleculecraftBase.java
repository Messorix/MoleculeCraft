package com.messorix.moleculecraft.base;

import com.anime.basic.MainModReference;
import com.messorix.moleculecraft.base.creativetabs.MoleculeCraftTab;
import com.messorix.moleculecraft.base.proxies.CommonProxy;

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

    public static ModAtoms atoms = new ModAtoms(); 
    public static ModBlocks blocks = new ModBlocks();
    public static ModItems items = new ModItems();
    public static ModRecipes recipes = new ModRecipes();
    public static MoleculeCraftTab moleculeCraftTab = new MoleculeCraftTab();
    

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        MoleculecraftBase.proxy.preInit(e);
    }

    @EventHandler
    public void init(FMLInitializationEvent e) {
    	MoleculecraftBase.proxy.init(e);
    	ModTileEntities.registerTileEntities();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
    	MoleculecraftBase.proxy.postInit(e);
    }
}