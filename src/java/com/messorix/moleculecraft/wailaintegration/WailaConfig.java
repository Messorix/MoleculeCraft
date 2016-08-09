package com.messorix.moleculecraft.wailaintegration;

import com.messorix.moleculecraft.base.blocks.BlockChalcociteOre;
import mcp.mobius.waila.api.IWailaRegistrar;

public class WailaConfig {

	public static void callbackRegister(IWailaRegistrar registrar) {
		registrar.registerBodyProvider(new WailaProviderOre(), BlockChalcociteOre.class);
	}
}