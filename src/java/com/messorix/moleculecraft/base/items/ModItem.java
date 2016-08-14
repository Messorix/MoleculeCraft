package com.messorix.moleculecraft.base.items;

import com.messorix.moleculecraft.base.ModAtoms;
import com.messorix.moleculecraft.base.MoleculecraftBase;
import com.messorix.moleculecraft.base.classes.ModMolecule;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ModItem extends Item
{
	public String NAME;
	public ModMolecule MOLECULE = (ModMolecule) new ModMolecule().setAmount(0);
	public ModAtoms modAtoms = new ModAtoms();
	
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