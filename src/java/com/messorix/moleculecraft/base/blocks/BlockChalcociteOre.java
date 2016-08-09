package com.messorix.moleculecraft.base.blocks;

import com.messorix.moleculecraft.base.classes.ModAtom;

import net.minecraft.block.material.Material;

public class BlockChalcociteOre extends BlockOre {
	public BlockChalcociteOre() {
		super("chalcocite_ore", "chalcocite_ore", Material.ROCK);

		MOLECULE.addAtom((ModAtom) modAtoms.getModAtomBySymbol("Cu").setAmount(2));
		MOLECULE.addAtom((ModAtom) modAtoms.getModAtomBySymbol("S").setAmount(1));
	}
}