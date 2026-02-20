package com.coop.skyblock2.listeners.dungeons;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import com.coop.skyblock2.utils.MagicRenderer;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class LividFindListener {

	private static String color = null;
	private static String healthBar = null;
	
	@SubscribeEvent
	public void findColor(PlayerTickEvent e) {
		
		if (!(e.player instanceof EntityPlayerSP)) return;
		
		IBlockState state = Minecraft.getMinecraft().player.world.getBlockState(new BlockPos(5, 108, 42));
		Block block = state.getBlock();
		
		if (block != Blocks.STAINED_GLASS) return;
		
		BlockStainedGlass glass = (BlockStainedGlass)block;
		
		int colorID = glass.damageDropped(state);
		
		switch (colorID) {
		case 0:
			color = "f";
			return;
		case 2:
			color = "d";
			return;
		case 14:
			color = "c";
			return;
		case 7:
			color = "7";
			return;
		case 13:
			color = "2";
			return;
		case 5:
			color = "a";
			return;
		case 11:
			color = "9";
			return;
		case 10:
			color = "5";
			return;
		case 4:
			color = "e";
			return;
		}
		
	}
	
	@SubscribeEvent
	public void drawBox(RenderWorldLastEvent e) {
		
		if (color == null) return;
		
		Entity livid = null;
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		for (Entity entity : p.world.getLoadedEntityList()) {
			
			if (!(entity instanceof EntityArmorStand)) continue;
			if (!entity.getName().contains(Utils.chat("&" + color + "&lLivid"))) continue;
			
			livid = entity;
			healthBar = entity.getName();
			break;
			
		}
		
		if (livid == null) return;
		
		float translatex = (float)(p.prevPosX + (p.posX - p.prevPosX) * e.getPartialTicks());
		float translatey = (float)(p.prevPosY + (p.posY - p.prevPosY) * e.getPartialTicks());
		float translatez = (float)(p.prevPosZ + (p.posZ - p.prevPosZ) * e.getPartialTicks());
	  
		float x = (float)(livid.prevPosX + (livid.posX - livid.prevPosX) * e.getPartialTicks());
		float y = (float)(livid.prevPosY + (livid.posY - livid.prevPosY) * e.getPartialTicks()) - 1.8f;
		float z = (float)(livid.prevPosZ + (livid.posZ - livid.prevPosZ) * e.getPartialTicks());
		
		MagicRenderer renderer = new MagicRenderer();
		renderer.autoTranslate(e.getPartialTicks());
		
		renderer.lineWidth(3);
		renderer.disableDepth();
		renderer.color(0, 255, 0);
		
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
		color = null;
		healthBar = null;
	}
	
}
