package com.messorix.moleculecraft.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.messorix.moleculecraft.base.classes.ModAtom;

public class ModAtoms {

	private ObjectMapper mapper = new ObjectMapper();
	private List<ModAtom> listOfAtoms = new ArrayList<ModAtom>();

	public ModAtoms() {

		try {

			InputStream jsonInput = ModAtoms.class.getClassLoader()
					.getResourceAsStream("assets/moleculecraft/periodictable.json");

			listOfAtoms = mapper.readValue(jsonInput, new TypeReference<List<ModAtom>>() {
			});

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Find the right Atom by name (e.g. Hydrogen)
	 * 
	 * @param name
	 *            the atom name to search for
	 * @return ModAtom
	 */
	public ModAtom getModAtomByName(String name) {

		for (ModAtom atom : listOfAtoms) {
			if (atom.getName().toLowerCase().equals(name.toLowerCase())) {
				return atom;
			}
		}
		return null;
	}

	/**
	 * Find the right Atom by symbol (e.g. H)
	 * 
	 * @param name
	 *            the atom symbol to search for
	 * @return ModAtom
	 */
	public ModAtom getModAtomBySymbol(String symbol) {

		for (ModAtom atom : listOfAtoms) {
			if (atom.getSymbol().toLowerCase().equals(symbol.toLowerCase())) {
				return atom;
			}
		}
		return null;
	}

}