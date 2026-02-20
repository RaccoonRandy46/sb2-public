package com.coop.skyblock2.listeners.experiments;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;

public class ExperimentUtils {

	public static GuiChest getChest(String name) {
		
		GuiScreen screen = Minecraft.getMinecraft().currentScreen;
		
		if (screen == null) return null;
		
		if (!(screen instanceof GuiChest)) return null;
		
		return (GuiChest)screen;
		
	}
	
	public static Container getGameGui(String name) {
		
		GuiChest chest = getChest(name);
		
		if (chest == null) return null;
		
		ContainerChest gui = (ContainerChest)(chest.inventorySlots);

		IInventory inv = gui.getLowerChestInventory();
		
		String invName = inv.getName();
		
		if (!invName.startsWith(name)) return null;
		
		return chest.inventorySlots;
		
	}
	
	public static void drawRect(int left, int top, int right, int bottom, int color) {
        float f = (float)(color >> 24 & 255) / 255.0F;
        float f1 = (float)(color >> 16 & 255) / 255.0F;
        float f2 = (float)(color >> 8 & 255) / 255.0F;
        float f3 = (float)(color & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)right, (double)top, (double)1000).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double)left, (double)top, (double)1000).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double)left, (double)bottom, (double)1000).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double)right, (double)bottom, (double)1000).color(f1, f2, f3, f).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
	
}
