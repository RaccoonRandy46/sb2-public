package com.coop.skyblock2.listeners.slayers;

import com.coop.skyblock2.listeners.slayers.voidgloom.HeadFindListener;
import com.coop.skyblock2.listeners.slayers.voidgloom.RenderVoidgloomEvent;
import com.coop.skyblock2.listeners.slayers.voidgloom.YangGlyphListener;

import net.minecraftforge.common.MinecraftForge;

public class SlayerManager {

	public static void registerSlayerHelpers() {
		
		MinecraftForge.EVENT_BUS.register(new HeadFindListener());
		MinecraftForge.EVENT_BUS.register(new YangGlyphListener());
		MinecraftForge.EVENT_BUS.register(new RenderVoidgloomEvent());
		
	}
	
}
