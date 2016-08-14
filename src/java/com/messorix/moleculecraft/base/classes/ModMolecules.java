package com.messorix.moleculecraft.base.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.anime.basic.logger.ModLogger;
import com.messorix.moleculecraft.base.init.ModAtoms;
import com.messorix.moleculecraft.base.init.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModMolecules {
	
	private static Map<String, ModMolecule> MOLECULES = new HashMap<String, ModMolecule>();
	
	/** A Map containing the Molecule for a specified item/block with meta. **/
	public static Map<ItemStack, ModMolecule> objectMolecules = new HashMap<ItemStack, ModMolecule>();
	
	/** Names of items from the OreDictionary(example oreCopper) **/
	private static List<String> oreDictionaryNames = new ArrayList<String>();
	
	/** Whether or not the molecules have been created. **/
	private static boolean moleculesCreated = false;
	
	/** Whether or not the ore dictionary names have been added. **/
	private static boolean oreDictNamesAdded = false;
	
	/**
	 * @param amount The amount of the molecule
	 * @return A new molecule with the specified amount.
	 */
	public static ModMolecule createMolecule(int amount) {
    	return (ModMolecule) new ModMolecule().setAmount(amount);
    }
    
	/** Creates the molecules once. **/
    public static void createMolecules() {
    	if (!moleculesCreated) {
    		ModAtoms atoms = new ModAtoms();
    		MOLECULES.put("copper", createMolecule(1).addAtom(atoms.getModAtomBySymbol("Cu").setAmount(2)).addAtom(atoms.getModAtomBySymbol("S").setAmount(1)));
    		MOLECULES.put("silver", createMolecule(1).addAtom(atoms.getModAtomBySymbol("Ag").setAmount(2)).addAtom(atoms.getModAtomBySymbol("S").setAmount(1)));
    		MOLECULES.put("tin", createMolecule(1).addAtom(atoms.getModAtomBySymbol("Sn").setAmount(1)).addAtom(atoms.getModAtomBySymbol("O").setAmount(2)));
    		MOLECULES.put("iron", createMolecule(1).addAtom(atoms.getModAtomBySymbol("Fe").setAmount(1)));
    		MOLECULES.put("gold", createMolecule(1).addAtom(atoms.getModAtomBySymbol("Au").setAmount(1)));
    		MOLECULES.put("carbon", createMolecule(1).addAtom(atoms.getModAtomBySymbol("C").setAmount(1)));
    		MOLECULES.put("diamond", createMolecule(1).addAtom(atoms.getModAtomBySymbol("C").setAmount(16)));
    		MOLECULES.put("emerald", createMolecule(1).addAtom(atoms.getModAtomBySymbol("Be").setAmount(3)).addAtom(atoms.getModAtomBySymbol("Al").setAmount(2)).addMolecule(createMolecule(6).addAtom(atoms.getModAtomBySymbol("Si").setAmount(1)).addAtom(atoms.getModAtomBySymbol("O").setAmount(3))));
    		
    		MOLECULES.put("lithiumcarbonoxide", createMolecule(1).addAtom(atoms.getModAtomBySymbol("Li").setAmount(1)).addAtom(atoms.getModAtomBySymbol("C").setAmount(1)).addAtom(atoms.getModAtomBySymbol("O").setAmount(2)));
    		MOLECULES.put("lithiummagneseoxide", createMolecule(1).addAtom(atoms.getModAtomBySymbol("Li").setAmount(1)).addAtom(atoms.getModAtomBySymbol("Mn").setAmount(2)).addAtom(atoms.getModAtomBySymbol("O").setAmount(4)));
    		MOLECULES.put("lithiumironphosphateoxide", createMolecule(1).addAtom(atoms.getModAtomBySymbol("Li").setAmount(1)).addAtom(atoms.getModAtomBySymbol("Fe").setAmount(1)).addAtom(atoms.getModAtomBySymbol("P").setAmount(1)).addAtom(atoms.getModAtomBySymbol("O").setAmount(4)));
    		MOLECULES.put("lithiumpolonium", createMolecule(1).addMolecule(createMolecule(8).addAtom(atoms.getModAtomBySymbol("Li").setAmount(1)).addAtom(atoms.getModAtomBySymbol("Po").setAmount(1))));
    		
    		MOLECULES.put("graphite", createMolecule(5).addAtom(atoms.getModAtomBySymbol("C").setAmount(1)));
    		// TODO Add more Molecules
    		moleculesCreated = true;
    		ModLogger.logInfoMessage("Succesfully created Molecules.");
    	}
    }
	
    /** Adds the Names to oreDictionaryNames. **/
    private static void addOreDictNames() {
    	if (!oreDictNamesAdded) {
    		oreDictionaryNames.add("oreCopper");
    		oreDictionaryNames.add("oreSilver");
    		oreDictionaryNames.add("oreTin");
    		oreDictionaryNames.add("oreIron");
    		oreDictionaryNames.add("oreGold");
    		oreDictionaryNames.add("oreCoal");
    		oreDictionaryNames.add("oreDiamond");
    		oreDictionaryNames.add("oreEmerald");
    		
    		oreDictionaryNames.add("ingotCopper");
    		oreDictionaryNames.add("ingotSilver");
    		oreDictionaryNames.add("ingotTin");
    		oreDictionaryNames.add("ingotIron");
    		oreDictionaryNames.add("ingotGold");
    		
    		oreDictionaryNames.add("coal");
    		
    		oreDictionaryNames.add("gemDiamond");
    		oreDictionaryNames.add("gemEmerald");
    		
    		oreDictionaryNames.add("dustCopper");
    		oreDictionaryNames.add("dustSilver");
    		oreDictionaryNames.add("dustTin");
    		oreDictionaryNames.add("dustIron");
    		oreDictionaryNames.add("dustGold");
    		oreDictionaryNames.add("dustCarbon");
    		oreDictionaryNames.add("dustDiamond");
    		// TODO Add more names
    		oreDictNamesAdded = true;
    		ModLogger.logInfoMessage("Succesfully added OreDictionay Names to List.");
    	}
    }
    
    /**  Binds the ItemStacks from the OreDictionary based on oreDictionaryNames. **/
    public static void bindMolecules() {
    	addOreDictNames();
    	final String ore = "ore";
    	final String gem = "gem";
    	final String dust = "dust";
    	final String ingot = "ingot";
    	names : for (String dictName : oreDictionaryNames) {
    		ModLogger.logInfoMessage("Name: " + dictName);
    		if (dictName == null || dictName.isEmpty()) continue;
    		ModMolecule molecule = null;
    		if (molecule == null && dictName.startsWith("oreCoal") || dictName.startsWith("coal")) molecule = getMoleculeByMaterial("carbon");
    		if (molecule == null && dictName.startsWith(ore)) {
    			molecule = getMoleculeByMaterial(dictName.substring(3));
    			ModLogger.logInfoMessage("Binding: " + dictName);
    		}
    		if (molecule == null && dictName.startsWith(gem)) {
    			molecule = getMoleculeByMaterial(dictName.substring(3));
    			ModLogger.logInfoMessage("Binding: " + dictName);
    		}
    		if (molecule == null && dictName.startsWith(dust)) {
    			molecule = getMoleculeByMaterial(dictName.substring(4));
    			ModLogger.logInfoMessage("Binding: " + dictName);
    		}
    		if (molecule == null && dictName.startsWith(ingot)) molecule = getMoleculeByMaterial(dictName.substring(5));
    		if (molecule == null) molecule = getMoleculeByMaterial(dictName);
    		for (ItemStack stack : OreDictionary.getOres(dictName)) {
    			if (molecule == null) continue names;
    			if (stack != null) objectMolecules.put(stack, molecule);
    		}
    	}
    	
    	objectMolecules.put(new ItemStack(ModItems.LCO_ELECTRODE), getMoleculeByMaterial("lithiumcarbonoxide"));
    	objectMolecules.put(new ItemStack(ModItems.LMO_ELECTRODE), getMoleculeByMaterial("lithiummagneseoxide"));
    	objectMolecules.put(new ItemStack(ModItems.LFP_ELECTRODE), getMoleculeByMaterial("lithiumironphosphateoxide"));
    	objectMolecules.put(new ItemStack(ModItems.LIPO_CASING), getMoleculeByMaterial("lithiumpolonium"));
    	
    	objectMolecules.put(new ItemStack(ModItems.GRAPHITE), getMoleculeByMaterial("graphite"));
    	ModLogger.logInfoMessage("Succesfully bound molecules to ItemStacks.");
    }
    
    /**
     * @param material The material for the molecule you want(example copper)
     * @return ModMolecule from MOLECULES.
     */
    public static ModMolecule getMoleculeByMaterial(String material) {
    	if (!moleculesCreated) createMolecules();
    	material = material.toLowerCase();
    	if (MOLECULES.containsKey(material)) return MOLECULES.get(material);
    	return null;
    }
    
}
