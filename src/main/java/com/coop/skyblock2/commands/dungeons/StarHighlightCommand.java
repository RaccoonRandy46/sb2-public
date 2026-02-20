package com.coop.skyblock2.commands.dungeons;

import java.io.IOException;

import com.coop.skyblock2.utils.FileManager;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class StarHighlightCommand {

	public static boolean alwaysActive = false;
	
	@SubscribeEvent
	public void chatGo(ClientChatEvent e) {
		
		String msg = e.getMessage();
		
		if (msg.equals("s>starhighlight") || msg.equals("s>starh") || msg.equals("s>star") || msg.equals("s>sh")) {
			
			alwaysActive = !alwaysActive;
			
			try {
				FileManager.writeFile("dungeons", "starhighlight", alwaysActive);
			} catch (IOException E) {}
			
			Minecraft.getMinecraft().player.sendMessage(new TextComponentString(Utils.chat(alwaysActive ? "&astar highlight set to always active" : "&cstar highlight set to keybind only [CTRL + F /// CTRL + SHIFT + F]")));
			
		}
		
	}
	
}
