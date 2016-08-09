package com.messorix.moleculecraft.base.blocks;

import com.messorix.moleculecraft.base.classes.ModAtom;

import net.minecraft.block.material.Material;

public class BlockAcanthiteOre extends BlockOre {
	public BlockAcanthiteOre() {
		super("acanthite_ore", "acanthite_ore", Material.ROCK);

		MOLECULE.addAtom((ModAtom) modAtoms.getModAtomBySymbol("Ag").setAmount(2));
		MOLECULE.addAtom((ModAtom) modAtoms.getModAtomBySymbol("S").setAmount(1));
	}
}