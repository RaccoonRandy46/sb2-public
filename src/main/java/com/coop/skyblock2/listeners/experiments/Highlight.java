package com.coop.skyblock2.listeners.experiments;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.inventory.Slot;

public class Highlight {

	public int x;
	public int y;
	
	public Highlight(Slot slot) {
		
		int xOffset = 0;
		int yOffset = 0;
		
		GuiScreen screen = Minecraft.getMinecraft().currentScreen;
		
		if (screen != null) {
			
			xOffset = (screen.width - 176) / 2;
			yOffset = (screen.height - 221) / 2;
			
		}
		
		this.x = slot.xPos + xOffset;
		this.y = slot.yPos + yOffset;
		
	}
	
}
