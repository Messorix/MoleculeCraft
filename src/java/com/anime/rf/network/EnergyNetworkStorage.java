package com.anime.rf.network;

import com.anime.basic.BlockPosHelper;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class EnergyNetworkStorage implements IStorage<INetwork> {

	@Override
	public NBTBase writeNBT(Capability<INetwork> capability, INetwork instance, EnumFacing side) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("Size", instance.getNetworks().size());
		for (int i = 0; i < instance.getNetworks().size(); i++) {
			EnergyNetwork net = instance.getNetworks().get(i);
			NBTTagCompound tag2 = new NBTTagCompound();
			NBTTagCompound tag3 = new NBTTagCompound();
			net.storage.writeToNBT(tag3);
			tag2.setTag("Energy", tag3);
			tag2.setInteger("Size", net.connections.size());
			for (int j = 0; j < net.connections.size(); j++) {
				tag2.setIntArray(Integer.toString(j), BlockPosHelper.blockPosToIntArray(net.connections.get(j)));
			}
			tag.setTag(Integer.toString(i), tag2);
		}
		return tag;
	}

	@Override
	public void readNBT(Capability<INetwork> capability, INetwork instance, EnumFacing side, NBTBase nbt) {
		if (nbt instanceof NBTTagCompound) {
			NBTTagCompound tag = (NBTTagCompound) nbt;
			for (int i = 0; i < tag.getInteger("Size"); i++) {
				NBTTagCompound tag2 = tag.getCompoundTag(Integer.toString(i));
				EnergyNetwork net = EnergyNetwork.createNetwork();
				net.storage.readFromNBT(tag2.getCompoundTag("Energy"));
				for (int j = 0; j < tag2.getInteger("Size"); j++) {
					net.addConnection(instance.getWorld(), BlockPosHelper.intArrayToBlockPos(tag2.getIntArray(Integer.toString(j))));
				}
				instance.addNetwork(net);
			}
		}
	}
	
}
