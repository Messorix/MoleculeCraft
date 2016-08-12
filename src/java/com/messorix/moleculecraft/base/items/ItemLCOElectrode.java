package com.messorix.moleculecraft.base.items;

import com.messorix.moleculecraft.base.classes.ModAtom;
import com.messorix.moleculecraft.base.classes.ModMolecule;

public class ItemLCOElectrode extends ModItem {

	public ItemLCOElectrode() {
		super("lco_electrode", "lco_electrode");
		
    	MOLECULE = (ModMolecule) new ModMolecule().setAmount(15);
		MOLECULE.addAtom((ModAtom) modAtoms.getModAtomBySymbol("Li").setAmount(1));
		MOLECULE.addAtom((ModAtom) modAtoms.getModAtomBySymbol("Co").setAmount(1));
		MOLECULE.addAtom((ModAtom) modAtoms.getModAtomBySymbol("O").setAmount(2));
	}
}
