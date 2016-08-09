package com.messorix.moleculecraft.wailaintegration;

import com.messorix.moleculecraft.base.blocks.*;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.block.Block;

public class WailaConfig {

	public static void callbackRegister(IWailaRegistrar registrar) {
		registrar.registerBodyProvider(new WailaProviderOre(), BlockChalcociteOre.class);
		registrar.registerBodyProvider(new WailaProviderVanillaOre(), Block.class);
	}
}