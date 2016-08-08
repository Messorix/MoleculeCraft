package com.messorix.moleculecraft.base.crafting;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipeRegister 
{
	public static void registerProcessingRecipe(Block output, ItemStack input, float exp)
	{
		GameRegistry.addSmelting(output, input, exp);
	}
	
	public static void registerProcessingRecipe(Item output, ItemStack input, float exp)
	{
		GameRegistry.addSmelting(output, input, exp);
	}
	
	public static void registerProcessingRecipe(ItemStack output, ItemStack input, float exp)
	{
		GameRegistry.addSmelting(output, input, exp);
	}
}