package com.messorix.moleculecraft.base.blocks;

import com.anime.basic.MainModReference;
import com.anime.basic.network.GuiHandler;

import javax.annotation.Nullable;

import com.messorix.moleculecraft.base.ModBlocks;
import com.messorix.moleculecraft.base.tileentities.TileEntityFluxGrinder;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockFluxGrinder extends BlockMachine 
{
	public BlockFluxGrinder() 
	{
		super("flux_grinder", "flux_grinder", Material.ROCK, false);
		//burning_sides = PropertyInteger.create("burning_sides", 0, 1);
		//one_side_light_value = 15;
	}

	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityFluxGrinder();
	}

    public static void setState(boolean active, World worldIn, BlockPos pos)
    {
    	BlockMachine.setState(active, worldIn, pos, ModBlocks.FLUXGRINDERWORKING, ModBlocks.FLUXGRINDER);
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

        if (stack.hasDisplayName())
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityFluxGrinder)
            {
                ((TileEntityFluxGrinder)tileentity).setCustomInventoryName(stack.getDisplayName());
            }
        }
    }

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!keepInventory)
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityFluxGrinder)
            {
                InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityFluxGrinder)tileentity);
                worldIn.updateComparatorOutputLevel(pos, this);
            }
        }

        super.breakBlock(worldIn, pos, state);
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return super.getItem(worldIn, pos, state, ModBlocks.FLUXGRINDER); 
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
       	if (worldIn.isRemote)
       	{
       		return true;
       	}
       	
       	playerIn.openGui(MainModReference.MODID, GuiHandler.FLUX_GRINDER, worldIn, pos.getX(), pos.getY(), pos.getZ());
       	
       	return true;
    }
    
    /*@Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
    	TileEntityFluxGrinder te = (TileEntityFluxGrinder)world.getTileEntity(pos);
    	int burningSlots = te.numberOfBurningFuelSlots();
    	burningSlots = MathHelper.clamp_int(burningSlots, 0, 1);
    	return super.getActualState(state, world, pos, burningSlots);
    }*/
}
