package com.messorix.moleculecraft.wailaintegration;

import java.util.List;

import com.messorix.moleculecraft.base.ModAtoms;
import com.messorix.moleculecraft.base.blocks.BlockOre;
import com.messorix.moleculecraft.base.classes.ModAtom;
import com.messorix.moleculecraft.base.classes.ModMolecule;

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
		return null;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {

		
		try {
			Block ore = (Block) accessor.getBlock();
			ModMolecule molecule = new ModMolecule();
			ModAtoms modAtoms = new ModAtoms();
			String classlocation = accessor.getBlock().getClass().toString();

			if (classlocation.contains("messorix"))
				molecule = ((BlockOre) ore).MOLECULE;
			else {

				switch (ore.getUnlocalizedName()) {
				case "tile.oreIron":

					molecule = (ModMolecule) new ModMolecule().setAmount(1);
					molecule.addAtom((ModAtom) modAtoms.getModAtomBySymbol("Fe").setAmount(1));

					break;
				case "tile.oreGold":

					molecule = (ModMolecule) new ModMolecule().setAmount(1);
					molecule.addAtom((ModAtom) modAtoms.getModAtomBySymbol("Au").setAmount(1));

					break;
				case "tile.oreCoal":

					molecule = (ModMolecule) new ModMolecule().setAmount(1);
					molecule.addAtom((ModAtom) modAtoms.getModAtomBySymbol("C").setAmount(1));

					break;
				case "tile.oreEmerald":

					molecule = (ModMolecule) new ModMolecule().setAmount(1);
					molecule.addAtom((ModAtom) modAtoms.getModAtomBySymbol("Be").setAmount(3));
					molecule.addAtom((ModAtom) modAtoms.getModAtomBySymbol("Al").setAmount(2));

					ModMolecule SiMolecule = (ModMolecule) new ModMolecule().setAmount(6);
					SiMolecule.addAtom((ModAtom) modAtoms.getModAtomBySymbol("Si").setAmount(1));
					SiMolecule.addAtom((ModAtom) modAtoms.getModAtomBySymbol("O").setAmount(3));

					molecule.addMolecule(SiMolecule);

					break;
				}
			}

			currenttip.add((molecule.getAmount() > 0 && !molecule.toString().isEmpty())
					? "Molecule: " + molecule.toString() : "");
			
		} catch (ClassCastException e) {
			// Ignore
		}
		
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
