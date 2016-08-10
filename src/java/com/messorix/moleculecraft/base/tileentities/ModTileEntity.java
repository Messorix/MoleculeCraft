package com.messorix.moleculecraft.base.tileentities;

import java.util.Arrays;

import com.anime.basic.NBT.NBTUtils;
import com.anime.basic.logger.ModLogger;
import com.messorix.moleculecraft.base.crafting.ModRecipes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class ModTileEntity extends TileEntity implements IInventory, ITickable {
	public static int fuel_slots = 0;
	public static int input_slots = 0;
	public static int output_slots = 0;
	public static int total_slots = fuel_slots + input_slots + output_slots;

	public static final int first_fuel_slot = 0;
	public static int first_input_slot = first_fuel_slot + fuel_slots;
	public static int first_output_slot = first_input_slot + input_slots;

	protected ItemStack[] itemStacks = new ItemStack[total_slots];

	protected int[] burnTimeRemaining;
	protected int[] burnTimeInitial;

	protected short processTime;
	protected static final short processing_time_for_completion = 200;
	protected int cachedNumberOfBurningSlots = -1;

	public double fractionOfFuelRemaining(int fuelSlot) {
		double fraction;

		if (burnTimeRemaining == null)
			burnTimeRemaining = new int[fuel_slots];
		if (burnTimeInitial == null)
			burnTimeInitial = new int[fuel_slots];

		if (burnTimeInitial[fuelSlot] <= 0) {
			fraction = 0;
		} else {
			fraction = burnTimeRemaining[fuelSlot] / (double) burnTimeInitial[fuelSlot];
		}

		return MathHelper.clamp_double(fraction, 0.0, 1.0);
	}

	public int secondsOfFuelRemaining(int fuelSlot) {
		if (burnTimeRemaining == null)
			burnTimeRemaining = new int[fuel_slots];
		
		if (burnTimeRemaining[fuelSlot] <= 0) {
			return 0;
		}

		return burnTimeRemaining[fuelSlot] / 20;
	}

	public int numberOfBurningFuelSlots() {
		int burningCount = 0;

		if (burnTimeRemaining == null)
			burnTimeRemaining = new int[fuel_slots];

		for (int burnTime : burnTimeRemaining) {
			if (burnTime > 0) {
				burningCount++;
			}
		}

		return burningCount;
	}

	public double fractionOfProcessingTimeComplete() {
		double fraction = processTime / (double) processing_time_for_completion;

		return MathHelper.clamp_double(fraction, 0.0, 1.0);
	}

	protected int burnFuel() {
		int burningCount = 0;
		boolean inventorychanged = false;

		if (burnTimeRemaining == null)
			burnTimeRemaining = new int[fuel_slots];
		if (burnTimeInitial == null)
			burnTimeInitial = new int[fuel_slots];

		for (int i = 0; i < fuel_slots; i++) {
			int fuelSlotNumber = i + first_fuel_slot;
			
			if (burnTimeRemaining[i] > 0) {
				--burnTimeRemaining[i];
				++burningCount;
			}

			if (burnTimeRemaining[i] == 0) {
				if (itemStacks[fuelSlotNumber] != null && getItemBurnTime(itemStacks[fuelSlotNumber]) > 0) {
					burnTimeRemaining[i] = burnTimeInitial[i] = getItemBurnTime(itemStacks[fuelSlotNumber]);
					--itemStacks[fuelSlotNumber].stackSize;
					++burningCount;
					inventorychanged = true;

					if (itemStacks[fuelSlotNumber].stackSize == 0) {
						itemStacks[fuelSlotNumber] = itemStacks[fuelSlotNumber].getItem()
								.getContainerItem(itemStacks[fuelSlotNumber]);
					}
				}
			}
		}

		if (inventorychanged) {
			markDirty();
		}

		return burningCount;
	}

	protected boolean canProcess(ModRecipes recipes) {
		return processItem(false, recipes);
	}

	protected void processItem(ModRecipes recipes) {
		processItem(true, recipes);
	}

	protected boolean processItem(boolean performProcess, ModRecipes recipes) {
		Integer firstSuitableInputSlot = null;
		Integer firstSuitableOutputSlot = null;
		ItemStack result = null;

		for (int inputSlot = first_input_slot; inputSlot < first_input_slot + input_slots; inputSlot++) {
			if (itemStacks[inputSlot] != null) {
				result = getProcessingResultForItem(recipes, itemStacks[inputSlot]);

				if (result != null) {
					for (int outputSlot = first_output_slot; outputSlot < first_output_slot + output_slots; outputSlot++) {
						
						ItemStack outputStack = itemStacks[outputSlot];

						if (outputStack == null) {
							firstSuitableInputSlot = inputSlot;
							firstSuitableOutputSlot = outputSlot;
							break;
						}

						if (outputStack.getItem() == result.getItem() && (!outputStack.getHasSubtypes()
								|| outputStack.getMetadata() == result.getMetadata())) {
							int combinedSize = itemStacks[outputSlot].stackSize + result.stackSize;

							if (combinedSize <= getInventoryStackLimit()
									&& combinedSize <= itemStacks[outputSlot].getMaxStackSize()) {
								firstSuitableInputSlot = inputSlot;
								firstSuitableOutputSlot = outputSlot;
								break;
							}
						}
					}

					if (firstSuitableInputSlot != null) {
						break;
					}
				}
			}
		}

		if (firstSuitableInputSlot == null) {
			return false;
		}

		if (!performProcess) {
			return true;
		}

		itemStacks[firstSuitableInputSlot].stackSize--;

		if (itemStacks[firstSuitableInputSlot].stackSize <= 0) {
			itemStacks[firstSuitableInputSlot] = null;
		}

		if (itemStacks[firstSuitableOutputSlot] == null) {
			itemStacks[firstSuitableOutputSlot] = result.copy();
		} else {
			itemStacks[firstSuitableOutputSlot].stackSize += result.stackSize;
		}
		
		System.out.println("StackSize" + result.stackSize);
		
		markDirty();
		return true;
	}
	
	public static short getItemBurnTime(ItemStack stack)
	{
		int burnTime = TileEntityFurnace.getItemBurnTime(stack);
		return (short) MathHelper.clamp_int(burnTime, 0, Short.MAX_VALUE);
	}
	
	public static ItemStack getProcessingResultForItem(ModRecipes recipes, ItemStack stack){
		return recipes.getProcessingResult(stack);
	}

	@Override
	public void update() {
		if (!worldObj.isRemote) {
			if (canProcess(ModRecipes.instance())) {
				int numberOfFuelBurning = burnFuel();

				if (numberOfFuelBurning > 0) {
					processTime += numberOfFuelBurning;
				} else {
					processTime -= 2;
				}

				if (processTime < 0) {
					processTime = 0;
				}

				if (processTime >= processing_time_for_completion) {
					processItem(ModRecipes.instance());
					processTime = 0;
				}
			} else {
				processTime = 0;
			}
		}

		/*int numberBurning = numberOfBurningFuelSlots();

		if (cachedNumberOfBurningSlots != numberBurning) {
			cachedNumberOfBurningSlots = numberBurning;

			if (worldObj.isRemote) {
				worldObj.markBlockRangeForRenderUpdate(pos, pos);
			}

			worldObj.checkLightFor(EnumSkyBlock.BLOCK, pos);
		}*/
	}

	@Override
	public int getSizeInventory() {
		return itemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return itemStacks[index];
	}

	@Override
	public ItemStack decrStackSize(int slotIndex, int count) {
		ItemStack itemStackInSlot = getStackInSlot(slotIndex);
		
		if (itemStackInSlot == null){
			return null;
		}
		
		ItemStack itemStackRemoved;
		
		if (itemStackInSlot.stackSize <= count)
		{
			itemStackRemoved = itemStackInSlot;
			setInventorySlotContents(slotIndex, null);
		}
		else
		{
			itemStackRemoved = itemStackInSlot.splitStack(count);
			
			if (itemStackInSlot.stackSize == 0)
			{
				setInventorySlotContents(slotIndex, null);
			}
		}
		
		markDirty();
		return itemStackRemoved;
	}

	@Override
	public void setInventorySlotContents(int slotIndex, ItemStack stack) {
		itemStacks[slotIndex] = stack;
		
		if (stack != null && stack.stackSize > getInventoryStackLimit())
		{
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
		if (this.worldObj.getTileEntity(this.pos) != this)
		{
			return false;
		}

		final double x_center_offset = 0.5;
		final double y_center_offset = 0.5;
		final double z_center_offset = 0.5;
		final double maximum_distance_sq = 8.0 * 8.0;
		
		return player.getDistanceSq(pos.getX() + x_center_offset, pos.getY() + y_center_offset, pos.getZ() + z_center_offset) < maximum_distance_sq;
	}

	public boolean isItemValidForFuelSlot(ItemStack stack) {
		return false;
	}

	public boolean isItemValidForInputSlot(ItemStack stack) {
		return false;
	}

	public boolean isItemValidForOutputSlot(ItemStack stack) {
		return false;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound parentNBTTagCompound)
	{
		super.writeToNBT(parentNBTTagCompound);
		
		parentNBTTagCompound = NBTUtils.writeItemStacksToNBT(parentNBTTagCompound, itemStacks, NBTUtils.MAIN_SLOTS_NAME);
		parentNBTTagCompound.setShort("ProcessTime", processTime);
		parentNBTTagCompound.setTag("BurnTimeRemaining", new NBTTagIntArray(burnTimeRemaining));
		parentNBTTagCompound.setTag("BurnTimeInitial", new NBTTagIntArray(burnTimeInitial));
		
		return parentNBTTagCompound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound)
	{
		super.readFromNBT(nbtTagCompound);
		
		itemStacks = NBTUtils.readItemStacksFromNBT(nbtTagCompound, total_slots, NBTUtils.MAIN_SLOTS_NAME);
		processTime = nbtTagCompound.getShort("ProcessTime");
		burnTimeRemaining = Arrays.copyOf(nbtTagCompound.getIntArray("BurnTimeRemaining"), fuel_slots);
		burnTimeInitial = Arrays.copyOf(nbtTagCompound.getIntArray("BurnTimeInitial"), fuel_slots);
		cachedNumberOfBurningSlots = -1;
	}
	
	@SuppressWarnings("rawtypes")
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		final int metadata = 0;
		return new SPacketUpdateTileEntity(this.pos, metadata, nbtTagCompound);
	}
	
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
		readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public void clear() {
		Arrays.fill(itemStacks, null);
	}

	@Override
	public String getName() {
		return "Set this first";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}
	
	public ITextComponent getDisplayName()
	{
		return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
	}

	protected static byte process_field_id = 0;
	protected static byte first_burn_time_remaining_field_id = 1;
	protected static byte first_burn_time_initial_field_id = (byte) (first_burn_time_remaining_field_id + fuel_slots);
	protected static byte number_of_fields = (byte) (first_burn_time_initial_field_id + fuel_slots);

	@Override
	public int getField(int id) {
		if (id == process_field_id)
		{
			ModLogger.logWarningMessage("Process time got");
			return processTime;
		}
		if (burnTimeRemaining == null) burnTimeRemaining = new int[fuel_slots];
		if (id >= first_burn_time_remaining_field_id && id < first_burn_time_initial_field_id)
		{
			ModLogger.logWarningMessage("Burn time remaining got");
			return burnTimeRemaining[id - first_burn_time_remaining_field_id];
		}
		if(burnTimeInitial == null) burnTimeInitial = new int[fuel_slots];
		if (id >= first_burn_time_initial_field_id && id < first_burn_time_initial_field_id + fuel_slots)
		{
			ModLogger.logWarningMessage("Burn time initial got");
			return burnTimeInitial[id - first_burn_time_initial_field_id];
		}
		
		System.err.println("Invalid field ID in TileInventoryProcessing.getField: " + id);
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		if (id == process_field_id)
		{
			ModLogger.logWarningMessage("Process time change.");
			processTime = (short)value;
		}
		if (burnTimeRemaining == null) burnTimeRemaining = new int[fuel_slots];
		else if (id >= first_burn_time_remaining_field_id && id < first_burn_time_remaining_field_id + fuel_slots)
		{
			ModLogger.logWarningMessage("Burn time remaining change");
			burnTimeRemaining[id - first_burn_time_remaining_field_id] = value;
		}
		if(burnTimeInitial == null) burnTimeInitial = new int[fuel_slots];
		else if (id >= first_burn_time_initial_field_id && id < first_burn_time_initial_field_id + fuel_slots)
		{
			ModLogger.logWarningMessage("Burn Time inital change");
			burnTimeInitial[id - first_burn_time_initial_field_id] = value;
		}
		else
		{
			System.err.println("Invalid field ID in TileInventoryProcessing.getField: " + id);
		}
	}

	@Override
	public int getFieldCount() {
		return number_of_fields;
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return isItemValidForFuelSlot(stack) || isItemValidForInputSlot(stack) || isItemValidForOutputSlot(stack);
	}

	@Override
	public ItemStack removeStackFromSlot(int slotIndex) {
		ItemStack stack = getStackInSlot(slotIndex);
		
		if (stack != null)
		{
			setInventorySlotContents(slotIndex, null);
		}
		
		return stack;
	}
	
	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}
}
