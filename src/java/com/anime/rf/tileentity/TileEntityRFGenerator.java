package com.anime.rf.tileentity;

import com.anime.basic.logger.ModLogger;
import com.anime.rf.fuel.FuelRecipesBase;

public class TileEntityRFGenerator extends TileEntityRFGeneratorBase {
	
	public TileEntityRFGenerator() {
		super();
		setup();
	}
	
	@Override
	protected boolean setup() {
		if (!hasBeenSetup) {
			recipes = FuelRecipesBase.instance();
			FUEL_SLOTS = 3;
			OUTPUT_SLOTS = 0;
			TOTAL_SLOTS = FUEL_SLOTS + OUTPUT_SLOTS;
			
			makesByproducts = true;
			
			initializeFromVars();
			
			// Used to debug stack sizes and multiple recipes.
//			recipes.addFuel(new ItemStack[]{new ItemStack(Items.COAL, 2), new ItemStack(Items.REDSTONE, 1)}, 50, 200);
			
			ModLogger.logInfoMessage("Setup finished");
			
			hasBeenSetup = true;
		}
		return hasBeenSetup;
	}
	
	public String getDefaultName() {
		return "RF Generator";
	}
	
}
