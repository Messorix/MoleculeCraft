package com.messorix.moleculecraft.base.recipes;

import com.messorix.moleculecraft.base.init.ModBlocks;
import com.messorix.moleculecraft.base.init.ModItems;

import net.minecraft.init.Items;
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
        addProcessingRecipe(new ItemStack(ModBlocks.CHALCOCITE_ORE),  new ItemStack(ModItems.COPPER_DUST, 2), 0.7F);
        addProcessingRecipe(new ItemStack(ModBlocks.ACANTHITE_ORE), new ItemStack(ModItems.SILVER_DUST, 2), 0.9F);
        addProcessingRecipe(new ItemStack(ModBlocks.CASSITERITE_ORE), new ItemStack(ModItems.TIN_DUST, 2), 0.7F);
        
        addProcessingRecipe( new ItemStack(ModItems.COPPER_INGOT), new ItemStack(ModItems.COPPER_DUST), 0.7F);
        addProcessingRecipe( new ItemStack(ModItems.SILVER_INGOT), new ItemStack(ModItems.SILVER_DUST), 0.9F);
        addProcessingRecipe( new ItemStack(ModItems.TIN_INGOT), new ItemStack(ModItems.TIN_DUST), 0.7F);
        addProcessingRecipe(new ItemStack(Items.IRON_INGOT), new ItemStack(ModItems.IRON_DUST), 0.7F);
        addProcessingRecipe(new ItemStack(Items.GOLD_INGOT), new ItemStack(ModItems.GOLD_DUST), 0.9F);
        addProcessingRecipe(new ItemStack(Items.COAL, 1, 0), new ItemStack(ModItems.CARBON_DUST), 0.7F);
        addProcessingRecipe(new ItemStack(Items.COAL, 1, 1), new ItemStack(ModItems.CARBON_DUST), 0.7F);
        addProcessingRecipe(new ItemStack(Items.DIAMOND), new ItemStack(ModItems.DIAMOND_DUST), 1.3F);
    }
}