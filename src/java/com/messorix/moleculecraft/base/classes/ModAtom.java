package com.messorix.moleculecraft.base.classes;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ModAtom {

	private int ATOMNUMBER;
	private String SYMBOL;
	private String NAME;
	private String ATOMICMASS;
	private float MELTINGPOINT;
	private float BOILINGPOINT;

	public boolean RADIOACTIVE;

	public ModAtom()
	{
		
	}
	
	public ModAtom(int atomNum, String symbol, String name, String weight, boolean ra, float mp, float bp) {
		ATOMNUMBER = atomNum;
		SYMBOL = symbol;
		NAME = name;
		ATOMICMASS = weight;
		RADIOACTIVE = ra;
		MELTINGPOINT = mp;
		BOILINGPOINT = bp;
	}

	/**
	 * 
	 * @return The atomicNumber
	 */
	public int getAtomicNumber() {
		return ATOMNUMBER;
	}

	/**
	 * 
	 * @return The symbol
	 */
	public String getSymbol() {
		return SYMBOL;
	}

	/**
	 * 
	 * @return The name
	 */
	public String getName() {
		return NAME;
	}

	/**
	 * 
	 * @return The atomicMass
	 */
	public String getAtomicMass() {
		return ATOMICMASS;
	}

	/**
	 * 
	 * @return The meltingPoint
	 */
	public float getMeltingPoint() {
		return MELTINGPOINT;
	}

	/**
	 * 
	 * @return The boilingPoint
	 */
	public float getBoilingPoint() {
		return BOILINGPOINT;
	}
}