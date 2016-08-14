package com.messorix.moleculecraft.base.creativetabs;

import com.messorix.moleculecraft.base.init.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MoleculeCraftTab extends CreativeTabs
{
	public MoleculeCraftTab()
	{
		super("MoleculeCraft Base");
	}
	
    @SideOnly(Side.CLIENT)
    public Item getTabIconItem(){
        ItemStack iStack = new ItemStack(ModItems.COPPER_INGOT);
        return iStack.getItem();
    }
    
    public String getTranslatedLabel()
    {
    	return getTabLabel();
    }
}