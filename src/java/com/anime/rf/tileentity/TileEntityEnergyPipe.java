package com.anime.rf.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.anime.rf.blocks.PipeBase;
import com.anime.rf.blocks.PipeBase.EnumPipeType;

import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class TileEntityEnergyPipe extends TileEntityPipeBase implements IEnergyConnection {
	
	@Override
	public void update() {
		if (!worldObj.isRemote) {
			// Handles adding energy to adjacent IEnergyRecievers from the surrounding IEnergyProviders.
			for (EnumFacing recieve : EnumFacing.VALUES) {
					if (isReciever(getPos().offset(recieve))) {
						BlockPos recieverPos = getPos().offset(recieve);
						IEnergyReceiver reciever = (IEnergyReceiver) worldObj.getTileEntity(recieverPos);
						for (EnumFacing providing : EnumFacing.VALUES) {
							if (isProvider(pos.offset(providing))) {
								BlockPos providerPos = getPos().offset(providing);
								IEnergyProvider provider = (IEnergyProvider) worldObj.getTileEntity(providerPos);
								provider.extractEnergy(providing.getOpposite(), reciever.receiveEnergy(recieve.getOpposite(), provider.extractEnergy(providing.getOpposite(), provider.getEnergyStored(providing.getOpposite()), true), false), false);
							}
					}
				}
			}
			// Handles pipe transfer.
			pipeSendCounter++;
			if (pipeSendCounter >= 1) {
				if (getPipeConnections().isEmpty()) {
					lastPosition = new BlockPos(currentPosition);
					currentPosition = new BlockPos(getPos());
				}
				for (EnumFacing providing : EnumFacing.VALUES) {
					if (isProvider(getPos().offset(providing))) {
						IEnergyProvider provider = (IEnergyProvider) worldObj.getTileEntity(getPos().offset(providing));
						List<EnumFacing> connections = getPipeConnections();
						if (connections.isEmpty()) {
							currentPosition = lastPosition;
							if (currentPosition == null) {
								currentPosition = new BlockPos(getPos());
								lastPosition = new BlockPos(getPos());
							}
							connections = getPipeConnections();
						}
						if (connections.size() > 0) {
							int connectionPos = new Random().nextInt(connections.size());
							from.clear();
							from.add(connections.get(connectionPos).getOpposite());
							lastPosition = new BlockPos(currentPosition);
							currentPosition = currentPosition.offset(connections.get(connectionPos));
							
							if (isReciever(currentPosition)) {
								IEnergyReceiver reciever = (IEnergyReceiver) worldObj.getTileEntity(currentPosition);
								provider.extractEnergy(providing.getOpposite(), reciever.receiveEnergy(connections.get(connectionPos).getOpposite(), provider.extractEnergy(providing.getOpposite(), provider.getEnergyStored(providing.getOpposite()), true), false), false);
								lastPosition = new BlockPos(currentPosition);
								currentPosition = new BlockPos(getPos());
								from.clear();
								break;
							}
							if (getPipeConnections().isEmpty()) {
								currentPosition = new BlockPos(lastPosition);
							}
						}
					}
				}
				pipeSendCounter = 0;
			}
		}
	}
	
	@Override
	public List<EnumFacing> getPipeConnections() {
		List<EnumFacing> connections = new ArrayList<EnumFacing>();
		for (EnumFacing facing : EnumFacing.VALUES) {
			if (from.contains(facing)) continue;
			Block block = worldObj.getBlockState(currentPosition.offset(facing)).getBlock();
			if (block instanceof PipeBase) {
				if (((PipeBase)block).getType() == EnumPipeType.ENERGY || ((PipeBase)block).getType() == EnumPipeType.ALL) {
					connections.add(facing);
				}
			}
			if (worldObj.getTileEntity(currentPosition.offset(facing)) instanceof IEnergyReceiver) {
				if (((IEnergyReceiver)worldObj.getTileEntity(currentPosition.offset(facing))).getEnergyStored(facing.getOpposite()) < ((IEnergyReceiver)worldObj.getTileEntity(currentPosition.offset(facing))).getMaxEnergyStored(facing.getOpposite()))
					connections.add(facing);
			}
		}
		return connections;
	}
	
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
	
	public boolean isReciever(BlockPos pos) {
		TileEntity te = worldObj.getTileEntity(pos);
		return te instanceof IEnergyReceiver;
	}
	
	public boolean isProvider(BlockPos pos) {
		TileEntity te = worldObj.getTileEntity(pos);
		return te instanceof IEnergyProvider;
	}
	
}
