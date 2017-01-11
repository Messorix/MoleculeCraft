package com.anime.rf.network;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface INetwork {
	
	public int getNetworkID(EnergyNetwork net);
	
	public void addNetwork(EnergyNetwork net);
	public void removeNetwork(EnergyNetwork net);
	
	public void validate(World world);
	
	public List<EnergyNetwork> getNetworks();
	
	void setNetworks(CopyOnWriteArrayList<EnergyNetwork> list);
	
	public List<BlockPos> removeRepeats(List<BlockPos> positions);
	
	public List<EnergyNetwork> networksContainingPos(BlockPos pos);
	
	public EnergyNetwork combineNetworks(World world, List<EnergyNetwork> nets);
	
	public void separateNetworks(World world, EnergyNetwork net, List<BlockPos> lastPositions);
	
	public void setWorld(World world);
	
	public World getWorld();

}
