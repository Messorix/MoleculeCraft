package com.anime.rf.tileentity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityEnergyPipe extends TileEntityPipeBase implements IEnergyConnection {
	
	private Random rand = new Random();
	
	@Override
	public void update() {
		if (!worldObj.isRemote) {
			// Handles adding energy to adjacent IEnergyRecievers from the surrounding IEnergyProviders.
//			for (EnumFacing provide : EnumFacing.VALUES) {
//				if (isProvider(getPos().offset(provide))) {
//					BlockPos providerPos = getPos().offset(provide);
//					System.out.println(providerPos);
//					IEnergyProvider provider = (IEnergyProvider) worldObj.getTileEntity(providerPos);
//					for (EnumFacing recieving : EnumFacing.VALUES) {
//						if (isReceiver(pos.offset(recieving))) {
//							BlockPos receiverPos = getPos().offset(recieving);
//							System.out.println(worldObj.getTileEntity(receiverPos).getDisplayName());
//							IEnergyReceiver receiver = (IEnergyReceiver) worldObj.getTileEntity(receiverPos);
//							provider.extractEnergy(provide.getOpposite(), receiver.receiveEnergy(provide.getOpposite(), provider.extractEnergy(provide.getOpposite(), provider.getEnergyStored(provide.getOpposite()), true), false), false);
//						}
//					}
//				}
//			}
			sendEnergyNearby();
			// Handles pipe transfer.
//			pipeSendCounter++;
//			if (pipeSendCounter >= 1) {
//				if (getPipeConnections().isEmpty()) {
//					lastPosition = new BlockPos(currentPosition);
//					currentPosition = new BlockPos(getPos());
//				}
//				for (EnumFacing providing : EnumFacing.VALUES) {
//					if (isProvider(getPos().offset(providing))) {
//						IEnergyProvider provider = (IEnergyProvider) worldObj.getTileEntity(getPos().offset(providing));
//						List<EnumFacing> connections = getPipeConnections();
//						if (connections.isEmpty()) {
//							currentPosition = lastPosition;
//							if (currentPosition == null) {
//								currentPosition = new BlockPos(getPos());
//								lastPosition = new BlockPos(getPos());
//							}
//							connections = getPipeConnections();
//						}
//						if (connections.size() > 0) {
//							int connectionPos = new Random().nextInt(connections.size());
//							from.clear();							from.add(connections.get(connectionPos).getOpposite());
//							lastPosition = new BlockPos(currentPosition);
//							currentPosition = currentPosition.offset(connections.get(connectionPos));
//							
//							if (isReceiver(currentPosition)) {
//								IEnergyReceiver reciever = (IEnergyReceiver) worldObj.getTileEntity(currentPosition);
//								provider.extractEnergy(providing.getOpposite(), reciever.receiveEnergy(connections.get(connectionPos).getOpposite(), provider.extractEnergy(providing.getOpposite(), provider.getEnergyStored(providing.getOpposite()), true), false), false);
//								lastPosition = new BlockPos(currentPosition);
//								currentPosition = new BlockPos(getPos());
//								from.clear();
//								continue;
//							}
//							if (getPipeConnections().isEmpty()) {
//								currentPosition = new BlockPos(lastPosition);
//							}
//						}
//					}
//				}
//				pipeSendCounter = 0;
//			}
			sendEnergyThroughPipes();
		}
	}
	
	public void sendEnergyNearby() {
		Map<EnumFacing, Integer> providers = getConnectedProviders();
		Map<EnumFacing, Integer> receivers = getConnectedReceivers();
		for (Entry<EnumFacing, Integer> providersEntry : providers.entrySet()) {
			IEnergyStorage providerStorage = null;
			IEnergyProvider provider = null;
			if (providersEntry.getValue() == 0) {
				provider = (IEnergyProvider) worldObj.getTileEntity(getPos().offset(providersEntry.getKey()));
			} else {
				providerStorage = worldObj.getTileEntity(getPos().offset(providersEntry.getKey())).getCapability(CapabilityEnergy.ENERGY, providersEntry.getKey());
			}
			for (Entry<EnumFacing, Integer> receiversEntry : receivers.entrySet()) {
				IEnergyStorage receiverStorage = null;
				IEnergyReceiver receiver = null;
				if (receiversEntry.getValue() == 0) {
					receiver = (IEnergyReceiver) worldObj.getTileEntity(getPos().offset(receiversEntry.getKey()));
				} else {
					receiverStorage = worldObj.getTileEntity(getPos().offset(receiversEntry.getKey())).getCapability(CapabilityEnergy.ENERGY, receiversEntry.getKey());
				}
				// Actually sends the energy
				if (providerStorage != null) {
					if (receiverStorage != null) {
						providerStorage.extractEnergy(receiverStorage.receiveEnergy(providerStorage.extractEnergy(providerStorage.getEnergyStored(), true), false), false);
					} else if (receiver != null) {
						providerStorage.extractEnergy(receiver.receiveEnergy(receiversEntry.getKey().getOpposite(), providerStorage.extractEnergy(providerStorage.getEnergyStored(), true), false), false);
					}
				} else if (provider != null) {
					if (receiverStorage != null) {
						provider.extractEnergy(providersEntry.getKey(), receiverStorage.receiveEnergy(provider.extractEnergy(providersEntry.getKey(), provider.getEnergyStored(providersEntry.getKey()), true), false), false);
					} else if (receiver != null) {
						provider.extractEnergy(providersEntry.getKey().getOpposite(), receiver.receiveEnergy(receiversEntry.getKey().getOpposite(), provider.extractEnergy(providersEntry.getKey().getOpposite(), provider.getEnergyStored(providersEntry.getKey().getOpposite()), true), false), false);
					}					
				}
			}
		}
	}
	
	public void sendEnergyThroughPipes() {
		pipeSendCounter++;
		if (pipeSendCounter >= 1) {
			cachedPositions.clear();
			cachedPositions.add(getPos());
			currentPosition = new BlockPos(getPos());
			from.clear();
			for (Entry<EnumFacing, Integer> entry : getConnectedProviders().entrySet()) {
			main:while (!getPipeConnections().isEmpty()) {
					System.out.println("while");
					List<EnumFacing> connections = getPipeConnections();
					for (EnumFacing facing : connections) {
						if (isReceiver(currentPosition.offset(facing)) || isReceiverCapa(currentPosition.offset(facing), facing)) {
							IEnergyStorage providerStorage = null;
							IEnergyProvider provider = null;
							if (entry.getValue() == 0) {
								provider = (IEnergyProvider) worldObj.getTileEntity(getPos().offset(entry.getKey()));
							} else {
								providerStorage = worldObj.getTileEntity(getPos().offset(entry.getKey())).getCapability(CapabilityEnergy.ENERGY, entry.getKey());
							}
							BlockPos current = currentPosition.offset(facing);
							if (providerStorage != null) {
								if (isReceiverCapa(current, facing)) {
									providerStorage.extractEnergy(worldObj.getCapability(CapabilityEnergy.ENERGY, facing).receiveEnergy(providerStorage.extractEnergy(providerStorage.getEnergyStored(), true), false), false);
								} else if (isReceiver(current)) {
									providerStorage.extractEnergy(((IEnergyReceiver)worldObj.getTileEntity(current)).receiveEnergy(facing.getOpposite(), providerStorage.extractEnergy(providerStorage.getEnergyStored(), true), false), false);
								}
							} else if (provider != null) {
								if (isReceiverCapa(current, facing)) {
									provider.extractEnergy(facing, worldObj.getTileEntity(current).getCapability(CapabilityEnergy.ENERGY, facing.getOpposite()).receiveEnergy(provider.extractEnergy(facing, provider.getEnergyStored(facing), true), false), false);
								} else if (isReceiver(current)) {
									provider.extractEnergy(facing.getOpposite(), ((IEnergyReceiver)worldObj.getTileEntity(current)).receiveEnergy(facing.getOpposite(), provider.extractEnergy(facing.getOpposite(), provider.getEnergyStored(facing.getOpposite()), true), false), false);
								}					
							}
							break main;
						}
					}
					boolean movement = true;
					EnumFacing tempFacing = null;
					while (movement == true) {
						if (from.size() > 6) {
							System.out.println("Size limit reached");
							if (!containsAllValues()) {
								System.out.println("Doesn't contain all");
								EnumFacing facing = from.get(from.size() - 1).getOpposite();
								System.out.println(tempFacing + " " + facing);
								if (tempFacing != null && facing == tempFacing.getOpposite()) {
									System.out.println("Facings the same.");
									tempFacing = null;
									return;
								} else if (tempFacing == null) tempFacing = facing;
								from.clear();
								from.add(facing);
								if (cachedPositions.isEmpty()) {
									currentPosition = new BlockPos(getPos());
									cachedPositions.clear();
									cachedPositions.add(getPos());
									from.clear();
									break main;
								}
								currentPosition = cachedPositions.get(cachedPositions.size() - 1);
								cachedPositions.remove(cachedPositions.size() - 1);
								connections = getPipeConnections();
								System.out.println(from + " " + currentPosition);
								continue;
							}
							System.out.println(from + " " + currentPosition);
							from.clear();
							cachedPositions.clear();
							cachedPositions.add(getPos());
							currentPosition = getPos();
							return;
						}
						if (!connections.isEmpty()) {
							int connectionPos = rand.nextInt(connections.size());
							EnumFacing facing = connections.get(connectionPos);
							from.add(facing.getOpposite());
							cachedPositions.add(currentPosition);
							currentPosition = currentPosition.offset(facing);
							System.out.println("Not Empty");
						}
						if (getPipeConnections().isEmpty()) {
							System.out.println(connections + " " + from + " " + currentPosition);
							if (cachedPositions.isEmpty()) {
								currentPosition = new BlockPos(getPos());
								cachedPositions.clear();
								cachedPositions.add(getPos());
								from.clear();
								break main;
							}
							currentPosition = cachedPositions.get(cachedPositions.size() - 1);
							cachedPositions.remove(cachedPositions.size() - 1);
							connections = getPipeConnections();
							System.out.println(connections + " " + from + " " + currentPosition);
							continue;
						}
						EnumFacing temp = from.get(from.size() - 1);
						from.clear();
						from.add(temp);
						movement = false;
					}
				}
				currentPosition = new BlockPos(getPos());
				cachedPositions.clear();
				cachedPositions.add(getPos());
				from.clear();
			}
		}
	}
	
	public boolean containsAllValues() {
		List<EnumFacing> facings = new ArrayList<EnumFacing>();
		for (EnumFacing facing : from) {
			if (!facings.contains(facing)) facings.add(facing);
		}
		return facings.size() > 5;
	}
	
	public List<EnumFacing> getAllOpposite(List<EnumFacing> facings) {
		List<EnumFacing> returns = new ArrayList<EnumFacing>();
		for (EnumFacing facing : facings) {
			returns.add(facing.getOpposite());
		}
		return returns;
	}
	
	public Map<EnumFacing, Integer> getConnectedProviders() {
		Map<EnumFacing, Integer> map = new HashMap<EnumFacing, Integer>();
		for (EnumFacing facing : EnumFacing.VALUES) {
			if (isProvider(getPos().offset(facing))) map.put(facing, 0);
			if (isProviderCapa(getPos().offset(facing), facing)) map.put(facing, 1);
		}
		return map;
	}
	
	public Map<EnumFacing, Integer> getConnectedReceivers() {
		Map<EnumFacing, Integer> map = new HashMap<EnumFacing, Integer>();
		for (EnumFacing facing : EnumFacing.VALUES) {
			if (isReceiver(getPos().offset(facing))) map.put(facing, 0);
			if (isReceiverCapa(getPos().offset(facing), facing)) map.put(facing, 1);
		}
		return map;
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
	
	public boolean isReceiver(BlockPos pos) {
		TileEntity te = worldObj.getTileEntity(pos);
		return te instanceof IEnergyReceiver;
	}
	
	public boolean isProvider(BlockPos pos) {
		TileEntity te = worldObj.getTileEntity(pos);
		return te instanceof IEnergyProvider;
	}
	
	public boolean isProviderCapa(BlockPos pos, EnumFacing facing) {
		TileEntity te = worldObj.getTileEntity(pos);
		return te != null && te.hasCapability(CapabilityEnergy.ENERGY, facing) && te.getCapability(CapabilityEnergy.ENERGY, facing).canExtract();
	}

	public boolean isReceiverCapa(BlockPos pos, EnumFacing facing) {
		TileEntity te = worldObj.getTileEntity(pos);
		return te != null && te.hasCapability(CapabilityEnergy.ENERGY, facing) && te.getCapability(CapabilityEnergy.ENERGY, facing).canReceive();
	}
	
}
