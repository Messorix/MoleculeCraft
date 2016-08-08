package com.messorix.moleculecraft.base.gui;

import com.messorix.moleculecraft.base.containers.ContainerFluxFurnace;
import com.messorix.moleculecraft.base.tileentities.TileEntityFluxFurnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandlerFluxFurnace implements IGuiHandler
{
	public static final int FLUX_FURNACE_GUI = 17002;
	public static int getGUIID() { return FLUX_FURNACE_GUI; }
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == FLUX_FURNACE_GUI)
		{
			return new ContainerFluxFurnace(player.inventory, (TileEntityFluxFurnace)world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == FLUX_FURNACE_GUI)
		{
			return new GuiFluxFurnace(player.inventory, (TileEntityFluxFurnace)world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}
	
}
