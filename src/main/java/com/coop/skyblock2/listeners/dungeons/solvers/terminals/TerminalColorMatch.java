package com.coop.skyblock2.listeners.dungeons.solvers.terminals;

import java.util.HashMap;

import com.coop.skyblock2.listeners.experiments.ExperimentUtils;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.renderer.GlStateManager;
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

public class TerminalColorMatch {
	
	private static final int[] SLOTS = {12, 13, 14, 21, 22, 23, 30, 31, 32};
	private static HashMap<Integer, Integer> colorMap = new HashMap<>();
	
	static {
		for (int slot : SLOTS) colorMap.put(slot, 0);
	}
	
	@SubscribeEvent
	public void drawMenu(GuiScreenEvent.DrawScreenEvent.Post e) {
		
		if (!Utils.isInSkyblock()) return;
		
		Container gui = getGameGui();
		GuiChest chest = getChest();
		
		if (gui == null) return;
		
		NonNullList<ItemStack> items = gui.getInventory();
		if (items.size() < 33) return;
		
		int optimalColor = 0;
		int lowestClicks = 0;
		
		for (int i = 1; i <= 5; i++) {
			
			int clicks = 0;
			for (int slot : SLOTS) {
				
				int color = getColorID(items.get(slot));
				if (color == 0) color = colorMap.get(slot);
				if (color == 0) continue;
				int leftClicks = i - color;
				if (leftClicks < 0) leftClicks += 5;
				int rightClicks = 5 - leftClicks;
				int requiredClicks = Math.min(leftClicks, rightClicks);
				colorMap.put(slot, color);
				clicks += requiredClicks;
				
			}
			
			if (clicks < lowestClicks || optimalColor == 0) {
				lowestClicks = clicks;
				optimalColor = i;
			}
			
		}
		
		for (int slot : SLOTS) {
			
			int color = getColorID(items.get(slot));
			if (color == 0) color = colorMap.get(slot);
			if (color == 0) continue;
			int leftClicks = optimalColor - color;
			if (leftClicks < 0) leftClicks += 5;
			int rightClicks = (5 - leftClicks) * -1;
			int requiredClicks = leftClicks < Math.abs(rightClicks) ? leftClicks : rightClicks;
			colorMap.put(slot, color);
			
			highlightSlot(chest, slot, requiredClicks);
			
		}
		
	}
	
	private static int getColorID(ItemStack glass) {
		
		if (glass == null) return 0;
		if (glass.getItem() != Item.getItemFromBlock(Blocks.STAINED_GLASS_PANE)) return 0;
		
		int color = glass.getItemDamage();
		
		switch (color) {
		case 14:
			return 1;
		case 1:
			return 2;
		case 4:
			return 3;
		case 13:
			return 4;
		case 11:
			return 5;
		}
		
		return 0;
		
	}
	
	private static void highlightSlot(GuiChest chest, int slotIndex, int clicks) {
		
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
        
        ExperimentUtils.drawRect(x, y, x + 16, y + 16, 0xcc000000);
        drawDots(x, y, clicks);
        
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
		
	}
	
	private static void drawDots(int x, int y, int clicks) {
		
		if (clicks == 0) return;
		
		int color = (clicks > 0 ? 0xffffffff : 0xffff0000);
		if (clicks < 0) clicks *= -1;
		
		if (clicks == 1) {
			ExperimentUtils.drawRect(x + 6, y + 6, x + 10, y + 10, color);
			return;
		}
		
		ExperimentUtils.drawRect(x + 2, y + 6, x + 6, y + 10, color);
		
		if (clicks < 2) return;
		
		ExperimentUtils.drawRect(x + 10, y + 6, x + 14, y + 10, color);
		
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
		
		if (!invName.equals("Change all to same color!")) return null;
		
		return chest.inventorySlots;
		
	}
	
}
