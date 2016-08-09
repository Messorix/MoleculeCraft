package com.messorix.moleculecraft.base.containers;

import com.messorix.moleculecraft.base.tileentities.ModTileEntity;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class ModContainer extends Container {
	protected ModTileEntity tileEntity;
	protected int[] cachedFields;

	protected final int hotbar = 9;
	protected final int player_inventory_row = 3;
	protected final int player_inventory_column = 9;
	protected final int player_inventory = player_inventory_row * player_inventory_column;
	protected final int vanilla_slots = hotbar + player_inventory;

	public int fuel_slots = 0;
	public int input_slots = 0;
	public int output_slots = 0;
	public final int process_slots = fuel_slots + input_slots + output_slots;

	protected final int first_vanilla_index = 0;
	protected final int first_fuel_index = first_vanilla_index + vanilla_slots;
	protected final int first_input_index;
	protected final int first_output_index;

	protected final int first_fuel_slot = 0;
	protected final int first_input_slot;
	protected final int first_output_slot;

	public ModContainer(InventoryPlayer player, ModTileEntity tileentity)
	{
		this.tileEntity = tileentity;

		first_input_index = first_fuel_index + ModTileEntity.fuel_slots;
		first_output_index = first_input_index + ModTileEntity.input_slots;
		first_input_slot = first_fuel_slot + ModTileEntity.fuel_slots;
		first_output_slot = first_input_slot + ModTileEntity.input_slots;

		//Hotbar
		final int slot_x_spacing = 18;
		final int slot_y_spacing = 18;
		final int hotbar_xpos = 8;
		final int hotbar_ypos = 142;
		
		for(int x = 0; x < hotbar; x++)
		{
			int slotNumber = x;
			
			addSlotToContainer(new Slot(player, slotNumber, hotbar_xpos + slot_x_spacing * x, hotbar_ypos));
		}
		
		//Player inventory
		final int player_inventory_xpos = 8;
		final int player_inventory_ypos = 84;
		
		for(int y = 0; y < player_inventory_row; y++)
		{
			for(int x = 0; x < player_inventory_column; x++)
			{
				int SlotNumber = hotbar + y * player_inventory_column + x;
				int xpos = player_inventory_xpos + x * slot_x_spacing;
				int ypos = player_inventory_ypos + y * slot_y_spacing;
				
				addSlotToContainer(new Slot(player, SlotNumber, xpos, ypos));
			}
		}

		//Fuel slots
		final int fuel_slots_xpos = 56;
		final int fuel_slots_ypos = 53;
		
		for (int x = 0; x < ModTileEntity.fuel_slots; x++)
		{
			int slotNumber = x + first_fuel_slot;
			addSlotToContainer(new SlotFuel(tileEntity, slotNumber, fuel_slots_xpos + ((slot_x_spacing + 4) * x), fuel_slots_ypos));
		}
		
		//Input slots
		final int input_slots_xpos = 56;
		final int input_slots_ypos = 17;
		
		for (int x = 0; x < ModTileEntity.input_slots; x++)
		{
			int SlotNumber = x + first_input_slot;
			addSlotToContainer(new SlotProcessableInput(tileEntity, SlotNumber, input_slots_xpos + ((slot_x_spacing + 4) * x), input_slots_ypos));
		}

		//Output slots
		final int output_slots_xpos = 112;
		final int output_slots_ypos = 35;
		
		for (int x = 0; x < ModTileEntity.output_slots; x++)
		{
			int SlotNumber = x + first_output_slot;
			addSlotToContainer(new SlotOutput(tileEntity, SlotNumber, output_slots_xpos + ((slot_x_spacing + 4) * x), output_slots_ypos));
		}
	}

	public class SlotFuel extends Slot
	{
		public SlotFuel(IInventory inventory, int index, int xpos, int ypos)
		{
			super(inventory, index, xpos, ypos);
		}
		
		@Override
		public boolean isItemValid(ItemStack stack)
		{
			return tileEntity.isItemValidForFuelSlot(stack);
		}
	}
	
	public class SlotProcessableInput extends Slot
	{
		public SlotProcessableInput(IInventory inventory, int index, int xpos, int ypos)
		{
			super(inventory, index, xpos, ypos);
		}
		
		@Override
		public boolean isItemValid(ItemStack stack)
		{
			return tileEntity.isItemValidForInputSlot(stack);
		}
	}
	
	public class SlotOutput extends Slot
	{
		public SlotOutput(IInventory inventory, int index, int xpos, int ypos)
		{
			super(inventory, index, xpos, ypos);
		}
		
		@Override
		public boolean isItemValid(ItemStack stack)
		{
			return tileEntity.isItemValidForOutputSlot(stack);
		}
	}
}
