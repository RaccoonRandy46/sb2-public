package com.coop.skyblock2.listeners.slayers.voidgloom;

import java.util.HashSet;
import java.util.UUID;

import com.coop.skyblock2.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class RenderVoidgloomEvent {

	private static HashSet<UUID> armorStandUUIDs = new HashSet<>();
	private static HashSet<UUID> voidgloomUUIDs = new HashSet<>();
	
	@SubscribeEvent
	public void findVoidglooms(PlayerTickEvent e) {
		
		if (!(e.player instanceof EntityPlayerSP)) return;
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		for (Entity entity : p.world.loadedEntityList) {
			
			if (!(entity instanceof EntityArmorStand)) continue;
			if (entity.ticksExisted <= 10) continue;
			if (armorStandUUIDs.contains(entity.getUniqueID())) continue;
			
			armorStandUUIDs.add(entity.getUniqueID());
			
			String decolorizedName = Utils.removeColorcodes(entity.getName());
			if (!decolorizedName.startsWith((char)9760 + " Voidgloom Seraph")) continue;
			
			EntityEnderman voidgloom = nearestEnderman(entity);
			if (voidgloom == null) continue;
			
			voidgloomUUIDs.add(voidgloom.getUniqueID());
			
		}
		
	}
	
	@SubscribeEvent
	public void addGlow(RenderLivingEvent.Pre<EntityEnderman> e) {
		
		if (!(e.getEntity() instanceof EntityEnderman)) return;
		if (voidgloomUUIDs.size() == 0) return;
		if (!voidgloomUUIDs.contains(e.getEntity().getUniqueID())) return;
		
		executeEvent(e, (EntityEnderman)e.getEntity());
		
	}
	
	@SubscribeEvent
	public void worldLoad(WorldEvent.Load e) {
		armorStandUUIDs = new HashSet<>();
		voidgloomUUIDs = new HashSet<>();
	}
	
	private static EntityEnderman nearestEnderman(Entity entity) {
		
		double shortestDist = -1;
		EntityEnderman out = null;
		
		for (Entity other : Minecraft.getMinecraft().player.world.loadedEntityList) {
			
			if (!(other instanceof EntityEnderman)) continue;
			
			double a = Math.abs(entity.posX - other.posX);
			double b = Math.abs(entity.posZ - other.posZ);
			double dist = Math.sqrt(a * a + b * b);
			
			if (dist > 1) continue;
			
			if (dist < shortestDist || shortestDist == -1) {
				shortestDist = dist;
				out = (EntityEnderman)other;
			}
			
		}
		
		return out;
		
	}
	
	private static void executeEvent(RenderLivingEvent.Pre<EntityEnderman> event, EntityEnderman enderman) {
		
		//always on bottom
		VoidgloomGlowListener.onEvent(event, enderman);
		
	}
	
}
