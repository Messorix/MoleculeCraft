package com.messorix.moleculecraft.base;

import java.util.ArrayList;
import java.util.List;

import com.anime.basic.registry.RegistryHelper;
import com.messorix.moleculecraft.base.items.ItemDusts;
import com.messorix.moleculecraft.base.items.ItemGraphite;
import com.messorix.moleculecraft.base.items.ItemIngots;
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
		RegistryHelper.registerItem(COPPER_INGOT = new ItemIngots("copper", "copper"), COPPER_INGOT.NAME);
		itemlist.add(COPPER_INGOT);
		RegistryHelper.registerItem(SILVER_INGOT = new ItemIngots("silver", "silver"), SILVER_INGOT.NAME);
		itemlist.add(SILVER_INGOT);
		RegistryHelper.registerItem(TIN_INGOT = new ItemIngots("tin", "tin"), TIN_INGOT.NAME);
		itemlist.add(TIN_INGOT);
		
		RegistryHelper.registerItem(COPPER_DUST = new ItemDusts("copper", "copper"), COPPER_DUST.NAME);
		itemlist.add(COPPER_DUST);
		RegistryHelper.registerItem(SILVER_DUST = new ItemDusts("silver", "silver"), SILVER_DUST.NAME);
		itemlist.add(SILVER_DUST);
		RegistryHelper.registerItem(TIN_DUST = new ItemDusts("tin", "tin"), TIN_DUST.NAME);
		itemlist.add(TIN_DUST);
		RegistryHelper.registerItem(IRON_DUST = new ItemDusts("iron", "iron"), IRON_DUST.NAME);
		itemlist.add(IRON_DUST);
		RegistryHelper.registerItem(GOLD_DUST = new ItemDusts("gold", "gold"), GOLD_DUST.NAME);
		itemlist.add(GOLD_DUST);
		RegistryHelper.registerItem(CARBON_DUST = new ItemDusts("carbon", "carbon"), CARBON_DUST.NAME);
		itemlist.add(CARBON_DUST);
		RegistryHelper.registerItem(DIAMOND_DUST = new ItemDusts("diamond", "diamond"), DIAMOND_DUST.NAME);
		itemlist.add(DIAMOND_DUST);
		
		RegistryHelper.registerItem(LITHIUM_ION_BATTERY = new ItemLithiumIonBattery(), ItemLithiumIonBattery.NAME);
		itemEnergylist.add(LITHIUM_ION_BATTERY);

		RegistryHelper.registerItem(LCO_ELECTRODE = new ItemLCOElectrode(), LCO_ELECTRODE.NAME);
		itemlist.add(LCO_ELECTRODE);
		RegistryHelper.registerItem(LMO_ELECTRODE = new ItemLMOElectrode(), LMO_ELECTRODE.NAME);
		itemlist.add(LMO_ELECTRODE);
		RegistryHelper.registerItem(LFP_ELECTRODE = new ItemLFPElectrode(), LFP_ELECTRODE.NAME);
		itemlist.add(LFP_ELECTRODE);
		RegistryHelper.registerItem(GRAPHITE = new ItemGraphite(), GRAPHITE.NAME);
		itemlist.add(GRAPHITE);
		RegistryHelper.registerItem(LIPO_CASING = new ItemLiPoCasing(), LIPO_CASING.NAME);
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