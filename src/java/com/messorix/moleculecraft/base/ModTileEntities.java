package com.messorix.moleculecraft.base;

import com.messorix.moleculecraft.base.tileentities.*;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities 
{
	public void registerTileEntities()
    {
		GameRegistry.registerTileEntity(TileEntityFluxGrinder.class, "tileEntityFluxGrinder");
		GameRegistry.registerTileEntity(TileEntityFluxFurnace.class, "tileEntityFluxFurnace");
    }
}