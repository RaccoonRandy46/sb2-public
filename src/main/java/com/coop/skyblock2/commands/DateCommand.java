package com.coop.skyblock2.commands;

import com.coop.skyblock2.utils.Utils;
import com.coop.skyblock2.utils.skyblock.SBTime;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DateCommand {
	
	@SubscribeEvent
	public void chatGo(ClientChatEvent e) {
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		if (e.getMessage().equals("s>sbdate")) {
			
			p.sendMessage(new TextComponentString(Utils.chat("&d&lThe date is currently:\n&a" + SBTime.getDate())));
			
		}
		
	}
	
}
