package com.messorix.moleculecraft.base.items;

import com.messorix.moleculecraft.base.classes.ModAtom;
import com.messorix.moleculecraft.base.classes.ModMolecule;

public class ItemCopperIngot extends ModItem 
{
    public ItemCopperIngot() 
    {
    	super("copper_ingot", "copper_ingot");	
    	
    	MOLECULE = (ModMolecule) new ModMolecule().setAmount(1);
		MOLECULE.addAtom((ModAtom) modAtoms.getModAtomBySymbol("Cu").setAmount(2));
		MOLECULE.addAtom((ModAtom) modAtoms.getModAtomBySymbol("S").setAmount(1));
    }
}