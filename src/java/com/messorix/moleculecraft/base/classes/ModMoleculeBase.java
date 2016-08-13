package com.messorix.moleculecraft.base.classes;

public class ModMoleculeBase {

	private int amount = 0;
		
	public ModMoleculeBase() {
		
	}
	
	public int getAmount()
	{
		return this.amount;
	}

	public String getStringAmount() {
		String returnable = "";
		
		for (char c : String.valueOf(this.amount).toCharArray())
		{
			switch(c)
			{
				case '0': 
					returnable += "⁰";
					break;
				case '1':
					returnable += "¹";
					break;
				case '2':
					returnable += "²";
					break;
				case '3':
					returnable += "³";
					break;
				case '4':
					returnable += "⁴";
					break;
				case '5':
					returnable += "⁵";
					break;
				case '6':
					returnable += "⁶";
					break;
				case '7':
					returnable += "⁷";
					break;
				case '8':
					returnable += "⁸";
					break;
				case '9':
					returnable += "⁹";
					break;
				default:
					break;
			}
		}
		
		return returnable;
	}
	

	public ModMoleculeBase setAmount(int amount) {
		this.amount = amount;
		return this;
	}
	
}
