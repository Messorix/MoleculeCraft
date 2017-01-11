package com.anime.rf.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import com.anime.basic.BlockPosHelper;
import com.anime.basic.network.PacketSendingHelper;
import com.anime.rf.EnergyHelper;
import com.anime.rf.blocks.PipeBase;

import cofh.api.energy.EnergyStorage;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnergyNetwork {
	
	public List<BlockPos> connections = new ArrayList<BlockPos>();
	public List<BlockPos> receiverConnections = new ArrayList<BlockPos>();
	public List<BlockPos> providerConnections = new ArrayList<BlockPos>();
	
	public EnergyStorage storage = new EnergyStorage((connections.size() * 1000)) {
		
		public NBTTagCompound writeToNBT(NBTTagCompound tag) {
			tag.setInteger("Energy", energy);
			tag.setInteger("Capacity", capacity);
			return tag;
		}
		
		
		public EnergyStorage readFromNBT(NBTTagCompound tag) {
			energy = tag.getInteger("Energy");
			capacity = tag.getInteger("Capacity");
			return this;
		}
		
	};
	
	public void addConnection(World world, BlockPos pos) {
		boolean providerFlag = false;
		for (EnumFacing facing : EnumFacing.VALUES) {
			if (EnergyHelper.isProviderCapa(world, pos, facing)) {
				providerFlag = true;
				break;
			}
		}
		boolean receiverFlag = false;
		for (EnumFacing facing : EnumFacing.VALUES) {
			if (EnergyHelper.isReceiverCapa(world, pos, facing)) {
				receiverFlag = true;
				break;
			}
		}
		if (EnergyHelper.isReceiver(world, pos) || receiverFlag) {
			receiverConnections.add(pos);
			receiverConnections = world.getCapability(EnergyNetworkProvider.ENERGY_NETWORK_CAPABILITY, null).removeRepeats(receiverConnections);
		} else if (EnergyHelper.isProvider(world, pos) || providerFlag) {
			providerConnections.add(pos);
			providerConnections = world.getCapability(EnergyNetworkProvider.ENERGY_NETWORK_CAPABILITY, null).removeRepeats(providerConnections);			
		}
		connections.add(pos);
		connections = world.getCapability(EnergyNetworkProvider.ENERGY_NETWORK_CAPABILITY, null).removeRepeats(connections);
		if (storage != null) {
			int val = connections.size() * 1000;
			storage.setCapacity(val);
			storage.setMaxTransfer(val);
		}
	}
	
	public void removeConnection(World world, BlockPos pos) {
		boolean providerFlag = false;
		for (EnumFacing facing : EnumFacing.VALUES) {
			if (EnergyHelper.isProviderCapa(world, pos, facing)) {
				providerFlag = true;
				break;
			}
		}
		boolean receiverFlag = false;
		for (EnumFacing facing : EnumFacing.VALUES) {
			if (EnergyHelper.isReceiverCapa(world, pos, facing)) {
				receiverFlag = true;
				break;
			}
		}
		if (EnergyHelper.isReceiver(world, pos) || receiverFlag) {
			receiverConnections.remove(pos);
			receiverConnections = world.getCapability(EnergyNetworkProvider.ENERGY_NETWORK_CAPABILITY, null).removeRepeats(receiverConnections);
		} else if (EnergyHelper.isProvider(world, pos) || providerFlag) {
			providerConnections.remove(pos);
			providerConnections = world.getCapability(EnergyNetworkProvider.ENERGY_NETWORK_CAPABILITY, null).removeRepeats(providerConnections);
		}
		connections.remove(pos);
		connections = world.getCapability(EnergyNetworkProvider.ENERGY_NETWORK_CAPABILITY, null).removeRepeats(connections);
		if (storage != null) {
			int val = connections.size() * 1000;
			storage.setCapacity(val);
			storage.setMaxTransfer(val);
		}
	}
	
	public NBTTagCompound writeConnectionsToNBT(NBTTagCompound tag) {
		tag.setInteger("Size", connections.size());
		tag.setTag("Energy", storage.writeToNBT(new NBTTagCompound()));
		for (int i = 0; i < connections.size(); i++) {
			tag.setIntArray(Integer.toString(i), BlockPosHelper.blockPosToIntArray(connections.get(i)));
		}
		return tag;
	}
	
	public void readNetworkFromNBT(NBTTagCompound storage, NBTTagCompound connections) {
		this.storage.readFromNBT(storage);
		for (int i = 0; i < connections.getInteger("Size"); i++) {
			this.connections.add(BlockPosHelper.intArrayToBlockPos(connections.getIntArray(Integer.toString(i))));
		}
	}
	
	public List<BlockPos> getConnections() {
		return connections;
	}
	
	public boolean contains(BlockPos pos) {
		for (BlockPos pos1 : connections) {
			if (pos.equals(pos1)) return true;
		}
		return false;
	}
	
	@Override
	public boolean equals(Object net) {
		if (net instanceof EnergyNetwork) {
			if (((EnergyNetwork)net).connections.size() != this.connections.size()) return false;
			for (BlockPos pos : ((EnergyNetwork)net).connections) {
				if (!contains(pos)) return false;
			}
			return true;
		}
		return false;
	}
	
	public static EnergyNetwork createNetwork() {
		return new EnergyNetwork();
	}
	
	public static class NetworkRegistry implements INetwork {
		
		private World world;
		
		private CopyOnWriteArrayList<EnergyNetwork> networks = new CopyOnWriteArrayList<EnergyNetwork>();
		
		private boolean changed = false;
		
		public void addNetwork(EnergyNetwork network) {
			networks.add(network);
			changed = true;
		}
		
		public void removeNetwork(EnergyNetwork network) {
			networks.remove(network);
			changed = true;
		}
		
		/** Validates all networks IE does all the advanced handling that can't be handled in the pipes placement. **/
		public void validate(World world) {
			removeRepeatPositions();
			int position = 0;
			while(position < networks.size()) {
				EnergyNetwork net = networks.get(position);
				Iterator<BlockPos> it2 = net.connections.iterator();
				while (it2.hasNext()) {
					BlockPos pos = it2.next();
					Block block = world.getBlockState(pos).getBlock();
					TileEntity te = world.getTileEntity(pos);
					if ((!(block instanceof PipeBase)) && te == null) {
						it2.remove();
						changed = true;
					}
				}
				if(net.connections.isEmpty()) {
					networks.remove(position);
					changed = true;
					continue;
				} else {
					position++;
				}
			}
			if (changed) {
				syncAllNetworks();
				changed = false;
			}
		}
		
		/** Combines networks in the passed list used when pipes are placed and the surrounding networks are not the same. **/
		public EnergyNetwork combineNetworks(World world, List<EnergyNetwork> nets) {
			EnergyNetwork largestNet = null;
			for (EnergyNetwork net : nets) {
				if (largestNet == null) {
					largestNet = net;
					continue;
				}
				if (largestNet.connections.size() < net.connections.size()) largestNet = net;
			}
			nets.remove(largestNet);
			for (EnergyNetwork net : nets) {
				if (!net.equals(largestNet)) {
					for (BlockPos pos : net.connections) {
						largestNet.addConnection(world, pos);
					}
					largestNet.storage.receiveEnergy(net.storage.getEnergyStored(), false);
				}
			}
//			addNetwork(largestNet);
			networks.removeAll(nets);
			changed = true;
			return largestNet;
		}
		
		/** Removes repeat BlockPoses in all of the networks. **/
		private void removeRepeatPositions() {
			for (EnergyNetwork net : networks) {
				List<BlockPos> positions = new ArrayList<BlockPos>();
				for (BlockPos pos : net.connections) {
					if (!positions.contains(pos)) positions.add(pos);
				}
				if (!matches(positions, net.connections)) net.connections = positions;
				List<BlockPos> rPositions = new ArrayList<BlockPos>();
				for (BlockPos pos : net.receiverConnections) {
					if (!rPositions.contains(pos)) rPositions.add(pos);
				}
				if (!matches(rPositions, net.receiverConnections)) net.receiverConnections = rPositions;
				List<BlockPos> pPositions = new ArrayList<BlockPos>();
				for (BlockPos pos : net.providerConnections) {
					if (!pPositions.contains(pos)) pPositions.add(pos);
				}
				if (!matches(pPositions, net.providerConnections)) net.providerConnections = pPositions;
			}
		}
		
		// TODO: Make networks split energy.
		/** Separates a network into all possible networks based on BlockPoses. **/
		public void separateNetworks(World world, EnergyNetwork net, List<BlockPos> lastPositions) {
			if (net.connections.size() <= 1) return;
			Map<BlockPos, List<BlockPos>> positions = new HashMap<BlockPos, List<BlockPos>>();
			for (BlockPos pos1 : net.connections) {
				positions.put(pos1, new ArrayList<BlockPos>());
				for (BlockPos pos2 : net.connections) {
					if (!pos1.equals(pos2)) {
						if (nextTo(pos1, pos2)) {
							positions.get(pos1).add(pos2);
						}
					}
				}
			}
			EnergyNetwork net2 = EnergyNetwork.createNetwork();
			Map<BlockPos, Boolean> map2 = new HashMap<BlockPos, Boolean>();
			for (BlockPos pos : positions.keySet()) {
				map2.put(pos, false);
			}
			List<BlockPos> pos = getRecursiveList(positions, positions.get(net.connections.get(0)), map2);
			pos = removeRepeats(pos);
			List<BlockPos> networkConnections = new ArrayList<BlockPos>();
			for (BlockPos pos2 : positions.keySet()) {
				if (!contains(pos, pos2)) networkConnections.add(pos2);
			}
			if (!networkConnections.isEmpty()) {
				int energyPerConnection = net.storage.getEnergyStored() / (net.connections.size() - 1);
				int remainder = net.storage.getEnergyStored() % (net.connections.size() - 1);
				if (!matches(lastPositions, networkConnections)) {
					net2.connections = networkConnections;
					addNetwork(net2);
					net.connections.removeAll(networkConnections);
					net.storage.setEnergyStored((net.connections.size() * energyPerConnection) + remainder - energyPerConnection);
					int amount = networkConnections.size() * 1000;
					net2.storage.setCapacity(amount);
					net2.storage.setMaxTransfer(amount);
					net2.storage.setEnergyStored(energyPerConnection * networkConnections.size());
					if (networkConnections.size() > 1) separateNetworks(world, net2, networkConnections);
				}
			}
			changed = true;
		}
		
		private boolean matches(List<BlockPos> list1, List<BlockPos> list2) {
			boolean fail = false;
			if (list1.isEmpty() && !list2.isEmpty()) return false;
			if (list2.isEmpty() && !list1.isEmpty()) return false;
			if (list1.isEmpty() && list2.isEmpty()) return true;
			List<Boolean> content = new ArrayList<Boolean>();
			for (BlockPos pos1 : list1) {
				content.add(fail);
				for (BlockPos pos2 : list2) {
					if (pos1.equals(pos2)) content.set(content.size() - 1, true);
				}
			}
			return !content.contains(fail);
		}
		
		/** Returns a list of all BlockPoses connected to each other with repeats. **/
		private List<BlockPos> getRecursiveList(Map<BlockPos, List<BlockPos>> map, List<BlockPos> list, Map<BlockPos, Boolean> map2) {
			List<BlockPos> finalList = new ArrayList<BlockPos>();
			for (BlockPos pos : list) {
				if (map2.get(pos).booleanValue() == false) {
					finalList.addAll(map.get(pos));
					map2.put(pos, true);
					finalList.addAll(getRecursiveList(map, finalList, map2));
				}
			}
			return finalList;
		}
		
		/** Removes any repeats in the list. **/
		public List<BlockPos> removeRepeats(List<BlockPos> positions) {
			List<BlockPos> pos2 = new ArrayList<BlockPos>();
			for (BlockPos pos : positions) {
				if (!contains(pos2, pos)) pos2.add(pos);
			}
			return pos2;
		}
		
		/** Truly returns if the list contains the BlockPos. **/
		public static boolean contains(List<BlockPos> list, BlockPos pos) {
			for (BlockPos pos2 : list) {
				if (pos2.equals(pos)) return true;
			}
			return false;
		}
		
		/** Determines if the BlockPoses are next to each other. **/
		private boolean nextTo(BlockPos pos1, BlockPos pos2) {
			for (EnumFacing facing : EnumFacing.VALUES) {
				BlockPos offset = pos1.offset(facing);
				if (offset.equals(pos2)) {
					return true;
				}
			}
			return false;
		}
		
		/** Scans the network for any networks containing the BlockPos. **/
		public List<EnergyNetwork> networksContainingPos(BlockPos pos) {
			List<EnergyNetwork> contains = new ArrayList<EnergyNetwork>();
			for (EnergyNetwork network : networks) {
				if (network.contains(pos)) {
					contains.add(network);
				}
			}
			return contains;
		}
		
		@Override
		public int getNetworkID(EnergyNetwork net) {
			for (int i = 0; i < networks.size(); i++) {
				if (net.equals(networks.get(i))) return i;
			}
			return networks.size();
		}
		
		@Override
		public List<EnergyNetwork> getNetworks() {
			return networks;
		}

		@Override
		public void setNetworks(CopyOnWriteArrayList<EnergyNetwork> list) {
			this.networks = list;
		}
		
		@Override
		public void setWorld(World world) {
			this.world = world;
		}

		@Override
		public World getWorld() {
			return world;
		}
		
		public void syncAllNetworks() {
			PacketSendingHelper.sendToDimension(new SyncAllNetworksMessage(getNetworks()), world.provider.getDimension());
		}
	}
}
