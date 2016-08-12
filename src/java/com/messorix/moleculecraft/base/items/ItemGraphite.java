package com.messorix.moleculecraft.base.items;

import com.messorix.moleculecraft.base.classes.ModAtom;
import com.messorix.moleculecraft.base.classes.ModMolecule;

public class ItemGraphite extends ModItem {

	public ItemGraphite() {
		super("graphite", "graphite");
		
    	MOLECULE = (ModMolecule) new ModMolecule().setAmount(5);
		MOLECULE.addAtom((ModAtom) modAtoms.getModAtomBySymbol("C").setAmount(1));
	}
}
