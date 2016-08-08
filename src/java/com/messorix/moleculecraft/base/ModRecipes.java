package com.messorix.moleculecraft.base;
import com.messorix.moleculecraft.base.crafting.ModRecipeRegister;

import net.minecraft.item.ItemStack;

public class ModRecipes 
{
	public void registerRecipes()
    {
		ModRecipeRegister.registerProcessingRecipe(ModItems.COPPERDUST, new ItemStack(ModItems.COPPERINGOT), 0.7F);
    }
}