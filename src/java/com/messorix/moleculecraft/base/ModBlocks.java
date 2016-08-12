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
    public static List<ModBlock> modblocklist = new ArrayList<ModBlock>();
    public static List<BlockOre> oreblocklist = new ArrayList<BlockOre>();
	
	public static BlockMachine FLUX_FURNACE;
	public static BlockMachine FLUX_FURNACE_ON;
	public static BlockMachine FLUX_GRINDER;
	public static BlockMachine FLUX_GRINDER_ON;

	public static BlockOre CHALCOCITE_ORE;
	public static BlockOre ACANTHITE_ORE;
	public static BlockOre CASSITERITE_ORE;

	public void registerBlocks()
	{
		RegistryHelper.registerBlock(CHALCOCITE_ORE = new BlockChalcociteOre(), BlockChalcociteOre.NAME);
		oreblocklist.add(CHALCOCITE_ORE);
		RegistryHelper.registerBlock(ACANTHITE_ORE = new BlockAcanthiteOre(), BlockAcanthiteOre.NAME);
		oreblocklist.add(ACANTHITE_ORE);
		RegistryHelper.registerBlock(CASSITERITE_ORE = new BlockCassiteriteOre(), BlockCassiteriteOre.NAME);
		oreblocklist.add(CASSITERITE_ORE);

		RegistryHelper.registerBlock(FLUX_GRINDER = new BlockFluxGrinder("flux_grinder", "flux_grinder", false), BlockFluxGrinder.NAME);
		modblocklist.add(FLUX_GRINDER);
		RegistryHelper.registerBlock(FLUX_GRINDER_ON = new BlockFluxGrinder("flux_grinder_on", "flux_grinder_on", true), BlockFluxGrinder.NAME);
		modblocklist.add(FLUX_GRINDER_ON);
		RegistryHelper.registerBlock(FLUX_FURNACE = new BlockFluxFurnace(), BlockFluxFurnace.NAME);
		modblocklist.add(FLUX_FURNACE);
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