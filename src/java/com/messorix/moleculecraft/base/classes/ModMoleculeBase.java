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
		String returnable = String.valueOf(this.amount);
		
	    returnable = returnable.replaceAll("0", "₀");
	    returnable = returnable.replaceAll("1", "₁");
	    returnable = returnable.replaceAll("2", "₂");
	    returnable = returnable.replaceAll("3", "₃");
	    returnable = returnable.replaceAll("4", "₄");
	    returnable = returnable.replaceAll("5", "₅");
	    returnable = returnable.replaceAll("6", "₆");
	    returnable = returnable.replaceAll("7", "₇");
	    returnable = returnable.replaceAll("8", "₈");
	    returnable = returnable.replaceAll("9", "₉");
		
		return returnable;
	}
	

	public ModMoleculeBase setAmount(int amount) {
		this.amount = amount;
		return this;
	}
	
}
