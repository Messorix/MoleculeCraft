package com.messorix.moleculecraft.base.tileentities;

import com.anime.basic.logger.ModLogger;
import com.messorix.moleculecraft.base.blocks.BlockFluxFurnace;
import com.messorix.moleculecraft.base.blocks.BlockMachine;
import com.messorix.moleculecraft.base.crafting.FluxFurnaceRecipes;
import com.messorix.moleculecraft.base.crafting.ModRecipes;

import net.minecraft.item.ItemStack;
import net.minecraft.world.EnumSkyBlock;

public class TileEntityFluxFurnace extends ModTileEntity
{
	private static boolean setupDone = false;
	
	public TileEntityFluxFurnace()
	{
		setup();
	}
	
	@Override
	public String getName() {
		return "container.tile_entity_flux_furnace.name";
	}

	public void setCustomInventoryName(String displayName) {
	}
	
	public static ItemStack getProcessingResultForItem(ItemStack[] stacks) {
		return FluxFurnaceRecipes.instance().getProcessingResult(stacks);
	}
	
	protected boolean processItem(boolean performProcess, ModRecipes recipes) {
		Integer firstSuitableOutputSlot = null;
		ItemStack result = null;

		result = getProcessingResultForItem(getInputSlots());

		if (result != null) {
			for (int outputSlot = first_output_slot; outputSlot < first_output_slot + output_slots; outputSlot++) {
				ModLogger.logInfoMessage("Process Item: " + output_slots);
				ItemStack outputStack = itemStacks[outputSlot];

				if (outputStack == null) {
					firstSuitableOutputSlot = outputSlot;
					break;
				}

				if (outputStack.getItem() == result.getItem() && (!outputStack.getHasSubtypes()
						|| outputStack.getMetadata() == result.getMetadata())) {
					int combinedSize = itemStacks[outputSlot].stackSize + result.stackSize;

					if (combinedSize <= getInventoryStackLimit()
							&& combinedSize <= itemStacks[outputSlot].getMaxStackSize()) {
						firstSuitableOutputSlot = outputSlot;
						break;
					}
				}
			}
		} else return false;
		if (!performProcess) {
			return true;
		}
		if (itemStacks != null) {
			itemStacks[1].stackSize--;
			if (itemStacks[1].stackSize <= 0) itemStacks[1] = null;
		}
		if (itemStacks[2] != null) {
			itemStacks[2].stackSize--;
			if (itemStacks[2].stackSize <= 0) itemStacks[2] = null;
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
	
	@Override
	public void update() {
		if (!worldObj.isRemote) {
			if (canProcess(FluxFurnaceRecipes.instance())) {
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
					processItem(FluxFurnaceRecipes.instance());
					processTime = 0;
				}
			} else {
				if (burnTimeRemaining != null) {
					for (int i = 0; i < burnTimeRemaining.length; i++) {
						if (burnTimeRemaining[i] > 0) {
							burnTimeRemaining[i]--;
						}
					}
				}
				processTime = 0;
			}

			int numberBurning = 0;

			if (setupDone) { 			
				if (this.burnTimeRemaining != null)
				{
					numberBurning = this.numberOfBurningFuelSlots();
				}
			}

			if (cachedNumberOfBurningSlots != numberBurning) {
				cachedNumberOfBurningSlots = numberBurning;

				if (worldObj.isRemote) {
					worldObj.markBlockRangeForRenderUpdate(pos, pos);
				}

				worldObj.checkLightFor(EnumSkyBlock.BLOCK, pos);
			}
			BlockMachine thisBlock = ((BlockMachine)worldObj.getBlockState(pos).getBlock());
			if (burnTimeRemaining != null) {
				for (int burnTime : burnTimeRemaining) {
					if (burnTime > 0) {
						if (thisBlock.isWorking == false) {
							BlockFluxFurnace.setState(true, worldObj, pos);
						}
						return;
					}
				}
			}
			if (thisBlock.isWorking == true) {
				BlockFluxFurnace.setState(false, worldObj, pos);
			}
		}
	}
	
	@Override
	public boolean isItemValidForFuelSlot(ItemStack stack) {
		return (getItemBurnTime(stack) > 0) ? true : false;
	}

	@Override
	public boolean isItemValidForInputSlot(ItemStack stack) {
		return FluxFurnaceRecipes.instance().isPartOfRecipe(stack);
	}

	@Override
	public boolean isItemValidForOutputSlot(ItemStack stack) {
		return false;
	}
	
	public void setup()
	{
		if (!setupDone)
		{
			fuel_slots = 1;
			input_slots = 2;
			output_slots = 1;
			
			total_slots = fuel_slots + input_slots + output_slots;
			first_input_slot = first_fuel_slot + fuel_slots;
			first_output_slot = first_input_slot + input_slots;
			itemStacks = new ItemStack[total_slots];
			burnTimeInitial = new int[fuel_slots];
			burnTimeRemaining = new int[fuel_slots];
			
			/** Used in the calculation of getField **/
			first_burn_time_initial_field_id = (byte) (first_burn_time_remaining_field_id + fuel_slots);
			number_of_fields = (byte) (first_burn_time_initial_field_id + fuel_slots);
			
			setupDone = true;
		}
	}
	
	private ItemStack[] getInputSlots() {
		return new ItemStack[]{itemStacks[1], itemStacks[2]};
	}
	
}
