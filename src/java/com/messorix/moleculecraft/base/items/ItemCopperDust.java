package com.messorix.moleculecraft.base.items;

import com.messorix.moleculecraft.base.classes.ModAtom;
import com.messorix.moleculecraft.base.classes.ModMolecule;

public class ItemCopperDust extends ModItem 
{
    public ItemCopperDust() 
    {
    	super("copper_dust", "copper_dust");
    	
    	MOLECULE = (ModMolecule) new ModMolecule().setAmount(1);
		MOLECULE.addAtom((ModAtom) modAtoms.getModAtomBySymbol("Cu").setAmount(2));
		MOLECULE.addAtom((ModAtom) modAtoms.getModAtomBySymbol("S").setAmount(1));
    }
}