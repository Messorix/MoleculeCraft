package com.messorix.moleculecraft.base.crafting;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.messorix.moleculecraft.base.ModBlocks;
import com.messorix.moleculecraft.base.ModItems;

import net.minecraft.item.ItemStack;

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
        addProcessingRecipe(new ItemStack(ModItems.COPPER_DUST),  new ItemStack(ModItems.COPPER_INGOT), 0.7F);
        addProcessingRecipe(new ItemStack[]{new ItemStack(ModItems.COPPER_INGOT), new ItemStack(ModItems.COPPER_DUST)}, new ItemStack(ModBlocks.CHALCOCITE_ORE), 1F);
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
    			if (this.areItemStacksEqual(stacks[0], entry.getKey()[0]) && this.areItemStacksEqual(stacks[1], entry.getKey()[1]))
    			{
    				return (ItemStack)entry.getValue();
    			}
    		} else if (containsNull(stacks) && containsNull(entry.getKey())){
    			if (this.areItemStacksEqual(stacks[0], entry.getKey()[0]) || this.areItemStacksEqual(stacks[1], entry.getKey()[1])) {
    				return (ItemStack) entry.getValue();
    			}
    		}
    	}
		return null;
    }
    
    @Override
    protected boolean areItemStacksEqual(ItemStack parItemStack1, ItemStack parItemStack2) {
    	if (parItemStack1 != null && parItemStack2 != null) return super.areItemStacksEqual(parItemStack1, parItemStack2);
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