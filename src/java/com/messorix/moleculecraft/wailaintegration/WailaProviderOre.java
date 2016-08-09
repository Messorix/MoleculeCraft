package com.messorix.moleculecraft.wailaintegration;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.messorix.moleculecraft.base.ModAtoms;
import com.messorix.moleculecraft.base.blocks.BlockOre;
import com.messorix.moleculecraft.base.classes.ModAtom;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WailaProviderOre implements IWailaDataProvider {

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP arg0, TileEntity arg1, NBTTagCompound arg2, World arg3,
			BlockPos arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		try
		{
			Block ore;
			Map<ModAtom, Integer> molecule;
			String shortend = "";
			
			String classlocation = accessor.getBlock().getClass().toString();
			
			if (classlocation.contains("messorix"))
			{
					ore = (BlockOre) accessor.getBlock();
					molecule = ((BlockOre)ore).MOLECULE;
			}
			else
			{
				ore = (Block) accessor.getBlock();
				molecule = Maps.newLinkedHashMap();
		    	switch (ore.getUnlocalizedName())
		    	{
			    	case "tile.oreIron":
			    		molecule.put(ModAtoms.getModAtomBySymbol("Fe"), 1);
			    		break;
			    	case "tile.oreGold":
			    		molecule.put(ModAtoms.getModAtomBySymbol("Au"), 1);
			    		break;
			    	case "tile.oreCoal":
			    		molecule.put(ModAtoms.getModAtomBySymbol("C"), 1);
			    		break;
			    	case "tile.oreEmerald":
			    		molecule.put(ModAtoms.getModAtomBySymbol("Be"), 3);
			    		molecule.put(ModAtoms.getModAtomBySymbol("Al"), 2);
			    		molecule.put(ModAtoms.getModAtomBySymbol("("), 1);
			    		molecule.put(ModAtoms.getModAtomBySymbol("Si"), 1);
			    		molecule.put(ModAtoms.getModAtomBySymbol("O"), 3);
			    		molecule.put(ModAtoms.getModAtomBySymbol(")"), 1);
			    		//molecule.put(ModAtoms.getModAtomBySymbol(""), 6);
			    		break;
		    	}
			}		
	
			
			for (Map.Entry<ModAtom, Integer> entry : molecule.entrySet())
			{
				if (entry.getValue() > 1)
					shortend += entry.getKey().getSymbol() + entry.getValue();
				else
					shortend += entry.getKey().getSymbol();
			}
				
			if (!shortend.isEmpty())
				shortend = "Molecule: " + shortend;
			
			currenttip.add(shortend);
		}
		catch (ClassCastException e)
		{}
		return currenttip;
	}

	@Override
	public List<String> getWailaHead(ItemStack arg0, List<String> arg1, IWailaDataAccessor arg2,
			IWailaConfigHandler arg3) {
		// TODO Auto-generated method stub
		return arg1;
	}

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor arg0, IWailaConfigHandler arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getWailaTail(ItemStack arg0, List<String> arg1, IWailaDataAccessor arg2,
			IWailaConfigHandler arg3) {
		// TODO Auto-generated method stub
		return arg1;
	}

}
