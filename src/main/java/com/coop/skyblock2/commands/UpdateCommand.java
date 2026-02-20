package com.coop.skyblock2.commands;

import com.coop.skyblock2.listeners.dungeons.ChestScamListener;
import com.coop.skyblock2.listeners.dungeons.FelsListener;
import com.coop.skyblock2.listeners.dungeons.MinimapListener;
import com.coop.skyblock2.listeners.dungeons.StarHighlightListener;
import com.coop.skyblock2.listeners.dungeons.secrets.SecretManager;

import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class UpdateCommand {

	@SubscribeEvent
	public void chatGo(ClientChatEvent e) {
		
		if (e.getMessage().equals("s>update")) updateAll();
		
	}
	
	@SubscribeEvent
	public void joinWorld(WorldEvent.Load e) {
		updateAll();
	}
	
	public static void updateAll() {
		
		FelsListener.updateSettings();
		MinimapListener.updateSettings();
		ChestScamListener.updateSettings();
		SecretManager.updateSettings();
		MinimapListener.updateSettings();
		StarHighlightListener.updateSettings();
		
	}
	
}
