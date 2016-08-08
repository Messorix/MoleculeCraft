package com.messorix.moleculecraft.base.blocks;

import com.messorix.moleculecraft.base.ModAtoms;

import net.minecraft.block.material.Material;

public class BlockChalcociteOre extends BlockOre 
{	
	@SuppressWarnings("unchecked")
	public BlockChalcociteOre() 
	{
		super("chalcocite_ore", "chalcocite_ore", Material.ROCK);
		MOLECULE.put(ModAtoms.HYDROGEN, 2);
		MOLECULE.put(ModAtoms.HYDROGEN, 1);
	}
}