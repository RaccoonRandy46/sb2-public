package com.coop.skyblock2.listeners.dungeons.solvers.creeper;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.coop.skyblock2.utils.Debug;
import com.coop.skyblock2.utils.MagicRenderer;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class CreeperListener {

	private static final int UNKNOWN = 0;
	private static final int POS_Z = 1;
	private static final int NEG_Z = 2;
	private static final int POS_X = 3;
	private static final int NEG_X = 4;
	
	private static int direction = UNKNOWN;
	private static int centerX = -1;
	private static int centerZ = -1;
	
	private static ArrayList<ArrayList<int[]>> pairs = new ArrayList<>();
	private static ArrayList<int[]> takenCoordinates = new ArrayList<>();
	
	@SubscribeEvent
	public void playerTick(PlayerTickEvent e) {
		
		if (!(e.player instanceof EntityPlayerSP)) return;
		
		if (!Utils.isInDungeons()) return;
		if (direction != UNKNOWN) return;
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		Entity creeper = null;
		
		for (Entity entity : p.world.loadedEntityList) {
			
			if (!(entity instanceof EntityCreeper)) continue;
			if (entity.posY < 75 || entity.posY > 80) continue;
			if (entity.isInvisible()) continue;
			
			creeper = entity;
			break;
			
		}
		
		if (creeper == null) return;
		
		int x = (int)creeper.posX;
		int z = (int)creeper.posZ;
		
		boolean foundCenter = false;
		
		for (int i = 0; i < 3; i++) {
			
			int by = (int)creeper.posY - i;
			
			for (int ix = -1; ix <= 1; ix++) {
				for (int iz = -1; iz <= 1; iz++) {
					
					int bx = x + ix;
					int bz = z + iz;
					
					Block block = p.world.getBlockState(new BlockPos(bx, by, bz)).getBlock();
					if (block != Blocks.SEA_LANTERN && block != Blocks.PRISMARINE) continue;
					
					foundCenter = true;
					centerX = bx;
					centerZ = bz;
					
					break;
					
				}
				if (foundCenter) break;
			}
			if (foundCenter) break;
		}
		
		if (!foundCenter) return;
		
		EnumFacing facing = creeper.getHorizontalFacing();
		
		switch (facing) {
		case NORTH:
			direction = POS_Z;
			break;
		case EAST:
			direction = NEG_X;
			break;
		case SOUTH:
			direction = NEG_Z;
			break;
		case WEST:
			direction = POS_X;
			break;
		case UP:
			return;
		case DOWN:
			return;
		}
		
		Debug.sendDungeonsDebug("creeper room found!\nx: " + centerX + "\nz: " + centerZ + "\ndirection: " + direction);
		
	}
	
	@SubscribeEvent
	public void getPairs(PlayerTickEvent e) {
		
		if (!(e.player instanceof EntityPlayerSP)) return;
		
		if (pairs.size() > 0 || direction == UNKNOWN) return;
		
		for (ArrayList<int[]> pair : CreeperPairs.pairs) {
			
			if (isPairAvailable(pair)) pairs.add(pair);
			
			takenCoordinates.add(pair.get(0));
			takenCoordinates.add(pair.get(1));
			
			if (pairs.size() >= 4) break;
			
		}
		
	}
	
	@SubscribeEvent
	public void drawBoxes(RenderWorldLastEvent e) {
		
		if (pairs.size() < 1) return;
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		if (Math.abs(p.posX - centerX) > 20) return;
		if (Math.abs(p.posZ - centerZ) > 20) return;
		
		for (int i = 0; i < pairs.size(); i++) {
			
			ArrayList<int[]> pair = pairs.get(i);
			
			int[] xz1 = translatedCoordinates(pair.get(0)[0], pair.get(0)[2]);
			int[] xz2 = translatedCoordinates(pair.get(1)[0], pair.get(1)[2]);
			
			int x1 = xz1[0];
			int y1 = pair.get(0)[1];
			int z1 = xz1[1];
			
			int x2 = xz2[0];
			int y2 = pair.get(1)[1];
			int z2 = xz2[1];
			
			int r = 255;
			int g = 0;
			int b = 0;
			
			switch (i) {
			case 1:
				r = 0;
				g = 255;
				b = 0;
				break;
			case 2:
				r = 0;
				g = 0;
				b = 255;
				break;
			case 3:
				r = 255;
				g = 255;
				b = 0;
				break;
			}
			
			Block b1 = p.world.getBlockState(new BlockPos(x1, y1, z1)).getBlock();
			Block b2 = p.world.getBlockState(new BlockPos(x2, y2, z2)).getBlock();
			
			if (b1 != Blocks.SEA_LANTERN && b2 != Blocks.SEA_LANTERN) continue;
			
			MagicRenderer renderer = new MagicRenderer();
			renderer.autoTranslate(e.getPartialTicks());
			
			renderer.lineWidth(3);
			renderer.disableDepth();
			renderer.color(r, g, b);
			
			renderer.begin(GL11.GL_LINES);
			
			drawBox(x1, y1, z1, renderer);
			drawBox(x2, y2, z2, renderer);
			
			drawLine(x1 + 0.5, y1 + 0.5, z1 + 0.5, x2 + 0.5, y2 + 0.5, z2 + 0.5, r, g, b, renderer);
			
			renderer.draw();
			renderer.enableDepth();
			renderer.end();
			
		}
		
	}
	
	private static boolean isPairAvailable(ArrayList<int[]> pair) {
		
		for (int[] coordinates : takenCoordinates) {
			for (int[] pairCoordinates : pair) {
				
				if (pairCoordinates[0] == coordinates[0] && pairCoordinates[1] == coordinates[1] && pairCoordinates[2] == coordinates[2]) return false;
				
			}
		}
		
		Debug.sendDungeonsDebug("-\npair: (" + pair.get(0)[0] + ", " + pair.get(0)[1] + ", " + pair.get(0)[2] + ") / (" + pair.get(1)[0] + ", " + pair.get(1)[1] + ", " + pair.get(1)[2] + ")");
		Debug.sendDungeonsDebug("translating...");
		
		int[] xz1 = translatedCoordinates(pair.get(0)[0], pair.get(0)[2]);
		int[] xz2 = translatedCoordinates(pair.get(1)[0], pair.get(1)[2]);
		
		int x1 = xz1[0];
		int y1 = pair.get(0)[1];
		int z1 = xz1[1];
		
		int x2 = xz2[0];
		int y2 = pair.get(1)[1];
		int z2 = xz2[1];
		
		Debug.sendDungeonsDebug("translated coordinates: (" + x1 + ", " + y1 + ", " + z1 + ") / (" + x2 + ", " + y2 + ", " + z2 + ")");
		
		Block b1 = Minecraft.getMinecraft().player.world.getBlockState(new BlockPos(x1, y1, z1)).getBlock();
		Block b2 = Minecraft.getMinecraft().player.world.getBlockState(new BlockPos(x2, y2, z2)).getBlock();
		
		Debug.sendDungeonsDebug("block 1: " + b1.getUnlocalizedName());
		Debug.sendDungeonsDebug("block 2: " + b2.getUnlocalizedName());
		
		if (b1 != Blocks.PRISMARINE && b1 != Blocks.SEA_LANTERN) return false;
		if (b2 != Blocks.PRISMARINE && b2 != Blocks.SEA_LANTERN) return false;
		
		return true;
		
	}
	
	private static int[] translatedCoordinates(int xTranslation, int zTranslation) {
		
		int tx = centerX;
		int tz = centerZ;
		
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
	
	private static void drawBox(int x, int y, int z, MagicRenderer renderer) {
		
		renderer.vc(x, y, z);
		renderer.vc(x + 1, y, z);
		
		renderer.vc(x + 1, y, z);
		renderer.vc(x + 1, y, z + 1);
		
		renderer.vc(x + 1, y, z + 1);
		renderer.vc(x, y, z + 1);
		
		renderer.vc(x, y, z + 1);
		renderer.vc(x, y, z);
		
		renderer.vc(x, y, z);
		renderer.vc(x, y + 1, z);
		
		renderer.vc(x + 1, y, z);
		renderer.vc(x + 1, y + 1, z);
		
		renderer.vc(x + 1, y, z + 1);
		renderer.vc(x + 1, y + 1, z + 1);
		
		renderer.vc(x, y, z + 1);
		renderer.vc(x, y + 1, z + 1);
		
		renderer.vc(x, y + 1, z);
		renderer.vc(x + 1, y + 1, z);
		
		renderer.vc(x + 1, y + 1, z);
		renderer.vc(x + 1, y + 1, z + 1);
		
		renderer.vc(x + 1, y + 1, z + 1);
		renderer.vc(x, y + 1, z + 1);
		
		renderer.vc(x, y + 1, z + 1);
		renderer.vc(x, y + 1, z);
		
	}
	
	private static void drawLine(double x1, double y1, double z1, double x2, double y2, double z2, int r, int g, int b, MagicRenderer renderer) {
		
		double smoothness = 100;
		int speed = 750;
		
		int cycleSize = 60;
		int lowAlpha = 25;
		double range = 255 - lowAlpha;
		
		double phase = System.currentTimeMillis() % speed;
		phase = phase / speed * cycleSize;
		
		double mx = (x2 - x1) / smoothness;
		double my = (y2 - y1) / smoothness;
		double mz = (z2 - z1) / smoothness;
		
		for (int i = 0; i < smoothness; i++) {
			
			double rx1 = x1 + mx * i;
			double rx2 = rx1 + mx;
			double ry1 = y1 + my * i;
			double ry2 = ry1 + my;
			double rz1 = z1 + mz * i;
			double rz2 = rz1 + mz;
			
			double alpha = i + phase;
			alpha %= cycleSize;
			alpha = 255 - Math.abs(alpha / cycleSize * range * 2 - range);
			
			renderer.color(r, g, b, (float)alpha);
			renderer.vc(rx1, ry1, rz1);
			renderer.vc(rx2, ry2, rz2);
			
		}
		
	}
	
	@SubscribeEvent
	public void loadWorld(WorldEvent.Load e) {
		reset();
	}
	
	public static void reset() {
		
		centerX = -1;
		centerZ = -1;
		direction = UNKNOWN;
		
		pairs = new ArrayList<>();
		takenCoordinates = new ArrayList<>();
		
	}
	
}
