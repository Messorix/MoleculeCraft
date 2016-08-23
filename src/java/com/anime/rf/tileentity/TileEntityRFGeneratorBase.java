package com.anime.rf.tileentity;

import java.util.HashMap;
import java.util.Map;

import com.anime.basic.NBT.NBTUtils;
import com.anime.rf.fuel.FuelRecipesBase;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public abstract class TileEntityRFGeneratorBase extends TileEntity implements ITickable, IEnergyProvider, ISidedInventory {

	/** Amount of ItemStacks there are for fuel. **/
	protected int FUEL_SLOTS = 1;
	/** Amount of ItemStacks there are for byproducts. **/
	protected int OUTPUT_SLOTS = 0;
	/** Total number of slots in the Generator. **/
	protected int TOTAL_SLOTS = FUEL_SLOTS + OUTPUT_SLOTS;
	protected ItemStack[] mainSlots = new ItemStack[TOTAL_SLOTS];

	/** The fuel recipes this Generator should use. **/
	protected FuelRecipesBase recipes;

	/** Whether this uses an ItemStack to generate RF (example: coal in energy out). **/
	protected boolean usesFuel = true;

	/** Whether this produces a byproduct from fuel. **/
	protected boolean makesByproducts = false;

	/** Whether this requires input from a player (example: SolarPanels). **/
	protected boolean isClosedSystem = false;

	/** Whether this generator tries to generate RF if it can't hold anymore. **/
	protected boolean wastesFuel = false;

	/** Whether or not it has called setup or not. **/
	protected boolean hasBeenSetup = false;

	/** Mics Energy things are exactly what they are called. **/
	protected int capacity = 100000, maxReceive = 1000, maxExtract = 1000;
	protected int ticksRemaining = 0, lastFuelsTicks = 0, currentRFPerTick = 0;
	protected EnergyStorage energyStorage = new EnergyStorage(capacity, maxReceive, maxExtract);

	/** Custom name provided by "forging" the item. **/
	protected String customName;

	public TileEntityRFGeneratorBase() {
		super();
	}

	/**
	 * Passes in true to {@link #generateEnergy(boolean)} method.
	 * @return if it can generate.
	 */
	protected boolean canGenerate() {
		return generateEnergy(true);
	}

	/**
	 * Main generation method handles all item based generation.
	 * @param simulate Whether this is a simulation won't actually do anything if true.
	 * @return If the process is possible or if it happened.
	 */
	protected boolean generateEnergy(boolean simulate) {
		if (!worldObj.isRemote && hasBeenSetup) {
			if (!isClosedSystem) {
				if (ticksRemaining <= 0) {
					boolean fuelExists = false;
					if (getFuelStacks() != null) {
						for (ItemStack fuelStack : getFuelStacks()) {
							if (fuelStack != null) {
								fuelExists = true;
								break;
							}
						}

						if (!fuelExists) return false;
						int[] RFTicksAndPerTick = recipes.getRFTicksAndPerTickForFuel(getFuelStacks());
						boolean canFuel = RFTicksAndPerTick[0] > 0 && RFTicksAndPerTick[1] > 0;
						ItemStack[] results = null;
						if (makesByproducts) {
							results = recipes.getByproducts(getFuelStacks());
							if (results == null) return false;
							if (!canByProductsFit(results)) return false;
							if (simulate) return canFuel;
							Map<Integer, Integer> outputPositions = getOutputPositions(results);
							int[] byproductStacksizes = recipes.getByproductStacksizes(getFuelStacks());
							for (int i = 0; i < results.length; i++) {
								if (outputPositions.get(i) > -1) {
									if (results[i] != null) {
										if (mainSlots[outputPositions.get(i)] == null) {
											results = recipes.getByproducts(getFuelStacks());
											mainSlots[outputPositions.get(i)] = new ItemStack(results[i].getItem(), byproductStacksizes[i], results[i].getMetadata());
											if (mainSlots[outputPositions.get(i)].stackSize <= 0) mainSlots[outputPositions.get(i)].stackSize = 1;

										} else {
											mainSlots[outputPositions.get(i)].stackSize += byproductStacksizes[i];
										}
									}
								}
							}
							markDirty();
						}
						if (simulate) return canFuel;

						currentRFPerTick = RFTicksAndPerTick[0];
						ticksRemaining = RFTicksAndPerTick[1];
						lastFuelsTicks = RFTicksAndPerTick[1];

						int[] stacksizes = recipes.getKeyStackSizes(getFuelStacks());
						for (int i = 0; i < FUEL_SLOTS; i++) {
							if (mainSlots[i] != null && mainSlots[i].stackSize > 0) {
								mainSlots[i].stackSize -= stacksizes[i];
								if (mainSlots[i].stackSize <= 0) mainSlots[i] = null;
							} else mainSlots[i] = null;
						}
						markDirty();
						return true;
					}
				} else {
					if (simulate) return true;
					if (energyStorage.receiveEnergy(currentRFPerTick, true) > 0) {
						energyStorage.receiveEnergy(currentRFPerTick, false);
						ticksRemaining--;
					} else if (wastesFuel) ticksRemaining--;
					markDirty();
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void update() {
		if (canGenerate()) {
			generateEnergy(false);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		energyStorage.writeToNBT(tag);
		tag = NBTUtils.writeItemStacksToNBT(tag, mainSlots, NBTUtils.MAIN_SLOTS_NAME);
		tag.setInteger("RFPerTick", currentRFPerTick);
		tag.setInteger("TicksRemaining", ticksRemaining);
		tag.setInteger("LastFuelsTicks", lastFuelsTicks);
		return tag;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		energyStorage.readFromNBT(tag);
		currentRFPerTick = tag.getInteger("RFPerTick");
		ticksRemaining = tag.getInteger("TicksRemaining");
		lastFuelsTicks = tag.getInteger("LastFuelsTicks");
		mainSlots = NBTUtils.readItemStacksFromNBT(tag, TOTAL_SLOTS, NBTUtils.MAIN_SLOTS_NAME);
	}

	@Override
	public int getSizeInventory() {
		return mainSlots.length;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		if (isInsideOfSlotBounds(index)) return mainSlots[index];
		return null;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if (index < mainSlots.length) {
			if (this.mainSlots[index] != null) {
				ItemStack itemstack;

				if (this.mainSlots[index].stackSize <= count) {
					itemstack = this.mainSlots[index];
					this.mainSlots[index] = null;
					markDirty();
					return itemstack;
				}
				else {
					itemstack = this.mainSlots[index].splitStack(count);
					if (this.mainSlots[index].stackSize == 0) {
						this.mainSlots[index] = null;
					}
					markDirty();
					return itemstack;
				}
			}
		}
		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack stack = getStackInSlot(index);
		setInventorySlotContents(index, null);
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		mainSlots[index] = stack;
		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
		markDirty();
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getTileEntity(pos) == this && player.getDistanceSq(player.getPosition().add(0.5D, 0.5D, 0.5D)) <= 64;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return index < FUEL_SLOTS && index >= 0 ? recipes.isPartOfRecipe(stack) : false;
	}

	@Override
	public int getField(int id) {
		switch (id) {
		case 0:
			return energyStorage.getEnergyStored();
		case 1: 
			return currentRFPerTick;
		case 2: 
			return ticksRemaining;
		case 3: 
			return lastFuelsTicks;
		}
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		switch (id) {
		case 0: energyStorage.setEnergyStored(value);
		break;
		case 1: currentRFPerTick = value;
		break;
		case 2: ticksRemaining = value;
		break;
		case 3: lastFuelsTicks = value;
		}
	}

	@Override
	public int getFieldCount() {
		return 4;
	}

	@Override
	public void clear() {
		mainSlots = new ItemStack[TOTAL_SLOTS];
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return null;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction) {
		return isItemValidForSlot(index, stack);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return index >= FUEL_SLOTS;
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	@Override
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		return energyStorage.extractEnergy(maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(EnumFacing from) {
		return energyStorage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return energyStorage.getMaxEnergyStored();
	}

	/** Use to set up variables that are different from default, make sure you call {@link #initializeFromVars()}. **/
	protected abstract boolean setup();

	public abstract String getDefaultName();

	/**
	 * If the byproducts can fit inside the generator used in {@link #generateEnergy(boolean)}.
	 * @param results The byproducts of the fuel.
	 * @return If the results can fit inside this generator.
	 */
	protected boolean canByProductsFit(ItemStack[] results) {
		if (getOutputStacks() != null) {
			if (results.length > getOutputStacks().length) return false;

			if (allNull(getOutputStacks())) return true;
			Map<Integer, Integer> matches = getOutputPositions(results);
			return !matches.containsValue(-1);
		}
		return false;
	}

	/**
	 * Grabs the output positions for byproducts, default position is -1.
	 * key is position in results and value is position in output slots.
	 * @param results The byproducts 
	 * @return A map containing possible output positions for the results
	 */
	protected Map<Integer, Integer> getOutputPositions(ItemStack[] results) {
		Map<Integer, Integer> matches = new HashMap<Integer, Integer>();
		for (int i = 0; i < results.length; i++) {
			matches.put(i, -1);
		}

		for (int i = 0; i < results.length; i++) {
			for (int j = 0; j < getOutputStacks().length; j++) {
				if (matches.get(i).equals(-1) && !matches.containsValue(j + FUEL_SLOTS) && (recipes.areItemStacksEqual(results[i], getOutputStacks()[j], false) || getOutputStacks()[j] == null)) {
					if (getOutputStacks()[j] == null || (getOutputStacks()[j].stackSize + results[i].stackSize < results[i].getMaxStackSize() && getOutputStacks()[j].stackSize + results[i].stackSize < getInventoryStackLimit())) {
						matches.put(i, j + FUEL_SLOTS);
					}
				}
			}
		}
		return matches;
	}

	/**
	 * Used to check if the Array contains a null value.
	 * @param stacks The array to check.
	 * @return If the passed array is contains null.
	 */
	protected boolean allNull(ItemStack[] stacks) {
		boolean allNull = true;
		for (int i = 0; i < stacks.length; i++) {
			if (stacks[i] != null) allNull = false;
		}
		return allNull;
	}

	/**
	 * Do not use to edit stacks WILL NOT WORK.
	 * @return A copy of the Fuel stacks from {@link #mainSlots}
	 */
	protected ItemStack[] getFuelStacks() {
		ItemStack[] fuelStacks = new ItemStack[FUEL_SLOTS];
		for (int i = 0; i < FUEL_SLOTS; i++) {
			fuelStacks[i] = mainSlots[i];
		}
		return fuelStacks;
	}

	/**
	 * Do not use to edit stacks WILL NOT WORK.
	 * @return A copy of the Output stacks from {@link #mainSlots}
	 */
	protected ItemStack[] getOutputStacks() {
		ItemStack[] outputStacks = new ItemStack[OUTPUT_SLOTS];
		for (int i = FUEL_SLOTS; i < FUEL_SLOTS + OUTPUT_SLOTS; i++) {
			outputStacks[i - FUEL_SLOTS] = mainSlots[i];
		}
		return outputStacks;
	}

	/**
	 * Checks if the index is inside of the inventory bounds.
	 * @param index The position to check.
	 * @return If the index if inside of the inventory bounds.
	 */
	protected boolean isInsideOfSlotBounds(int index) {
		return index >= 0 && index < getSizeInventory();
	}

	/** Initializes slots and energyStorage from variables. **/
	protected void initializeFromVars() {
		mainSlots = new ItemStack[TOTAL_SLOTS];
		energyStorage = new EnergyStorage(capacity, maxReceive, maxExtract);

	}

	//**************************************Getters*************************************************//

	public int getFuelSlots() {
		return FUEL_SLOTS;
	}

	public int getOutputSlots() {
		return OUTPUT_SLOTS;
	}

	public int getRFPerTick() {
		return currentRFPerTick;
	}

	public int getTicksRemaining() {
		return ticksRemaining;
	}

	public int getLastFuelsTicks() {
		return lastFuelsTicks;
	}

	public FuelRecipesBase getRecipes() {
		return recipes;
	}

	//**************************************Setters*************************************************//

	public void setCustomName(String customName) {
		this.customName = customName;
	}

}
