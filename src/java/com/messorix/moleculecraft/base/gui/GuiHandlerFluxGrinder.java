package com.messorix.moleculecraft.base.gui;

import com.messorix.moleculecraft.base.containers.ContainerFluxGrinder;
import com.messorix.moleculecraft.base.tileentities.TileEntityFluxGrinder;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandlerFluxGrinder implements IGuiHandler
{
	public static final int FLUX_GRINDER_GUI = 17001;
	public static int getGUIID() { return FLUX_GRINDER_GUI; }
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == FLUX_GRINDER_GUI)
		{
			return new ContainerFluxGrinder(player.inventory, (TileEntityFluxGrinder)world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == FLUX_GRINDER_GUI)
		{
			return new GuiFluxGrinder(player.inventory, (TileEntityFluxGrinder)world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}
	
}
