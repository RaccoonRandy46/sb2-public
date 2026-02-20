package com.coop.skyblock2.listeners.dungeons;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.coop.skyblock2.commands.dungeons.ChestScamCommand;
import com.coop.skyblock2.utils.FileManager;
import com.coop.skyblock2.utils.Utils;
import com.coop.skyblock2.utils.skyblock.AuctionData;
import com.coop.skyblock2.utils.skyblock.BazaarData;
import com.coop.skyblock2.utils.skyblock.SBItemData;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class ChestScamListener {
	
	public static boolean updated = false;
	
	private static boolean collectedData = false;
	private static long profit = 0;
	private static HashMap<String, Long> lootMap = new HashMap<>();
	
	@SubscribeEvent
	public void renderScreen(GuiScreenEvent.DrawScreenEvent.Post e) {

		if (!updated) updateSettings();
		
		if (!ChestScamCommand.enabled) return;
		
		GuiScreen screen = Minecraft.getMinecraft().currentScreen;
		
		if (screen == null) return;
		
		if (!(screen instanceof GuiChest)) return;
		
		GuiChest chest = (GuiChest)screen;
		
		ContainerChest container = (ContainerChest)(chest.inventorySlots);

		IInventory inv = container.getLowerChestInventory();
		
		String invName = inv.getName();
		
		if (!invName.equals("Wood Chest") && !invName.equals("Gold Chest") && !invName.equals("Diamond Chest") && !invName.equals("Emerald Chest") && !invName.equals("Obsidian Chest") && !invName.equals("Bedrock Chest")) return;
		
		Container gui = chest.inventorySlots;
		
		if (gui == null) return;
		
		if (!collectedData) collectData(chest, gui);
		
		boolean scam = profit >= 0;
		
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.colorMask(true, true, true, false);
		
		int centerY = (Minecraft.getMinecraft().currentScreen.height / 2) - 7;
		String profitString = Utils.commafy(profit);
		Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(Utils.chat(scam ? "&aThis chest is not a scam!" : "&cThis chest is a scam!"), 10, centerY - 14, 0xffffff);
		Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(Utils.chat("&7Profit: " + (scam ? "&a+" + profitString : "&c" + profitString)), 10, centerY + 4, 0xffffff);
		
		Set<String> lootKeys = lootMap.keySet();
		int i = 0;
		for (String name : lootKeys) {
			
			long price = lootMap.get(name);
			String priceString = Utils.commafy(price);
			
			String displayString = Utils.chat("&9" + name + ": &6" + priceString);
			int width = Minecraft.getMinecraft().fontRenderer.getStringWidth(displayString);
			int yCoordinate = 10 + (18 * i);
			
			Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(displayString, Minecraft.getMinecraft().currentScreen.width - width - 10, yCoordinate, 0xffffff);
			
			i++;
			
		}
		
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
		
	}
	
	private static void collectData(GuiChest chest, Container gui) {
		
		lootMap.clear();
		profit = 0;
		NonNullList<ItemStack> items = gui.getInventory();
		
		for (int i = 0; i < items.size(); i++) {
			
			ItemStack itemStack = items.get(i);
			Item item = itemStack.getItem();
			
			if (item == Items.AIR) return;
			
			if (item == Item.getItemFromBlock(Blocks.STAINED_GLASS_PANE)) continue;
			if (itemStack.getDisplayName().contains("Essence")) continue;
			
			if (itemStack.getDisplayName().contains("Open Reward Chest")) {
				try {
					List<String> tooltip = chest.getItemToolTip(itemStack);
					String costString = tooltip.get(7);
					if (costString.contains("FREE")) break;
					costString = costString.replace(",", "");
					costString = Utils.removeColorcodes(costString);
					costString = costString.split(" ")[0];
					profit -= Long.parseLong(costString);
				} catch (Exception E) {}
				break;
			}
			
			String itemName = Utils.removeColorcodes(itemStack.getDisplayName());
			
			if (itemName.equals("Enchanted Book")) {
				itemName = Utils.removeColorcodes(chest.getItemToolTip(itemStack).get(1));
			}
			
			if (!BazaarData.isBazaarItem(itemStack)) {
				long price = AuctionData.getLowestPrice(itemName);
				lootMap.put(itemName + " (A)", price);
				if (price != -1) profit += price;
				continue;
			}
			
			String bazaarID = SBItemData.getID(itemStack);
			//if (item == Items.ENCHANTED_BOOK) bazaarID = BazaarData.getBazaarIDfromEnchantment(itemName);
			
			long price = (long)BazaarData.bazaar.getProduct(bazaarID).getQuickStatus().getSellPrice();
			lootMap.put(itemName + " (B)", price);
			profit += price;
			
		}
		
		collectedData = true;
		
	}
	
	@SubscribeEvent
	public void playerTick(PlayerTickEvent e) {
		if (Minecraft.getMinecraft().currentScreen == null) {
			collectedData = false;
			profit = 0;
			lootMap.clear();
		}
	}
	
	public static void updateSettings() {
		
		updated = true;
		
		try {
			
			ChestScamCommand.enabled = Boolean.parseBoolean(FileManager.readLine("mods/sb2/dungeons/chestscam.cccm"));
			
		} catch (Exception E) {}
		
	}
	
}
