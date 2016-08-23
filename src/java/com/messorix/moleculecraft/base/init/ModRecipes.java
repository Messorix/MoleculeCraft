package com.messorix.moleculecraft.base.init;
import com.anime.basic.recipies.RecipeHelper;

public class ModRecipes 
{
	public void registerRecipes()
    {
		RecipeHelper.addSmeltingRecipe(ModItems.COPPER_DUST, 0, ModItems.COPPER_INGOT, 1, 0, 0.7F);
		RecipeHelper.addCraftingRecipe(ModItems.LITHIUM_ION_BATTERY, 1, 0,
				RecipeHelper.getShapedCrafting(new String[]{"CCC", "G L", "CCC"},
						new char[]{'G', 'L', 'C'},
						new Object[]{ModItems.GRAPHITE, ModItems.LMO_ELECTRODE, ModItems.LIPO_CASING}),
				false);
    }
}