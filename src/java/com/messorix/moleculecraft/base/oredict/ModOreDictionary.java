package com.messorix.moleculecraft.base.oredict;

import com.anime.basic.registry.RegistryHelper;
import com.messorix.moleculecraft.base.ModBlocks;
import com.messorix.moleculecraft.base.ModItems;

import net.minecraft.init.Items;

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
		RegistryHelper.addIngotToOreDict(ModItems.SILVER_INGOT, "Silver");
		RegistryHelper.addIngotToOreDict(ModItems.TIN_INGOT, "Tin");
	}
	
	public void addDustsToOreDictionary() {
		RegistryHelper.addDustToOreDict(ModItems.COPPER_DUST, "Copper");
		RegistryHelper.addDustToOreDict(ModItems.SILVER_DUST, "Silver");
		RegistryHelper.addDustToOreDict(ModItems.TIN_DUST, "Tin");
		RegistryHelper.addDustToOreDict(ModItems.IRON_DUST, "Iron");
		RegistryHelper.addDustToOreDict(ModItems.GOLD_DUST, "Gold");
		RegistryHelper.addDustToOreDict(ModItems.CARBON_DUST, "Carbon");
		RegistryHelper.addDustToOreDict(ModItems.DIAMOND_DUST, "Diamond");
	}
	
	public void addMiscToOreDictionary() {
		RegistryHelper.registerObjectToOreDict(Items.COAL, "coal");
	}
	
}
