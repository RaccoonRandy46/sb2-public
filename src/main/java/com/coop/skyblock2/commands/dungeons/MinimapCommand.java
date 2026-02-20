package com.coop.skyblock2.commands.dungeons;

import java.io.IOException;

import com.coop.skyblock2.utils.FileManager;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MinimapCommand {

	public static boolean enabled = false;
	
	@SubscribeEvent
	public void chatGo(ClientChatEvent e) {
		
		if (e.getMessage().equals("s>dmap")) {
			
			enabled = !enabled;
			
			try {
				FileManager.writeFile("dungeons", "minimap", enabled);
			} catch (IOException E) {}
			
			Minecraft.getMinecraft().player.sendMessage(new TextComponentString(Utils.chat(enabled ? "&aenabled minimap" : "&cdisabled minimap")));
			
		}
		
	}
	
}
