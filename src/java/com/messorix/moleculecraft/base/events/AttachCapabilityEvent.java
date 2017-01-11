package com.messorix.moleculecraft.base.events;

import com.anime.basic.MainModReference;
import com.anime.rf.network.EnergyNetworkProvider;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AttachCapabilityEvent {
	
	private static final ResourceLocation ENERGY_NETWORK_ID = new ResourceLocation(MainModReference.MODID, "ENERGY_NETWORK");
	
	@SubscribeEvent
	public void attachWorldCapabilities(AttachCapabilitiesEvent<World> event) {
		event.addCapability(ENERGY_NETWORK_ID, new EnergyNetworkProvider(event.getObject()));
	}
	
}
