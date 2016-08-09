package com.messorix.moleculecraft.wailaintegration;

import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.block.Block;

public class WailaConfig {

	public static void callbackRegister(IWailaRegistrar registrar) {
		registrar.registerBodyProvider(new WailaProviderOre(), Block.class);
	}
}