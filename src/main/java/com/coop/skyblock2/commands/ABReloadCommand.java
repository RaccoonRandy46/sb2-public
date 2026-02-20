package com.coop.skyblock2.commands;

import com.coop.skyblock2.utils.Utils;
import com.coop.skyblock2.utils.skyblock.AuctionData;
import com.coop.skyblock2.utils.skyblock.BazaarData;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ABReloadCommand {

	@SubscribeEvent
	public void chatGo(ClientChatEvent e) {
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		if (e.getMessage().equals("s>abreload")) {
			
			long cooldownTime = System.currentTimeMillis() - AuctionData.cooldown;
			
			if (cooldownTime < 600000) {
				
				long remainingTime = 600000 - cooldownTime;
				
				long minutes = remainingTime / 60000;
				long seconds = remainingTime % 60000 / 1000;
				String secondsString = "" + (seconds < 10 ? "0" + seconds : seconds);
				
				String cooldownString = "(";
				if (minutes > 0) cooldownString += minutes + "m ";
				cooldownString += secondsString + "s)";
				
				p.sendMessage(new TextComponentString(Utils.chat("&cthis command is on cooldown! " + cooldownString)));
				return;
				
			}
			
			BazaarData.reloadData();
			AuctionData.reloadData();
			
			p.sendMessage(new TextComponentString(Utils.chat("&areloading auction and bazaar data...")));
			
		}
		
	}
	
}
