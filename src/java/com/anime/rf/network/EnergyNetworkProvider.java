package com.anime.rf.network;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class EnergyNetworkProvider implements ICapabilitySerializable<NBTTagCompound> {
	
	@CapabilityInject(INetwork.class)
	public static final Capability<INetwork> ENERGY_NETWORK_CAPABILITY = null;
	
	private INetwork instance = ENERGY_NETWORK_CAPABILITY.getDefaultInstance();
	
	public EnergyNetworkProvider() {}
	
	public EnergyNetworkProvider(World world) {
		instance.setWorld(world);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == ENERGY_NETWORK_CAPABILITY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return hasCapability(capability, facing) ? ENERGY_NETWORK_CAPABILITY.<T>cast(instance) : null;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		return (NBTTagCompound) ENERGY_NETWORK_CAPABILITY.getStorage().writeNBT(ENERGY_NETWORK_CAPABILITY, instance, null);
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		ENERGY_NETWORK_CAPABILITY.getStorage().readNBT(ENERGY_NETWORK_CAPABILITY, instance, null, nbt);
	}

}
