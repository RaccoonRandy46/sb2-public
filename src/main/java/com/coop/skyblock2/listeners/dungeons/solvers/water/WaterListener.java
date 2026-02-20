package com.coop.skyblock2.listeners.dungeons.solvers.water;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import com.coop.skyblock2.utils.Debug;
import com.coop.skyblock2.utils.MagicRenderer;
import com.coop.skyblock2.utils.Positioning;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class WaterListener {

	private static final int UNKNOWN = 0;
	private static final int POS_Z = 1;
	private static final int NEG_Z = 2;
	private static final int POS_X = 3;
	private static final int NEG_X = 4;
	
	public static final int PURPLE = 4;
	public static final int ORANGE = 3;
	public static final int BLUE = 2;
	public static final int GREEN = 1;
	public static final int RED = 0;
	
	private static int originX = -1;
	private static int originZ = -1;
	private static int direction = UNKNOWN;
	
	private static int[] quartzXZ = null;
	private static int[] goldXZ = null;
	private static int[] coalXZ = null;
	private static int[] diamondXZ = null;
	private static int[] emeraldXZ = null;
	private static int[] clayXZ = null;
	
	private static String roomcode = null;
	private static ArrayList<BlockPos> waterPositions = new ArrayList<>();
	
	private static boolean puzzleComplete = false;
	
	@SubscribeEvent
	public void calibrateRoom(PlayerTickEvent e) {
		
		if (!(e.player instanceof EntityPlayerSP)) return;
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		if (!Utils.isInDungeons()) return;
		if (direction != UNKNOWN) return;
		
		if (p.posY < 59 || p.posY > 70.3) return;
		
		int x = (int)p.posX;
		int z = (int)p.posZ;
		
		if (x < 0) x--;
		if (z < 0) z--;
		
		for (int i = 0; i < 7; i++) {
			
			float dist = i * 2;
			double[] xyz = Positioning.HLO_XYZ_CXYZPY(0, 0, dist, (float)p.posX, 58, (float)p.posZ, p.rotationYaw, 0);
			
			if (p.world.getBlockState(new BlockPos((int)xyz[0], 58, (int)xyz[2])).getBlock() != Blocks.STAINED_GLASS) {
				if (i == 6) return;
				continue;
			}
			
			x = (int)xyz[0];
			z = (int)xyz[2];
			
			break;
			
		}

		boolean foundOrigin = false;
		
		for (int ax = -18; ax <= 18; ax++) {
			for (int az = -18; az <= 18; az++) {
				
				int bx = x + ax;
				int bz = z + az;
				
				if (p.world.getBlockState(new BlockPos(bx, 60, bz)).getBlock() != Blocks.LEVER) continue;
				
				foundOrigin = true;
				
				originX = bx;
				originZ = bz;
				
				break;
				
			}
			if (foundOrigin) break;
		}
		
		if (!foundOrigin) return;
		
		if (p.world.getBlockState(new BlockPos(originX - 1, 60, originZ)).getBlock() == Blocks.WATER) direction = POS_X;
		else if (p.world.getBlockState(new BlockPos(originX + 1, 60, originZ)).getBlock() == Blocks.WATER) direction = NEG_X;
		else if (p.world.getBlockState(new BlockPos(originX, 60, originZ - 1)).getBlock() == Blocks.WATER) direction = POS_Z;
		else if (p.world.getBlockState(new BlockPos(originX, 60, originZ + 1)).getBlock() == Blocks.WATER) direction = NEG_Z;
		
		Debug.sendDungeonsDebug("x: " + originX + "\nz: " + originZ + "\ndirection: " + direction);
		
	}
	
	@SubscribeEvent
	public void collectData(PlayerTickEvent e) {
		
		if (!(e.player instanceof EntityPlayerSP)) return;
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		if (direction == UNKNOWN || roomcode != null) return;
		
		int[] oxz = translatedCoordinates(originX, originZ, -9, 21);
		
		int ox = oxz[0];
		int oz = oxz[1];
		
		String code = "";
		for (int y = 61; y <= 78; y++) {
			for (int tx = 0; tx <= 18; tx++) {
				
				int[] xz = translatedCoordinates(ox, oz, tx, 0);
				int x = xz[0];
				int z = xz[1];
				
				BlockPos pos = new BlockPos(x, y, z);
				Block block = p.world.getBlockState(pos).getBlock();
				boolean isAir = (block == Blocks.WATER || block == Blocks.FLOWING_WATER || block == Blocks.AIR || block == Blocks.QUARTZ_BLOCK || block == Blocks.GOLD_BLOCK || block == Blocks.COAL_BLOCK || block == Blocks.DIAMOND_BLOCK || block == Blocks.EMERALD_BLOCK || block == Blocks.HARDENED_CLAY);
				
				code += (isAir ? "0" : "1");
				
				if (isAir) waterPositions.add(pos);
				
			}
		}
		
		roomcode = code;
		
		quartzXZ = translatedCoordinates(originX, originZ, -5, 15);
		goldXZ = translatedCoordinates(originX, originZ, -5, 10);
		coalXZ = translatedCoordinates(originX, originZ, -5, 5);
		diamondXZ = translatedCoordinates(originX, originZ, 5, 15);
		emeraldXZ = translatedCoordinates(originX, originZ, 5, 10);
		clayXZ = translatedCoordinates(originX, originZ, 5, 5);
		
		Debug.sendDungeonsDebug("room code: " + roomcode);
		
		if (!WaterSolutions.solutions.containsKey(roomcode)) puzzleComplete = true;
		
	}
	
	@SubscribeEvent
	public void drawSolution(RenderWorldLastEvent e) {
		
		if (roomcode == null || puzzleComplete) return;
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		if (Math.abs(p.posX - originX) > 20) return;
		if (Math.abs(p.posY - 59) > 10) return;
		if (Math.abs(p.posZ - originZ) > 20) return;
		
		int color = -1;
		for (int i = 0; i < 5; i++) {
			int[] xz = translatedCoordinates(originX, originZ, 0, 10 + i);
			if (p.world.getBlockState(new BlockPos(xz[0], 56, xz[1])).getBlock() != Blocks.WOOL) continue;
			color = i;
			break;
		}
		
		if (color == -1) return;
		
		HashMap<Integer, String> solution = WaterSolutions.solutions.get(roomcode);
		boolean isWaterEnabled = isWaterFlowing();
		
		highlightLevers(solution, color, isWaterEnabled, e.getPartialTicks());
		
	}
	
	private static void highlightLevers(HashMap<Integer, String> solution, int color, boolean isWaterEnabled, float ticks) {
		
		highlightLever(solution, color, isWaterEnabled, ticks, quartzXZ, WaterSolutions.QUARTZ);
		highlightLever(solution, color, isWaterEnabled, ticks, goldXZ, WaterSolutions.GOLD);
		highlightLever(solution, color, isWaterEnabled, ticks, coalXZ, WaterSolutions.COAL);
		highlightLever(solution, color, isWaterEnabled, ticks, diamondXZ, WaterSolutions.DIAMOND);
		highlightLever(solution, color, isWaterEnabled, ticks, emeraldXZ, WaterSolutions.EMERALD);
		highlightLever(solution, color, isWaterEnabled, ticks, clayXZ, WaterSolutions.CLAY);
		
	}
	
	private static void highlightLever(HashMap<Integer, String> solution, int color, boolean isWaterEnabled, float ticks, int[] leverXZ, int lever) {
		
		boolean leverPowered = Minecraft.getMinecraft().player.world.getBlockState(new BlockPos(leverXZ[0], 61, leverXZ[1])).getValue(BlockLever.POWERED);
		char leverRequirement = WaterSolutions.getLeverRequirement(solution, color, lever);
		if (shouldHighlightLever(leverRequirement, leverPowered, isWaterEnabled)) drawBox(leverXZ[0], leverXZ[1], ticks);
		
	}
	
	private static boolean shouldHighlightLever(char requirement, boolean flipped, boolean waterOn) {
		
		if (requirement == WaterSolutions.ON_AFTER_WATER) return ((waterOn && !flipped) || (!waterOn && flipped));
		if (requirement == WaterSolutions.ON_BEFORE_WATER) return ((waterOn && flipped) || (!waterOn && !flipped));
		
		return ((requirement == WaterSolutions.ALWAYS_ON && !flipped) || (requirement == WaterSolutions.ALWAYS_OFF && flipped));
		
	}
	
	private static void drawBox(int x, int z, float ticks) {
		
		int y = 61;
		
		MagicRenderer renderer = new MagicRenderer();
		renderer.autoTranslate(ticks);
		
		renderer.lineWidth(3);
		renderer.disableDepth();
		renderer.color(0, 255, 0);
		
		renderer.begin(GL11.GL_LINES);
		
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
		
		renderer.draw();
		renderer.enableDepth();
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
	
	private static boolean isWaterFlowing() {
		
		for (BlockPos pos : waterPositions) {
			Block block = Minecraft.getMinecraft().player.world.getBlockState(pos).getBlock();
			if (block == Blocks.WATER || block == Blocks.FLOWING_WATER) return true;
		}
		
		return false;
		
	}
	
	@SubscribeEvent
	public void loadWorld(WorldEvent.Load e) {
		reset();
	}
	
	public static void reset() {
		
		originX = -1;
		originZ = -1;
		direction = UNKNOWN;
		
		quartzXZ = null;
		goldXZ = null;
		coalXZ = null;
		diamondXZ = null;
		emeraldXZ = null;
		clayXZ = null;
		
		roomcode = null;
		waterPositions = new ArrayList<>();
		
		puzzleComplete = false;
		
	}
	
}
