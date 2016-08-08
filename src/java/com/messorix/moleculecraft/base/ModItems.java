package com.messorix.moleculecraft.base;

import java.util.ArrayList;
import java.util.List;

import com.messorix.moleculecraft.base.items.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
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
    	RenderItem renderitem = Minecraft.getMinecraft().getRenderItem();
    	
    	for (ModItem item:itemlist)
    	{
    		renderitem.getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5)));
    		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5)));
    	}
    }
}