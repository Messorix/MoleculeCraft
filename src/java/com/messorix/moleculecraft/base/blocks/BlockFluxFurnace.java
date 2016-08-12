package com.messorix.moleculecraft.base.blocks;

import java.util.Random;

import com.anime.basic.MainModReference;
import com.anime.basic.network.GuiHandler;
import com.messorix.moleculecraft.base.ModBlocks;
import com.messorix.moleculecraft.base.tileentities.TileEntityFluxFurnace;

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

public class BlockFluxFurnace extends BlockMachine
{
	public BlockFluxFurnace(String unlocalizedName, String registryName, boolean isOn)
	{
		super(unlocalizedName, registryName, Material.IRON, isOn);
	}
	
	@Override
	// TODO Make "Machine Block"
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(ModBlocks.FLUX_FURNACE);
	}
	
    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(ModBlocks.FLUX_FURNACE);
    }
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{
		return new TileEntityFluxFurnace();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (player.isSneaking()) return false;
		player.openGui(MainModReference.MODID, GuiHandler.FLUX_FURNACE, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	
	@Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

        if (stack.hasDisplayName())
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityFluxFurnace)
            {
                ((TileEntityFluxFurnace)tileentity).setCustomInventoryName(stack.getDisplayName());
            }
        }
    }
	
	@Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!keepInventory)
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityFluxFurnace)
            {
                InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityFluxFurnace)tileentity);
                worldIn.updateComparatorOutputLevel(pos, this);
            }
        }

        super.breakBlock(worldIn, pos, state);
    }
	
    public static void setState(boolean active, World worldIn, BlockPos pos)
    {
    	BlockMachine.setState(active, worldIn, pos, ModBlocks.FLUX_FURNACE_ON, ModBlocks.FLUX_FURNACE);
    }
	
}