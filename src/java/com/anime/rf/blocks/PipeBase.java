package com.anime.rf.blocks;

import java.util.ArrayList;
import java.util.List;

import cofh.api.energy.IEnergyProvider;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

public class PipeBase extends Block {

	protected EnumPipeType type;
	
	public static PropertyBool NORTH_CONNECTION = PropertyBool.create("north");
	public static PropertyBool SOUTH_CONNECTION = PropertyBool.create("south");
	public static PropertyBool WEST_CONNECTION = PropertyBool.create("west");
	public static PropertyBool EAST_CONNECTION = PropertyBool.create("east");
	public static PropertyBool UP_CONNECTION = PropertyBool.create("up");
	public static PropertyBool DOWN_CONNECTION = PropertyBool.create("down");
	
	public static final double PIXEL_LENGTH = (1D / 16D);
	public static final AxisAlignedBB NO_CONNECTIONS = new AxisAlignedBB((6 * PIXEL_LENGTH), (6 * PIXEL_LENGTH), (6 * PIXEL_LENGTH), (10 * PIXEL_LENGTH), (10 * PIXEL_LENGTH), (10 * PIXEL_LENGTH));
	public static final AxisAlignedBB UP_CONNECTIONS = new AxisAlignedBB((6 * PIXEL_LENGTH), (10 * PIXEL_LENGTH), (6 * PIXEL_LENGTH), (10 * PIXEL_LENGTH), 1, (10 * PIXEL_LENGTH));
	public static final AxisAlignedBB DOWN_CONNECTIONS = new AxisAlignedBB((6 * PIXEL_LENGTH), (6 * PIXEL_LENGTH), (6 * PIXEL_LENGTH), (10 * PIXEL_LENGTH), 0, (10 * PIXEL_LENGTH));
	public static final AxisAlignedBB NORTH_CONNECTIONS = new AxisAlignedBB((6 * PIXEL_LENGTH), (6 * PIXEL_LENGTH), (0 * PIXEL_LENGTH), (10 * PIXEL_LENGTH), (10 * PIXEL_LENGTH), (6 * PIXEL_LENGTH));
	public static final AxisAlignedBB SOUTH_CONNECTIONS = new AxisAlignedBB((6 * PIXEL_LENGTH), (6 * PIXEL_LENGTH), (10 * PIXEL_LENGTH), (10 * PIXEL_LENGTH), (10 * PIXEL_LENGTH), (16 * PIXEL_LENGTH));
	public static final AxisAlignedBB WEST_CONNECTIONS = new AxisAlignedBB((0 * PIXEL_LENGTH), (6 * PIXEL_LENGTH), (6 * PIXEL_LENGTH), (6 * PIXEL_LENGTH), (10 * PIXEL_LENGTH), (10 * PIXEL_LENGTH));
	public static final AxisAlignedBB EAST_CONNECTIONS = new AxisAlignedBB((10 * PIXEL_LENGTH), (6 * PIXEL_LENGTH), (6 * PIXEL_LENGTH), (16 * PIXEL_LENGTH), (10 * PIXEL_LENGTH), (10 * PIXEL_LENGTH));
	
	public PipeBase(Material materialIn, EnumPipeType type) {
		super(materialIn);
		this.type = type;
		this.setDefaultState(getDefaultState().withProperty(NORTH_CONNECTION, false).withProperty(SOUTH_CONNECTION, false).withProperty(WEST_CONNECTION, false).withProperty(EAST_CONNECTION, false).withProperty(UP_CONNECTION, false).withProperty(DOWN_CONNECTION, false));
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.withProperty(NORTH_CONNECTION, canConnectTo(world, pos.north(), pos)).withProperty(SOUTH_CONNECTION, canConnectTo(world, pos.south(), pos)).withProperty(WEST_CONNECTION, canConnectTo(world, pos.west(), pos)).withProperty(EAST_CONNECTION, canConnectTo(world, pos.east(), pos)).withProperty(UP_CONNECTION, canConnectTo(world, pos.up(), pos)).withProperty(DOWN_CONNECTION, canConnectTo(world, pos.down(), pos));
	}
	
	public boolean canConnectTo(IBlockAccess world, BlockPos checkinPos, BlockPos originalPos) {
		EnumFacing facing = null;
		for(EnumFacing facing1 : EnumFacing.HORIZONTALS) {
			facing = facing1;
			if (checkinPos.equals(originalPos.offset(facing))) break;
		}
		if (world.getTileEntity(checkinPos) != null && world.getTileEntity(checkinPos).hasCapability(CapabilityEnergy.ENERGY, facing)) return true;
		if (type == EnumPipeType.ITEM && (world.getTileEntity(checkinPos) instanceof ISidedInventory || world.getTileEntity(checkinPos).hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing))) return true;
		if (type == EnumPipeType.ENERGY && world.getTileEntity(checkinPos) instanceof IEnergyProvider) return true;
		if (type == EnumPipeType.FLUID && world.getTileEntity(checkinPos) instanceof IFluidHandler) return true;
		if (world.getBlockState(checkinPos).getBlock() instanceof PipeBase) {
			return ((PipeBase)world.getBlockState(checkinPos).getBlock()).getType() == type || ((PipeBase)world.getBlockState(checkinPos).getBlock()).getType() == EnumPipeType.ALL || type == EnumPipeType.ALL;
		}
		return false;
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn) {
		Block.addCollisionBoxToList(pos, entityBox, collidingBoxes, NO_CONNECTIONS);
		if (state.getActualState(worldIn, pos).getValue(UP_CONNECTION).booleanValue() == true) Block.addCollisionBoxToList(pos, entityBox, collidingBoxes, UP_CONNECTIONS);
		if (state.getActualState(worldIn, pos).getValue(DOWN_CONNECTION).booleanValue() == true) Block.addCollisionBoxToList(pos, entityBox, collidingBoxes, DOWN_CONNECTIONS);
		if (state.getActualState(worldIn, pos).getValue(NORTH_CONNECTION).booleanValue() == true) Block.addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_CONNECTIONS);
		if (state.getActualState(worldIn, pos).getValue(SOUTH_CONNECTION).booleanValue() == true) Block.addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_CONNECTIONS);
		if (state.getActualState(worldIn, pos).getValue(WEST_CONNECTION).booleanValue() == true) Block.addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_CONNECTIONS);
		if (state.getActualState(worldIn, pos).getValue(EAST_CONNECTION).booleanValue() == true) Block.addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_CONNECTIONS);
	}
	
	public List<AxisAlignedBB> addCollisionBoxToList(IBlockState state, World world, BlockPos pos) {
		List<AxisAlignedBB> boxes = new ArrayList<AxisAlignedBB>();
		boxes.add(NO_CONNECTIONS);
		if (state.getActualState(world, pos).getValue(UP_CONNECTION).booleanValue() == true) boxes.add(UP_CONNECTIONS);
		if (state.getActualState(world, pos).getValue(DOWN_CONNECTION).booleanValue() == true) boxes.add(DOWN_CONNECTIONS);
		if (state.getActualState(world, pos).getValue(NORTH_CONNECTION).booleanValue() == true) boxes.add(NORTH_CONNECTIONS);
		if (state.getActualState(world, pos).getValue(SOUTH_CONNECTION).booleanValue() == true) boxes.add(SOUTH_CONNECTIONS);
		if (state.getActualState(world, pos).getValue(WEST_CONNECTION).booleanValue() == true) boxes.add(WEST_CONNECTIONS);
		if (state.getActualState(world, pos).getValue(EAST_CONNECTION).booleanValue() == true) boxes.add(EAST_CONNECTIONS);
		return boxes;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return NO_CONNECTIONS;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
		return NO_CONNECTIONS;
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public RayTraceResult collisionRayTrace(IBlockState state, World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
		for (AxisAlignedBB aabb : addCollisionBoxToList(state, worldIn, pos)) {
			RayTraceResult trace = rayTrace(pos, start, end, aabb);
			if (trace != null) return trace;
		}
		return super.collisionRayTrace(state, worldIn, pos, start, end);
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return getCollisionBoundingBox(state, null, null) == FULL_BLOCK_AABB;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return isFullBlock(state);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState();
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, NORTH_CONNECTION, SOUTH_CONNECTION, WEST_CONNECTION, EAST_CONNECTION, UP_CONNECTION, DOWN_CONNECTION);
	}
	
	public EnumPipeType getType() {
		return type;
	}
	
	public static enum EnumPipeType {
		ITEM(), ENERGY(), FLUID(), ALL();
		
	}
	
}
