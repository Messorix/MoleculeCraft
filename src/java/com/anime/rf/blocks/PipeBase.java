package com.anime.rf.blocks;

import java.util.ArrayList;
import java.util.List;

import com.anime.rf.network.EnergyNetwork;
import com.anime.rf.network.EnergyNetworkProvider;

import cofh.api.energy.IEnergyHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
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
		if (type == EnumPipeType.ENERGY && world.getTileEntity(checkinPos) instanceof IEnergyHandler) return true;
		if (type == EnumPipeType.FLUID && world.getTileEntity(checkinPos) instanceof IFluidHandler) return true;
		if (world.getBlockState(checkinPos).getBlock() instanceof PipeBase) {
			return ((PipeBase)world.getBlockState(checkinPos).getBlock()).getType() == type || ((PipeBase)world.getBlockState(checkinPos).getBlock()).getType() == EnumPipeType.ALL || type == EnumPipeType.ALL;
		}
		return false;
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if (!world.isRemote) {
			List<EnergyNetwork> nets = new ArrayList<EnergyNetwork>();
			for (EnumFacing facing : EnumFacing.VALUES) {
				for (EnergyNetwork net : world.getCapability(EnergyNetworkProvider.ENERGY_NETWORK_CAPABILITY, null).networksContainingPos(pos.offset(facing))) {
					nets.add(net);
				}
			}
			EnergyNetwork net = null;
			if (nets.isEmpty()) {
				net = EnergyNetwork.createNetwork();
				net.addConnection(world, pos);
				world.getCapability(EnergyNetworkProvider.ENERGY_NETWORK_CAPABILITY, null).addNetwork(net);
			} else if (allTheSame(nets)) {
				net = nets.get(0);
				net.addConnection(world, pos);
			} else {
				net = world.getCapability(EnergyNetworkProvider.ENERGY_NETWORK_CAPABILITY, null).combineNetworks(world, nets);
				net.addConnection(world, pos);
			}
			for (EnumFacing facing : EnumFacing.VALUES) {
				if (canConnectTo(world, pos.offset(facing), pos)) {
					if (!net.connections.contains(pos.offset(facing))) net.addConnection(world, pos.offset(facing));
				}
			}
		}
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		super.breakBlock(world, pos, state);
		if (!world.isRemote) {
			List<EnergyNetwork> nets = world.getCapability(EnergyNetworkProvider.ENERGY_NETWORK_CAPABILITY, null).networksContainingPos(pos);
			if (!nets.isEmpty()) {
				for (EnumFacing facing : EnumFacing.VALUES) {
					BlockPos pos2 = pos.offset(facing);
					boolean noConnections = true;
					for (EnumFacing face : EnumFacing.VALUES) {
						if (canConnectTo(world, pos2.offset(face), pos2)) noConnections = false;
					}
					if (noConnections && !(world.getBlockState(pos2).getBlock() instanceof PipeBase)) {
						for (EnergyNetwork net : world.getCapability(EnergyNetworkProvider.ENERGY_NETWORK_CAPABILITY, null).networksContainingPos(pos2)) {
							net.removeConnection(world, pos2);
							world.getCapability(EnergyNetworkProvider.ENERGY_NETWORK_CAPABILITY, null).separateNetworks(world, net, new ArrayList<BlockPos>());
						}
					} else {
						List<EnergyNetwork> networks = new ArrayList<EnergyNetwork>();
						for (EnumFacing face : EnumFacing.VALUES) {
							for (EnergyNetwork net : world.getCapability(EnergyNetworkProvider.ENERGY_NETWORK_CAPABILITY, null).networksContainingPos(pos2.offset(face))) {
								if (!networks.contains(net)) networks.add(net);
							}
						}
						if (!networks.isEmpty()) {
							for (EnergyNetwork net : nets) {
								if (!networks.contains(net)) {
									net.removeConnection(world, pos2);
								}
								net.removeConnection(world, pos);
								world.getCapability(EnergyNetworkProvider.ENERGY_NETWORK_CAPABILITY, null).separateNetworks(world, net, new ArrayList<BlockPos>());
							}
						}
					}
				}
			}
		}
	}
	
	// TODO: Remove after it has been determined that it is not neccesary.
	@Override
	public void onNeighborChange(IBlockAccess worldIn, BlockPos pos, BlockPos neighbor) {
		if (worldIn instanceof World) {
			System.out.println("Neighbor change is World based.");
			World world = (World) worldIn;
			if (!world.isRemote) {
				List<EnergyNetwork> nets = world.getCapability(EnergyNetworkProvider.ENERGY_NETWORK_CAPABILITY, null).networksContainingPos(pos);
				if (canConnectTo(world, neighbor, pos)) {
					if (world.getCapability(EnergyNetworkProvider.ENERGY_NETWORK_CAPABILITY, null).networksContainingPos(neighbor).isEmpty()) {
						for (EnergyNetwork net : nets) {
							net.addConnection(world, neighbor);
						}
					}
				} else {
					System.out.println("Can't connect to.");
				}
			}
		}
	}
	
	protected boolean allTheSame(List<EnergyNetwork> networks) {
		if (networks.size() == 1) return true;
		List<Boolean> booleans = new ArrayList<Boolean>();
		for (EnergyNetwork net : networks) {
			if (net.equals(networks.get(0))) {
				booleans.add(true);
			} else booleans.add(false);
		}
		return !booleans.contains(false);
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn) {
		Block.addCollisionBoxToList(pos, entityBox, collidingBoxes, NO_CONNECTIONS);
		if (state.getActualState(world, pos).getValue(UP_CONNECTION).booleanValue() == true) Block.addCollisionBoxToList(pos, entityBox, collidingBoxes, UP_CONNECTIONS);
		if (state.getActualState(world, pos).getValue(DOWN_CONNECTION).booleanValue() == true) Block.addCollisionBoxToList(pos, entityBox, collidingBoxes, DOWN_CONNECTIONS);
		if (state.getActualState(world, pos).getValue(NORTH_CONNECTION).booleanValue() == true) Block.addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_CONNECTIONS);
		if (state.getActualState(world, pos).getValue(SOUTH_CONNECTION).booleanValue() == true) Block.addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_CONNECTIONS);
		if (state.getActualState(world, pos).getValue(WEST_CONNECTION).booleanValue() == true) Block.addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_CONNECTIONS);
		if (state.getActualState(world, pos).getValue(EAST_CONNECTION).booleanValue() == true) Block.addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_CONNECTIONS);
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
	public AxisAlignedBB getBoundingBox(IBlockState blockState, IBlockAccess world, BlockPos pos) {
		return NO_CONNECTIONS;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World world, BlockPos pos) {
		return NO_CONNECTIONS;
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public RayTraceResult collisionRayTrace(IBlockState state, World world, BlockPos pos, Vec3d start, Vec3d end) {
		for (AxisAlignedBB aabb : addCollisionBoxToList(state, world, pos)) {
			RayTraceResult trace = rayTrace(pos, start, end, aabb);
			if (trace != null) return trace;
		}
		return super.collisionRayTrace(state, world, pos, start, end);
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
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
