package com.coop.skyblock2.utils;

import com.coop.skyblock2.commands.DebugCommand;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

public class Debug {

	public static void sendDungeonsDebug(String message) {
		
		if (!DebugCommand.solvers) return;
		
		Minecraft.getMinecraft().player.sendMessage(new TextComponentString(message));
		
	}
	
	public static void sendSecretsDebug(String message) {
		
		if (!DebugCommand.secrets) return;
		
		Minecraft.getMinecraft().player.sendMessage(new TextComponentString(Utils.chat(message)));
		
	}
	
}
