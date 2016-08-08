package com.messorix.moleculecraft.base.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockFluxFurnace extends BlockMachine
{
	public BlockFluxFurnace()
	{
		super("flux_furnace", "flux_furnace", Material.ROCK, false);
	}

	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{
		return null;
	}
}