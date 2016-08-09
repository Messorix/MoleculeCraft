package com.messorix.moleculecraft.base;

import java.util.ArrayList;
import java.util.List;

import com.messorix.moleculecraft.base.items.ItemCopperDust;
import com.messorix.moleculecraft.base.items.ItemCopperIngot;
import com.messorix.moleculecraft.base.items.ModItem;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems 
{
	public static ModItem COPPERINGOT;
	public static ModItem COPPERDUST;
    public static List<ModItem> itemlist = new ArrayList<ModItem>();
    
    @SuppressWarnings("deprecation")
	public void registerItems()
    {
		GameRegistry.registerItem(COPPERINGOT = new ItemCopperIngot(), ItemCopperIngot.NAME);
		itemlist.add(COPPERINGOT);
		GameRegistry.registerItem(COPPERDUST = new ItemCopperDust(), ItemCopperDust.NAME);
		itemlist.add(COPPERDUST);
    }
    
    public void setItemModels()
    {
    	for (ModItem item:itemlist)
    	{
    		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5)));
    	}
    }
}