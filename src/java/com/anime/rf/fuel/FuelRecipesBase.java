package com.anime.rf.fuel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.anime.basic.logger.ModLogger;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class FuelRecipesBase {
	
	/** A Map of the actual fuels. **/
	protected Map<Integer, Map<Integer, ItemStack>> fuels = new HashMap<Integer, Map<Integer, ItemStack>>();
	/** A Map of the byproducts. **/
	public Map<Integer, Map<Integer, ItemStack>> byproductsMap = new HashMap<Integer, Map<Integer, ItemStack>>();
	/** Stack sizes for byproducts. **/
	protected Map<Integer, int[]> byproductsStacksizes = new HashMap<Integer, int[]>();
	/** Valid fuels for generation and there RF per tick. **/
	protected Map<Integer, Integer> RFPerTickMap = new HashMap<Integer, Integer>();
	/** Valid fuels for generation and the amount of ticks the fuel lasts for. **/
	protected Map<Integer, Integer> genTicksMap = new HashMap<Integer, Integer>();
	
	/** The maximum length of the specified type **/
	protected int maxLengthInput = 3, maxLengthByproduct = 0;
	
	protected static FuelRecipesBase instance = new FuelRecipesBase();
	
	public FuelRecipesBase() {
		addFuel(new ItemStack[]{new ItemStack(Items.COAL), new ItemStack(Items.REDSTONE), new ItemStack(Items.APPLE)}, 30, 600);
	}
	
	/**
	 * A loop into {@link #addFuel(ItemStack[], ItemStack[], int, int) addFuel} method.
	 * @param fuel The ItemStacks for the fuel, can be any size as long as it is below A loop into {@link #maxLengthInput} method..
	 * @param RFPerTick RF to be generated per tick.
	 * @param numTicks The amount of time this fuel should last in ticks(20 ticks = 1 second).
	 */
	public void addFuel(ItemStack[] fuel, int RFPerTick, int numTicks) {
		addFuel(fuel, new ItemStack[maxLengthByproduct], RFPerTick, numTicks);
	}
	
	/**
	 * A loop into {@link #addFuel(ItemStack[], ItemStack[], int, int) addFuel} method.
	 * @param fuel The fuel ItemStack you want to add.
	 * @param byproducts The ItemStack for the fuel byproduct.
	 * @param RFPerTick RF to be generated per tick.
	 * @param numTicks The amount of time this fuel should last in ticks(20 ticks = 1 second).
	 */
	public void addFuel(ItemStack fuel, ItemStack byproducts, int RFPerTick, int numTicks) {
		addFuel(new ItemStack[]{fuel}, new ItemStack[]{byproducts}, RFPerTick, numTicks);
	}
	
	/**
	 * A loop into {@link #addFuel(ItemStack[], int, int) addFuel} method.
	 * @param fuel The fuel ItemStack you want to add.
	 * @param RFPerTick RF to be generated per tick.
	 * @param numTicks The amount of time this fuel should last in ticks(20 ticks = 1 second).
	 */
	public void addFuel(ItemStack fuel, int RFPerTick, int numTicks) {
		addFuel(new ItemStack[]{fuel}, RFPerTick, numTicks);
	}
	
	/**
	 * The base for adding fuels and other data.
	 * @param fuel The ItemStacks for the fuel, can be any size as long as it is below A loop into {@link #maxLengthInput} method..
	 * @param byproducts The ItemStacks for the fuel byproducts, can be any size as long as it is below {@link #maxLengthByproduct}.
	 * @param RFPerTick RF to be generated per tick.
	 * @param numTicks The amount of time this fuel should last in ticks(20 ticks = 1 second).
	 */
	public void addFuel(ItemStack[] fuel, ItemStack[] byproducts, int RFPerTick, int numTicks) {
		if (fuel.length != maxLengthInput) {
			ItemStack[] cache = new ItemStack[maxLengthInput];
			for (int i = 0; i < maxLengthInput; i++) {
				if (fuel.length > i) {
					cache[i] = fuel[i];
				} else cache[i] = null;
			}
			fuel = cache;
		}
		List<Map<Integer, ItemStack>> possibliities = getCombinations((int) Math.pow(2, maxLengthInput), getMapFromArray(fuel));
		for (int i = 0; i < possibliities.size(); i++) {
			ModLogger.logInfoMessage("List: " + possibliities.get(i));
			if (possibliities.get(i).size() == maxLengthInput) {
				ItemStack[] newFuel = getItemStacksFromMap(possibliities.get(i));
				if (newFuel != null) {
					ModLogger.logInfoMessage("Adding: " + newFuel[0] + " " + possibliities.size() + " " + RFPerTick + " " + numTicks);
					byproductsMap.put(byproductsMap.size(), getMapFromArray(byproducts));
					int[] byproductStacksizes = new int[byproducts.length];
					for (int j = 0; j < byproductStacksizes.length; j++) {
						byproductStacksizes[j] = byproducts[j].stackSize;
					}
					byproductsStacksizes.put(byproductsMap.size() - 1, byproductStacksizes);
					fuels.put(byproductsMap.size() - 1, getMapFromArray(newFuel));
					RFPerTickMap.put(byproductsMap.size() - 1, RFPerTick);
					genTicksMap.put(byproductsMap.size() - 1, numTicks);
				}
			}
		}
	}
	
	/**
	 * Takes the Map passed into this method and makes a list for all combinations of it's size.
	 * @param combinations The max amount of combinations for it to return(should include repeats).
	 * @param current The base Map of ItemStacks
	 * @return A list of all possible combinations of current.
	 */
	public List<Map<Integer, ItemStack>> getCombinations(int combinations, Map<Integer, ItemStack> current) {
		List<Map<Integer, ItemStack>> returns = new ArrayList<Map<Integer, ItemStack>>();
		for (int combo = 0;  combo < combinations; combo++) {
			if (combo == 0) {
				returns.add(current);
				continue;
			}
			Map<Integer, ItemStack> newMap = new HashMap<Integer, ItemStack>();
			int position = 0;
			if (combo > 0 && combo < maxLengthInput) {
				for (position = 0; position < current.size(); position++) {
					newMap.put(getCorrectedPosition(maxLengthInput, position + combo), current.get(position));
				}
				returns.add(newMap);
			}
			if (combo >= maxLengthInput) {
				for (int i = 0; i < current.size(); i++) {
					for (int j = 0; j < current.size(); j++) {
						if (i != j) {
							Map<Integer, ItemStack> newM = new HashMap<Integer, ItemStack>();
							ItemStack stack1 = current.get(i);
							ItemStack stack2 = current.get(j);
							newM.put(i, stack2);
							newM.put(j, stack1);
							for (int k = 0; k < current.size(); k++) {
								if (newM.containsKey(k)) continue;
								newM.put(k, current.get(k));
							}
							returns.add(newM);
						}
					}
				}
			}
		}
		return removeRepeats(returns);
	}
	
	/**
	 * Used to remove all repeats from current.
	 * @param current The List of a Map that should be 
	 * @return The new Map with repeats removed.
	 */
	private List<Map<Integer, ItemStack>> removeRepeats(List<Map<Integer, ItemStack>> current) {
		List<Map<Integer, ItemStack>> returns = new ArrayList<Map<Integer, ItemStack>>();
		for (Map<Integer, ItemStack> stacks : current) {
			if (returns.contains(stacks)) continue;
			returns.add(stacks);
		}
		return returns;
	}
	
	/**
	 * Used to correct the position of {@link #getCombinations(int, Map<Integer, ItemStack>) getCombinations} method.
	 * @param max The maximum position size.
	 * @param passed The integer to be corrected.
	 * @return The new integer that has been corrected.
	 */
	private int getCorrectedPosition(int max, int passed) {
		if (passed < max) return passed;
		if (passed == max) return 0;
		while (passed > max) passed -= max;
		return passed;
	}
	
	/**
	 * Takes in a Map of ItemStacks and turns it into an array of ItemStacks.
	 * @param stacks The map to be converted.
	 * @return The new array, made from a map.
	 */
	private ItemStack[] getItemStacksFromMap(Map<Integer, ItemStack> stacks) {
		ItemStack[] returns = new ItemStack[stacks.size()];
		for (int i = 0; i < stacks.size(); i++) {
			returns[i] = stacks.get(i);
		}
		return returns;
	}
	
	/**
	 * Takes in an array of ItemStacks and turns it into a Map of ItemStacks.
	 * @param stacks The array to be converted.
	 * @return The new Map, made from the array.
	 */
	private Map<Integer, ItemStack> getMapFromArray(ItemStack[] stacks) {
		Map<Integer, ItemStack> returns = new HashMap<Integer, ItemStack>(stacks.length);
		for (int i = 0; i < stacks.length; i++) {
			returns.put(i, stacks[i]);
		}
		return returns;
	}
	
	/**
	 * Gets the Byproducts from the passed fuel.
	 * @param stacks The fuel ItemStacks.
	 * @return The Byproducts linked to the fuel.
	 */
	public ItemStack[] getByproducts(ItemStack[] stacks) {
		int index = getValidPosition(stacks);
		if (index >= 0 && index < byproductsMap.size()) return getItemStacksFromMap(byproductsMap.get(index));
		return null;
	}
	
	/**
	 * An array of Integers containing the RFPerTick and the time in ticks.
	 * @param stacks The fuel to get the data from.
	 * @return An array containing the RFPerTick then the time in Ticks.
	 */
	public int[] getRFTicksAndPerTickForFuel(ItemStack[] stacks) {
		int[] RFGen = new int[2];
		int index = getValidPosition(stacks);
		if (index >= 0 && index < byproductsMap.size()) {
			RFGen[0] = RFPerTickMap.get(index);
			RFGen[1] = genTicksMap.get(index);
		}
		return RFGen;
	}
	
	/**
	 * Grabs the valid position from the passed fuel stacks.
	 * @param stacks The fuel to get the position from.
	 * @return The position of the fuel in the maps.
	 */
	public int getValidPosition(ItemStack[] stacks) {
		for (int i = 0; i < byproductsMap.size(); i++) {
			ItemStack[] key = getItemStacksFromMap(fuels.get(i));
			boolean itemStacksEqual = true;
			for (int j = 0; j < stacks.length; j++) {
				if (!containsNull(stacks) && !containsNull(key)) {
					if (!areItemStacksEqual(stacks[j], key[j], true)) {
						itemStacksEqual = false;
					}
				} else if (containsNull(stacks) && containsNull(key)) {
					if (!areItemStacksEqual(stacks[j], key[j], false)) {
						itemStacksEqual = false;
					}
				} else {
					itemStacksEqual = false;
				}
				if (!itemStacksEqual) j = stacks.length;
			}
			if (itemStacksEqual) return i;
		}
		return -1;
	}
	
	/**
	 * Checks to see if the Items and meta are the same, ignores the NBT data of the Stacks.
	 * @param parItemStack1 The first stack to check.
	 * @param parItemStack2 The second stack to check.
	 * @return If the two passed Stacks are the same.
	 */
    protected boolean areItemStacksEqual(ItemStack parItemStack1, ItemStack parItemStack2) {	
    	if (parItemStack1 != null && parItemStack1.getItem() != null && parItemStack2 != null && parItemStack2.getItem() != null)
    		return parItemStack2.getItem() == parItemStack1.getItem() && (parItemStack2.getMetadata() == 32767 || parItemStack2.getMetadata() == parItemStack1.getMetadata());
    	return parItemStack1 == null && parItemStack2 == null ? true : false;
    }
    
    /**
     * Checks to see if the Items and meta are the same, ignores the NBT data of the Stacks, if strict compares the stack sizes.
     * @param parItemStack1 The first stack to check.
     * @param parItemStack2 The second stack to check.
     * @param isStrict Whether it should check if parItemStack1's stack size is greater than parItemStack2's stack size.
     * @return If the two passed Stacks are the same.
     */
    public boolean areItemStacksEqual(ItemStack parItemStack1, ItemStack parItemStack2, boolean isStrict) {
    	if (!isStrict) return (areItemStacksEqual(parItemStack1, parItemStack2));
    	if (parItemStack1 != null && parItemStack2 != null && isStrict) return (areItemStacksEqual(parItemStack1, parItemStack2) && parItemStack1.stackSize >= parItemStack2.stackSize);
    	return false;
    }
    
    /**
     * Grabs the keys stack sizes from the passed fuel.
     * @param stacks The fuel to get the stack sizes from.
     * @return The stack sizes from fuel in the fuel maps.
     */
    public int[] getKeyStackSizes(ItemStack[] stacks) {
    	int[] stacksizes = new int[stacks.length];
    	int index = getValidPosition(stacks);
    	for (int i = 0; i < stacks.length; i++) {
    		stacksizes[i] = 0;
    	}
    	ItemStack[] keys = getItemStacksFromMap(this.fuels.get(index));
    	for (int i = 0; i < keys.length; i++) {
    		if (keys[i] != null) stacksizes[i] = keys[i].stackSize;
    	}
    	return stacksizes;
    }
    
    /**
     * Grabs the byproducts stack sizes from the passed fuel.
     * @param stacks The fuel to get the stack sizes from.
     * @return The stack sizes from fuel in the byproduct map.
     */
    public int[] getByproductStacksizes(ItemStack[] stacks) {
    	int[] returns = new int[stacks.length];
    	int index = getValidPosition(stacks);
    	for (int i = 0; i < stacks.length; i++) {
    		returns[i] = 0;
    	}
    	int[] stacksizes = this.byproductsStacksizes.get(index);
    	for (int i = 0; i < stacksizes.length; i++) {
    		returns[i] = stacksizes[i];
    	}
    	return stacksizes;
    }
    
    /**
     * Used to get if the passed stack is a fuel.
     * @param stack The stack to check if it is in {@link #fuels}.
     * @return If the stack is part of a fuel recipe.
     */
    public boolean isPartOfRecipe(ItemStack stack) {
    	for (Entry<Integer, Map<Integer, ItemStack>> entry : this.fuels.entrySet()) {
    		for (ItemStack key : getItemStacksFromMap(entry.getValue()))
    			if (this.areItemStacksEqual(stack, key)) {
    				return true;
    			}
    	}
    	return false;
    }
    
    /**
     * Used to check if the passed array contains null.
     * @param stacks The array to check.
     * @return If the passed array contains null.
     */
    private boolean containsNull(ItemStack[] stacks) {
    	Map<Integer, ItemStack> stackMap = new HashMap<Integer, ItemStack>();
    	for (int i = 0; i < stacks.length; i++) {
    		stackMap.put(i, stacks[i]);
    	}
    	return stackMap.containsValue(null);
    }
	
    /**
     * The basic instance of these fuels.
     * @return {@link #instance}.
     */
    public static FuelRecipesBase instance() {
    	return instance;
    }
    
}
