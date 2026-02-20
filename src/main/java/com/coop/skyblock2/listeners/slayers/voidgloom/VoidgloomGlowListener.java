package com.coop.skyblock2.listeners.slayers.voidgloom;

import org.lwjgl.opengl.GL11;

import net.minecraft.entity.monster.EntityEnderman;
import net.minecraftforge.client.event.RenderLivingEvent;

public class VoidgloomGlowListener {

	public static void onEvent(RenderLivingEvent.Pre<EntityEnderman> e, EntityEnderman enderman) {
		
		int r = 255;
		int g = 66;
		int b = 66;
		
		if (System.currentTimeMillis() % 200 > 100) {
			r = 66;
			g = 255;
			b = 255;
		}
		
		GL11.glColor4ub((byte)r, (byte)g, (byte)b, (byte)255);
		
	}
	
}
