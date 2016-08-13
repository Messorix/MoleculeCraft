package com.messorix.moleculecraft.base.blocks;

import com.messorix.moleculecraft.base.MoleculecraftBase;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public abstract class BlockOre extends Block
{
	public static String NAME;
	
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