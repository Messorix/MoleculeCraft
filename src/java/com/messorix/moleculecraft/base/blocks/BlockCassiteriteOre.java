package com.messorix.moleculecraft.base.blocks;

import com.messorix.moleculecraft.base.classes.ModAtom;

import net.minecraft.block.material.Material;

public class BlockCassiteriteOre extends BlockOre {
	public BlockCassiteriteOre() {
		super("cassiterite_ore", "cassiterite_ore", Material.ROCK);

		MOLECULE.addAtom((ModAtom) modAtoms.getModAtomBySymbol("Sn").setAmount(1));
		MOLECULE.addAtom((ModAtom) modAtoms.getModAtomBySymbol("O").setAmount(2));
	}
}