package com.messorix.moleculecraft.base.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.anime.basic.MainModReference;
import com.anime.basic.network.GuiHandler;
import com.messorix.moleculecraft.base.init.ModBlocks;
import com.messorix.moleculecraft.base.tileentities.TileEntityFluxGrinder;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockFluxGrinder extends BlockMachine 
{
	public BlockFluxGrinder(String unlocalizedName, String registryName, boolean isOn) 
	{
		super(unlocalizedName, registryName, Material.IRON, isOn);
	}

	@Override
	// TODO Make "Machine Block"
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(ModBlocks.FLUX_GRINDER);
	}

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(ModBlocks.FLUX_GRINDER);
    }
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityFluxGrinder();
	}

    public static void setState(boolean active, World worldIn, BlockPos pos)
    {
    	BlockMachine.setState(active, worldIn, pos, ModBlocks.FLUX_GRINDER_ON, ModBlocks.FLUX_GRINDER);
    }
    
    @Override
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
    
    @Override
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

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
    	if (player.isSneaking()) return false;
       	player.openGui(MainModReference.MODID, GuiHandler.FLUX_GRINDER, world, pos.getX(), pos.getY(), pos.getZ());
       	return true;
    }
    
}
