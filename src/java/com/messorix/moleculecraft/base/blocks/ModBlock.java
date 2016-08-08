package com.messorix.moleculecraft.base.blocks;

import com.messorix.moleculecraft.base.MoleculecraftBase;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public abstract class ModBlock extends Block
{
	public static String NAME;
	
    public ModBlock(String unlocalizedName, String registryName, Material material)
    {
    	super(material);
    	
    	NAME = unlocalizedName;
        this.setUnlocalizedName(unlocalizedName);
        this.setCreativeTab(MoleculecraftBase.moleculeCraftTab);
    }

	public int getLightValue(IBlockAccess world, BlockPos pos) {
		return 0;
	}
	
	public boolean isNormalCube(IBlockState state)
	{
		return false;
	}

	public boolean isFullCube(IBlockState state) {
		return false;
	}
}