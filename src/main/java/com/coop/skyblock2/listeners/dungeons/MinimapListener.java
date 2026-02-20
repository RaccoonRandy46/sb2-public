package com.coop.skyblock2.listeners.dungeons;

import org.lwjgl.opengl.GL11;

import com.coop.skyblock2.commands.dungeons.MinimapCommand;
import com.coop.skyblock2.utils.FileManager;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MinimapListener {

	private static boolean updated = false;
	
	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent.Post e) {

		if (!updated) updateSettings();
		if (!MinimapCommand.enabled) return;
		if (!Utils.isInDungeons()) return;
		
		if (e.getType() != ElementType.FOOD) return;
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
        MapData data = (MapData)p.world.loadData(MapData.class, "map_1024");
		
		if (data == null) return;
		
		int guiScale = Minecraft.getMinecraft().gameSettings.guiScale;
		if (guiScale == 0) guiScale = 4;
		
		float mapScale = 3f / guiScale;
		
        GlStateManager.disableLighting();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(50, 50, 0).tex(0.0D, 1.0D).endVertex();
        bufferbuilder.pos(100, 50, 0).tex(1.0D, 1.0D).endVertex();
        bufferbuilder.pos(100, 100, 0).tex(1.0D, 0.0D).endVertex();
        bufferbuilder.pos(50, 100, 0.0D).tex(0.0D, 0.0D).endVertex();
        tessellator.draw();

        GlStateManager.translate(20 / guiScale, 20 / guiScale, 0);
        
        GlStateManager.scale(mapScale, mapScale, 1);
        
        Minecraft.getMinecraft().entityRenderer.getMapItemRenderer().renderMap(data, true);
        
        int k = 0;
        for (MapDecoration mapdecoration : data.mapDecorations.values()) {
            if (mapdecoration.render(k)) { k++; continue; }
            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/map/map_icons.png"));
            GlStateManager.pushMatrix();
            GlStateManager.translate(0, 0, 100);
            GlStateManager.translate(0.0F + (float)mapdecoration.getX() / 2.0F + 64.0F, 0.0F + (float)mapdecoration.getY() / 2.0F + 64.0F, -0.02F);
            GlStateManager.rotate((float)(mapdecoration.getRotation() * 360) / 16.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.scale(4.0F, 4.0F, 3.0F);
            GlStateManager.translate(-0.125F, 0.125F, 0.0F);
            byte b0 = mapdecoration.getImage();
            float f1 = (float)(b0 % 4 + 0) / 4.0F;
            float f2 = (float)(b0 / 4 + 0) / 4.0F;
            float f3 = (float)(b0 % 4 + 1) / 4.0F;
            float f4 = (float)(b0 / 4 + 1) / 4.0F;
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.pos(-1.0D, 1.0D, (double)((float)k * -0.001F)).tex((double)f1, (double)f2).endVertex();
            bufferbuilder.pos(1.0D, 1.0D, (double)((float)k * -0.001F)).tex((double)f3, (double)f2).endVertex();
            bufferbuilder.pos(1.0D, -1.0D, (double)((float)k * -0.001F)).tex((double)f3, (double)f4).endVertex();
            bufferbuilder.pos(-1.0D, -1.0D, (double)((float)k * -0.001F)).tex((double)f1, (double)f4).endVertex();
            tessellator.draw();
            GlStateManager.popMatrix();
            ++k;
        }
        
        GlStateManager.scale(1 / mapScale, 1 / mapScale, 1);
        
        GlStateManager.translate(-20 / guiScale, -20 / guiScale, 0);
        
        Minecraft.getMinecraft().getTextureManager().bindTexture(Gui.ICONS);
        GL11.glColor3ub((byte)255, (byte)255, (byte)255);
		
	}
	
	public static void updateSettings() {
		
		updated = true;
		
		try {
			
			MinimapCommand.enabled = Boolean.parseBoolean(FileManager.readLine("mods/sb2/dungeons/minimap.cccm"));
			
		} catch (Exception E) {}
		
	}
	
}
