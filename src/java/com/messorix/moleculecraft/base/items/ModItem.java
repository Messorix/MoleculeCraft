package com.messorix.moleculecraft.base.items;

import com.messorix.moleculecraft.base.MoleculecraftBase;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ModItem extends Item
{
	public String name;
    public ModItem(String unlocalizedName, String registryName)
    {
    	name = unlocalizedName;
        this.setUnlocalizedName(unlocalizedName);
    }
    
    public CreativeTabs getCreativeTab()
    {
    	return MoleculecraftBase.moleculeCraftTab;
    }
}