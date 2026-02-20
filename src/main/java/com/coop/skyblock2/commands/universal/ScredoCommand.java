package com.coop.skyblock2.commands.universal;

import com.coop.skyblock2.Main;
import com.coop.skyblock2.listeners.dungeons.secrets.SecretListener;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ScredoCommand {

	public static int originX = -1;
	public static int originZ = -1;
	public static int direction = -1;
	
	@SubscribeEvent
	public void chatGo(ClientChatEvent e) {
		
		if (!Main.universal) return;
		
		String[] args = e.getMessage().split(" ");
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		if (args[0].equals("s>scredo")) {
			
			if (!Utils.isInDungeons()) {
				p.sendMessage(new TextComponentString(Utils.chat("&cyou are not in dungeons")));
				return;
			}
			
			SecretListener.clearCell();
			
		}
		
	}
	
}
