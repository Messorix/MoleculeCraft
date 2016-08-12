package com.messorix.moleculecraft.base.tileentities;

import com.messorix.moleculecraft.base.crafting.FluxGrinderRecipes;

import net.minecraft.item.ItemStack;
import net.minecraft.world.EnumSkyBlock;

public class TileEntityFluxGrinder extends ModTileEntity
{
	private static boolean setupDone = false; 
	
	public TileEntityFluxGrinder()
	{
		setup();
	}
	
	@Override
	public String getName() {
		return "container.tile_entity_flux_grinder.name";
	}

	public void setCustomInventoryName(String displayName) {
	}
	
	@Override
	public void update() {
		if (!worldObj.isRemote) {
			if (canProcess(FluxGrinderRecipes.instance())) {
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
					processItem(FluxGrinderRecipes.instance());
					processTime = 0;
				}
			} else {
				processTime = 0;
			}

			int numberBurning = 0;

			if (setupDone) { 			
				if (super.burnTimeRemaining != null)
				{
					numberBurning = super.numberOfBurningFuelSlots();
				}
			}

			if (cachedNumberOfBurningSlots != numberBurning) {
				cachedNumberOfBurningSlots = numberBurning;

				if (worldObj.isRemote) {
					worldObj.markBlockRangeForRenderUpdate(pos, pos);
				}

				worldObj.checkLightFor(EnumSkyBlock.BLOCK, pos);
			}
		}
	}

	@Override
	public boolean isItemValidForFuelSlot(ItemStack stack) {
		return (getItemBurnTime(stack) > 0) ? true : false;
	}

	@Override
	public boolean isItemValidForInputSlot(ItemStack stack) {
		return (getProcessingResultForItem(FluxGrinderRecipes.instance(), stack) != null) ? true : false;
	}

	@Override
	public boolean isItemValidForOutputSlot(ItemStack stack) {
		return false;
	}
	
	private void setup()
	{
		if (!setupDone)
		{
			fuel_slots = 1;
			input_slots = 1;
			output_slots = 2;
			
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
}
