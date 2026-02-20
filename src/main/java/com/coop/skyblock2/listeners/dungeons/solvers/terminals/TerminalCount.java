package com.coop.skyblock2.listeners.dungeons.solvers.terminals;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.coop.skyblock2.listeners.experiments.ExperimentUtils;
import com.coop.skyblock2.utils.Positioning;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TerminalCount {
	
	private static float hx = 0;
	private static float hz = 0;
	
	@SubscribeEvent
	public void drawMenu(GuiScreenEvent.DrawScreenEvent.Post e) {
		
		if (!Utils.isInSkyblock()) return;
		
		Container gui = getGameGui();
		GuiChest chest = getChest();
		
		if (gui == null) return;
		
		int guiSize = ((chest.getYSize() - 114) / 18) * 9;
		
		NonNullList<ItemStack> items = gui.getInventory();
		int[] slots = new int[14];
		
		for (int i = 0; i < items.size() && i < guiSize; i++) {
			
			ItemStack stack = items.get(i);
			if (stack == null) continue;
			if (stack.getItem() != Item.getItemFromBlock(Blocks.STAINED_GLASS_PANE)) continue;
			if (stack.getItemDamage() != 14 && stack.getItemDamage() != 5) continue;
			
			slots[stack.getCount() - 1] = i;
			
		}
		
		int nextNumber = -1;
		
		for (int i = 0; i < 14; i++) {
			
			ItemStack stack = items.get(slots[i]);
			if (stack == null) continue;
			if (stack.getItem() != Item.getItemFromBlock(Blocks.STAINED_GLASS_PANE)) continue;
			if (stack.getItemDamage() != 14) continue;
			
			nextNumber = stack.getCount() - 1;
			break;
			
		}
		
		if (nextNumber == -1) return;
			
		drawTarget(chest, slots[nextNumber]);
		if (nextNumber >= 13) return;
		
		highlightSlot(chest, slots[nextNumber + 1], 0x9900ffff);
		
	}
	
	private static int[] slotXZ(GuiChest chest, int slotIndex) {
		
		Slot slot = chest.inventorySlots.inventorySlots.get(slotIndex);
		
		int xOffset = 0;
		int yOffset = 0;
		
		GuiScreen screen = Minecraft.getMinecraft().currentScreen;
		
		if (screen != null) {
			
			xOffset = (screen.width - chest.getXSize()) / 2;
			yOffset = (screen.height - chest.getYSize()) / 2;
			
		}
		
		int x = slot.xPos + xOffset;
		int y = slot.yPos + yOffset;
		
		return new int[] {x, y};
		
	}
	
	private static int[] slotCenterXZ(GuiChest chest, int slotIndex) {
		int[] xz = slotXZ(chest, slotIndex);
		return new int[] {xz[0] + 8, xz[1] + 8};
	}
	
	private static void highlightSlot(GuiChest chest, int slotIndex, int color) {
		
		Slot slot = chest.inventorySlots.inventorySlots.get(slotIndex);
		
		int xOffset = 0;
		int yOffset = 0;
		
		GuiScreen screen = Minecraft.getMinecraft().currentScreen;
		
		if (screen != null) {
			
			xOffset = (screen.width - chest.getXSize()) / 2;
			yOffset = (screen.height - chest.getYSize()) / 2;
			
		}
		
		int x = slot.xPos + xOffset;
		int y = slot.yPos + yOffset;
		
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.colorMask(true, true, true, false);
        
        ExperimentUtils.drawRect(x, y, x + 16, y + 16, color);
        
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
		
	}
	
	private static void drawTarget(GuiChest chest, int slotIndex) {
		
		float yawA = System.currentTimeMillis() % 1000;
		yawA /= 1000;
		int c = Color.HSBtoRGB(yawA, 1, 1);
		yawA *= 359;
		float yawB = 359 - yawA;
		
		GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.colorMask(true, true, true, false);
		
        GlStateManager.glLineWidth(10);
        
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bb = tessellator.getBuffer();
        bb.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        
        int[] xz = slotCenterXZ(chest, slotIndex);
        float tx = xz[0] - hx;
        float tz = xz[1] - hz;
        
        hx += (tx * 0.15);
        hz += (tz * 0.15);
        
        float size = 8;
        float sp = size / 4;
        
        hloVert(bb, c, -size, sp, hx, hz, yawA);
        hloVert(bb, c, -size, size, hx, hz, yawA);
        
        hloVert(bb, c, -size, size, hx, hz, yawA);
        hloVert(bb, c, -sp, size, hx, hz, yawA);
        
        hloVert(bb, c, sp, size, hx, hz, yawA);
        hloVert(bb, c, size, size, hx, hz, yawA);
        
        hloVert(bb, c, size, size, hx, hz, yawA);
        hloVert(bb, c, size, sp, hx, hz, yawA);
        
        hloVert(bb, c, size, -sp, hx, hz, yawA);
        hloVert(bb, c, size, -size, hx, hz, yawA);
        
        hloVert(bb, c, size, -size, hx, hz, yawA);
        hloVert(bb, c, sp, -size, hx, hz, yawA);
        
        hloVert(bb, c, -sp, -size, hx, hz, yawA);
        hloVert(bb, c, -size, -size, hx, hz, yawA);
        
        hloVert(bb, c, -size, -size, hx, hz, yawA);
        hloVert(bb, c, -size, -sp, hx, hz, yawA);
        
        size = 5;
        sp = size / 4;
        
        hloVert(bb, c, -size, sp, hx, hz, yawB);
        hloVert(bb, c, -size, size, hx, hz, yawB);
        
        hloVert(bb, c, -size, size, hx, hz, yawB);
        hloVert(bb, c, -sp, size, hx, hz, yawB);
        
        hloVert(bb, c, sp, size, hx, hz, yawB);
        hloVert(bb, c, size, size, hx, hz, yawB);
        
        hloVert(bb, c, size, size, hx, hz, yawB);
        hloVert(bb, c, size, sp, hx, hz, yawB);
        
        hloVert(bb, c, size, -sp, hx, hz, yawB);
        hloVert(bb, c, size, -size, hx, hz, yawB);
        
        hloVert(bb, c, size, -size, hx, hz, yawB);
        hloVert(bb, c, sp, -size, hx, hz, yawB);
        
        hloVert(bb, c, -sp, -size, hx, hz, yawB);
        hloVert(bb, c, -size, -size, hx, hz, yawB);
        
        hloVert(bb, c, -size, -size, hx, hz, yawB);
        hloVert(bb, c, -size, -sp, hx, hz, yawB);
        
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
		
	}
	
	private static void hloVert(BufferBuilder bb, int color, float x_offset, float y_offset, float x, float y, float yaw) {
		Color c = Color.decode(color + "");
		float r = c.getRed() / 255f;
		float g = c.getGreen() / 255f;
		float b = c.getBlue() / 255f;
		double[] xz = Positioning.HLO_XY_CXYY(x_offset, y_offset, x, y, yaw);
		bb.pos(xz[0], xz[1], (double)1000).color(r, g, b, 1).endVertex();
	}
	
	public static GuiChest getChest() {
		
		GuiScreen screen = Minecraft.getMinecraft().currentScreen;
		
		if (screen == null) return null;
		
		if (!(screen instanceof GuiChest)) return null;
		
		return (GuiChest)screen;
		
	}
	
	public static Container getGameGui() {
		
		GuiChest chest = getChest();
		
		if (chest == null) return null;
		
		ContainerChest gui = (ContainerChest)(chest.inventorySlots);

		IInventory inv = gui.getLowerChestInventory();
		
		String invName = inv.getName();
		
		if (!invName.equals("Click in order!")) return null;
		
		return chest.inventorySlots;
		
	}
	
}
