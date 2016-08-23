package com.messorix.moleculecraft.base.items;

import com.messorix.moleculecraft.base.MoleculecraftBase;

import cofh.api.energy.ItemEnergyContainer;
import net.minecraft.creativetab.CreativeTabs;

public class ModItemEnergy extends ItemEnergyContainer
{
	public String name;
	
    public ModItemEnergy(String unlocalizedName, String registryName)
    {
    	name = unlocalizedName;
        this.setUnlocalizedName(unlocalizedName);
    }
    
    public CreativeTabs getCreativeTab()
    {
    	return MoleculecraftBase.moleculeCraftTab;
    }
}