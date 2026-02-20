package com.coop.skyblock2.listeners.dungeons.secrets;

import java.io.File;

import com.coop.skyblock2.commands.dungeons.AutoCycleCommand;
import com.coop.skyblock2.utils.FileManager;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SecretManager {

	public static boolean enabled = false;
	
	public static void registerFinders() {
		
		MinecraftForge.EVENT_BUS.register(new SecretManager());
		MinecraftForge.EVENT_BUS.register(new SecretListener());
		
	}
	
	@SubscribeEvent
	public void loadWorld(WorldEvent.Load e) {
		
		updateSettings();
		
	}
	
	public static void updateSettings() {
		
		try {
			
			File secrets = new File("mods/sb2/dungeons/secrets.cccm");
			File autocycle = new File("mods/sb2/dungeons/autocycle.cccm");
			
			if (secrets.exists()) enabled = Boolean.parseBoolean(FileManager.readLine("mods/sb2/dungeons/secrets.cccm"));
			if (autocycle.exists()) AutoCycleCommand.enabled = Boolean.parseBoolean(FileManager.readLine("mods/sb2/dungeons/autocycle.cccm"));
			
		} catch (Exception E) {}
		
	}
	
}
