package com.messorix.moleculecraft.base.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemLithiumIonBattery extends ModItemEnergy {

	public ItemLithiumIonBattery() {
		super("li_battery", "li_battery");
	}
	
	public ItemLithiumIonBattery setOutputPerTick(String positiveElectrode)
	{
		int outputRate = 0;
		int capacity = 0;

		if (positiveElectrode.equals("lmo"))
		{
			outputRate = 15;
			capacity = 25000;
		}
		else if (positiveElectrode.equals("lco"))
		{
			outputRate = 30;
			capacity = 50000;
		}
		else if (positiveElectrode.equals("lfp"))
		{
			outputRate = 60;
			capacity = 100000;
		}

		this.maxExtract = outputRate;
		this.capacity = capacity;
		return this;
	}
	
	public ItemLithiumIonBattery setInputPerTick(String salt, String solvent)
	{
		int inputRate = 0;

		if (salt.equals("lpf"))
		{
			inputRate = 10;
		}
		else if (salt.equals("lbf"))
		{
			inputRate = 20;
		}
		else if (salt.equals("lco"))
		{
			inputRate = 40;
		}

		if (solvent.equals("cho"))
		{
			inputRate += 5;
		}
		else if (solvent.equals("och"))
		{
			inputRate += 10;
		}
		else if (solvent.equals("oc"))
		{
			inputRate += 20;
		}

		this.maxReceive = inputRate;
		return this;
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) 
	{
		if (stack.getTagCompound() == null) {
			stack.setTagCompound(new NBTTagCompound());
		}
		
		String electrode = stack.getTagCompound().getString("Positive Electrode");
		setOutputPerTick(electrode);
		String salt = stack.getTagCompound().getString("Salt");
		String solvent = stack.getTagCompound().getString("Solvent");
		setInputPerTick(salt, solvent);
		
		super.onCreated(stack, worldIn, playerIn);
	}
}