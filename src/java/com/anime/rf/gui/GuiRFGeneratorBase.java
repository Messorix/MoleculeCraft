package com.anime.rf.gui;

import java.util.ArrayList;
import java.util.List;

import com.anime.basic.MainModReference;
import com.anime.rf.containers.ContainerRFGeneratorBase;
import com.anime.rf.tileentity.TileEntityRFGenerator;
import com.anime.rf.tileentity.TileEntityRFGeneratorBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

public class GuiRFGeneratorBase extends GuiContainer {

	protected TileEntityRFGeneratorBase tile;
	
	/** A copy of slots from the container used to get positions. **/
	protected List<Slot> inventorySlots = new ArrayList<Slot>();
	
	/** Just the amount of Fuel Slots **/
	protected int FUEL_SLOTS;
	
	/** True if this GUI should render a time remaining fuel bar. **/
	protected boolean renderFuelGauge = true;
	
	/** The booleans to determine the directions to draw. **/
	protected boolean fuelDirection, outputDirection;
	
	/**
	 * Basic version of this GUIs constructor.
	 * @param iPlayer The inventory of the player that opened this.
	 * @param tile The TileEntityRFGenerator of this container.
	 * @param fuelDirection The direction that the fuel slots should go(true is left-right).
	 * @param outputDirection The direction that the output slots should go(true is left-right)
	 */
	public GuiRFGeneratorBase(InventoryPlayer iPlayer, TileEntityRFGenerator tile, boolean fuelDirection, boolean outputDirection) {
		super(new ContainerRFGeneratorBase(iPlayer, tile, fuelDirection, outputDirection));
		this.tile = tile;
		this.inventorySlots = new ContainerRFGeneratorBase(iPlayer, tile, fuelDirection, outputDirection).inventorySlots;
		xSize = 176;
        ySize = 166;
        FUEL_SLOTS = tile.getFuelSlots();
        this.fuelDirection = fuelDirection;
        this.outputDirection = outputDirection;
	}
	
	/**
	 * More customizable constructor
	 * @param iPlayer The inventory of the player that opened this.
	 * @param tile The TileEntityRFGenerator of this container.
	 * @param slots The slots of the specified inventory excluding players inventory.
	 */
	public GuiRFGeneratorBase(InventoryPlayer iPlayer, TileEntityRFGenerator tile, Slot[] slots, boolean fuelDirection) {
		super(new ContainerRFGeneratorBase(iPlayer, tile, slots));
		this.tile = tile;
		this.inventorySlots = new ContainerRFGeneratorBase(iPlayer, tile, slots).inventorySlots;
		xSize = 176;
        ySize = 166;
        this.fuelDirection = fuelDirection;
	}
	
	@Override
 	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		// Temp I will fix later
		fontRendererObj.drawString("Energy: " + tile.getField(0), guiLeft + 50, guiTop + 5, 0xFFFFFF);
		fontRendererObj.drawString("RF/T: " + tile.getField(1), guiLeft + 50, guiTop + 15, 0xFFFFFF);
		fontRendererObj.drawString("Remaining Ticks: " + tile.getField(2), guiLeft + 50, guiTop + 25, 0xFFFFFF);
		fontRendererObj.drawString("Last Fuels Ticks: " + tile.getField(3), guiLeft + 50, guiTop + 35, 0xFFFFFF);
		if (tile.getTicksRemaining() > 0 && tile.getLastFuelsTicks() > 0) {
    		double burnRemaining = (double) tile.getField(2) / (double) tile.getField(3);
    		int yOffset = (int)((1.0 - burnRemaining) * 14);
			fontRendererObj.drawString("Offset: " + yOffset, guiLeft + 50, guiTop + 45, 0xFFFFFF);
		}
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		Minecraft.getMinecraft().getTextureManager().bindTexture(getBaseResourceLocation());
    	drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    	
    	GlStateManager.disableLighting();
    	Minecraft.getMinecraft().getTextureManager().bindTexture(getSpritesResourceLocation());
    	for (int i = 0; i < inventorySlots.size(); i++) {
    		Slot slot = inventorySlots.get(i);
    		int startingX = guiLeft + slot.xDisplayPosition - 1;
    		int startingY = guiTop + slot.yDisplayPosition - 1;
    		drawTexturedModalRect(startingX, startingY, 238, 0, 18, 18);
    		if (renderFuelGauge && FUEL_SLOTS - 1 == i) {
    			if (fuelDirection) {
    				drawTexturedModalRect(startingX + 20, startingY + 2, 14, 0, 14, 14);
    				if (tile.getTicksRemaining() > 0 && tile.getLastFuelsTicks() > 0) {
    					this.mc.getTextureManager().bindTexture(getSpritesResourceLocation());
    		    		double burnRemaining = (double) tile.getField(2) / (double) tile.getField(3);
    		    		int yOffset = (int)((1.0 - burnRemaining) * 14);
    					this.drawTexturedModalRect(startingX + 20, startingY + 2 + yOffset, 0, 0 + yOffset, 14, 14 - yOffset);
    				}
    			} else {
    				drawTexturedModalRect(startingX + 20, startingY + 2, 14, 0, 14, 14);
    				if (tile.getTicksRemaining() > 0 && tile.getLastFuelsTicks() > 0) {
    		    		double burnRemaining = (double) tile.getField(2) / (double) tile.getField(3);
    		    		int yOffset = (int)((1.0 - burnRemaining) * 14);
    					this.drawTexturedModalRect(startingX + 20, startingY + 2 + yOffset, 0, 0 + yOffset, 14, 14 - yOffset);
    				}
    			}
        		
    		}
    	}
    	GlStateManager.disableLighting();
	}
	
	/** The base resource location for the GUI. **/
	protected ResourceLocation getBaseResourceLocation() {
		return new ResourceLocation(MainModReference.MODID, "textures/gui/base/main.png");
	}
	
	/** The GUI sprite sheet location. **/
	protected ResourceLocation getSpritesResourceLocation() {
		return new ResourceLocation(MainModReference.MODID, "textures/gui/base/gui_spritesheet.png");
	}
	
	/** If the mouse position passed is within the bounding box. **/
    protected boolean isMouseHovering(int x, int y, int xSize, int ySize, int mouseX, int mouseY) {
    	return ((mouseX >= x && mouseX <= x + xSize) && (mouseY >= y && mouseY <= y + ySize));
    }
	
}
