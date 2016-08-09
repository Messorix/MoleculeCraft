package com.messorix.moleculecraft.base.classes;

import javax.annotation.Generated;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@Generated("org.jsonschema2pojo")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModAtom extends ModMoleculeBase {

	@JsonProperty("atomicNumber")
	private int ATOMNUMBER;

	@JsonProperty("symbol")
	private String SYMBOL;

	@JsonProperty("name")
	private String NAME;

	@JsonProperty("atomicMass")
	private float ATOMICMASS;

	@JsonProperty("meltingPoint")
	private float MELTINGPOINT;

	@JsonProperty("boilingPoint")
	private float BOILINGPOINT;

	@JsonProperty("radioActive")
	public boolean RADIOACTIVE;

	
	public ModAtom() {
		super();
	}

	
	/**
	 * 
	 * @return The atomicNumber
	 */
	public ModAtom setRadioActive(boolean radioActive) {
		this.RADIOACTIVE = radioActive;
		return this;
	}

	/**
	 * 
	 * @return The atomicNumber
	 */
	public boolean getRadioActive() {
		return RADIOACTIVE;
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
	public float getAtomicMass() {
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

	public ModAtom setSymbol(String string) {
		SYMBOL = string;
		return this;
	}


}