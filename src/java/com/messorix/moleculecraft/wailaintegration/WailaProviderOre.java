package com.messorix.moleculecraft.wailaintegration;

import java.util.List;
import java.util.Map.Entry;

import com.anime.rf.network.EnergyNetwork;
import com.anime.rf.network.EnergyNetworkProvider;
import com.messorix.moleculecraft.base.classes.ModMolecule;
import com.messorix.moleculecraft.base.classes.ModMolecules;
import com.mojang.realmsclient.gui.ChatFormatting;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer.EnumChatVisibility;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WailaProviderOre implements IWailaDataProvider {

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP arg0, TileEntity arg1, NBTTagCompound arg2, World arg3, BlockPos arg4) {
		return null;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		for (Entry<ItemStack, ModMolecule> entry : ModMolecules.objectMolecules.entrySet()) {
			if (areItemStacksEqual(itemStack, entry.getKey())) {
				ModMolecule molecule = entry.getValue();
				currenttip.add((molecule.getAmount() > 0 && !molecule.toString().isEmpty()) ? "Molecule: " + molecule.toString() : "");
			}
		}
		if ((accessor.getWorld().getCapability(EnergyNetworkProvider.ENERGY_NETWORK_CAPABILITY, null).networksContainingPos(accessor.getPosition()).size() > 0)) {
			List<EnergyNetwork> nets = accessor.getWorld().getCapability(EnergyNetworkProvider.ENERGY_NETWORK_CAPABILITY, null).networksContainingPos(accessor.getPosition());
			for (int i = 0; i < nets.size(); i++) {
				EnergyNetwork net = nets.get(i);
				currenttip.add(ChatFormatting.WHITE + "Network: " + (net.storage.getEnergyStored() + " / " + net.storage.getMaxEnergyStored()) + " RF");
			}
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaHead(ItemStack arg0, List<String> arg1, IWailaDataAccessor arg2, IWailaConfigHandler arg3) {
		// TODO Auto-generated method stub
		return arg1;
	}

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor arg0, IWailaConfigHandler arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getWailaTail(ItemStack arg0, List<String> arg1, IWailaDataAccessor arg2, IWailaConfigHandler arg3) {
		// TODO Auto-generated method stub
		return arg1;
	}

    private boolean areItemStacksEqual(ItemStack parItemStack1, ItemStack parItemStack2) {
        return parItemStack2.getItem() == parItemStack1.getItem() && (parItemStack2.getMetadata() == 32767 || parItemStack2.getMetadata() == parItemStack1.getMetadata());
    }
	
}
