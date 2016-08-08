package com.messorix.moleculecraft.base.crafting;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import net.minecraft.item.ItemStack;

public class ModRecipes {
    private final Map processingList = Maps.newHashMap();
    private final Map experienceList = Maps.newHashMap();
    
    private static final ModRecipes processingBase = new ModRecipes();
    /** The list of grinding results. */

    public static ModRecipes instance()
    {
        return processingBase;
    }
    
    public void addProcessingRecipe(ItemStack parItemStackIn, ItemStack parItemStackOut, float parExperience)
    {
        processingList.put(parItemStackIn, parItemStackOut);
        experienceList.put(parItemStackOut, Float.valueOf(parExperience));
    }

    /**
     * Returns the Processing result of an item.
     */
    public ItemStack getProcessingResult(ItemStack parItemStack)
    {
        Iterator iterator = processingList.entrySet().iterator();
        Entry entry;

        do
        {
            if (!iterator.hasNext())
            {
                return null;
            }

            entry = (Entry)iterator.next();
        }
        while (!areItemStacksEqual(parItemStack, (ItemStack)entry.getKey()));

        return (ItemStack)entry.getValue();
    }

    private boolean areItemStacksEqual(ItemStack parItemStack1, 
          ItemStack parItemStack2)
    {
        return parItemStack2.getItem() == parItemStack1.getItem() 
              && (parItemStack2.getMetadata() == 32767 
              || parItemStack2.getMetadata() == parItemStack1
              .getMetadata());
    }

    public Map getProcessingList()
    {
        return processingList;
    }

    public float getProcessingExperience(ItemStack parItemStack)
    {
        Iterator iterator = experienceList.entrySet().iterator();
        Entry entry;

        do
        {
            if (!iterator.hasNext())
            {
                return 0.0F;
            }

            entry = (Entry)iterator.next();
        }
        while (!areItemStacksEqual(parItemStack, 
              (ItemStack)entry.getKey()));

        return ((Float)entry.getValue()).floatValue();
    }

}