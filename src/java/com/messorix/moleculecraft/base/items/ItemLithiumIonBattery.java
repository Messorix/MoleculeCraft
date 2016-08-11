package com.messorix.moleculecraft.base.items;

public class ItemLithiumIonBattery extends ModItemEnergy {

	public ItemLithiumIonBattery() {
		super("li_battery", "li_battery");
	}
	
	public ItemLithiumIonBattery setOutputPerTick(ModItem positiveElectrode)
	{
		int outputRate = 0;
		
		System.out.println(positiveElectrode.getClass().toString());
		
		switch (positiveElectrode.getClass().toString())
		{
			case "":
				break;
		}
		
		this.setMaxExtract(outputRate);
		return this;
	}
}