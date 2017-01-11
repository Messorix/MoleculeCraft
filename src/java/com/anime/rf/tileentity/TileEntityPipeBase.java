package com.anime.rf.tileentity;

import com.anime.rf.blocks.PipeBase.EnumPipeType;

import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityPipeBase extends TileEntity {
	
	public TileEntityPipeBase() {}
	
	public abstract EnumPipeType getType();
	
}
