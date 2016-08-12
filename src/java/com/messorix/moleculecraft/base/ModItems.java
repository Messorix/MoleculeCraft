package com.messorix.moleculecraft.base;

import java.util.ArrayList;
import java.util.List;

import com.anime.basic.registry.RegistryHelper;
import com.messorix.moleculecraft.base.items.*;

public class ModItems 
{
	public static ModItem COPPERINGOT;
	public static ModItem COPPERDUST;
	public static ModItem LCOELECTRODE;
	public static ModItem LMOELECTRODE;
	public static ModItem LFPELECTRODE;
	public static ModItem GRAPHITE;
	
	public static ModItemEnergy LITHIUMIONBATTERY;
    public static List<ModItem> itemlist = new ArrayList<ModItem>();
    public static List<ModItemEnergy> itemEnergylist = new ArrayList<ModItemEnergy>();
    
	public void registerItems()
    {
		RegistryHelper.registerItem(COPPERINGOT = new ItemCopperIngot(), ItemCopperIngot.NAME);
		itemlist.add(COPPERINGOT);
		
		RegistryHelper.registerItem(COPPERDUST = new ItemCopperDust(), ItemCopperDust.NAME);
		itemlist.add(COPPERDUST);

		RegistryHelper.registerItem(LITHIUMIONBATTERY = new ItemLithiumIonBattery(), ItemLithiumIonBattery.NAME);
		itemEnergylist.add(LITHIUMIONBATTERY);

		RegistryHelper.registerItem(LCOELECTRODE = new ItemLCOElectrode(), ItemLCOElectrode.NAME);
		itemlist.add(LCOELECTRODE);
		RegistryHelper.registerItem(LMOELECTRODE = new ItemLMOElectrode(), ItemLMOElectrode.NAME);
		itemlist.add(LMOELECTRODE);
		RegistryHelper.registerItem(LFPELECTRODE = new ItemLFPElectrode(), ItemLFPElectrode.NAME);
		itemlist.add(LFPELECTRODE);
		RegistryHelper.registerItem(GRAPHITE = new ItemGraphite(), ItemGraphite.NAME);
		itemlist.add(GRAPHITE);

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