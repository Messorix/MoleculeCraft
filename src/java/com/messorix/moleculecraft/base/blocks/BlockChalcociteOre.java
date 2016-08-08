package com.messorix.moleculecraft.base.blocks;

import com.messorix.moleculecraft.base.ModAtoms;
import com.messorix.moleculecraft.base.classes.ModAtom;

import net.minecraft.block.material.Material;

public class BlockChalcociteOre extends BlockOre {
	@SuppressWarnings("unchecked")
	public BlockChalcociteOre() 
	{
		super("chalcocite_ore", "chalcocite_ore", Material.ROCK);
		
		ModAtom atom = ModAtoms.getModAtomByName("Copper");
		System.out.println("\n--------FOUND ATOM -------"+
						   "\nName: "+atom.getName()+ 
						   "\nAtomic Number:" +atom.getAtomicNumber()+ 
						   "\nSymbol:" +atom.getSymbol()+ 
						   "\n--------FOUND ATOM -------");
		
		// Add Atom by name
		MOLECULE.put(ModAtoms.getModAtomByName("HYDROGEN"), 1);
				
		// Add Atom by name, being radio active
		MOLECULE.put(ModAtoms.getModAtomByName("HYDROGEN").setRadioActive(true), 2);
		
		// Add Atom by symbol
		MOLECULE.put(ModAtoms.getModAtomBySymbol("H"), 3);
		
		// Add Atom by symbol, being radio active
		MOLECULE.put(ModAtoms.getModAtomBySymbol("H").setRadioActive(true), 4);
	}
}