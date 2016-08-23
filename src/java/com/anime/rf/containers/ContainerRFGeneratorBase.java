package com.anime.rf.containers;

import com.anime.rf.tileentity.TileEntityRFGeneratorBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerRFGeneratorBase extends Container {
	
	protected TileEntityRFGeneratorBase tile;
	
	protected int energy;
	protected int RFPerTick;
	protected int ticksRemaining;
	protected int lastFuelsTicks;
	
	protected int FUEL_SLOTS;
	protected int OUTPUT_SLOTS;
	protected int HOTBAR_SLOTS = 9;
	protected int VANILLA_MAIN_SLOTS = 27;
	protected int TOTAL_VANILLA_SLOTS = HOTBAR_SLOTS + VANILLA_MAIN_SLOTS;
	protected int ARMOR_SLOTS = 4;
	
	protected static final int FUEL_STARTING_POS = 0;
	protected int OUTPUT_STARTING_POS;
	protected int VANILLA_HOTBAR_STARTING_POS;
	protected int VANILLA_MAIN_STARTING_POS;
	protected int VANILLA_ARMOR_STARTING_POS;
	protected int FINAL_SLOT;
	
	/**
	 * Basic version of this containers constructor.
	 * @param iPlayer The inventory of the player that opened this.
	 * @param tile The TileEntityRFGenerator of this container.
	 * @param fuelDirection The direction that the fuel slots should go(true is left-right).
	 * @param outputDirection The direction that the output slots should go(true is left-right)
	 */
	public ContainerRFGeneratorBase(InventoryPlayer iPlayer, TileEntityRFGeneratorBase tile, boolean fuelDirection, boolean outputDirection) {
		super();
		this.tile = tile;
		
		init();
		
		int i = 0;
		
		int originalFuelXPos = 56;
		int originalFuelYPos = 17;
		
		if (fuelDirection) {
			originalFuelXPos -= (20 * FUEL_SLOTS);
			if (originalFuelXPos < 6) originalFuelXPos = 6;
		} else {
			originalFuelYPos -= (20 * FUEL_SLOTS);
			if (originalFuelYPos < 6) originalFuelYPos = 6;
		}
		
		for (i = FUEL_STARTING_POS; i < FUEL_SLOTS; i++) {
			if (fuelDirection) {
				this.addSlotToContainer(new SlotFuel(tile, i, originalFuelXPos + (20 * i), originalFuelYPos));
			} else this.addSlotToContainer(new SlotFuel(tile, i, originalFuelXPos, originalFuelYPos + (20 * i)));
		}
		
		int originalOutputXPos = 122;
		int originalOutputYPos = 35;
		
		if (outputDirection) {
			originalOutputXPos -= (20 * OUTPUT_SLOTS);
			if (originalOutputXPos < 6) originalOutputXPos = 6;
		} else {
			originalOutputYPos -= (20 * OUTPUT_SLOTS);
			if (originalOutputYPos < 6) originalOutputYPos = 6;
		}
		
		for (i = OUTPUT_STARTING_POS; i < FUEL_SLOTS + OUTPUT_SLOTS; i++) {
			if (outputDirection) {
				this.addSlotToContainer(new SlotOutput(tile, i, originalOutputXPos + (20 * (i - FUEL_SLOTS)), originalOutputYPos));
			} else this.addSlotToContainer(new SlotOutput(tile, i, originalOutputXPos, originalOutputYPos + (20 * (i - FUEL_SLOTS))));
		}
		
		final int hotbarXPos = 8;
		final int hotbar_ypos = 142;
		
		for(int x = 0; x < HOTBAR_SLOTS; x++)
		{
			int slotNumber = x;
			
			addSlotToContainer(new Slot(iPlayer, slotNumber, hotbarXPos + 18 * x, hotbar_ypos));
		}
		
		//Player inventory
		final int player_inventory_xpos = 8;
		final int player_inventory_ypos = 84;
		
		for(int y = 0; y < 3; y++)
		{
			for(int x = 0; x < 9; x++)
			{
				int SlotNumber = HOTBAR_SLOTS + y * 9 + x;
				int xpos = player_inventory_xpos + x * 18;
				int ypos = player_inventory_ypos + y * 18;
				
				addSlotToContainer(new Slot(iPlayer, SlotNumber, xpos, ypos));
			}
		}
		
	}
	
	/**
	 * More customizable constructor
	 * @param iPlayer The inventory of the player that opened this.
	 * @param tile The TileEntityRFGenerator of this container.
	 * @param slots The slots of the specified inventory excluding players inventory.
	 */
	public ContainerRFGeneratorBase(InventoryPlayer iPlayer, TileEntityRFGeneratorBase tile, Slot[] slots) {
		super();
		this.tile = tile;
		this.energy = tile.getEnergyStored(null);
		this.RFPerTick = tile.getRFPerTick();
		this.ticksRemaining = tile.getTicksRemaining();
		
		for (Slot slot : slots) {
			inventorySlots.add(slot);
		}
		
		final int hotbarXPos = 8;
		final int hotbar_ypos = 142;
		
		for(int x = 0; x < HOTBAR_SLOTS; x++)
		{
			int slotNumber = x;
			
			addSlotToContainer(new Slot(iPlayer, slotNumber, hotbarXPos + 18 * x, hotbar_ypos));
		}
		
		//Player inventory
		final int player_inventory_xpos = 8;
		final int player_inventory_ypos = 84;
		
		for(int y = 0; y < 3; y++)
		{
			for(int x = 0; x < 9; x++)
			{
				int SlotNumber = HOTBAR_SLOTS + y * 9 + x;
				int xpos = player_inventory_xpos + x * 18;
				int ypos = player_inventory_ypos + y * 18;
				
				addSlotToContainer(new Slot(iPlayer, SlotNumber, xpos, ypos));
			}
		}
		
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		ItemStack itemstack = null;
		Slot slot = (Slot)this.inventorySlots.get(par2);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par2 >= FUEL_STARTING_POS && par2 <  OUTPUT_STARTING_POS + OUTPUT_SLOTS) {
				if (!this.mergeItemStack(itemstack1, VANILLA_HOTBAR_STARTING_POS, TOTAL_VANILLA_SLOTS, false)) {
					return null;
				}
			}
			else {
				if (tile.getRecipes().isPartOfRecipe(itemstack1)) {
					if (!this.mergeItemStack(itemstack1, FUEL_STARTING_POS, FUEL_STARTING_POS + FUEL_SLOTS, false)) {
						return null;	
					}
				}
				else if (par2 >= HOTBAR_SLOTS && par2 < TOTAL_VANILLA_SLOTS) {
					// place in action bar
					if (!this.mergeItemStack(itemstack1, VANILLA_HOTBAR_STARTING_POS, HOTBAR_SLOTS, false)) {
						return null;
					}
				}
				else if (par2 >= VANILLA_HOTBAR_STARTING_POS && par2 < HOTBAR_SLOTS) {
					if (!this.mergeItemStack(itemstack1, HOTBAR_SLOTS, VANILLA_ARMOR_STARTING_POS, false)) {
						return null;
					}
				}
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack)null);
			}
			else {
				slot.onSlotChanged();
			}
			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}
			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}
		return itemstack;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tile.isUseableByPlayer(player);
	}

	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendAllWindowProperties(this, tile);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int i = 0; i < this.listeners.size(); i++) {
			IContainerListener icrafting = (IContainerListener)this.listeners.get(i);
			if (tile.getField(0) != energy) {
				icrafting.sendProgressBarUpdate(this, 0, tile.getField(0));
			}
			if (tile.getField(1) != RFPerTick) {
				icrafting.sendProgressBarUpdate(this, 1, tile.getField(1));
			}
			if (tile.getField(2) != ticksRemaining) {
				icrafting.sendProgressBarUpdate(this, 2, tile.getField(2));
			}
			if (tile.getField(3) != lastFuelsTicks) {
				icrafting.sendProgressBarUpdate(this, 3, tile.getField(3));
			}
		}
		this.energy = tile.getField(0);
		this.RFPerTick = tile.getField(1);
		this.ticksRemaining = tile.getField(2);
		this.lastFuelsTicks = tile.getField(3);
	}
	
	@Override
	public void updateProgressBar(int id, int data) {
		super.updateProgressBar(id, data);
		this.tile.setField(id, data);
	}
	
	/** Initializes non passed variables. **/
	protected void init() {
		energy = tile.getEnergyStored(null);
		RFPerTick = tile.getRFPerTick();
		ticksRemaining = tile.getTicksRemaining();
		lastFuelsTicks = tile.getLastFuelsTicks();
		
		FUEL_SLOTS = tile.getFuelSlots();
		OUTPUT_SLOTS = tile.getOutputSlots();
		
		OUTPUT_STARTING_POS = FUEL_STARTING_POS + FUEL_SLOTS;
		VANILLA_HOTBAR_STARTING_POS = OUTPUT_STARTING_POS + OUTPUT_SLOTS;
		VANILLA_MAIN_STARTING_POS = VANILLA_HOTBAR_STARTING_POS + HOTBAR_SLOTS;
		VANILLA_ARMOR_STARTING_POS = VANILLA_MAIN_STARTING_POS + VANILLA_MAIN_SLOTS;
		FINAL_SLOT = VANILLA_ARMOR_STARTING_POS + ARMOR_SLOTS;
	}
	
	//****************************************Getters***********************************************//
	
	/** Gets the TileEntity of this Container. **/
	public TileEntityRFGeneratorBase getTile() {
		return tile;
	}
	
	//*********************************************************************************************//
	
	public class SlotFuel extends Slot {
		
		private int index;
		
		public SlotFuel(TileEntityRFGeneratorBase tile, int index, int xPosition, int yPosition) {
			super(tile, index, xPosition, yPosition);
			this.index = index;
		}
		
		@Override
		public boolean isItemValid(ItemStack stack) {
			return tile.isItemValidForSlot(index, stack);
		}
		
	}
	
	//*********************************************************************************************//
	
	public class SlotOutput extends Slot {

		public SlotOutput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}
		
		@Override
		public boolean isItemValid(ItemStack stack) {
			return false;
		}
		
	}
	
}
