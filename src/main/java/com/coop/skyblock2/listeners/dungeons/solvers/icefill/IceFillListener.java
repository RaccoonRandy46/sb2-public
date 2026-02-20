package com.coop.skyblock2.listeners.dungeons.solvers.icefill;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.coop.skyblock2.utils.Debug;
import com.coop.skyblock2.utils.MagicRenderer;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class IceFillListener {

	private static final int UNKNOWN = 0;
	private static final int POS_Z = 1;
	private static final int NEG_Z = 2;
	private static final int POS_X = 3;
	private static final int NEG_X = 4;
	
	private static int originX = -1;
	private static int originZ = -1;
	private static int direction = UNKNOWN;
	
	private static String smallCode = null;
	private static String mediumCode = null;
	private static String largeCode = null;
	
	@SubscribeEvent
	public void calibrateRoom(PlayerTickEvent e) {
		
		if (!(e.player instanceof EntityPlayerSP)) return;
		
		if (!Utils.isInDungeons()) return;
		if (direction != UNKNOWN) return;
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
	
		int x = (int)p.posX;
		int y = (int)p.posY;
		int z = (int)p.posZ;
		
		if (x < 0) x--;
		if (z < 0) z--;
		
		if (p.world.getBlockState(new BlockPos(x, y - 1, z)).getBlock() != Blocks.PACKED_ICE) return;
		
		if (p.world.getBlockState(new BlockPos(x - 2, y - 1, z)).getBlock() == Blocks.STONE_BRICK_STAIRS) direction = POS_X;
		else if (p.world.getBlockState(new BlockPos(x + 2, y - 1, z)).getBlock() == Blocks.STONE_BRICK_STAIRS) direction = NEG_X;
		else if (p.world.getBlockState(new BlockPos(x, y - 1, z - 2)).getBlock() == Blocks.STONE_BRICK_STAIRS) direction = POS_Z;
		else if (p.world.getBlockState(new BlockPos(x, y - 1, z + 2)).getBlock() == Blocks.STONE_BRICK_STAIRS) direction = NEG_Z;
		
		if (direction == UNKNOWN) return;
		
		originX = x;
		originZ = z;
		
		Debug.sendDungeonsDebug("x: " + originX + "\nz: " + originZ + "\ndirection: " + direction);
		
	}
	
	@SubscribeEvent
	public void getCodes(PlayerTickEvent e) {
		
		if (!(e.player instanceof EntityPlayerSP)) return;
		
		if (direction == UNKNOWN || smallCode != null) return;
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		int ox = originX;
		int oz = originZ;
		
		int[] newOrigin = translatedCoordinates(ox, oz, -1, 0);
		ox = newOrigin[0];
		oz = newOrigin[1];
		
		String small = "";
		for (int z = 0; z < 3; z++) {
			for (int x = 0; x < 3; x++) {
				int[] xz = translatedCoordinates(ox, oz, x, z);
				small += (p.world.getBlockState(new BlockPos(xz[0], 70, xz[1])).getBlock() == Blocks.AIR ? "0" : "1");
			}
		}
		
		newOrigin = translatedCoordinates(ox, oz, -1, 5);
		ox = newOrigin[0];
		oz = newOrigin[1];
		
		String medium = "";
		for (int z = 0; z < 5; z++) {
			for (int x = 0; x < 5; x++) {
				int[] xz = translatedCoordinates(ox, oz, x, z);
				medium += (p.world.getBlockState(new BlockPos(xz[0], 71, xz[1])).getBlock() == Blocks.AIR ? "0" : "1");
			}
		}
		
		newOrigin = translatedCoordinates(ox, oz, -1, 7);
		ox = newOrigin[0];
		oz = newOrigin[1];
		
		String large = "";
		for (int z = 0; z < 7; z++) {
			for (int x = 0; x < 7; x++) {
				int[] xz = translatedCoordinates(ox, oz, x, z);
				large += (p.world.getBlockState(new BlockPos(xz[0], 72, xz[1])).getBlock() == Blocks.AIR ? "0" : "1");
			}
		}
		
		smallCode = small;
		mediumCode = medium;
		largeCode = large;
		
		Debug.sendDungeonsDebug("small: " + smallCode + "\nmedium: " + mediumCode + "\nlarge: " + largeCode);
		
	}
	
	@SubscribeEvent
	public void displaySolution(RenderWorldLastEvent e) {
		
		if (smallCode == null) return;
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		if (Math.abs(p.posX - originX) > 30) return;
		if (Math.abs(p.posZ - originZ) > 30) return;
		
		ArrayList<int[]> smallPath = null;
		ArrayList<int[]> mediumPath = null;
		ArrayList<int[]> largePath = null;
		
		if (IceFillSolutions.solutions.containsKey(smallCode)) smallPath = IceFillSolutions.solutions.get(smallCode);
		if (IceFillSolutions.solutions.containsKey(mediumCode)) mediumPath = IceFillSolutions.solutions.get(mediumCode);
		if (IceFillSolutions.solutions.containsKey(largeCode)) largePath = IceFillSolutions.solutions.get(largeCode);
		
		int ox = originX;
		int oz = originZ;
		
		if (smallPath != null) drawPath(smallPath, ox, 70, oz, e.getPartialTicks());
		
		int[] newOrigin = translatedCoordinates(ox, oz, 0, 5);
		ox = newOrigin[0];
		oz = newOrigin[1];
		
		if (mediumPath != null) drawPath(mediumPath, ox, 71, oz, e.getPartialTicks());
		
		newOrigin = translatedCoordinates(ox, oz, 0, 7);
		ox = newOrigin[0];
		oz = newOrigin[1];
		
		if (largePath != null) drawPath(largePath, ox, 72, oz, e.getPartialTicks());
		
	}
	
	private static void drawPath(ArrayList<int[]> path, int x, int y, int z, float ticks) {
		
		MagicRenderer renderer = new MagicRenderer();
		renderer.autoTranslate(ticks);
		
		renderer.lineWidth(3);
		renderer.color(0, 255, 0);
		
		renderer.begin(GL11.GL_LINES);
		
		for (int i = 0; i < path.size() - 1; i++) {
			
			int[] pointA = path.get(i);
			int[] pointB = path.get(i + 1);
			
			int[] xz1 = translatedCoordinates(x, z, pointA[0], pointA[1]);
			int[] xz2 = translatedCoordinates(x, z, pointB[0], pointB[1]);
			
			int x1 = xz1[0];
			int z1 = xz1[1];
			
			int x2 = xz2[0];
			int z2 = xz2[1];
			
			renderer.vc(x1 + 0.5f, y + 0.5f, z1 + 0.5f);
			renderer.vc(x2 + 0.5f, y + 0.5f, z2 + 0.5f);
			
		}
		
		renderer.draw();
		renderer.end();
		
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
		
		originX = -1;
		originZ = -1;
		direction = UNKNOWN;
		smallCode = null;
		mediumCode = null;
		largeCode = null;
		
	}
	
}
