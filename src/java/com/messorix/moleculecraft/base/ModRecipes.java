package com.messorix.moleculecraft.base;
import com.anime.basic.recipies.RecipeHelper;

public class ModRecipes 
{
	public void registerRecipes()
    {
		RecipeHelper.addSmeltingRecipe(ModItems.COPPERDUST, 0, ModItems.COPPERINGOT, 1, 0, 0.7F);
		RecipeHelper.addCraftingRecipe(ModItems.LITHIUMIONBATTERY, 1, 0,
				RecipeHelper.getShapedCrafting(new String[]{"   ", "G L", "   "},
						new char[]{'G', 'L'},
						new Object[]{ModItems.GRAPHITE, ModItems.LMOELECTRODE}),
				false);
    }
}