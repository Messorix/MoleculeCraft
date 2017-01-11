package com.anime.rf.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import com.anime.rf.EnergyHelper;
import com.anime.rf.blocks.PipeBase;
import com.messorix.moleculecraft.base.events.EnergyNetworkProvider;

import cofh.api.energy.EnergyStorage;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class EnergyNetwork {
	
	public List<BlockPos> connections = new ArrayList<BlockPos>();
	public List<BlockPos> receiverConnections = new ArrayList<BlockPos>();
	public List<BlockPos> providerConnections = new ArrayList<BlockPos>();
	
	public EnergyStorage storage = new EnergyStorage((connections.size() * 1000));
	
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
		if (EnergyHelper.isProvider(world, pos) || providerFlag) {
			providerConnections.add(pos);
			providerConnections = world.getCapability(EnergyNetworkProvider.ENERGY_NETWORK_CAPABILITY, null).removeRepeats(providerConnections);
		} else if (EnergyHelper.isReceiver(world, pos) || receiverFlag) {
			receiverConnections.add(pos);
			receiverConnections = world.getCapability(EnergyNetworkProvider.ENERGY_NETWORK_CAPABILITY, null).removeRepeats(receiverConnections);
		} else {
			connections.add(pos);
			connections = world.getCapability(EnergyNetworkProvider.ENERGY_NETWORK_CAPABILITY, null).removeRepeats(connections);
		}
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
		if (EnergyHelper.isProvider(world, pos) || providerFlag) {
			providerConnections.remove(pos);
			providerConnections = world.getCapability(EnergyNetworkProvider.ENERGY_NETWORK_CAPABILITY, null).removeRepeats(providerConnections);
		} else if (EnergyHelper.isReceiver(world, pos) || receiverFlag) {
			receiverConnections.remove(pos);
			receiverConnections = world.getCapability(EnergyNetworkProvider.ENERGY_NETWORK_CAPABILITY, null).removeRepeats(receiverConnections);
		} else {
			connections.remove(pos);
			connections = world.getCapability(EnergyNetworkProvider.ENERGY_NETWORK_CAPABILITY, null).removeRepeats(connections);
		}
		if (storage != null) {
			int val = connections.size() * 1000;
			storage.setCapacity(val);
			storage.setMaxTransfer(val);
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
		
		public void addNetwork(EnergyNetwork network) {
			networks.add(network);
		}
		
		public void removeNetwork(EnergyNetwork network) {
			networks.remove(network);
		}
		
		/** Validates all networks IE does all the advanced handling that can't be handled in the pipes placement. **/
		public void validate(World world) {
			int position = 0;
			while(position < networks.size()) {
				EnergyNetwork net = networks.get(position);
				if(net.connections.isEmpty()) {
					networks.remove(position);
					continue;
				} else {
					position++;
				}
				Iterator<BlockPos> it2 = net.connections.iterator();
				while (it2.hasNext()) {
					BlockPos pos = it2.next();
					Block block = world.getBlockState(pos).getBlock();
					TileEntity te = world.getTileEntity(pos);
					if ((!(block instanceof PipeBase)) && te == null) it2.remove();
				}
			}
			System.out.println("Validate first loop broke");
			removeRepeatPositions();
			System.out.println("Position: " + networks.size());
		}
		
		/** Combines networks in the passed list used when pipes are placed and the surrounding networks are not the same. **/
		public EnergyNetwork combineNetworks(World world, List<EnergyNetwork> nets) {
			System.out.println(networks.removeAll(nets));
			EnergyNetwork largestNet = null;
			for (EnergyNetwork net : nets) {
				if (largestNet == null) {
					largestNet = net;
					continue;
				}
				if (largestNet.connections.size() < net.connections.size()) largestNet = net;
			}
			for (EnergyNetwork net : nets) {
				if (!net.equals(largestNet)) {
					for (BlockPos pos : net.connections) {
						largestNet.addConnection(world, pos);
					}
					largestNet.storage.receiveEnergy(net.storage.getEnergyStored(), false);
				}
			}
			System.out.println("Current networks: " + networks + " Largest network: " + largestNet);
			networks.add(largestNet);
			System.out.println("New networks: " + networks + " Connections: " + largestNet.connections);
			return largestNet;
		}
		
		/** Removes repeat BlockPoses in all of the networks. **/
		private void removeRepeatPositions() {
			for (EnergyNetwork net : networks) {
				List<BlockPos> positions = new ArrayList<BlockPos>();
				for (BlockPos pos : net.connections) {
					if (!positions.contains(pos)) positions.add(pos);
				}
				net.connections = positions;
			}
		}
		
		// TODO: Make networks split energy.
		/** Separates a network into all possible networks based on BlockPoses. **/
		public void separateNetworks(IBlockAccess world, EnergyNetwork net, List<BlockPos> lastPositions) {
			if (net.connections.size() == 1) return;
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
				System.out.println("Network not empty: " + networkConnections + " " + lastPositions);
				if (!matches(lastPositions, networkConnections)) {
					System.out.println("Does not match");
					net2.connections = networkConnections;
					addNetwork(net2);
					net.connections.removeAll(networkConnections);
					if (networkConnections.size() > 1) separateNetworks(world, net2, networkConnections);
				}
			}
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
		public List<EnergyNetwork> getNetworks() {
			return networks;
		}

		@Override
		public void setWorld(World world) {
			this.world = world;
		}

		@Override
		public World getWorld() {
			return world;
		}

	}
}
