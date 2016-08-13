package com.messorix.moleculecraft.base.crafting;

import com.messorix.moleculecraft.base.ModBlocks;
import com.messorix.moleculecraft.base.ModItems;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FluxGrinderRecipes extends ModRecipes
{
    private static final FluxGrinderRecipes grindingBase = new FluxGrinderRecipes();
    /** The list of grinding results. */

    public static FluxGrinderRecipes instance()
    {
        return grindingBase;
    }
    
    private FluxGrinderRecipes()
    {
        addProcessingRecipe(
                new ItemStack(Item.getItemFromBlock(ModBlocks.CHALCOCITE_ORE)), 
                new ItemStack(ModItems.COPPER_DUST, 2), 0.7F);
        addProcessingRecipe(
                new ItemStack(ModItems.COPPER_INGOT), 
                new ItemStack(ModItems.COPPER_DUST), 0.7F);
    }
}