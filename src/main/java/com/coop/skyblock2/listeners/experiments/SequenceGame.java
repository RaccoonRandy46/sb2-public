package com.coop.skyblock2.listeners.experiments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SequenceGame {

	private static boolean isPlaying = false;
	
	private static HashMap<Integer, Integer> pattern = new HashMap<>();
	private static int currentItem = 1;
	
	private static List<Highlight> highlights = new ArrayList<>();
	
	@SubscribeEvent
	public void renderOverlay(GuiScreenEvent.DrawScreenEvent.Post e) {
		
		Container gui = ExperimentUtils.getGameGui("Ultrasequencer (");
		GuiChest chest = ExperimentUtils.getChest("Ultrasequencer (");
		
		if (gui == null) {
			reset();
			return;
		}
		
		isPlaying = true;
		
		highlightAll();
		highlights.clear();
		
		NonNullList<ItemStack> items = gui.getInventory();
		
		ItemStack stateSlot = items.get(49);
			
		boolean isReading = !stateSlot.getItem().equals(Items.CLOCK);
		
		if (isReading) {
			currentItem = 1;
			for (int i = 9; i < 45; i++) {
				
				ItemStack item = items.get(i);
				
				if (item.getItem().equals(Item.getItemFromBlock(Blocks.STAINED_GLASS_PANE))) continue;
				
				pattern.put(item.getCount(), i);
				
			}
			return;
		}
		
		if (!pattern.containsKey(currentItem)) return;
		
		int currentSlot = pattern.get(currentItem);
		
		ItemStack item = items.get(currentSlot);
		
		if (item.getItem().equals(Item.getItemFromBlock(Blocks.STAINED_GLASS_PANE))) addHighlight(chest, currentSlot);
		else currentItem++;
		
	}
	
	private static void highlightAll() {
		
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.colorMask(true, true, true, false);
        
        for (int i = 0; i < highlights.size(); i++) {
        	Highlight hl = highlights.get(i);
        	ExperimentUtils.drawRect(hl.x, hl.y, hl.x + 16, hl.y + 16, 0x7700ff00);
        }
        
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
		
	}
	
	private static void addHighlight(GuiChest chest, int slot) {
		highlights.add(new Highlight(chest.inventorySlots.inventorySlots.get(slot)));
	}
	
	private static void reset() {
		
		if (!isPlaying) return;
		
		isPlaying = false;
		pattern.clear();
		
	}
	
}
