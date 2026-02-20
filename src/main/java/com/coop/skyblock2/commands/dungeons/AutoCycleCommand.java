package com.coop.skyblock2.commands.dungeons;

import java.io.IOException;

import com.coop.skyblock2.utils.FileManager;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoCycleCommand {

	public static boolean enabled = true;
	
	@SubscribeEvent
	public void chatGo(ClientChatEvent e) {
		
		if (e.getMessage().equals("s>autocycle")) {
			
			enabled = !enabled;
			
			try {
				FileManager.writeFile("dungeons", "autocycle", enabled);
			} catch (IOException E) {}
			
			Minecraft.getMinecraft().player.sendMessage(new TextComponentString(Utils.chat(enabled ? "&aauto cycle enabled" : "&cauto cycle disabled")));
			
		}
		
	}
	
}
