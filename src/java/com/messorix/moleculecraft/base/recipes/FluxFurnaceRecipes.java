package com.messorix.moleculecraft.base.recipes;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.messorix.moleculecraft.base.init.ModBlocks;
import com.messorix.moleculecraft.base.init.ModItems;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class FluxFurnaceRecipes extends ModRecipes
{
	private static final FluxFurnaceRecipes smeltingBase = new FluxFurnaceRecipes();
	
	private final Map<ItemStack[], ItemStack> processingList = new HashMap<ItemStack[], ItemStack>();
	private final Map<ItemStack[], Float> xpList = new HashMap<ItemStack[], Float>();
	
    public static FluxFurnaceRecipes instance()
    {
        return smeltingBase;
    }

    private FluxFurnaceRecipes()
    {
    	// Adds vanilla furnace recipes to Flux Furnace Recipes if they don't already exist.
    	for (Entry<ItemStack, ItemStack> entry : FurnaceRecipes.instance().getSmeltingList().entrySet()) {
    		if (getProcessingResult(new ItemStack[]{null, entry.getKey()}) == null) {
    			addProcessingRecipe(entry.getKey(), entry.getValue(), FurnaceRecipes.instance().getSmeltingExperience(entry.getKey()));
    		}
    	}
    	addProcessingRecipe(new ItemStack(ModBlocks.CHALCOCITE_ORE),  new ItemStack(ModItems.COPPER_INGOT), 0.7F);
    	addProcessingRecipe(new ItemStack(ModBlocks.ACANTHITE_ORE), new ItemStack(ModItems.SILVER_INGOT), 0.9F);
    	addProcessingRecipe(new ItemStack(ModBlocks.CASSITERITE_ORE), new ItemStack(ModItems.TIN_INGOT), 0.7F);

    	addProcessingRecipe(new ItemStack(ModItems.COPPER_DUST),  new ItemStack(ModItems.COPPER_INGOT), 0.7F);
    	addProcessingRecipe(new ItemStack(ModItems.SILVER_DUST), new ItemStack(ModItems.SILVER_INGOT), 0.9F);
    	addProcessingRecipe(new ItemStack(ModItems.TIN_DUST), new ItemStack(ModItems.TIN_INGOT), 0.7F);

    	addProcessingRecipe(new ItemStack(ModItems.IRON_DUST), new ItemStack(Items.IRON_INGOT), 0.7F);
    	addProcessingRecipe(new ItemStack(ModItems.GOLD_DUST), new ItemStack(Items.GOLD_INGOT), 0.9F);

    	// TODO Add alloys
    	 addProcessingRecipe(new ItemStack[]{new ItemStack(ModItems.COPPER_INGOT, 3), new ItemStack(ModItems.COPPER_DUST)}, new ItemStack(ModBlocks.CHALCOCITE_ORE, 2), 1F);
    }
    
    public void addProcessingRecipe(ItemStack[] stacksRequired, ItemStack result, float xp) {
    	processingList.put(new ItemStack[]{stacksRequired[0], stacksRequired[1]}, result);
    	processingList.put(new ItemStack[]{stacksRequired[1], stacksRequired[0]}, result);
    	xpList.put(new ItemStack[]{stacksRequired[0], stacksRequired[1]}, xp);
    	xpList.put(new ItemStack[]{stacksRequired[1], stacksRequired[0]}, xp);
    }
    
    @Override
    public void addProcessingRecipe(ItemStack parItemStackIn, ItemStack parItemStackOut, float parExperience) {
    	addProcessingRecipe(new ItemStack[]{null, parItemStackIn}, parItemStackOut, parExperience);
    }
    
    public ItemStack getProcessingResult(ItemStack[] stacks) {
    	for (Entry<ItemStack[], ItemStack> entry : this.processingList.entrySet())
    	{
    		if (!containsNull(entry.getKey()) && !containsNull(stacks)) {
    			if (this.areItemStacksEqual(stacks[0], entry.getKey()[0], true) && this.areItemStacksEqual(stacks[1], entry.getKey()[1], true))
    			{
    				return (ItemStack)entry.getValue();
    			}
    		} else if (containsNull(stacks) && containsNull(entry.getKey())) {
    			if (this.areItemStacksEqual(stacks[0], entry.getKey()[0]) || this.areItemStacksEqual(stacks[1], entry.getKey()[1])) {
    				return (ItemStack) entry.getValue();
    			}
    		} else {
    			if (this.areItemStacksEqual(stacks[0], stacks[1])) {
    				if (containsNull(entry.getKey()) && (areItemStacksEqual(entry.getKey()[0], stacks[0]) || areItemStacksEqual(entry.getKey()[1], stacks[0]))) {
    					return (ItemStack) entry.getValue();
    				}
    			}
    		}
    	}
		return null;
    }
    
    public int[] getKeyStackSizes(ItemStack[] stacks) {
    	int[] stacksizes = new int[stacks.length];
    	for (int i = 0; i < stacks.length; i++) {
    		stacksizes[i] = 0;
    	}
    	for (Entry<ItemStack[], ItemStack> entry : this.processingList.entrySet())
    	{
    		if (!containsNull(entry.getKey()) && !containsNull(stacks)) {
    			if (this.areItemStacksEqual(stacks[0], entry.getKey()[0], true) && this.areItemStacksEqual(stacks[1], entry.getKey()[1], true))
    			{
    				for (int i = 0; i < entry.getKey().length; i++) {
    					stacksizes[i] = entry.getKey()[i].stackSize;
    				}
    				return stacksizes;
    			}
    		}
    	}
		return stacksizes;
    }
    
    @Override
    public boolean areItemStacksEqual(ItemStack parItemStack1, ItemStack parItemStack2) {
    	if (parItemStack1 != null && parItemStack2 != null) return super.areItemStacksEqual(parItemStack1, parItemStack2);
    	return false;
    }
    
    @Override
    public boolean areItemStacksEqual(ItemStack parItemStack1, ItemStack parItemStack2, boolean isStrict) {
    	if (parItemStack1 != null && parItemStack2 != null && !isStrict) return (super.areItemStacksEqual(parItemStack1, parItemStack2, false));
    	if (parItemStack1 != null && parItemStack2 != null && isStrict) return (super.areItemStacksEqual(parItemStack1, parItemStack2, false) && parItemStack1.stackSize >= parItemStack2.stackSize);
    	return false;
    }
    
    public boolean isSingleInputRecipe(ItemStack[] stacks) {
    	for (Entry<ItemStack[], ItemStack> entry : this.processingList.entrySet()) {
    		if (getKeyStackSizes(stacks)[0] == 0 && getKeyStackSizes(stacks)[1] == 0) {
    			if (containsNull(entry.getKey()) && (areItemStacksEqual(entry.getKey()[0], stacks[0], false) || areItemStacksEqual(entry.getKey()[1], stacks[0]) || areItemStacksEqual(entry.getKey()[1], stacks[1]) || areItemStacksEqual(entry.getKey()[0], stacks[1]))) {
    				return entry.getValue() != null;
    			}
    		}
    	}
    	return false;
    }
    
    /**
     * For Flux Furnace use the ItemStack[] version.
     */
    @Override
    @Deprecated
    public ItemStack getProcessingResult(ItemStack stack) {
    	return null;
    }
    
    public boolean isPartOfRecipe(ItemStack stack) {
      	for (Entry<ItemStack[], ItemStack> entry : this.processingList.entrySet())
    	{
      		for (ItemStack key : entry.getKey())
    		if (this.areItemStacksEqual(stack, key))
    		{
    			return true;
    		}
    	}
		return false;
    }
    
    private boolean containsNull(ItemStack[] stacks) {
    	Map<Integer, ItemStack> stackMap = new HashMap<Integer, ItemStack>();
    	stackMap.put(0, stacks[0]);
    	stackMap.put(1, stacks[1]);
    	return stackMap.containsValue(null);
    }
    
}