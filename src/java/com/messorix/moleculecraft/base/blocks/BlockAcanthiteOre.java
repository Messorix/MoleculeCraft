package com.messorix.moleculecraft.base.blocks;

import com.messorix.moleculecraft.base.ModAtoms;

import net.minecraft.block.material.Material;

public class BlockAcanthiteOre extends BlockOre 
{
	public BlockAcanthiteOre() 
	{
		super("acanthite_ore", "acanthite_ore", Material.ROCK);
		
		// Add Atom by symbol
		MOLECULE.put(ModAtoms.getModAtomBySymbol("Ag"), 2);
		MOLECULE.put(ModAtoms.getModAtomBySymbol("S"), 1);
	}
}