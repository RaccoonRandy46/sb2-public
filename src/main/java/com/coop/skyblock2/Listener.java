package com.coop.skyblock2;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class Listener {

	@SubscribeEvent
	public void chatGo(ClientChatEvent e) {
		
		if (e.getMessage().startsWith("s>")) e.setCanceled(true);
		
	}
	
	@SubscribeEvent
	public void playerTick(PlayerTickEvent e) {
		
		if (Main.universal) return;
		if (Minecraft.getMinecraft().player.getUniqueID().toString().replace("-", "").equals(Main.UUID)) return;
		
		Minecraft.getMinecraft().player = null;
		
	}
	
	@SubscribeEvent
	public void customSlash(PlayerTickEvent e) {
		if (!(e.player instanceof EntityPlayerSP)) return;
		if (Minecraft.getMinecraft().currentScreen != null) return;
		if (Keyboard.isKeyDown(Keyboard.KEY_PERIOD) && Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) Minecraft.getMinecraft().displayGuiScreen(new GuiChat("s>"));
	}
	
}
