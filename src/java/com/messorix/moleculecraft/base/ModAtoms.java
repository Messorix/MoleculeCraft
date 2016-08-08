package com.messorix.moleculecraft.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.messorix.moleculecraft.base.classes.ModAtom;

public class ModAtoms
{
	private ObjectMapper mapper = new ObjectMapper();
	private List<ModAtom> listOfAtoms = new ArrayList<ModAtom>();
	
	/*public static final ModAtom HYDROGEN = new ModAtom(1, "H", "Hydrogen", 1.008f, false, 14.01f, 20.28f);
	public static final ModAtom HELIUM = new ModAtom(2, "He", "Helium", 4.002602f, false, 0.95f, 4.22f);
	public static final ModAtom LITHIUM = new ModAtom(3, "Li", "Lithium", 6.94f, false, 453.69f, 1615f);
	public static final ModAtom BERYLLIUM = new ModAtom(4, "Be", "Beryllium", 9.0121831f, false, 1560f, 2743f);
	public static final ModAtom BORON = new ModAtom(5, "B", "Boron", 10.81f, false, 2348f, 4273f);
	public static final ModAtom CARBON = new ModAtom(6, "C", "Carbon", 12.011f, false, 3823f, 4300f);
	public static final ModAtom NITROGEN = new ModAtom(7, "N", "Nitrogen", 14.007f, false, 63.05f, 77.36f);
	public static final ModAtom OXYGEN = new ModAtom(8, "O", "Oxygen", 15.999f, false, 54.8f, 90.2f);*/
	/*public static final ModAtom HYDROGEN = new ModAtom(1, "H", "Hydrogen", 1.008f, false, 14.01f, 20.28f);
	public static final ModAtom HYDROGEN = new ModAtom(1, "H", "Hydrogen", 1.008f, false, 14.01f, 20.28f);
	public static final ModAtom HYDROGEN = new ModAtom(1, "H", "Hydrogen", 1.008f, false, 14.01f, 20.28f);
	public static final ModAtom HYDROGEN = new ModAtom(1, "H", "Hydrogen", 1.008f, false, 14.01f, 20.28f);
	public static final ModAtom HYDROGEN = new ModAtom(1, "H", "Hydrogen", 1.008f, false, 14.01f, 20.28f);
	public static final ModAtom HYDROGEN = new ModAtom(1, "H", "Hydrogen", 1.008f, false, 14.01f, 20.28f);
	public static final ModAtom HYDROGEN = new ModAtom(1, "H", "Hydrogen", 1.008f, false, 14.01f, 20.28f);*/
	
	public ModAtoms()
	{
		InputStream jsonInput = ModAtoms.class.getClassLoader()
                .getResourceAsStream("assets/moleculecraft/periodictable.json");
		
		try {
			listOfAtoms = mapper.readValue(jsonInput, new TypeReference<List<ModAtom>>(){});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}