package com.messorix.moleculecraft.base.events;

import java.util.Map.Entry;

import com.messorix.moleculecraft.base.classes.ModMolecule;
import com.messorix.moleculecraft.base.classes.ModMolecules;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemOverlayEvent {
	
	/**
	 * Adds the molecule to the items tooltip when hovered over in a gui.
	 * @param event Minecraft Forge event that is called when an item is hovered over in a gui.
	 */
	@SubscribeEvent
	public void hoverOverItem(ItemTooltipEvent event) {
		for (Entry<ItemStack, ModMolecule> entry : ModMolecules.objectMolecules.entrySet()) {
			if (areItemStacksEqual(event.getItemStack(), entry.getKey())) {
				ModMolecule molecule = entry.getValue();
				event.getToolTip().add((molecule.getAmount() > 0 && !molecule.toString().isEmpty()) ? "Molecule: " + molecule.toString() : "");
			}
		}
	}
	
	private boolean areItemStacksEqual(ItemStack parItemStack1, ItemStack parItemStack2) {
        return parItemStack2.getItem() == parItemStack1.getItem() && (parItemStack2.getMetadata() == 32767 || parItemStack2.getMetadata() == parItemStack1.getMetadata());
    }
	
}
