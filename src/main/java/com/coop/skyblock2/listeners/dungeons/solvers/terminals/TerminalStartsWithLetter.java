package com.coop.skyblock2.listeners.dungeons.solvers.terminals;

import com.coop.skyblock2.listeners.experiments.ExperimentUtils;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TerminalStartsWithLetter {

	private static String letter = "";
	
	@SubscribeEvent
	public void drawMenu(GuiScreenEvent.DrawScreenEvent.Post e) {
		
		//if (!Utils.isInSkyblock()) return;
		
		Container gui = getGameGui();
		GuiChest chest = getChest();
		
		if (gui == null) return;
		
		int guiSize = ((chest.getYSize() - 114) / 18) * 9;
		
		NonNullList<ItemStack> items = gui.getInventory();
		
		for (int i = 0; i < items.size() && i < guiSize; i++) {
			
			ItemStack stack = items.get(i);
			
			if (stack == null) continue;
			
			int color = 0x5500ff00;
			
			if (!Utils.removeColorcodes(stack.getDisplayName().toLowerCase()).startsWith(letter)) color = 0xcc000000;
			if (stack.isItemEnchanted()) color = 0xcc000000;
			
			highlightSlot(chest, i, color);
			
		}
		
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
		
		if (!invName.contains("'")) return null;
		if (invName.split("'").length != 3) return null;
		letter = invName.split("'")[1].toLowerCase();
		
		return chest.inventorySlots;
		
	}
	
}
