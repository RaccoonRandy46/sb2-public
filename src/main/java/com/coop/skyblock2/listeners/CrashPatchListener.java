package com.coop.skyblock2.listeners;

import java.util.HashMap;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CrashPatchListener {

	private static HashMap<UUID, Integer> cryptMap = new HashMap<>();
	private static HashMap<UUID, Integer> endermanMap = new HashMap<>();
	
	@SubscribeEvent
	public void renderPlayer(RenderPlayerEvent.Pre e) {
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		EntityPlayer u = e.getEntityPlayer();
		
		if (u instanceof EntityPlayerSP) return;
		
		UUID uuid = u.getUniqueID();
		u.setPrimaryHand(EnumHandSide.RIGHT);
		
		if (!cryptMap.containsKey(uuid)) {
			
			int crashIndex = 0;
			try {
				u.isWearing(EnumPlayerModelParts.CAPE);
				u.isWearing(EnumPlayerModelParts.HAT);
				u.isWearing(EnumPlayerModelParts.JACKET);
				u.isWearing(EnumPlayerModelParts.LEFT_PANTS_LEG);
				u.isWearing(EnumPlayerModelParts.LEFT_SLEEVE);
				u.isWearing(EnumPlayerModelParts.RIGHT_PANTS_LEG);
				u.isWearing(EnumPlayerModelParts.RIGHT_SLEEVE);
				u.getPrimaryHand();
				crashIndex = 8;
			} catch (Exception E) {}
			
			if (crashIndex == 0) p.sendMessage(new TextComponentString("crash index = 7"));
			
			cryptMap.put(uuid, crashIndex);
			return;
			
		}
		
		if (cryptMap.get(uuid) == 8) return;
		
		e.setCanceled(true);
		
	}
	
	@SubscribeEvent
	public void renderEnderman(RenderLivingEvent<EntityEnderman> e) {
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		EntityEnderman u = (EntityEnderman)e.getEntity();
		
		UUID uuid = u.getUniqueID();
		
		if (!endermanMap.containsKey(uuid)) {
			
			int crashIndex = 0;
			try {
				u.getHeldBlockState();
				crashIndex = 8;
			} catch (Exception E) {}
			
			if (crashIndex == 0) p.sendMessage(new TextComponentString("crash index = 8"));
			
			endermanMap.put(uuid, crashIndex);
			return;
			
		}
		
		if (endermanMap.get(uuid) == 8) return;
		
		e.setCanceled(true);
		
	}
	
	@SubscribeEvent
	public void loadWorld(WorldEvent.Load e) {
		cryptMap = new HashMap<>();
		endermanMap = new HashMap<>();
	}
	
}
