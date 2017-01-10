package com.messorix.moleculecraft.base.events;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.anime.rf.blocks.PipeBase;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderSelectionEvent {
	
	@SubscribeEvent
	public void renderPipeSelectionOutline(DrawBlockHighlightEvent event) {
		EntityPlayer player = event.getPlayer();
		World world = player.worldObj;
		RayTraceResult trace = player.rayTrace(5, event.getPartialTicks());
		BlockPos pos = trace.getBlockPos();
		if (pos == null) return;
		if (world.getBlockState(pos).getBlock() instanceof PipeBase) {
			PipeBase pipe = (PipeBase) world.getBlockState(pos).getBlock();
			List<AxisAlignedBB> collsion = pipe.addCollisionBoxToList(world.getBlockState(pos), world, pos);
			GL11.glTranslatef(pos.getX(), pos.getY(), pos.getZ());
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.glLineWidth(2.0F);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
			float partialTicks = event.getPartialTicks();
			for (AxisAlignedBB aabb : collsion) {
                double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)partialTicks;
                double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)partialTicks;
                double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)partialTicks;
				RenderGlobal.drawSelectionBoundingBox(aabb.expandXyz(0.0020000000949949026D).offset(-d0, -d1, -d2), 0.0F, 0.0F, 0.0F, 0.4F);
			}
            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
			event.setCanceled(true);
		}
	}
	
}
