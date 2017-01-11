package com.messorix.moleculecraft.base;

import com.anime.basic.MainModReference;
import com.anime.basic.registry.RegistryHelper;
import com.anime.rf.events.PipeNetworkEventHandler;
import com.anime.rf.network.EnergyNetwork.NetworkRegistry;
import com.anime.rf.network.EnergyNetworkStorage;
import com.anime.rf.network.INetwork;
import com.anime.rf.tileentity.TileEntityEnergyPipe;
import com.anime.rf.tileentity.TileEntityRFGenerator;
import com.messorix.moleculecraft.base.classes.ModMolecules;
import com.messorix.moleculecraft.base.creativetabs.MoleculeCraftTab;
import com.messorix.moleculecraft.base.events.AttachCapabilityEvent;
import com.messorix.moleculecraft.base.init.ModBlocks;
import com.messorix.moleculecraft.base.init.ModItems;
import com.messorix.moleculecraft.base.init.ModRecipes;
import com.messorix.moleculecraft.base.oredict.ModOreDictionary;
import com.messorix.moleculecraft.base.proxies.ServerProxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
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
    public static ServerProxy proxy;

    public static ModBlocks blocks = new ModBlocks();
    public static ModItems items = new ModItems();
    public static ModRecipes recipes = new ModRecipes();
    public static MoleculeCraftTab moleculeCraftTab = new MoleculeCraftTab();
    public static ModOreDictionary oreDict = new ModOreDictionary();

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        proxy.preInit(e);
        CapabilityManager.INSTANCE.register(INetwork.class, new EnergyNetworkStorage(), NetworkRegistry.class);
    }

    @EventHandler
    public void init(FMLInitializationEvent e) {
    	proxy.init(e);
    	MinecraftForge.EVENT_BUS.register(new AttachCapabilityEvent());
    	MinecraftForge.EVENT_BUS.register(new PipeNetworkEventHandler());
    	RegistryHelper.registerTileEntity(TileEntityRFGenerator.class);
    	RegistryHelper.registerTileEntity(TileEntityEnergyPipe.class);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
    	proxy.postInit(e);
    	ModMolecules.createMolecules();
    	ModMolecules.bindMolecules();
    }
    
}