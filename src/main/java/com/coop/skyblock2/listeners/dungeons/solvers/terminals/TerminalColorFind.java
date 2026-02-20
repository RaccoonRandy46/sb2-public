package com.coop.skyblock2.listeners.dungeons.solvers.terminals;

import com.coop.skyblock2.listeners.experiments.ExperimentUtils;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TerminalColorFind {
	
	private static String color = "";
	
	@SubscribeEvent
	public void drawMenu(GuiScreenEvent.DrawScreenEvent.Post e) {
		
		if (!Utils.isInSkyblock()) return;
		
		Container gui = getGameGui();
		GuiChest chest = getChest();
		
		if (gui == null) return;
		
		int guiSize = ((chest.getYSize() - 114) / 18) * 9;
		
		NonNullList<ItemStack> items = gui.getInventory();
		
		int id = getColorID(color);
		int hColor = getHexFromID(id);
		
		for (int i = 0; i < items.size() && i < guiSize; i++) {
			
			ItemStack stack = items.get(i);
			highlightSlot(chest, i, isMatch(stack, id) ? hColor : 0xcc000000);
			
		}
		
	}
	
	private static boolean isMatch(ItemStack stack, int id) {
		
		if (stack.isItemEnchanted()) return false;
		
		if (stack.getDisplayName().toLowerCase().startsWith(color)) return true;
		
		if (color.equals("silver") && stack.getDisplayName().toLowerCase().startsWith("light gray")) return true;
		
		Item item = stack.getItem();

		int damage = stack.getItemDamage();
		
		boolean isDye = item == Items.DYE;
		int dyeID = (id - 15) * -1;
		
		return isDye ? damage == dyeID : damage == id;
		
	}
	
	private static int getHexFromID(int id) {
		int[] hexArray = new int[] {0x55ffffff, 0x55ff9430, 0x55ff2bdf, 0x5538f8ff, 0x55f8ff33, 0x554aff50, 0x55ff85d2, 0x553b3b3b, 0x55a8a8a8, 0x5552b7ff, 0x55b247ff, 0x554d4dff, 0x5563400f, 0x55276921, 0x55ff4a4a, 0x00000000};
		return hexArray[id];
	}
	
	private static int getColorID(String color) {
		
		switch (color) {
		case "white":
			return 0;
		case "orange":
			return 1;
		case "magenta":
			return 2;
		case "light blue":
			return 3;
		case "yellow":
			return 4;
		case "lime":
			return 5;
		case "pink":
			return 6;
		case "gray":
			return 7;
		case "silver":
			return 8;
		case "cyan":
			return 9;
		case "purple":
			return 10;
		case "blue":
			return 11;
		case "brown":
			return 12;
		case "green":
			return 13;
		case "red":
			return 14;
		case "black":
			return 15;
		}
		
		return -1;
		
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
		
		if (!invName.startsWith("Select all the ")) return null;
		if (!invName.endsWith(" items!")) return null;
		
		color = invName.substring(15, invName.length() - 7).toLowerCase();
		
		return chest.inventorySlots;
		
	}
	
}
