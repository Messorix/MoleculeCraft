package com.anime.rf.network;

import java.util.List;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public interface INetwork {
	
	public void addNetwork(EnergyNetwork net);
	public void removeNetwork(EnergyNetwork net);
	
	public void validate(World world);
	
	public List<EnergyNetwork> getNetworks();
	
	public List<BlockPos> removeRepeats(List<BlockPos> positions);
	
	public List<EnergyNetwork> networksContainingPos(BlockPos pos);
	
	public EnergyNetwork combineNetworks(World world, List<EnergyNetwork> nets);
	
	public void separateNetworks(IBlockAccess world, EnergyNetwork net, List<BlockPos> lastPositions);
	
	public void setWorld(World world);
	
	public World getWorld();
}
