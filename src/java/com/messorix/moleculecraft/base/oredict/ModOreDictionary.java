package com.messorix.moleculecraft.base.oredict;

import com.anime.basic.registry.RegistryHelper;
import com.messorix.moleculecraft.base.ModBlocks;
import com.messorix.moleculecraft.base.ModItems;

public class ModOreDictionary {
	
	public ModOreDictionary() {
		addOresToOreDictionary();
		addIngotsToOreDictionary();
		addDustsToOreDictionary();
	}
	
	public void addOresToOreDictionary() {
		RegistryHelper.addOreToOreDict(ModBlocks.ACANTHITE_ORE, "Silver");
		RegistryHelper.addOreToOreDict(ModBlocks.CASSITERITE_ORE, "Tin");
		RegistryHelper.addOreToOreDict(ModBlocks.CHALCOCITE_ORE, "Copper");
		
	}
	
	public void addIngotsToOreDictionary() {
		RegistryHelper.addIngotToOreDict(ModItems.COPPER_INGOT, "Copper");
	}
	
	public void addDustsToOreDictionary() {
		RegistryHelper.addDustToOreDict(ModItems.COPPER_DUST, "Copper");
	}
	
}
