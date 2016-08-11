package com.messorix.moleculecraft.base.items;

import com.messorix.moleculecraft.base.MoleculecraftBase;

import cofh.api.energy.ItemEnergyContainer;
import net.minecraft.creativetab.CreativeTabs;

public class ModItemEnergy extends ItemEnergyContainer
{
	public static String NAME;
	
    public ModItemEnergy(String unlocalizedName, String registryName)
    {
    	NAME = unlocalizedName;
        this.setUnlocalizedName(unlocalizedName);
    }
    
    public CreativeTabs getCreativeTab()
    {
    	return MoleculecraftBase.moleculeCraftTab;
    }
}