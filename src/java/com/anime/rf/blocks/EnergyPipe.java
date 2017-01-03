package com.anime.rf.blocks;

import com.anime.rf.tileentity.TileEntityEnergyPipe;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class EnergyPipe extends PipeBase {

	public EnergyPipe(Material materialIn) {
		super(materialIn, EnumPipeType.ENERGY);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityEnergyPipe();
	}
	
}
