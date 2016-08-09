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
					returnable += "₀";
					break;
				case '1':
					returnable += "₁";
					break;
				case '2':
					returnable += "₂";
					break;
				case '3':
					returnable += "₃";
					break;
				case '4':
					returnable += "₄";
					break;
				case '5':
					returnable += "₅";
					break;
				case '6':
					returnable += "₆";
					break;
				case '7':
					returnable += "₇";
					break;
				case '8':
					returnable += "₈";
					break;
				case '9':
					returnable += "₉";
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
