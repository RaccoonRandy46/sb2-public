package com.coop.skyblock2.listeners.dungeons;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import com.coop.skyblock2.utils.MagicRenderer;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SpiritBearFindListener {
	
	private static String healthBar = null;
	
	@SubscribeEvent
	public void drawBox(RenderWorldLastEvent e) {
		
		Entity spiritBear = null;
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		for (Entity entity : p.world.getLoadedEntityList()) {
			
			if (!(entity instanceof EntityArmorStand)) continue;
			if (!Utils.isInSkyblock()) continue;
			if (!entity.getName().toLowerCase().contains(Utils.chat("&d"))) continue;
			if (!Utils.removeColorcodes(entity.getName()).toLowerCase().contains("spirit bear")) continue;
			
			spiritBear = entity;
			healthBar = entity.getName();
			break;
			
		}
		
		if (spiritBear == null) return;
		
		float translatex = (float)(p.prevPosX + (p.posX - p.prevPosX) * e.getPartialTicks());
		float translatey = (float)(p.prevPosY + (p.posY - p.prevPosY) * e.getPartialTicks());
		float translatez = (float)(p.prevPosZ + (p.posZ - p.prevPosZ) * e.getPartialTicks());
	  
		float x = (float)(spiritBear.prevPosX + (spiritBear.posX - spiritBear.prevPosX) * e.getPartialTicks());
		float y = (float)(spiritBear.prevPosY + (spiritBear.posY - spiritBear.prevPosY) * e.getPartialTicks()) - 1.8f;
		float z = (float)(spiritBear.prevPosZ + (spiritBear.posZ - spiritBear.prevPosZ) * e.getPartialTicks());
		
		MagicRenderer renderer = new MagicRenderer();
		renderer.autoTranslate(e.getPartialTicks());
		
		renderer.lineWidth(3);
		renderer.disableDepth();
		renderer.color(255, 0, 255);
		
		renderer.begin(GL11.GL_LINES);
		
		renderer.vc(x + -0.5f, y + 0, z + -0.5f);
		renderer.vc(x + -0.5f, y + 0, z + 0.5f);
		renderer.vc(x + -0.5f, y + 0, z + 0.5f);
		renderer.vc(x + 0.5f, y + 0, z + 0.5f);
		renderer.vc(x + 0.5f, y + 0, z + 0.5f);
		renderer.vc(x + 0.5f, y + 0, z + -0.5f);
		renderer.vc(x + 0.5f, y + 0, z + -0.5f);
		renderer.vc(x + -0.5f, y + 0, z + -0.5f);

		renderer.vc(x + -0.5f, y + 2, z + -0.5f);
		renderer.vc(x + -0.5f, y + 2, z + 0.5f);
		renderer.vc(x + -0.5f, y + 2, z + 0.5f);
		renderer.vc(x + 0.5f, y + 2, z + 0.5f);
		renderer.vc(x + 0.5f, y + 2, z + 0.5f);
		renderer.vc(x + 0.5f, y + 2, z + -0.5f);
		renderer.vc(x + 0.5f, y + 2, z + -0.5f);
		renderer.vc(x + -0.5f, y + 2, z + -0.5f);

		renderer.vc(x + -0.5f, y + 0, z + -0.5f);
		renderer.vc(x + -0.5f, y + 2, z + -0.5f);
		renderer.vc(x + -0.5f, y + 0, z + 0.5f);
		renderer.vc(x + -0.5f, y + 2, z + 0.5f);
		renderer.vc(x + 0.5f, y + 0, z + 0.5f);
		renderer.vc(x + 0.5f, y + 2, z + 0.5f);
		renderer.vc(x + 0.5f, y + 0, z + -0.5f);
		renderer.vc(x + 0.5f, y + 2, z + -0.5f);
		
		renderer.vc(x, y + 1, z);
		renderer.vc(translatex, translatey + 1, translatez);
		
		renderer.draw();
		renderer.enableDepth();
		renderer.end();
		
	}
	
	@SubscribeEvent
	public void overlayEvent_TXT(RenderGameOverlayEvent.Post e) throws IOException {

		if (healthBar == null) return;
		
		GL11.glPushMatrix();

		int guiScale = Minecraft.getMinecraft().gameSettings.guiScale;
		if (guiScale == 0) guiScale = 4;
		
		float scale = 2.5f;
		
		float stringWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(healthBar) * scale;
		float screenCenter = (Minecraft.getMinecraft().displayWidth / 2) / guiScale;
		float textOffset = stringWidth / 2;
		float textX = screenCenter - textOffset;
		
		GL11.glScaled(scale, scale, 0);
		
		Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(healthBar, textX / scale, 35 / scale, 0xffffff);
		
		GL11.glScaled(1f / scale, 1f / scale, 0);
		
		GL11.glPopMatrix();
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(Gui.ICONS);
		GL11.glColor3ub((byte)255, (byte)255, (byte)255);
		
	}
	
	@SubscribeEvent
	public void loadWorld(WorldEvent.Load e) {
		healthBar = null;
	}
	
}
