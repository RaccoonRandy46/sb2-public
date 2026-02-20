package com.coop.skyblock2.listeners.experiments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.lwjgl.input.Keyboard;

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

public class SuperpairsGame {

	private static boolean isPlaying = false;
	
	private static HashMap<String, Set<Integer>> pairs = new HashMap<>();
	
	private static Set<Integer> tilesFound = new HashSet<>();
	
	private static Set<Integer> tilesMatched = new HashSet<>();
	
	private static HashMap<Integer, Item> itemMap = new HashMap<>();
	
	private static boolean awaitZero = false;
	
	private static int click1 = -1;
	private static int click2 = -1;
	
	private static List<Highlight> highlights = new ArrayList<>();
	
	@SubscribeEvent
	public void renderOverlay(GuiScreenEvent.DrawScreenEvent.Post e) {
		
		Container gui = ExperimentUtils.getGameGui("Superpairs (");
		GuiChest chest = ExperimentUtils.getChest("Superpairs (");
		
		if (gui == null) {
			reset();
			return;
		}
		
		isPlaying = true;
		
		highlightAll();
		highlights.clear();
		
		NonNullList<ItemStack> items = gui.getInventory();
		
		if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
			
			awaitZero = false;
			click1 = -1;
			click2 = -1;
			
			for (int i = 9; i < 45; i++) {
				
				ItemStack item = items.get(i);
				
				if (!isInBoard(item)) continue;
				if (isHidden(item)) continue;
				
				tilesMatched.add(i);
				
			}
			
		}
		
		if (awaitZero) {
			
			ItemStack item1 = items.get(click1);
			ItemStack item2 = items.get(click2);
			
			if (!isHidden(item1)) return;
			if (!isHidden(item2)) return;
			
			click1 = -1;
			click2 = -1;
			
			awaitZero = false;
			
		}
		
		if (click1 < 0 && click2 < 0) {
			
			for (int i = 9; i < 45; i++) {
				
				ItemStack item = items.get(i);
				
				if (!isInBoard(item)) continue;
				if (isHidden(item)) continue;
				if (tilesMatched.contains(i)) continue;
				addItem(item, i);
				if (isBonus(item)) continue;
				
				click1 = i;
				return;
				
			}
			
			Set<Integer> match = null;
			
			Iterator<String> pairIterator = pairs.keySet().iterator();
			while (pairIterator.hasNext()) {
				
				String name = pairIterator.next();
				if (pairs.get(name).size() < 2) continue;
				
				Set<Integer> newMatch = new HashSet<>();
				
				Iterator<Integer> slotIterator = pairs.get(name).iterator();
				while (slotIterator.hasNext()) {
					
					int slot = slotIterator.next();
					
					newMatch.clear();
					newMatch.add(slot);
					
					Iterator<Integer> otherSlotIterator = pairs.get(name).iterator();
					while (otherSlotIterator.hasNext()) {
						
						int otherSlot = otherSlotIterator.next();
						
						if (!itemMap.get(otherSlot).equals(itemMap.get(slot))) continue;
						if (otherSlot == slot) continue;
						
						newMatch.add(otherSlot);
						break;
						
					}
					
					if (newMatch.size() == 2) {
						match = newMatch;
						break;
					}
					
				}
				
				if (match != null) break;
				
			}
			
			if (match != null) {
				
				Object[] matchArr = match.toArray();
				
				addHighlight(chest, (int)matchArr[0]);
				addHighlight(chest, (int)matchArr[1]);
				
				return;
			
			}
			
			for (int i = 9; i < 45; i++) {
				
				ItemStack item = items.get(i);
				
				if (!isInBoard(item)) continue;
				if (isBonus(item)) continue;
				if (tilesFound.contains(i)) continue;
				
				addHighlight(chest, i);
				
			}
			
			return;
			
		}
		
		if (click2 < 0) {
			
			for (int i = 9; i < 45; i++) {
				
				ItemStack item = items.get(i);
				
				if (i == click1) continue;
				if (!isInBoard(item)) continue;
				if (isHidden(item)) continue;
				if (tilesMatched.contains(i)) continue;
				addItem(item, i);
				if (isBonus(item)) continue;
				
				click2 = i;
				return;
				
			}
			
			ItemStack item1 = items.get(click1);
			String itemName = item1.getDisplayName();
			
			if (pairs.containsKey(itemName) && pairs.get(itemName).size() > 1) {
				
				int slot = -1;
				
				Iterator<Integer> slotIterator = pairs.get(itemName).iterator();
				while (slotIterator.hasNext()) {
					
					int currentSlot = slotIterator.next();
					
					if (!itemMap.get(currentSlot).equals(item1.getItem())) continue;
					if (currentSlot == click1) continue;
					
					slot = currentSlot;
					
				}
				
				if (slot > -1) {
					addHighlight(chest, slot);
					return;
				}
				
			}
			
			for (int i = 9; i < 45; i++) {
				
				ItemStack item = items.get(i);
				
				if (!isInBoard(item)) continue;
				if (isBonus(item)) continue;
				if (tilesFound.contains(i)) continue;
				
				addHighlight(chest, i);
				
			}
			
			return;
			
		}
		
		ItemStack item1 = items.get(click1);
		ItemStack item2 = items.get(click2);
		
		boolean itemsMatch = true;
		
		if (!item1.getDisplayName().equals(item2.getDisplayName())) itemsMatch = false;
		if (!item1.getItem().equals(item2.getItem())) itemsMatch = false;
		
		if (!itemsMatch) {
			awaitZero = true;
			return;
		}
		
		tilesMatched.add(click1);
		tilesMatched.add(click2);
		
		pairs.get(item1.getDisplayName()).remove(click1);
		pairs.get(item1.getDisplayName()).remove(click2);
		
		click1 = -1;
		click2 = -1;
		
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
	
	private static void addItem(ItemStack item, int slot) {
		
		tilesFound.add(slot);
		itemMap.put(slot, item.getItem());
		
		if (isBonus(item)) return;
		
		String name = item.getDisplayName();
		
		if (!pairs.containsKey(name)) pairs.put(name, new HashSet<>());
		
		pairs.get(name).add(slot);
		
	}
	
	private static boolean isBonus(ItemStack item) {
		
		if (item.getItem().equals(Items.AIR)) return true;
		if (item.getDisplayName().contains("Instant Find")) return true;
		if (item.getDisplayName().contains("Clicks")) return true;
		
		return false;
		
	}
	
	private static boolean isInBoard(ItemStack item) {
		
		if (item.getItem().equals(Item.getItemFromBlock(Blocks.STAINED_GLASS_PANE))) {
			if (item.getItemDamage() == 15) return false;
		}
		
		return true;
		
	}
	
	private static boolean isHidden(ItemStack item) {
		
		return item.getItem().equals(Item.getItemFromBlock(Blocks.STAINED_GLASS));
		
	}
	
	private static void reset() {
		
		if (!isPlaying) return;
		
		isPlaying = false;
		tilesFound.clear();
		tilesMatched.clear();
		itemMap.clear();
		pairs.clear();
		click1 = -1;
		click2 = -1;
		awaitZero = false;
		
	}
	
}
