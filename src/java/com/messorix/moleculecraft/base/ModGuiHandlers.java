package com.messorix.moleculecraft.base;

import com.messorix.moleculecraft.base.gui.*;

import net.minecraftforge.fml.common.network.NetworkRegistry;

public class ModGuiHandlers 
{
	public static GuiHandlerFluxGrinder fluxGrinder = new GuiHandlerFluxGrinder();
	public static GuiHandlerFluxFurnace fluxFurnace = new GuiHandlerFluxFurnace();
    
	public void registerGuis()
    {
		NetworkRegistry.INSTANCE.registerGuiHandler(Reference.MOD_ID, fluxGrinder);
		//NetworkRegistry.INSTANCE.registerGuiHandler(Reference.MOD_ID, fluxFurnace);
    }
}
