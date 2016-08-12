package com.messorix.moleculecraft.base.containers;

import com.messorix.moleculecraft.base.crafting.FluxFurnaceRecipes;
import com.messorix.moleculecraft.base.tileentities.TileEntityFluxFurnace;
import com.messorix.moleculecraft.base.tileentities.TileEntityFluxGrinder;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerFluxFurnace extends ModContainer
{
	private TileEntityFluxFurnace tileEntityFluxFurnace;

	public final int fuel_slots = 1;
	public final int input_slots = 1;
	public final int output_slots = 2;
	
	public ContainerFluxFurnace(InventoryPlayer player, TileEntityFluxFurnace tileentity)
	{
		super(player, tileentity);
		
		tileEntityFluxFurnace = tileentity;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot)this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			// If item is in the TE's inventory
			if (par2 >= vanilla_slots && par2 <  first_output_index + output_slots)
			{
				// Try to place in player inventory / action bar
				if (!this.mergeItemStack(itemstack1, first_vanilla_index, vanilla_slots, false))
				{
					return null;
				}
			}
			// Item is in inventory / hotbar, try to place in the in TE if possible if not move in inventory
			else
			{
				// If item is a part of a recipe
				if (FluxFurnaceRecipes.instance().getProcessingResult(itemstack1) != null)
				{
					if (!this.mergeItemStack(itemstack1, first_input_index, first_input_index + input_slots, false))
					{
						return null;	
					}
				}
				// If item is fuel
				else if (TileEntityFluxGrinder.getItemBurnTime(itemstack1) > 0) {
					if (!this.mergeItemStack(itemstack1, first_fuel_index, first_fuel_index + fuel_slots, false))
					{
						return null;
					}
				}
				// Item in player's inventory, but not in action bar
				else if (par2 >= hotbar && par2 < vanilla_slots)
				{
					// place in action bar
					if (!this.mergeItemStack(itemstack1, first_vanilla_index, hotbar, false))
					{
						return null;
					}
				}
				// Item in action bar - place in player inventory
				else if (par2 >= 0 && par2 < 9)
				{
					if (!this.mergeItemStack(itemstack1, 9, 36, false))
					{
						return null;
					}
				}
			}

			if (itemstack1.stackSize == 0)
			{
				slot.putStack((ItemStack)null);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize)
			{
				return null;
			}

			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}

		return itemstack;
	}
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		
		boolean allFieldsHaveChanged = false;
		boolean fieldHasChanged [] = new boolean[this.tileEntityFluxFurnace.getFieldCount()];
		
		if (cachedFields == null)
		{
			cachedFields = new int[this.tileEntityFluxFurnace.getFieldCount()];
			allFieldsHaveChanged = true;
		}
		
		for(int i = 0; i < cachedFields.length; i++)
		{
			if (allFieldsHaveChanged || cachedFields[i] != this.tileEntityFluxFurnace.getField(i))
			{
				cachedFields[i] = this.tileEntityFluxFurnace.getField(i);
				fieldHasChanged[i] = true;
			}
		}
		
		for (int i = 0; i < this.listeners.size(); i++)
		{
			IContainerListener icrafting = (IContainerListener)this.listeners.get(i);
			
			for (int fieldID = 0; fieldID < this.tileEntityFluxFurnace.getFieldCount(); fieldID++)
			{
				if (fieldHasChanged[fieldID])
				{
					icrafting.sendProgressBarUpdate(this, fieldID, cachedFields[fieldID]);
				}
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar (int id, int data)
	{
		this.tileEntityFluxFurnace.setField(id, data);
	}
	
    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return tileEntityFluxFurnace.isUseableByPlayer(playerIn);
    }
}