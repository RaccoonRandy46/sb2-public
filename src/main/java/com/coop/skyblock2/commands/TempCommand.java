package com.coop.skyblock2.commands;

import com.coop.skyblock2.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TempCommand {

	public static boolean on = false;
	
	@SubscribeEvent
	public void chatGo(ClientChatEvent e) {

		EntityPlayerSP p = Minecraft.getMinecraft().player;
		String[] args = e.getMessage().split(" ");
		
		if (args[0].equals("s>ttest")) {
			
			for (Entity entity : p.world.getLoadedEntityList()) {
				
				if (Math.abs(p.posX - entity.posX) > 2) continue;
				if (Math.abs(p.posY - entity.posY) > 3) continue;
				if (Math.abs(p.posZ - entity.posZ) > 2) continue;
				
				if (!(entity instanceof EntityArmorStand)) continue;
				
				String name = entity.getCustomNameTag();
				String rawName = Utils.removeColorcodes(name);
				
				p.sendMessage(new TextComponentString("-\nname: [" + name + "]"));
				for (int i = 0; i < rawName.length(); i++) {
					
					char c = rawName.charAt(i);
					int id = (int)c;
					
					p.sendMessage(new TextComponentString(i + ": '" + c + "' (" + id + ")"));
					
				}
				
			}
			
		}
		
	}
	
}
