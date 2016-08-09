package com.messorix.moleculecraft.base;

import java.util.ArrayList;
import java.util.List;

import com.anime.basic.registry.RegistryHelper;
import com.messorix.moleculecraft.base.blocks.BlockAcanthiteOre;
import com.messorix.moleculecraft.base.blocks.BlockCassiteriteOre;
import com.messorix.moleculecraft.base.blocks.BlockChalcociteOre;
import com.messorix.moleculecraft.base.blocks.BlockFluxFurnace;
import com.messorix.moleculecraft.base.blocks.BlockFluxGrinder;
import com.messorix.moleculecraft.base.blocks.BlockMachine;
import com.messorix.moleculecraft.base.blocks.BlockOre;
import com.messorix.moleculecraft.base.blocks.ModBlock;

public class ModBlocks 
{
	public static BlockMachine FLUXFURNACE;
	public static BlockMachine FLUXFURNACEWORKING;
	public static BlockMachine FLUXGRINDER;
	public static BlockMachine FLUXGRINDERWORKING;

	public static BlockOre CHALCOCITEORE;
	public static BlockOre ACANTHITEORE;
	public static BlockOre CASSITERITEORE;

    public static List<ModBlock> modblocklist = new ArrayList<ModBlock>();
    public static List<BlockOre> oreblocklist = new ArrayList<BlockOre>();
    
	public void registerBlocks()
    {
		RegistryHelper.registerBlock(CHALCOCITEORE = new BlockChalcociteOre(), BlockChalcociteOre.NAME);
		oreblocklist.add(CHALCOCITEORE);
		RegistryHelper.registerBlock(ACANTHITEORE = new BlockAcanthiteOre(), BlockAcanthiteOre.NAME);
		oreblocklist.add(ACANTHITEORE);
		RegistryHelper.registerBlock(CASSITERITEORE = new BlockCassiteriteOre(), BlockCassiteriteOre.NAME);
		oreblocklist.add(CASSITERITEORE);
		
		RegistryHelper.registerBlock(FLUXGRINDER = new BlockFluxGrinder(), BlockFluxGrinder.NAME);
		modblocklist.add(FLUXGRINDER);		
		RegistryHelper.registerBlock(FLUXFURNACE = new BlockFluxFurnace(), BlockFluxFurnace.NAME);
		modblocklist.add(FLUXFURNACE);
    }

	public void setBlockModels()
    {
    	for (ModBlock block:modblocklist)
    	{
    		RegistryHelper.registerBlockModel(block, 0);
    	}

    	for (BlockOre block:oreblocklist)
    	{
    		RegistryHelper.registerBlockModel(block, 0);
    	}
    }
}