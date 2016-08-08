package com.messorix.moleculecraft.base.crafting;

import com.messorix.moleculecraft.base.ModItems;

import net.minecraft.item.ItemStack;

public class FluxFurnaceRecipes extends ModRecipes
{
    private static final FluxFurnaceRecipes smeltingBase = new FluxFurnaceRecipes();

    public static FluxFurnaceRecipes instance()
    {
        return smeltingBase;
    }

    private FluxFurnaceRecipes()
    {
        addProcessingRecipe(
                new ItemStack(ModItems.COPPERDUST), 
                new ItemStack(ModItems.COPPERINGOT), 0.7F);
    }
}