package com.messorix.moleculecraft.base;

import java.util.ArrayList;
import java.util.List;

import com.messorix.moleculecraft.base.blocks.BlockChalcociteOre;
import com.messorix.moleculecraft.base.blocks.BlockFluxFurnace;
import com.messorix.moleculecraft.base.blocks.BlockFluxGrinder;
import com.messorix.moleculecraft.base.blocks.BlockMachine;
import com.messorix.moleculecraft.base.blocks.BlockOre;
import com.messorix.moleculecraft.base.blocks.ModBlock;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks 
{
	public static BlockMachine FLUXFURNACE;
	public static BlockMachine FLUXFURNACEWORKING;
	public static BlockMachine FLUXGRINDER;
	public static BlockMachine FLUXGRINDERWORKING;
	
	public static BlockOre CHALCOCITEORE;

    public static List<ModBlock> modblocklist = new ArrayList<ModBlock>();
    public static List<BlockOre> oreblocklist = new ArrayList<BlockOre>();
    
    @SuppressWarnings("deprecation")
	public void registerBlocks()
    {
		GameRegistry.registerBlock(CHALCOCITEORE = new BlockChalcociteOre(), BlockOre.NAME);
		oreblocklist.add(CHALCOCITEORE);
		
		GameRegistry.registerBlock(FLUXGRINDER = new BlockFluxGrinder(), BlockFluxGrinder.NAME);
		modblocklist.add(FLUXGRINDER);
		
		GameRegistry.registerBlock(FLUXFURNACE = new BlockFluxFurnace(), BlockFluxFurnace.NAME);
		modblocklist.add(FLUXFURNACE);
    }
    
    public void setItemModels()
    {
    	RenderItem renderitem = Minecraft.getMinecraft().getRenderItem();
    	
    	for (ModBlock block:modblocklist)
    	{
    		renderitem.getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(Reference.MOD_ID + ":" + block.getUnlocalizedName().substring(5)));
    		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(Reference.MOD_ID + ":" + block.getUnlocalizedName().substring(5)));
    	}

    	for (BlockOre block:oreblocklist)
    	{
    		renderitem.getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(Reference.MOD_ID + ":" + block.getUnlocalizedName().substring(5)));
    		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(Reference.MOD_ID + ":" + block.getUnlocalizedName().substring(5)));
    	}
    }
}