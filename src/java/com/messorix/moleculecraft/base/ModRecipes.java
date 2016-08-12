package com.messorix.moleculecraft.base;
import com.anime.basic.recipies.RecipeHelper;
import com.messorix.moleculecraft.base.items.ItemLithiumIonBattery;

public class ModRecipes 
{
	public void registerRecipes()
    {
		RecipeHelper.addSmeltingRecipe(ModItems.COPPERDUST, 0, ModItems.COPPERINGOT, 1, 0, 0.7F);
		RecipeHelper.addCraftingRecipe(new ItemLithiumIonBattery().setOutputPerTick(ModItems.LCOELECTRODE),
										1, 0, new String[]{" G ", "  ", " L "}, false);
    }
}