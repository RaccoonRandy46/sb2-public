package com.coop.skyblock2.commands.dungeons;

import org.lwjgl.input.Keyboard;

import com.coop.skyblock2.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class IIDCommand {

	public static boolean bool = false;
	
	@SubscribeEvent
	public void chatGo(ClientChatEvent e) {
		
		if (e.getMessage().equals("s>iid")) {
			
			bool = true;
			Minecraft.getMinecraft().player.sendMessage(new TextComponentString(Utils.chat("&c&lOK SORRY")));
			
		}
		
	}
	
	@SubscribeEvent
	public void playerTick(PlayerTickEvent e) {
		
		if (!(e.player instanceof EntityPlayerSP)) return;
		
		if (!Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) return;
		if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) return;
		if (!Keyboard.isKeyDown(Keyboard.KEY_D)) return;
		if (!Keyboard.isKeyDown(Keyboard.KEY_F12)) return;
		
		bool = true;
		
	}
	
	@SubscribeEvent
	public void worldLoad(WorldEvent.Load e) {
		
		bool = false;
		
	}
	
}
