package com.messorix.moleculecraft.base.containers;

import com.messorix.moleculecraft.base.tileentities.TileEntityFluxFurnace;

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
	public ItemStack transferStackInSlot(EntityPlayer player, int sourceSlotIndex)
	{
		Slot sourceSlot = (Slot)inventorySlots.get(sourceSlotIndex);
		
		if (sourceSlot == null || !sourceSlot.getHasStack())
		{
			return null;
		}
		
		ItemStack sourceStack =  sourceSlot.getStack();
		ItemStack copyOfSourceStack = sourceStack.copy();
		
		if(sourceSlotIndex >= first_vanilla_index && sourceSlotIndex < first_vanilla_index + vanilla_slots)
		{
			if (this.tileEntityFluxFurnace.getProcessingResultForItem(sourceStack) != null)
			{
				if (!mergeItemStack(sourceStack, first_input_index, first_input_index + input_slots, false))
				{
					return null;
				}
			}
			else if (this.tileEntityFluxFurnace.getItemBurnTime(sourceStack) > 0)
			{
				if (!mergeItemStack(sourceStack, first_fuel_index, first_fuel_index + fuel_slots, true))
				{
					return null;
				}
			}
		}
		else if (sourceSlotIndex > first_fuel_index && sourceSlotIndex < first_fuel_index + process_slots)
		{
			if (!mergeItemStack(sourceStack, first_vanilla_index, first_vanilla_index + vanilla_slots, false))
			{
				return null;
			}
		}
		else
		{
			System.err.println("Invalid slotIndex: " + sourceSlotIndex);
			return null;
		}
		
		if (sourceStack.stackSize == 0)
		{
			sourceSlot.putStack(null);
		}
		else
		{
			sourceSlot.onSlotChanged();
		}
		
		sourceSlot.onPickupFromSlot(player, sourceStack);
		return copyOfSourceStack;
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