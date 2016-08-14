package com.messorix.moleculecraft.base.containers;

import com.messorix.moleculecraft.base.recipes.FluxFurnaceRecipes;
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

	public ContainerFluxFurnace(InventoryPlayer player, TileEntityFluxFurnace tileentity)
	{
		super(player, tileentity);
		
		this.fuel_slots = 1;
		this.input_slots = 2;
		this.output_slots = 1;
		this.process_slots = fuel_slots + input_slots + output_slots;
		
		first_input_index = first_fuel_index + fuel_slots;
		first_output_index = first_input_index + input_slots;
		first_input_slot = first_fuel_slot + fuel_slots;
		first_output_slot = first_input_slot + input_slots;
		
		tileEntityFluxFurnace = tileentity;

		final int slot_x_spacing = 18;
		
		//Fuel slots
		final int fuel_slots_xpos = 56;
		final int fuel_slots_ypos = 53;

		for (int x = 0; x < fuel_slots; x++)
		{
			int slotNumber = x + first_fuel_slot;
			addSlotToContainer(new SlotFuel(tileEntity, slotNumber, fuel_slots_xpos + ((slot_x_spacing + 4) * x), fuel_slots_ypos));
		}

		//Input slots
		final int input_slots_xpos = 45;
		final int input_slots_ypos = 17;

		for (int x = 0; x < input_slots; x++)
		{
			int SlotNumber = x + first_input_slot;
			addSlotToContainer(new SlotProcessableInput(tileEntity, SlotNumber, input_slots_xpos + ((slot_x_spacing + 3) * x), input_slots_ypos));
		}

		//Output slots
		final int output_slots_xpos = 116;
		final int output_slots_ypos = 35;

		for (int x = 0; x < output_slots; x++)
		{
			int SlotNumber = x + first_output_slot;
			addSlotToContainer(new SlotOutput(tileEntity, SlotNumber, output_slots_xpos + ((slot_x_spacing + 4) * x), output_slots_ypos));
		}

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
				if (FluxFurnaceRecipes.instance().isPartOfRecipe(itemstack1))
				{
					if (!this.mergeItemStack(itemstack1, first_input_index, first_input_index + input_slots, false))
					{
						return null;	
					}
				}
				// If item is fuel
				else if (TileEntityFluxFurnace.getItemBurnTime(itemstack1) > 0) {
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
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendAllWindowProperties(this, tileEntityFluxFurnace);
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