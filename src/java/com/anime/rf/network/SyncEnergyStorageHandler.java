package com.anime.rf.network;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SyncEnergyStorageHandler implements IMessageHandler<SyncEnergyStorageMessage, IMessage> {

	@Override
	public IMessage onMessage(SyncEnergyStorageMessage message, MessageContext ctx) {
		System.out.println("onMessage");
		System.out.println(message.getNetworkID());
		if (message.getNetworkID() > -1) {
			System.out.println("Message received");
			Minecraft.getMinecraft().addScheduledTask(new Runnable() {
				
				@Override
				public void run() {
					System.out.println("Running");
					if (Minecraft.getMinecraft() != null && Minecraft.getMinecraft().theWorld != null) {
						INetwork network = Minecraft.getMinecraft().theWorld.getCapability(EnergyNetworkProvider.ENERGY_NETWORK_CAPABILITY, null);
						if (network.getNetworks().size() - 1 < message.getNetworkID()) {
							// Create new Network
							EnergyNetwork net = EnergyNetwork.createNetwork();
							net.storage.readFromNBT(message.getStorage());
							network.addNetwork(net);
							System.out.println("Created and added a new network");
						} else if (network.getNetworks().get(message.getNetworkID()) != null) {
							EnergyNetwork net = network.getNetworks().get(message.getNetworkID());
							net.storage.readFromNBT(message.getStorage());
							System.out.println("Modified a network.");
						} else {
							// Maybe create a new Network?
						}
					}
				}
				
			});
		}
		return null;
	}

}
