package com.coop.skyblock2.listeners.experiments;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SongGame {

	private static boolean isPlaying = false;
	private static boolean isReading = true;
	
	private static List<Highlight> highlights = new ArrayList<>();
	
	private static List<Integer> pattern = new ArrayList<>();
	private static int currentNote = 0;
	
	private static boolean noteFound = false;
	
	@SubscribeEvent
	public void renderOverlay(GuiScreenEvent.DrawScreenEvent.Post e) {
		
		Container gui = ExperimentUtils.getGameGui("Chronomatron (");
		GuiChest chest = ExperimentUtils.getChest("Chronomatron (");
		
		if (gui == null) {
			reset();
			return;
		}
		
		isPlaying = true;
		
		highlightAll();
		highlights.clear();
		
		NonNullList<ItemStack> items = gui.getInventory();
		
		ItemStack stateSlot = items.get(49);
			
		if (stateSlot.getItem().equals(Items.CLOCK) ? isReading : !isReading) {
			
			boolean noEnchants = true;
			for (int i = 9; i < 45; i++) {
				if (EnchantmentHelper.getEnchantments(items.get(i)).size() > 0) {
					noEnchants = false;
					break;
				}
			}
			
			if (noEnchants) {
				isReading = !isReading;
				noteFound = false;
				currentNote = 0;
				if (!stateSlot.getItem().equals(Items.CLOCK)) pattern.clear();
			}
			
		}
		
		if (!isReading) {
			
			boolean noteClicked = false;
			for (int i = 9; i < 45; i++) {
				ItemStack item = items.get(i);
				
				if (EnchantmentHelper.getEnchantments(item).size() > 0) {
					noteClicked = true;
					
					if (!noteFound) {
						noteFound = true;
						currentNote++;
					}
					
					break;
				}
			}
			
			if (!noteClicked) noteFound = false;
			
			for (int i = 9; i < 45; i++) {
				
				ItemStack item = items.get(i);
				if (currentNote < pattern.size() && item.getItemDamage() == pattern.get(currentNote)) addHighlight(chest, i);
				
			}
			
			return;
			
		}
		
		boolean noEnchants = true;
		for (int i = 9; i < 45; i++) {
			ItemStack item = items.get(i);
			if (EnchantmentHelper.getEnchantments(item).size() > 0) {
				noEnchants = false;
				
				if (!noteFound) {
					noteFound = true;
					pattern.add(item.getItemDamage());
				}
				
				break;
			}
		}
		
		if (noEnchants) {
			
			noteFound = false;
			
		}
		
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
		isReading = true;
		highlights.clear();
		pattern.clear();
		currentNote = 0;
		
	}
	
}
