package com.anime.rf.network;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class EnergyNetworkStorage implements IStorage<INetwork> {

	@Override
	public NBTBase writeNBT(Capability<INetwork> capability, INetwork instance, EnumFacing side) {
		System.out.println("Writing Capability");
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
				tag2.setIntArray(Integer.toString(j), blockPosToIntArray(net.connections.get(j)));
			}
			tag.setTag(Integer.toString(i), tag2);
		}
		return tag;
	}

	@Override
	public void readNBT(Capability<INetwork> capability, INetwork instance, EnumFacing side, NBTBase nbt) {
		System.out.println("Reading Capability");
		if (nbt instanceof NBTTagCompound) {
			System.out.println("NBTTagCompound");
			NBTTagCompound tag = (NBTTagCompound) nbt;
			System.out.println(tag);
			System.out.println(tag.getInteger("Size"));
			for (int i = 0; i < tag.getInteger("Size"); i++) {
				NBTTagCompound tag2 = tag.getCompoundTag(Integer.toString(i));
				EnergyNetwork net = EnergyNetwork.createNetwork();
				net.storage.readFromNBT(tag2.getCompoundTag("Energy"));
				System.out.println(tag2.getInteger("Size"));
				for (int j = 0; j < tag2.getInteger("Size"); j++) {
					net.addConnection(instance.getWorld(), intArrayToBlockPos(tag2.getIntArray(Integer.toString(j))));
				}
				instance.addNetwork(net);
			}
		}
	}
	
	private int[] blockPosToIntArray(BlockPos pos) {
		return new int[]{pos.getX(), pos.getY(), pos.getZ()};
	}
	
	private BlockPos intArrayToBlockPos(int[] pos) {
		return new BlockPos(pos[0], pos[1], pos[2]);
	}
	
}
