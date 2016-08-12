package com.messorix.moleculecraft.base.items;

import com.messorix.moleculecraft.base.classes.ModAtom;
import com.messorix.moleculecraft.base.classes.ModMolecule;

public class ItemLiPoCasing extends ModItem {

	public ItemLiPoCasing() {
		super("lipo_casing", "lipo_casing");
		
    	MOLECULE = (ModMolecule) new ModMolecule().setAmount(8);
		MOLECULE.addAtom((ModAtom) modAtoms.getModAtomBySymbol("Li").setAmount(1));
		MOLECULE.addAtom((ModAtom) modAtoms.getModAtomBySymbol("Po").setAmount(1));
	}
}