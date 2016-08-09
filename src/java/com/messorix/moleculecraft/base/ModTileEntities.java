package com.messorix.moleculecraft.base;

import com.anime.basic.registry.RegistryHelper;
import com.messorix.moleculecraft.base.tileentities.TileEntityFluxFurnace;
import com.messorix.moleculecraft.base.tileentities.TileEntityFluxGrinder;

public class ModTileEntities 
{
	public static void registerTileEntities()
    {
		RegistryHelper.registerTileEntity(TileEntityFluxGrinder.class);
		RegistryHelper.registerTileEntity(TileEntityFluxFurnace.class);
    }
}