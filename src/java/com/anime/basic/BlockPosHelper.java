package com.anime.basic;

import net.minecraft.util.math.BlockPos;

public class BlockPosHelper {
	
	public static BlockPos intArrayToBlockPos(int[] pos) {
		return new BlockPos(pos[0], pos[1], pos[2]);
	}
	
	public static int[] blockPosToIntArray(BlockPos pos) {
		return new int[]{pos.getX(), pos.getY(), pos.getZ()};
	}
	
}
