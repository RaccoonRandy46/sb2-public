package com.coop.skyblock2.utils;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class MagicRenderer {

	private float red = 1;
	private float green = 1;
	private float blue = 1;
	private float alpha = 1;
	
	private Tessellator tessellator;
	private BufferBuilder bufferBuilder;
	
	private double translateX = 0;
	private double translateY = 0;
	private double translateZ = 0;
	
	private boolean textureMode = false;
	
	private static final double[][] PHASES = {{1, 0}, {0, 0}, {0, 1}, {1, 1}, {1, 1}, {0, 1}, {0, 0}, {1, 0}};
	private int phase = 0;
	
	public MagicRenderer() {
		
        this.tessellator = Tessellator.getInstance();
        this.bufferBuilder = this.tessellator.getBuffer();
        
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        
	}
	
	public MagicRenderer(float partialTicks) {
		this();
		this.autoTranslate(partialTicks);
	}
	
	public void autoTranslate(float partialTicks) {
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		this.translateX = -(p.prevPosX + (p.posX - p.prevPosX) * partialTicks);
		this.translateY = -(p.prevPosY + (p.posY - p.prevPosY) * partialTicks);
		this.translateZ = -(p.prevPosZ + (p.posZ - p.prevPosZ) * partialTicks);
		
	}
	
	public void translate(double x, double y, double z) {
		
		this.translateX += x;
		this.translateY += y;
		this.translateZ += z;
		
	}

	public void blendFunc(int blendSrc, int blendDst) {
		GL11.glBlendFunc(blendSrc, blendDst);
	}
	
	public void color(float red, float green, float blue, float alpha) {
		this.red = red / 255;
		this.green = green / 255;
		this.blue = blue / 255;
		this.alpha = alpha / 255;
	}
	
	public void color(float red, float green, float blue) {
		this.color(red, green, blue, this.alpha * 255);
	}
	
	public void phase(int phase) {
		if (phase < 0) phase = 0;
		if (phase > 7) phase = 7;
		this.phase = phase;
	}
	
	public void enableDepth() {
		GlStateManager.enableDepth();
	}
	
	public void disableDepth() {
		GlStateManager.disableDepth();
	}
	
	public static void enableGlow() {
	    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
	}
	
	public static void disableGlow(Entity e) {
	    
        int brightness = e.getBrightnessForRender();
	    int x = brightness % 65536;
	    int y = brightness / 65536;
	    
	    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, x, y);
		
	}
	
	public void lineWidth(float width) {
		GlStateManager.glLineWidth(width);
	}
	
	public void begin(int type) {
		this.bufferBuilder.begin(type, DefaultVertexFormats.POSITION_COLOR);
	}
	
	public void beginTex(ResourceLocation texture) {
		this.textureMode = true;
		this.bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
		GlStateManager.enableTexture2D();
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
	}
	
	public void beginTex(int texture) {
		
		this.textureMode = true;
		this.bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
		GlStateManager.enableTexture2D();
		GlStateManager.bindTexture(texture);
		
	}
	
	public void vc(double x, double y, double z) {
		
		if (this.textureMode) {
			this.vcTex(x, y, z);
			return;
		}
		
		this.bufferBuilder.pos(x + this.translateX, y + this.translateY, z + this.translateZ).color(this.red, this.green, this.blue, this.alpha).endVertex();
		
	}
	
	public void hlo(double x_offset, double y_offset, double z_offset, double x, double y, double z, double yaw, double pitch) {
		
		x += this.translateX;
		y += this.translateY;
		z += this.translateZ;
		
		double[] hlo = Positioning.HLO_XYZ_CXYZPY(x_offset, y_offset, z_offset, x, y, z, yaw, pitch);
		
		double vx = hlo[0] - this.translateX;
		double vy = hlo[1] - this.translateY;
		double vz = hlo[2] - this.translateZ;
		
		if (this.textureMode) {
			this.vcTex(vx, vy, vz);
			return;
		}
		
		this.vc(vx, vy, vz);
		
	}
	
	private void vcTex(double x, double y, double z) {
		
		double[] currentPhase = PHASES[this.phase];
		
		this.bufferBuilder.pos(x + this.translateX, y + this.translateY, z + this.translateZ).tex(currentPhase[0], currentPhase[1]).color(255, 255, 255, 255).endVertex();
		
		this.phase++;
		if (this.phase % 4 == 0) this.phase -= 4;
		
	}

	public void draw() {
		this.tessellator.draw();
	}
	
	public void end() {
		
        GlStateManager.depthMask(true);
        if (!this.textureMode) GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
		
	}
	
}
