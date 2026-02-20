package com.coop.skyblock2.listeners.experiments;

import net.minecraftforge.common.MinecraftForge;

public class ExperimentManager {

	public static void registerExperiments() {
		
		MinecraftForge.EVENT_BUS.register(new SequenceGame());
		MinecraftForge.EVENT_BUS.register(new SongGame());
		MinecraftForge.EVENT_BUS.register(new SuperpairsGame());
		
	}
	
}
