package com.anime.rf.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.anime.rf.blocks.PipeBase.EnumPipeType;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public abstract class TileEntityPipeBase extends TileEntity implements ITickable {
	
	protected BlockPos currentPosition, lastPosition;
	protected List<EnumFacing> from = new ArrayList<EnumFacing>();
	protected int pipeSendCounter = 0;
	
	public TileEntityPipeBase() {}
	
	@Override
	public void onLoad() {
		this.currentPosition = this.getPos();
		this.lastPosition = this.getPos();
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setIntArray("currentPos", blockPosToIntArray(currentPosition));
		compound.setIntArray("lastPos", blockPosToIntArray(lastPosition));
		List<Integer> list = new ArrayList<Integer>();
		for (EnumFacing facing : from) {
			list.add(facing.getIndex());
		}
		compound.setIntArray("from", listToArray(list));
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		currentPosition = intArrayToBlockPos(compound.getIntArray("currentPos"));
		lastPosition = intArrayToBlockPos(compound.getIntArray("lastPos"));
		for (int i : compound.getIntArray("from")) {
			from.add(EnumFacing.VALUES[i]);
		}
	}
	
	public static int[] listToArray(List<Integer> list) {
		int[] array = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
		}
		return array;
	}
	
	public static int[] blockPosToIntArray(BlockPos pos) {
		return new int[]{pos.getX(), pos.getY(), pos.getZ()};
	}
	
	public static BlockPos intArrayToBlockPos(int[] pos) {
		return new BlockPos(pos[0], pos[1], pos[2]);
	}
 	
	public abstract EnumPipeType getType();
	
	public abstract List<EnumFacing> getPipeConnections();
	
}
