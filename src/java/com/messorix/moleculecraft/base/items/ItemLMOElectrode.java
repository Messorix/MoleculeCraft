package com.messorix.moleculecraft.base.items;

import com.messorix.moleculecraft.base.classes.ModAtom;
import com.messorix.moleculecraft.base.classes.ModMolecule;

public class ItemLMOElectrode extends ModItem {

	public ItemLMOElectrode() {
		super("lmo_electrode", "lmo_electrode");
		
    	MOLECULE = (ModMolecule) new ModMolecule().setAmount(5);
		MOLECULE.addAtom((ModAtom) modAtoms.getModAtomBySymbol("Li").setAmount(1));
		MOLECULE.addAtom((ModAtom) modAtoms.getModAtomBySymbol("Mn").setAmount(2));
		MOLECULE.addAtom((ModAtom) modAtoms.getModAtomBySymbol("O").setAmount(4));
	}
}
