package com.coop.skyblock2.listeners.dungeons.solvers;

import org.lwjgl.opengl.GL11;

import com.coop.skyblock2.utils.MagicRenderer;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlazeListener {

	private static final int UNKNOWN = 0;
	private static final int ASCENDING = 1;
	private static final int DESCENDING = 2;
	
	private static int order = UNKNOWN;
	
	private static double originX = -1;
	private static double originZ = -1;
	
	@SubscribeEvent
	public void getOrder(RenderWorldLastEvent e) {
		
		if (!Utils.isInDungeons()) return;
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		if (order != UNKNOWN) return;
		
		Entity entity = null;
		
		for (Entity currentEntity : p.world.loadedEntityList) {
			if (currentEntity instanceof EntityBlaze) {
				entity = currentEntity;
				break;
			}
		}
		
		if (entity == null) return;
		
		order = ASCENDING;
		
		originX = entity.posX;
		originZ = entity.posZ;
		
		int ax = (int)entity.posX;
		int ay = 19;
		int az = (int)entity.posZ;
		
		boolean passedCheck1 = false;
		
		for (int x = -3; x <= 3; x++) {
			for (int z = -3; z <= 3; z++) {
				if (entity.world.getBlockState(new BlockPos(ax + x, ay, az + z)).getBlock() != Blocks.AIR) {
					passedCheck1 = true;
					break;
				}
			}
			if (passedCheck1) break;
		}
		
		if (!passedCheck1) return;
		ay = 18;

		for (int x = -3; x <= 3; x++) {
			for (int z = -3; z <= 3; z++) {
				Block b = entity.world.getBlockState(new BlockPos(ax + x, ay, az + z)).getBlock();
				if (b == Blocks.WATER) {
					order = DESCENDING;
					return;
				}
			}
		}
		
	}
	
	@SubscribeEvent
	public void drawBox(RenderWorldLastEvent e) {
		
		if (!Utils.isInDungeons()) return;
		
		if (order == UNKNOWN) return;
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		int nextHP = -1;
		
		Entity entity = null;
		
		for (Entity currentEntity : p.world.loadedEntityList) {
			
			if (!(currentEntity instanceof EntityArmorStand)) continue;
			
			if (Math.abs(currentEntity.posX - originX) > 7) continue;
			if (Math.abs(currentEntity.posZ - originZ) > 7) continue;
			
			String name = Utils.removeColorcodes(currentEntity.getName());
			if (!name.contains("Blaze") || !name.contains("/")) continue;
			
			String hpString = name.split("/")[1];
			hpString = hpString.substring(0, hpString.length() - 1);
			hpString = hpString.replace(",", "");
			int hp = Integer.parseInt(hpString);
			
			if ((order == ASCENDING ? hp < nextHP : hp > nextHP) || nextHP == -1) {
				nextHP = hp;
				entity = currentEntity;
			}
			
		}
		
		if (entity == null) return;
		
		if (Math.abs(entity.posX - p.posX) > 17) return;
		if (Math.abs(entity.posZ - p.posZ) > 17) return;
	  
		float x = (float)entity.posX;
		float y = (float)entity.posY - 2;
		float z = (float)entity.posZ;
		
		MagicRenderer renderer = new MagicRenderer();
		renderer.autoTranslate(e.getPartialTicks());
		
		renderer.lineWidth(3);
		renderer.color(0, 255, 0);
		renderer.disableDepth();
		
		renderer.begin(GL11.GL_LINES);
		
		renderer.vc(x + -0.5f, y + 0, z + -0.5f);
		renderer.vc(x + -0.5f, y + 0, z + 0.5f);
		renderer.vc(x + -0.5f, y + 0, z + 0.5f);
		renderer.vc(x + 0.5f, y + 0, z + 0.5f);
		renderer.vc(x + 0.5f, y + 0, z + 0.5f);
		renderer.vc(x + 0.5f, y + 0, z + -0.5f);
		renderer.vc(x + 0.5f, y + 0, z + -0.5f);
		renderer.vc(x + -0.5f, y + 0, z + -0.5f);

		renderer.vc(x + -0.5f, y + 2, z + -0.5f);
		renderer.vc(x + -0.5f, y + 2, z + 0.5f);
		renderer.vc(x + -0.5f, y + 2, z + 0.5f);
		renderer.vc(x + 0.5f, y + 2, z + 0.5f);
		renderer.vc(x + 0.5f, y + 2, z + 0.5f);
		renderer.vc(x + 0.5f, y + 2, z + -0.5f);
		renderer.vc(x + 0.5f, y + 2, z + -0.5f);
		renderer.vc(x + -0.5f, y + 2, z + -0.5f);

		renderer.vc(x + -0.5f, y + 0, z + -0.5f);
		renderer.vc(x + -0.5f, y + 2, z + -0.5f);
		renderer.vc(x + -0.5f, y + 0, z + 0.5f);
		renderer.vc(x + -0.5f, y + 2, z + 0.5f);
		renderer.vc(x + 0.5f, y + 0, z + 0.5f);
		renderer.vc(x + 0.5f, y + 2, z + 0.5f);
		renderer.vc(x + 0.5f, y + 0, z + -0.5f);
		renderer.vc(x + 0.5f, y + 2, z + -0.5f);
		
		renderer.draw();
		renderer.enableDepth();
		renderer.end();
		
	}
	
	@SubscribeEvent
	public void worldLoad(WorldEvent.Load e) {
		reset();
	}
	
	public static void reset() {
		order = UNKNOWN;
		originX = -1;
		originZ = -1;
	}
	
}
