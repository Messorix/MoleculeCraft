package com.anime.rf;

import com.anime.rf.blocks.PipeBase;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.energy.CapabilityEnergy;

public class EnergyHelper {
	
	public static boolean isReceiver(IBlockAccess world, BlockPos pos) {
		if (world.getBlockState(pos).getBlock() instanceof PipeBase) return false;
		TileEntity te = world.getTileEntity(pos);
		return te instanceof IEnergyReceiver;
	}
	
	public static boolean isProvider(IBlockAccess world, BlockPos pos) {
		if (world.getBlockState(pos).getBlock() instanceof PipeBase) return false;
		TileEntity te = world.getTileEntity(pos);
		return te instanceof IEnergyProvider;
	}
	
	public static boolean isProviderCapa(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		if (world.getBlockState(pos).getBlock() instanceof PipeBase) return false;
		TileEntity te = world.getTileEntity(pos);
		return te != null && te.hasCapability(CapabilityEnergy.ENERGY, facing) && te.getCapability(CapabilityEnergy.ENERGY, facing).canExtract();
	}

	public static boolean isReceiverCapa(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		if (world.getBlockState(pos).getBlock() instanceof PipeBase) return false;
		TileEntity te = world.getTileEntity(pos);
		return te != null && te.hasCapability(CapabilityEnergy.ENERGY, facing) && te.getCapability(CapabilityEnergy.ENERGY, facing).canReceive();
	}
	
}
