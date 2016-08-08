package com.messorix.moleculecraft.base.items;

import com.messorix.moleculecraft.base.MoleculecraftBase;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ModItem extends Item
{
	public static String NAME;
	
    public ModItem(String unlocalizedName, String registryName)
    {
    	NAME = unlocalizedName;
        this.setUnlocalizedName(unlocalizedName);
    }
    
    public CreativeTabs getCreativeTab()
    {
    	return MoleculecraftBase.moleculeCraftTab;
    }
}