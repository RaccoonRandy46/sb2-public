package com.coop.skyblock2.listeners.dungeons.solvers;

import org.lwjgl.opengl.GL11;

import com.coop.skyblock2.utils.Debug;
import com.coop.skyblock2.utils.MagicRenderer;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class RatHockeyListener {

	private static final int UNKNOWN = 0;
	private static final int POS_Z = 1;
	private static final int NEG_Z = 2;
	private static final int POS_X = 3;
	private static final int NEG_X = 4;
	
	private static int direction = UNKNOWN;
	private static int originX = -1;
	private static int originZ = -1;
	
	@SubscribeEvent
	public void calibrateRoom(PlayerTickEvent e) {
		
		if (!(e.player instanceof EntityPlayerSP)) return;
		
		if (!Utils.isInDungeons()) return;
		if (direction != UNKNOWN) return;
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		int x = 0;
		int z = 0;
		
		Entity silverfish = null;
		
		for (Entity entity : p.world.loadedEntityList) {
			
			if (!(entity instanceof EntitySilverfish)) continue;
			
			x = (int)entity.posX;
			z = (int)entity.posZ;
			
			if (x < 0) x--;
			if (z < 0) z--;
			
			Block block = p.world.getBlockState(new BlockPos(x, 66, z)).getBlock();
			
			if (block != Blocks.PACKED_ICE) continue;
			
			silverfish = entity;
			break;
			
		}
		
		if (silverfish == null) return;
		
		Debug.sendDungeonsDebug("-\nstarting xz: (" + x + ", " + z + ")");
		
		int maxX = x;
		int minX = x;
		int maxZ = z;
		int minZ = z;
		
		while (p.world.getBlockState(new BlockPos(maxX + 1, 66, z)).getBlock() == Blocks.PACKED_ICE) maxX++;
		
		while (p.world.getBlockState(new BlockPos(minX - 1, 66, z)).getBlock() == Blocks.PACKED_ICE) minX--;
		
		while (p.world.getBlockState(new BlockPos(x, 66, maxZ + 1)).getBlock() == Blocks.PACKED_ICE) maxZ++;
		
		while (p.world.getBlockState(new BlockPos(x, 66, minZ - 1)).getBlock() == Blocks.PACKED_ICE) minZ--;
		
		Debug.sendDungeonsDebug("minX: " + minX + ", maxX: " + maxX);
		Debug.sendDungeonsDebug("minZ: " + minZ + ", maxZ: " + maxZ);
		
		x = (minX + maxX) / 2;
		z = (minZ + maxZ) / 2;
		
		Debug.sendDungeonsDebug("centerXZ: (" + x + ", " + z + ")");
		
		originX = x;
		originZ = z;
		
		for (int i = 0; i < 18; i++) {
			
			if (p.world.getBlockState(new BlockPos(x + i, 67, z)).getBlock() == Blocks.CHEST) {
				x += i;
				direction = POS_X;
				break;
			}
			
			if (p.world.getBlockState(new BlockPos(x - i, 67, z)).getBlock() == Blocks.CHEST) {
				x -= i;
				direction = NEG_X;
				break;
			}
			
			if (p.world.getBlockState(new BlockPos(x, 67, z + i)).getBlock() == Blocks.CHEST) {
				z += i;
				direction = POS_Z;
				break;
			}
			
			if (p.world.getBlockState(new BlockPos(x, 67, z - i)).getBlock() == Blocks.CHEST) {
				z -= i;
				direction = NEG_Z;
				break;
			}
			
		}
		
		int[] xz = translatedCoordinates(x, z, -8, -20);
		
		originX = xz[0];
		originZ = xz[1];
		
		Debug.sendDungeonsDebug("x: " + originX + "\nz: " + originZ + "\ndirection: " + direction);
		
	}
	
	@SubscribeEvent
	public void drawSolution(RenderWorldLastEvent e) {
		
		if (direction == UNKNOWN) return;
		
		MagicRenderer renderer = new MagicRenderer();
		renderer.autoTranslate(e.getPartialTicks());
		
		renderer.lineWidth(3);
		renderer.color(0, 255, 0);
		
		renderer.begin(GL11.GL_LINES);
		
		translatedVertex(renderer, 15, 1);
		translatedVertex(renderer, 15, 0);
		
		translatedVertex(renderer, 15, 0);
		translatedVertex(renderer, 14, 0);
		
		translatedVertex(renderer, 14, 0);
		translatedVertex(renderer, 14, 16);
		
		translatedVertex(renderer, 14, 16);
		translatedVertex(renderer, 16, 16);
		
		translatedVertex(renderer, 16, 16);
		translatedVertex(renderer, 16, 14);
		
		translatedVertex(renderer, 16, 14);
		translatedVertex(renderer, 13, 14);
		
		translatedVertex(renderer, 13, 14);
		translatedVertex(renderer, 13, 1);
		
		translatedVertex(renderer, 13, 1);
		translatedVertex(renderer, 11, 1);
		
		translatedVertex(renderer, 11, 1);
		translatedVertex(renderer, 11, 0);
		
		translatedVertex(renderer, 11, 0);
		translatedVertex(renderer, 3, 0);
		
		translatedVertex(renderer, 3, 0);
		translatedVertex(renderer, 3, 16);
		
		translatedVertex(renderer, 3, 16);
		translatedVertex(renderer, 4, 16);
		
		translatedVertex(renderer, 4, 16);
		translatedVertex(renderer, 4, 15);
		
		translatedVertex(renderer, 4, 15);
		translatedVertex(renderer, 2, 15);
		
		translatedVertex(renderer, 2, 15);
		translatedVertex(renderer, 2, 6);
		
		translatedVertex(renderer, 2, 6);
		translatedVertex(renderer, 9, 6);
		
		translatedVertex(renderer, 9, 6);
		translatedVertex(renderer, 9, 17);
		
		renderer.color(0, 255, 255);
		
		translatedVertex(renderer, 15, 1);
		translatedVertex(renderer, 13, 1);
		
		renderer.draw();
		renderer.end();
		
	}
	
	private static void translatedVertex(MagicRenderer renderer, int xTranslation, int zTranslation) {
		int[] xz = translatedCoordinates(originX, originZ, xTranslation, zTranslation);
		renderer.vc(xz[0] + 0.5f, 67.5f, xz[1] + 0.5f);
	}
	
	private static int[] translatedCoordinates(int tx, int tz, int xTranslation, int zTranslation) {
		
		switch (direction) {
		case POS_Z:
			tx -= xTranslation;
			tz += zTranslation;
			break;
		case NEG_Z:
			tx += xTranslation;
			tz -= zTranslation;
			break;
		case POS_X:
			tx += zTranslation;
			tz += xTranslation;
			break;
		case NEG_X:
			tx -= zTranslation;
			tz -= xTranslation;
			break;
		}
		
		return new int[] {tx, tz};
		
	}
	
	@SubscribeEvent
	public void loadWorld(WorldEvent.Load e) {
		reset();
	}
	
	public static void reset() {
		direction = UNKNOWN;
		originX = -1;
		originZ = -1;
	}
	
}
