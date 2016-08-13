package com.messorix.moleculecraft.base;
import com.anime.basic.recipies.RecipeHelper;

public class ModRecipes 
{
	public void registerRecipes()
    {
		RecipeHelper.addSmeltingRecipe(ModItems.COPPER_DUST, 0, ModItems.COPPER_INGOT, 1, 0, 0.7F);
		RecipeHelper.addCraftingRecipe(ModItems.LITHIUM_ION_BATTERY, 1, 0,
				RecipeHelper.getShapedCrafting(new String[]{"   ", "G L", "   "},
						new char[]{'G', 'L'},
						new Object[]{ModItems.GRAPHITE, ModItems.LMO_ELECTRODE}),
				false);
    }
}