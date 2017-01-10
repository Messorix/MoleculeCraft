package com.anime.rf.events;

import com.anime.rf.EnergyHelper;
import com.anime.rf.network.EnergyNetwork;
import com.messorix.moleculecraft.base.events.EnergyNetworkProvider;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PipeNetworkEventHandler {
	
	@SubscribeEvent
	public void networkTick(TickEvent.WorldTickEvent event) {
		World world = event.world;
		if (!world.isRemote) {
			world.getCapability(EnergyNetworkProvider.ENERGY_NETWORK_CAPABILITY, null).validate(world);
			System.out.println("Network total: " + world.getCapability(EnergyNetworkProvider.ENERGY_NETWORK_CAPABILITY, null).getNetworks().size());
			for (EnergyNetwork net : world.getCapability(EnergyNetworkProvider.ENERGY_NETWORK_CAPABILITY, null).getNetworks()) {
				if (!net.providerConnections.isEmpty()) {
					for (int i = 0; i < net.providerConnections.size(); i++) {
						BlockPos pos = net.providerConnections.get(i);
						boolean flag = false;
						EnumFacing face = null;
						for (EnumFacing facing : EnumFacing.VALUES) {
							if (EnergyHelper.isProviderCapa(world, pos, facing)) {
								flag = true;
								face = facing;
								break;
							}
						}
						if (EnergyHelper.isProvider(world, pos) || flag) {
							if (flag) {
								IEnergyStorage storage = ((IEnergyStorage)world.getTileEntity(pos).getCapability(CapabilityEnergy.ENERGY, face));
								storage.extractEnergy(net.storage.receiveEnergy(storage.extractEnergy(storage.getEnergyStored(), true), false), false);
							} else {
								IEnergyProvider storage = ((IEnergyProvider)world.getTileEntity(pos));
								for (EnumFacing facing : EnumFacing.VALUES) {
									if (storage.extractEnergy(facing, storage.getEnergyStored(facing), true) > 0) {
										storage.extractEnergy(facing, net.storage.receiveEnergy(storage.extractEnergy(facing, storage.getEnergyStored(facing), true), false), false);
										break;
									}
								}
							}
						}
					}
				}
				if (!net.receiverConnections.isEmpty()) {
					int maxSend = (net.storage.getEnergyStored() / net.receiverConnections.size());
					for (int i = 0; i < net.receiverConnections.size(); i++) {
						BlockPos pos = net.receiverConnections.get(i);
						boolean flag = false;
						EnumFacing face = null;
						for (EnumFacing facing : EnumFacing.VALUES) {
							if (EnergyHelper.isReceiverCapa(world, pos, facing)) {
								flag = true; 
								face = facing;
								break;
							}
						}
						if (EnergyHelper.isReceiver(world, pos) || flag) {
							if (flag) {
								IEnergyStorage storage = ((IEnergyStorage)world.getTileEntity(pos).getCapability(CapabilityEnergy.ENERGY, face));
								net.storage.extractEnergy(storage.receiveEnergy(net.storage.extractEnergy(maxSend, true), false), false);
							} else {
								IEnergyReceiver storage = ((IEnergyReceiver)world.getTileEntity(pos));
								for (EnumFacing facing : EnumFacing.VALUES) {
									net.storage.extractEnergy(storage.receiveEnergy(facing, net.storage.extractEnergy(maxSend, true), false), false);
									break;
								}
							}
						}
					}
				}
			}
		}
	}
	
}
