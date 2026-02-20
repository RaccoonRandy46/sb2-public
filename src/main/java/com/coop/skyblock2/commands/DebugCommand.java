package com.coop.skyblock2.commands;

import java.io.File;

import com.coop.skyblock2.utils.FileManager;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DebugCommand {

	public static boolean solvers = false;
	public static boolean secrets = false;
	
	@SubscribeEvent
	public void onCommand(ClientChatEvent e) {
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		String[] args = e.getMessage().split(" ");
		
		if (args[0].equals("s>debug")) {
			
			if (args.length < 2) {
				p.sendMessage(new TextComponentString(Utils.chat("&cUsage: s>debug <type>")));
				return;
			}
			
			try {
			
				switch (args[1]) {
				case "solvers":
					solvers = !solvers;
					FileManager.writeFile("debug", "solvers", solvers);
					p.sendMessage(new TextComponentString(Utils.chat(solvers ? ("&a" + args[1] + " debug enabled") : ("&c" + args[1] + " debug disabled"))));
					return;
				case "secrets":
					secrets = !secrets;
					FileManager.writeFile("debug", "secrets", secrets);
					p.sendMessage(new TextComponentString(Utils.chat(secrets ? ("&a" + args[1] + " debug enabled") : ("&c" + args[1] + " debug disabled"))));
					return;
				}
			
			} catch (Exception E) {}
			
			p.sendMessage(new TextComponentString(Utils.chat("&cunknown type")));
			
		}
		
	}
	
	@SubscribeEvent
	public void updateSettings(WorldEvent.Load e) {
		
		try {
			
			File solversFile = new File("mods/sb2/debug/solvers.cccm");
			File secretsFile = new File("mods/sb2/debug/secrets.cccm");
			
			if (solversFile.exists()) solvers = Boolean.parseBoolean(FileManager.readLine("mods/sb2/debug/solvers.cccm"));
			if (secretsFile.exists()) secrets = Boolean.parseBoolean(FileManager.readLine("mods/sb2/debug/secrets.cccm"));
			
		} catch (Exception E) {}
		
	}
	
}
