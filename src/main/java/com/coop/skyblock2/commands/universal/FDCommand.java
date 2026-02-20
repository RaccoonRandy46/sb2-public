package com.coop.skyblock2.commands.universal;

import java.util.List;
import java.util.regex.Pattern;

import com.coop.skyblock2.Main;
import com.coop.skyblock2.listeners.experiments.ExperimentUtils;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FDCommand {

	private static boolean enabled = false;
	private static int killThreshold = 10000;
	
	@SubscribeEvent
	public void chatGo(ClientChatEvent e) {
		
		if (!Main.universal) return;
		
		String[] args = e.getMessage().split(" ");
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		if (args[0].equals("s>fd")) {
			
			if (!Utils.isInSkyblock()) {
				p.sendMessage(new TextComponentString(Utils.chat("&cyou are not in skyblock")));
				return;
			}
			
			if (args.length < 2) {
				
				enabled = !enabled;
				p.sendMessage(new TextComponentString(Utils.chat(enabled ? "&afd on" : "&cfd off")));
				return;
				
			}
			
			int newThreshold = 0;
			
			try {
				
				newThreshold = Integer.parseInt(args[1]);
				
			} catch (Exception E) {
				
				p.sendMessage(new TextComponentString(Utils.chat("&cthat not number")));
				return;
				
			}
			
			killThreshold = newThreshold;
			enabled = true;
			p.sendMessage(new TextComponentString(Utils.chat("&afd on\n&8kill threshold: &6" + newThreshold)));
			
		}
		
	}
	
	@SubscribeEvent
	public void drawMenu(GuiScreenEvent.DrawScreenEvent.Post e) {
		
		if (!enabled) return;
		if (!Utils.isInSkyblock()) return;
		
		Container gui = getGameGui();
		GuiChest chest = getChest();
		
		if (gui == null) return;
		
		int guiSize = ((chest.getYSize() - 114) / 18) * 9;
		
		NonNullList<ItemStack> items = gui.getInventory();
		
		for (int i = 0; i < items.size() && i < guiSize; i++) {
			
			ItemStack stack = items.get(i);
			if (!Utils.removeColorcodes(stack.getDisplayName()).contains("Final Destination")) continue;
			
			int color = 0xcc000000;
			
			List<String> lore = chest.getItemToolTip(stack);
			
			for (String s : lore) {
				
				s = Utils.removeColorcodes(s);

				if (!s.startsWith("Next Upgrade: ")) continue;

				String killString = s.split(Pattern.quote("("))[1].split("/")[0].replace(",", "");
				int kills = Integer.parseInt(killString);
				
				if (kills < killThreshold) continue;
				
				color = 0x7700ff00;
				break;
				
			}
			
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
		
		if (!invName.equals("Auctions Browser") && !invName.startsWith("Auctions: \"")) return null;
		
		return chest.inventorySlots;
		
	}
	
}
