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
		RegistryHelper.addOreToOreDict(ModBlocks.ACANTHITEORE, "Silver");
		RegistryHelper.addOreToOreDict(ModBlocks.CASSITERITEORE, "Tin");
		RegistryHelper.addOreToOreDict(ModBlocks.CHALCOCITEORE, "Copper");
	}
	
	public void addIngotsToOreDictionary() {
		RegistryHelper.addIngotToOreDict(ModItems.COPPERINGOT, "Copper");
	}
	
	public void addDustsToOreDictionary() {
		RegistryHelper.addDustToOreDict(ModItems.COPPERDUST, "Copper");
	}
	
}
