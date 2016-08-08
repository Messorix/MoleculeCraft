package com.messorix.moleculecraft.base.classes;

public class ModAtom 
{
	public static int ATOMNUMBER;
	public static String SYMBOL;
	public static String NAME;
	public static float WEIGHT;
	public static boolean RADIOACTIVE;
	public static float MELTINGPOINT;
	public static float BOILINGPOINT;
	
	public ModAtom(int atomNum, String symbol, String name, float weight, boolean ra, float mp, float bp)	
	{
		ATOMNUMBER = atomNum;
		SYMBOL = symbol;
		NAME = name;
		WEIGHT = weight;
		RADIOACTIVE = ra;
		MELTINGPOINT = mp;
		BOILINGPOINT = bp;
	}
}