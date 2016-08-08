package com.messorix.moleculecraft.base.blocks;

import com.google.common.collect.Maps;
import com.messorix.moleculecraft.base.MoleculecraftBase;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.Map;

public abstract class BlockOre extends Block
{
	public static String NAME;
	public static Map MOLECULE = Maps.newHashMap();
	
    public BlockOre(String unlocalizedName, String registryName, Material material) 
    {
    	super(material);
    	
    	NAME = unlocalizedName;
        this.setUnlocalizedName(unlocalizedName);
        this.setCreativeTab(MoleculecraftBase.moleculeCraftTab);
        
		setHardness(3F);
		setResistance(5F);
	}
}
