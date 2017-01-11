package com.anime.rf.tileentity;

import java.util.List;

import com.anime.basic.network.PacketSendingHelper;
import com.anime.rf.blocks.PipeBase;
import com.anime.rf.blocks.PipeBase.EnumPipeType;
import com.anime.rf.network.EnergyNetwork;
import com.anime.rf.network.EnergyNetworkProvider;
import com.anime.rf.network.SyncEnergyStorageMessage;

import cofh.api.energy.IEnergyReceiver;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class TileEntityEnergyPipe extends TileEntityPipeBase implements IEnergyReceiver {
	
	@Override
	public EnumPipeType getType() {
		return EnumPipeType.ENERGY;
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		BlockPos connectionPos = getPos().offset(from);
		Block block = worldObj.getBlockState(getPos()).getBlock();
		if (block instanceof PipeBase) {
			return ((PipeBase) block).canConnectTo(worldObj, connectionPos, pos);
		}
		return true;
	}
	
	@Override
	public int getEnergyStored(EnumFacing from) {
		List<EnergyNetwork> nets = worldObj.getCapability(EnergyNetworkProvider.ENERGY_NETWORK_CAPABILITY, null).networksContainingPos(getPos());
		if (nets.size() == 1) return nets.get(0).storage.getEnergyStored();
		return 0;
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		List<EnergyNetwork> nets = worldObj.getCapability(EnergyNetworkProvider.ENERGY_NETWORK_CAPABILITY, null).networksContainingPos(getPos());
		if (nets.size() == 1) return nets.get(0).storage.getMaxEnergyStored();
		return 0;
	}

	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		List<EnergyNetwork> nets = worldObj.getCapability(EnergyNetworkProvider.ENERGY_NETWORK_CAPABILITY, null).networksContainingPos(getPos());
		if (!nets.isEmpty()) {
			int change = nets.get(0).storage.receiveEnergy(maxReceive, simulate);
			PacketSendingHelper.sendToDimension(new SyncEnergyStorageMessage(worldObj.getCapability(EnergyNetworkProvider.ENERGY_NETWORK_CAPABILITY, null).getNetworkID(nets.get(0)), nets.get(0).storage.writeToNBT(new NBTTagCompound())), worldObj.provider.getDimension());
			return change;
		}
		return 0;
	}
	
}
