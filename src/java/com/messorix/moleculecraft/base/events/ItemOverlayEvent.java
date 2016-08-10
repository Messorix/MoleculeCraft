package com.messorix.moleculecraft.base.events;

import com.messorix.moleculecraft.base.ModBlocks;
import com.messorix.moleculecraft.base.blocks.BlockOre;

import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemOverlayEvent {
	
	/**
	 * Adds the molecule to the items tooltip when hovered over in a gui.
	 * @param event Minecraft Forge event that is called when an item is hovered over in a gui.
	 */
	@SubscribeEvent
	public void hoverOverItem(ItemTooltipEvent event) {
		for (BlockOre ore : ModBlocks.oreblocklist) {
			if (Item.getItemFromBlock(ore) == event.getItemStack().getItem()) {
				if (ore.MOLECULE.getAtomsAndMolecules() != null && !ore.MOLECULE.getAtomsAndMolecules().isEmpty()) {
					event.getToolTip().add(ore.MOLECULE.toString());
				}
			}
		}
	}
	
}
