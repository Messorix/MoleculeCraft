package com.messorix.moleculecraft.wailaintegration;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.messorix.moleculecraft.base.ModAtoms;
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

public class WailaProviderVanillaOre implements IWailaDataProvider {

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP arg0, TileEntity arg1, NBTTagCompound arg2, World arg3,
			BlockPos arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		Block ore = (Block) accessor.getBlock();
		String shortend = "Molecule: ";
		
		Map<ModAtom, Integer> molecule = Maps.newHashMap();

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
    	}
		
		for (Map.Entry<ModAtom, Integer> entry : molecule.entrySet())
		{
			if (entry.getValue() > 1)
				shortend += entry.getKey().getSymbol() + entry.getValue();
			else
				shortend += entry.getKey().getSymbol();
		}
			
		currenttip.add(shortend);
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
