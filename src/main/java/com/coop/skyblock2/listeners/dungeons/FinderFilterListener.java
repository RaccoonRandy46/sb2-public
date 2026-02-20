package com.coop.skyblock2.listeners.dungeons;

import java.util.List;

import com.coop.skyblock2.commands.dungeons.DungeonClassCommand;
import com.coop.skyblock2.listeners.experiments.ExperimentUtils;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FinderFilterListener {

	@SubscribeEvent
	public void drawMenu(GuiScreenEvent.DrawScreenEvent.Post e) {
		
		Container gui = ExperimentUtils.getGameGui("Party Finder");
		GuiChest chest = ExperimentUtils.getChest("Party Finder");
		
		if (gui == null) return;
		
		int guiSize = ((chest.getYSize() - 114) / 18) * 9;
		
		NonNullList<ItemStack> items = gui.getInventory();
		
		for (int i = 0; i < items.size() && i < guiSize; i++) {
			
			ItemStack stack = items.get(i);
			
			if (stack == null) continue;
			
			Item item = stack.getItem();
			if (!item.equals(Items.SKULL)) continue;
			
			List<String> lore = chest.getItemToolTip(stack);
			
			int color = getColor(lore);
			
			highlightSlot(chest, i, color);
			
		}
		
	}
	
	private static int getColor(List<String> lore) {
		
		int color = 0x5500ff00;
		
		for (int i = 0; i < lore.size(); i++) {
			
			String os = lore.get(i);
			
			String s = Utils.removeColorcodes(os);
			String sl = s.toLowerCase();
			
			if (s.startsWith("Note: ") || (!s.contains(":") && i <= 5)) {
				
				if (sl.contains("paper")) color = 0x77ffff00;
				if (sl.contains("perm")) color = 0x77ff7700;
				
				if (sl.contains("carry")) color = 0x77ff0000;
				if (sl.contains("carries")) color = 0x77ff0000;
				if (sl.contains("0k")) color = 0x77ff0000;
				if (sl.contains("vc")) color = 0x77ff0000;
				
				continue;
				
			}
			
			if (s.contains(": " + DungeonClassCommand.classToString(DungeonClassCommand.dungeonClass) + " (")) color = 0x77ff0000;
			if (s.contains("Requires Catacombs Level ")) color = 0x99660000;
			if (s.contains("Requires a Class at Level ")) color = 0x99660000;
			
		}
		
		return color;
		
	}
	
	private static void highlightSlot(GuiChest chest, int slotIndex, int color) {
		
		Slot slot = chest.inventorySlots.inventorySlots.get(slotIndex);
		
		int xOffset = 0;
		int yOffset = 0;
		
		GuiScreen screen = Minecraft.getMinecraft().currentScreen;
		
		if (screen != null) {
			
			xOffset = (screen.width - 176) / 2;
			yOffset = (screen.height - 221) / 2;
			
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
	
}
