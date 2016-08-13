package com.messorix.moleculecraft.base.classes;

import java.util.ArrayList;

public class ModMolecule extends ModMoleculeBase {
	private ArrayList<ModMoleculeBase> atomsAndMolecules = new ArrayList<>();

	public ModMolecule() {

	}

	public ArrayList<ModMoleculeBase> getAtomsAndMolecules() {
		return this.atomsAndMolecules;
	}

	public ModMolecule addMolecule(ModMoleculeBase molecule) {
		atomsAndMolecules.add(molecule);
		return this;
	}

	public ModMolecule addAtom(ModMoleculeBase atom) {
		atomsAndMolecules.add(atom);
		return this;
	}

	@Override
	public String toString() {

		String stringValue = "";
		for (ModMoleculeBase m : atomsAndMolecules) {

			if (m instanceof ModAtom) {

				if (m.getAmount() > 1) {
					stringValue += ((ModAtom) m).getSymbol() + m.getStringAmount();
				} else
					stringValue += ((ModAtom) m).getSymbol();

			} else if (m != null) {
				stringValue += "(" + m + ")" + m.getStringAmount();
			} else {
				stringValue += "null";
			}
		}

		return stringValue;
	}

}