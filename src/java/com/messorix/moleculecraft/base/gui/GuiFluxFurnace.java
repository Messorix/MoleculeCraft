package com.messorix.moleculecraft.base.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.messorix.moleculecraft.base.Reference;
import com.messorix.moleculecraft.base.containers.ContainerFluxFurnace;
import com.messorix.moleculecraft.base.tileentities.TileEntityFluxFurnace;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiFluxFurnace  extends GuiContainer
{
    private static final ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/flux_furnace_gui.png");
    private final TileEntityFluxFurnace entity;

    public GuiFluxFurnace(InventoryPlayer player, TileEntityFluxFurnace entity)
    {
        super(new ContainerFluxFurnace(player, entity));
        
        xSize = 176;
        ySize = 166;
        
        this.entity = entity;
    }

    final int process_bar_xpos = 49;
    final int process_bar_ypos = 60;
    final int process_bar_icon_u = 176;
    final int process_bar_icon_v = 14;
    final int process_bar_width = 24;
    final int process_bar_height = 17;

    final int flame_xpos = 57;
    final int flame_ypos = 37;
    final int flame_icon_u = 176;
    final int flame_icon_v = 0;
    final int flame_width = 14;
    final int flame_height = 14;
    final int flame_x_spacing = 18;
    
    /**
     * Args : renderPartialTicks, mouseX, mouseY
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
    	Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
    	GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    	this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, xSize, ySize);
    	
    	double processProgress = entity.fractionOfProcessingTimeComplete();
    	this.drawTexturedModalRect(this.guiLeft + process_bar_xpos, 
    			this.guiTop + process_bar_ypos, 
    			process_bar_icon_u, 
    			process_bar_icon_v, 
    			(int)(processProgress * process_bar_width), 
    			 process_bar_height);
    	
    	for ( int i = 0; i < entity.fuel_slots; i++)
    	{
    		double burnRemaining = entity.fractionOfFuelRemaining(i);
    		int yOffset = (int)((1.0 - burnRemaining) * flame_height);
    		
    		this.drawTexturedModalRect(guiLeft + flame_xpos + flame_x_spacing * i, 
    				guiTop + flame_ypos + yOffset, 
    				flame_icon_u, 
    				flame_icon_v + yOffset, 
    				flame_width, 
    				flame_height - yOffset);
    	}
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
    	super.drawGuiContainerForegroundLayer(mouseX, mouseY);

    	final int label_xpos = 5;
    	final int label_ypos = 5;
    	
    	fontRendererObj.drawString(entity.getDisplayName().getUnformattedText(), label_xpos, label_ypos, Color.DARK_GRAY.getRGB());
    	
    	List<String> text = new ArrayList<String>();
    	
    	if (isInRect(guiLeft + process_bar_xpos, guiTop + process_bar_ypos, process_bar_width, process_bar_height, mouseX, mouseY))
    	{
    		text.add("Progress:");
    		
    		int processPercentage = (int)(entity.fractionOfProcessingTimeComplete() * 100);
    		
    		text.add(processPercentage + "%");
    	}
    	
    	for(int i = 0; i < entity.fuel_slots; i++)
    	{
        	if (isInRect(guiLeft + flame_xpos + flame_x_spacing * i, guiTop + flame_ypos, flame_width, flame_height, mouseX, mouseY))
        	{
        		text.add("Fuel Time: ");
        		text.add(entity.secondsOfFuelRemaining(i) + "s");
        	}
    	}
    	
    	if (!text.isEmpty())
    	{
    		drawHoveringText(text, mouseX - guiLeft, mouseY - guiTop, fontRendererObj);
    	}
    }
    
    public static boolean isInRect(int x, int y, int xSize, int ySize, int mouseX, int mouseY)
    {
    	return ((mouseX >= x && mouseX <= x + xSize) && (mouseY >= y && mouseY <= y + ySize));
    }
 }