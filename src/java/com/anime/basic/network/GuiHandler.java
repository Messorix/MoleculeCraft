package com.anime.basic.network;

import com.messorix.moleculecraft.base.containers.ContainerFluxFurnace;
import com.messorix.moleculecraft.base.containers.ContainerFluxGrinder;
import com.messorix.moleculecraft.base.gui.GuiFluxFurnace;
import com.messorix.moleculecraft.base.gui.GuiFluxGrinder;
import com.messorix.moleculecraft.base.tileentities.TileEntityFluxFurnace;
import com.messorix.moleculecraft.base.tileentities.TileEntityFluxGrinder;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	
	public static final int FLUX_GRINDER = 0;
	public static final int FLUX_FURNACE = 1;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity tile = world.getTileEntity(pos);
		switch (ID) {
		case FLUX_GRINDER:
			if (tile instanceof TileEntityFluxGrinder) return new ContainerFluxGrinder(player.inventory, (TileEntityFluxGrinder)tile);
			break;
		case FLUX_FURNACE: if (tile instanceof TileEntityFluxFurnace) return new ContainerFluxFurnace(player.inventory, (TileEntityFluxFurnace)tile);
		default: return null;
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity tile = world.getTileEntity(pos);
		switch (ID) {
		case FLUX_GRINDER:
			if (tile instanceof TileEntityFluxGrinder) return new GuiFluxGrinder(player.inventory, (TileEntityFluxGrinder)tile);
			break;
		case FLUX_FURNACE: if (tile instanceof TileEntityFluxFurnace) return new GuiFluxFurnace(player.inventory, (TileEntityFluxFurnace)tile);
		default: return null;
		}
		return null;
	}

}
