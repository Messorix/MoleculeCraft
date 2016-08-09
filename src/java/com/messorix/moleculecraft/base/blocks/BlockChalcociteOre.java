package com.messorix.moleculecraft.base.blocks;

import com.messorix.moleculecraft.base.ModAtoms;

import net.minecraft.block.material.Material;

public class BlockChalcociteOre extends BlockOre 
{
	public BlockChalcociteOre() 
	{
		super("chalcocite_ore", "chalcocite_ore", Material.ROCK);
		
		// Add Atom by symbol
		MOLECULE.put(ModAtoms.getModAtomBySymbol("Cu"), 2);
		MOLECULE.put(ModAtoms.getModAtomBySymbol("S"), 1);
	}
}