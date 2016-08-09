package com.messorix.moleculecraft.base.tileentities;

import com.messorix.moleculecraft.base.crafting.FluxFurnaceRecipes;

import net.minecraft.item.ItemStack;
import net.minecraft.world.EnumSkyBlock;

public class TileEntityFluxFurnace extends ModTileEntity
{
	private static boolean setupDone = false;
	
	public TileEntityFluxFurnace()
	{
		setup();
	}
	
	public static ItemStack getProcessingResultForItem(ItemStack stack){
		return ModTileEntity.getProcessingResultForItem(FluxFurnaceRecipes.instance(), FluxFurnaceRecipes.instance().getProcessingResult(stack));
	}

	@Override
	public String getName() {
		return "container.tile_entity_flux_furnace.name";
	}

	public void setCustomInventoryName(String displayName) {
	}
	
	@Override
	public void update() {
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
	
	private void setup()
	{
		System.out.println("Setup runned");
		
		if (!setupDone)
		{
			System.out.println("Setup not done yet");
			
			fuel_slots = 1;
			input_slots = 2;
			output_slots = 1;
			
			total_slots = fuel_slots + input_slots + output_slots;
			first_input_slot = first_fuel_slot + input_slots;
			first_output_slot = first_input_slot + output_slots;
			itemStacks = new ItemStack[total_slots];
			burnTimeInitial = new int[fuel_slots];
			burnTimeRemaining = new int[fuel_slots];
			
			setupDone = true;
			System.out.println("Setup done");
		}
	}
}
