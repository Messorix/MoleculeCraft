package com.messorix.moleculecraft.base;

import com.messorix.moleculecraft.base.creativetabs.MoleculeCraftTab;
import com.messorix.moleculecraft.base.proxies.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class MoleculecraftBase 
{
	@Instance
    public static MoleculecraftBase instance = new MoleculecraftBase();

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    public static ModAtoms atoms = new ModAtoms(); 
    public static ModBlocks blocks = new ModBlocks();
    public static ModItems items = new ModItems();
    public static ModRecipes recipes = new ModRecipes();
    public static ModTileEntities tileentities = new ModTileEntities();
    public static ModGuiHandlers guis = new ModGuiHandlers();
    public static MoleculeCraftTab moleculeCraftTab = new MoleculeCraftTab();
    

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        MoleculecraftBase.proxy.preInit(e);
    }

    @EventHandler
    public void init(FMLInitializationEvent e) {
    	MoleculecraftBase.proxy.init(e);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
    	MoleculecraftBase.proxy.postInit(e);
    }
}