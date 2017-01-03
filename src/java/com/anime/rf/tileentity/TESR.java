package com.anime.rf.tileentity;

import java.util.Random;

import com.messorix.moleculecraft.base.tileentities.TileEntityFluxFurnace;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class TESR extends TileEntitySpecialRenderer<TileEntityFluxFurnace> {
	
	private int frame = 0;
	
	@Override
    public void renderTileEntityAt(TileEntityFluxFurnace te, double x, double y, double z, float partialTicks, int destroyStage) {
		System.out.println(frame);
		frame++;
		if (frame > 550) frame = 0;
		Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        RenderHelper.disableStandardItemLighting();
        float f = ((float)frame + partialTicks) / 200.0F;
        float f1 = 0.0F;

        if (f > 0.8F)
        {
            f1 = (f - 0.8F) / 0.2F;
        }
//
        Random random = new Random(432L);
        GlStateManager.disableTexture2D();
        GlStateManager.shadeModel(7425);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        GlStateManager.disableAlpha();
        GlStateManager.enableCull();
        GlStateManager.depthMask(false);
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + .5, y + 7, z + .5);

        for (int i = 0; (float)i < (f + f * f) / 2.0F * 60.0F; ++i)
        {
            GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(random.nextFloat() * 360.0F + f * 90.0F, 0.0F, 0.0F, 1.0F);
            float f2 = random.nextFloat() * 20.0F + 5.0F + f1 * 10.0F;
            float f3 = random.nextFloat() * 2.0F + 1.0F + f1 * 2.0F;
            vertexbuffer.begin(6, DefaultVertexFormats.POSITION_COLOR);
            vertexbuffer.pos(0.0D, 0.0D, 0.0D).color(255, 255, 255, (int)(255.0F * (1.0F - f1))).endVertex();
            vertexbuffer.pos(-0.866D * (double)f3, (double)f2, (double)(-0.5F * f3)).color(255, 0, 255, 0).endVertex();
            vertexbuffer.pos(0.866D * (double)f3, (double)f2, (double)(-0.5F * f3)).color(255, 0, 255, 0).endVertex();
            vertexbuffer.pos(0.0D, (double)f2, (double)(1.0F * f3)).color(255, 0, 255, 0).endVertex();
            vertexbuffer.pos(-0.866D * (double)f3, (double)f2, (double)(-0.5F * f3)).color(255, 0, 255, 0).endVertex();
            tessellator.draw();
        }

        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.disableCull();
        GlStateManager.disableBlend();
        GlStateManager.shadeModel(7424);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableTexture2D();
        GlStateManager.enableAlpha();
        RenderHelper.enableStandardItemLighting();
	}
	
}
