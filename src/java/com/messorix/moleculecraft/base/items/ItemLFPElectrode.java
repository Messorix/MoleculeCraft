package com.messorix.moleculecraft.base.items;

import com.messorix.moleculecraft.base.classes.ModAtom;
import com.messorix.moleculecraft.base.classes.ModMolecule;

public class ItemLFPElectrode extends ModItem {

	public ItemLFPElectrode() {
		super("lfp_electrode", "lfp_electrode");
		
    	MOLECULE = (ModMolecule) new ModMolecule().setAmount(4);
		MOLECULE.addAtom((ModAtom) modAtoms.getModAtomBySymbol("Li").setAmount(1));
		MOLECULE.addAtom((ModAtom) modAtoms.getModAtomBySymbol("Fe").setAmount(1));
		MOLECULE.addAtom((ModAtom) modAtoms.getModAtomBySymbol("P").setAmount(1));
		MOLECULE.addAtom((ModAtom) modAtoms.getModAtomBySymbol("O").setAmount(4));
	}
}
