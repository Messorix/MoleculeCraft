package com.messorix.moleculecraft.base.containers;

import com.messorix.moleculecraft.base.crafting.FluxGrinderRecipes;
import com.messorix.moleculecraft.base.tileentities.TileEntityFluxGrinder;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerFluxGrinder extends ModContainer
{
	private TileEntityFluxGrinder tileEntityFluxGrinder;

	public ContainerFluxGrinder(InventoryPlayer player, TileEntityFluxGrinder tileentity)
	{
		super(player, tileentity);
		
		super.fuel_slots = 1;
		super.input_slots = 1;
		super.output_slots = 2;
		
		tileEntityFluxGrinder = tileentity;
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
			if (this.tileEntityFluxGrinder.getProcessingResultForItem(FluxGrinderRecipes.instance(), sourceStack) != null)
			{
				System.out.println(first_input_index + ", " + input_slots + ", " + (first_input_index + input_slots));
				
				if (!mergeItemStack(sourceStack, first_input_index, (int)(first_input_index + input_slots), false))
				{
					return null;
				}
			}
			else if (this.tileEntityFluxGrinder.getItemBurnTime(sourceStack) > 0)
			{
				if (!mergeItemStack(sourceStack, first_fuel_index, first_fuel_index + fuel_slots, false))
				{
					return null;
				}
			}
		}
		else if (sourceSlotIndex >= first_fuel_index && sourceSlotIndex < first_fuel_index + tileEntity.total_slots)
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
		boolean fieldHasChanged [] = new boolean[this.tileEntityFluxGrinder.getFieldCount()];
		
		if (cachedFields == null)
		{
			cachedFields = new int[this.tileEntityFluxGrinder.getFieldCount()];
			allFieldsHaveChanged = true;
		}
		
		for(int i = 0; i < cachedFields.length; i++)
		{
			if (allFieldsHaveChanged || cachedFields[i] != this.tileEntityFluxGrinder.getField(i))
			{
				cachedFields[i] = this.tileEntityFluxGrinder.getField(i);
				fieldHasChanged[i] = true;
			}
		}
		
		for (int i = 0; i < this.listeners.size(); i++)
		{
			IContainerListener icrafting = (IContainerListener)this.listeners.get(i);
			
			for (int fieldID = 0; fieldID < this.tileEntityFluxGrinder.getFieldCount(); fieldID++)
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
		this.tileEntityFluxGrinder.setField(id, data);
	}
	
    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return tileEntityFluxGrinder.isUseableByPlayer(playerIn);
    }
}