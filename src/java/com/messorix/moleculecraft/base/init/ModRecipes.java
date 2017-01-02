package com.messorix.moleculecraft.base.init;
import com.anime.basic.recipies.RecipeHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModRecipes 
{
	public void registerRecipes()
    {
		RecipeHelper.addSmeltingRecipe(ModItems.COPPER_DUST, 0, ModItems.COPPER_INGOT, 1, 0, 0.7F);
		
		ItemStack battery = new ItemStack(ModItems.LITHIUM_ION_BATTERY);
		
		battery.setTagCompound(batteryCompound("lmo", "lpf", "cho"));

		RecipeHelper.addCraftingRecipe(battery, 1, 0,
				RecipeHelper.getShapedCrafting(new String[]{"CCC", "GEL", "CCC"},
						new char[]{'G', 'E', 'L', 'C'},
						new Object[]{ModItems.GRAPHITE, ModItems.ELECTROLYTE, ModItems.LMO_ELECTRODE, ModItems.LIPO_CASING}),
				false);

		battery.setTagCompound(batteryCompound("lmo", "lpf", "och"));
		
		RecipeHelper.addCraftingRecipe(battery, 1, 0,
				RecipeHelper.getShapedCrafting(new String[]{"CCC", "GEL", "CCC"},
						new char[]{'G', 'E', 'L', 'C'},
						new Object[]{ModItems.GRAPHITE, ModItems.ELECTROLYTE, ModItems.LMO_ELECTRODE, ModItems.LIPO_CASING}),
				false);

		battery.setTagCompound(batteryCompound("lmo", "lpf", "oc"));
		
		RecipeHelper.addCraftingRecipe(battery, 1, 0,
				RecipeHelper.getShapedCrafting(new String[]{"CCC", "GEL", "CCC"},
						new char[]{'G', 'E', 'L', 'C'},
						new Object[]{ModItems.GRAPHITE, ModItems.ELECTROLYTE, ModItems.LMO_ELECTRODE, ModItems.LIPO_CASING}),
				false);
    }
	
	private NBTTagCompound batteryCompound(String posElectrode, String salt, String solvent) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("PositiveElectrode", posElectrode);
        tag.setString("Salt", salt);
        tag.setString("Solvent", solvent);
        return tag;
    }
}