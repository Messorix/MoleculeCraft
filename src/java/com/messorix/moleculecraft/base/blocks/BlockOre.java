package com.messorix.moleculecraft.base.blocks;

import com.messorix.moleculecraft.base.ModAtoms;
import com.messorix.moleculecraft.base.MoleculecraftBase;
import com.messorix.moleculecraft.base.classes.ModMolecule;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public abstract class BlockOre extends Block
{
	public static String NAME;
	public ModMolecule MOLECULE = (ModMolecule) new ModMolecule().setAmount(1);
	public ModAtoms modAtoms = new ModAtoms();
	
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