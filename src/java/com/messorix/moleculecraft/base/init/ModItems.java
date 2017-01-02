package com.messorix.moleculecraft.base.init;

import java.util.ArrayList;
import java.util.List;

import com.anime.basic.registry.RegistryHelper;
import com.messorix.moleculecraft.base.items.*;

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
	public static ModItem ELECTROLYTE;
	public static ModItem LCL_SALT;
	public static ModItem LPF_SALT;
	public static ModItem LBF_SALT;
	
	public static ModItemEnergy LITHIUM_ION_BATTERY;
	
	public void registerItems()
    {
		RegistryHelper.registerItem(COPPER_INGOT = new ItemIngots("copper", "copper"), COPPER_INGOT.name);
		itemlist.add(COPPER_INGOT);
		RegistryHelper.registerItem(SILVER_INGOT = new ItemIngots("silver", "silver"), SILVER_INGOT.name);
		itemlist.add(SILVER_INGOT);
		RegistryHelper.registerItem(TIN_INGOT = new ItemIngots("tin", "tin"), TIN_INGOT.name);
		itemlist.add(TIN_INGOT);
		
		RegistryHelper.registerItem(COPPER_DUST = new ItemDusts("copper", "copper"), COPPER_DUST.name);
		itemlist.add(COPPER_DUST);
		RegistryHelper.registerItem(SILVER_DUST = new ItemDusts("silver", "silver"), SILVER_DUST.name);
		itemlist.add(SILVER_DUST);
		RegistryHelper.registerItem(TIN_DUST = new ItemDusts("tin", "tin"), TIN_DUST.name);
		itemlist.add(TIN_DUST);
		RegistryHelper.registerItem(IRON_DUST = new ItemDusts("iron", "iron"), IRON_DUST.name);
		itemlist.add(IRON_DUST);
		RegistryHelper.registerItem(GOLD_DUST = new ItemDusts("gold", "gold"), GOLD_DUST.name);
		itemlist.add(GOLD_DUST);
		RegistryHelper.registerItem(CARBON_DUST = new ItemDusts("carbon", "carbon"), CARBON_DUST.name);
		itemlist.add(CARBON_DUST);
		RegistryHelper.registerItem(DIAMOND_DUST = new ItemDusts("diamond", "diamond"), DIAMOND_DUST.name);
		itemlist.add(DIAMOND_DUST);
		
		RegistryHelper.registerItem(LITHIUM_ION_BATTERY = new ItemLithiumIonBattery(), LITHIUM_ION_BATTERY.name);
		itemEnergylist.add(LITHIUM_ION_BATTERY);

		RegistryHelper.registerItem(LCO_ELECTRODE = new ItemElectrodes("lco_electrode", "lco_electrode"), LCO_ELECTRODE.name);
		itemlist.add(LCO_ELECTRODE);
		RegistryHelper.registerItem(LMO_ELECTRODE = new ItemElectrodes("lmo_electrode", "lmo_electrode"), LMO_ELECTRODE.name);
		itemlist.add(LMO_ELECTRODE);
		RegistryHelper.registerItem(LFP_ELECTRODE = new ItemElectrodes("lfp_electrode", "lfp_electrode"), LFP_ELECTRODE.name);
		itemlist.add(LFP_ELECTRODE);
		RegistryHelper.registerItem(GRAPHITE = new ItemGraphite(), GRAPHITE.name);
		itemlist.add(GRAPHITE);
		RegistryHelper.registerItem(LIPO_CASING = new ModItem("lipo_casing", "lipo_casing"), LIPO_CASING.name);
		itemlist.add(LIPO_CASING);
		RegistryHelper.registerItem(ELECTROLYTE = new ItemElectrolyte(), ELECTROLYTE.name);
		itemlist.add(ELECTROLYTE);
		RegistryHelper.registerItem(LCL_SALT = new ItemSalts("lco_salt", "lco_salt"), LCL_SALT.name);
		itemlist.add(LCL_SALT);
		RegistryHelper.registerItem(LPF_SALT = new ItemSalts("lpf_salt", "lpf_salt"), LPF_SALT.name);
		itemlist.add(LPF_SALT);
		RegistryHelper.registerItem(LBF_SALT = new ItemSalts("lbf_salt", "lbf_salt"), LBF_SALT.name);
		itemlist.add(LBF_SALT);
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