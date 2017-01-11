package com.anime.rf.network;

import java.util.concurrent.CopyOnWriteArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SyncAllNetworksHandler implements IMessageHandler<SyncAllNetworksMessage, IMessage> {

	@Override
	public IMessage onMessage(SyncAllNetworksMessage message, MessageContext ctx) {
		System.out.println("Synced all networks.");
		Minecraft.getMinecraft().addScheduledTask(new Runnable() {
			
			@Override
			public void run() {
				if (Minecraft.getMinecraft() != null && Minecraft.getMinecraft().theWorld != null) {
					CopyOnWriteArrayList<EnergyNetwork> networks = new CopyOnWriteArrayList<EnergyNetwork>();
					for (NBTTagCompound tag : message.getNetworks()) {
						EnergyNetwork net = EnergyNetwork.createNetwork();
						System.out.println(tag);
						net.readNetworkFromNBT(tag.getCompoundTag("Energy"), tag);
						networks.add(net);
					}
					Minecraft.getMinecraft().theWorld.getCapability(EnergyNetworkProvider.ENERGY_NETWORK_CAPABILITY, null).setNetworks(networks);
				}
			}
		});
		return null;
	}

}