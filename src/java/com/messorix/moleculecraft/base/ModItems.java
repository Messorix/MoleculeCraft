package com.messorix.moleculecraft.base;

import java.util.ArrayList;
import java.util.List;

import com.anime.basic.registry.RegistryHelper;
import com.messorix.moleculecraft.base.items.ItemCopperDust;
import com.messorix.moleculecraft.base.items.ItemCopperIngot;
import com.messorix.moleculecraft.base.items.ItemGraphite;
import com.messorix.moleculecraft.base.items.ItemLCOElectrode;
import com.messorix.moleculecraft.base.items.ItemLFPElectrode;
import com.messorix.moleculecraft.base.items.ItemLMOElectrode;
import com.messorix.moleculecraft.base.items.ItemLiPoCasing;
import com.messorix.moleculecraft.base.items.ItemLithiumIonBattery;
import com.messorix.moleculecraft.base.items.ModItem;
import com.messorix.moleculecraft.base.items.ModItemEnergy;

public class ModItems 
{
    public static List<ModItem> itemlist = new ArrayList<ModItem>();
    public static List<ModItemEnergy> itemEnergylist = new ArrayList<ModItemEnergy>();
	
	public static ModItem COPPER_INGOT;
	public static ModItem SILVER_INGOT;
	public static ModItem TIN_INGOT;
	
	public static ModItem COPPER_DUST;
	public static ModItem SILVER_DUST;
	public static ModItem TIN_DUST;
	public static ModItem IRON_DUST;
	public static ModItem GOLD_DUST;
	public static ModItem CARBON_DUST;
	public static ModItem DIAMOND_DUST;
	
	public static ModItem LCO_ELECTRODE;
	public static ModItem LMO_ELECTRODE;
	public static ModItem LFP_ELECTRODE;
	public static ModItem GRAPHITE;
	public static ModItem LIPO_CASING;
	
	public static ModItemEnergy LITHIUM_ION_BATTERY;
	
	public void registerItems()
    {
		RegistryHelper.registerItem(COPPER_INGOT = new ItemCopperIngot(), ItemCopperIngot.NAME);
		itemlist.add(COPPER_INGOT);
//		RegistryHelper.registerItem(SILVER_INGOT = new ItemCopperIngot(), ItemCopperIngot.NAME);
//		itemlist.add(SILVER_INGOT);
//		RegistryHelper.registerItem(TIN_INGOT = new ItemCopperIngot(), ItemCopperIngot.NAME);
//		itemlist.add(TIN_INGOT);
		
		RegistryHelper.registerItem(COPPER_DUST = new ItemCopperDust(), ItemCopperDust.NAME);
		itemlist.add(COPPER_DUST);

		RegistryHelper.registerItem(LITHIUM_ION_BATTERY = new ItemLithiumIonBattery(), ItemLithiumIonBattery.NAME);
		itemEnergylist.add(LITHIUM_ION_BATTERY);

		RegistryHelper.registerItem(LCO_ELECTRODE = new ItemLCOElectrode(), ItemLCOElectrode.NAME);
		itemlist.add(LCO_ELECTRODE);
		RegistryHelper.registerItem(LMO_ELECTRODE = new ItemLMOElectrode(), ItemLMOElectrode.NAME);
		itemlist.add(LMO_ELECTRODE);
		RegistryHelper.registerItem(LFP_ELECTRODE = new ItemLFPElectrode(), ItemLFPElectrode.NAME);
		itemlist.add(LFP_ELECTRODE);
		RegistryHelper.registerItem(GRAPHITE = new ItemGraphite(), ItemGraphite.NAME);
		itemlist.add(GRAPHITE);
		RegistryHelper.registerItem(LIPO_CASING = new ItemLiPoCasing(), ItemLiPoCasing.NAME);
		itemlist.add(LIPO_CASING);

    }
    
    public void setItemModels()
    {
    	for (ModItem item:itemlist)
    	{
    		RegistryHelper.registerItemModel(item, 0);
    	}
    	
    	for (ModItemEnergy item:itemEnergylist)
    	{
    		RegistryHelper.registerItemModel(item, 0);
    	}
    }
}