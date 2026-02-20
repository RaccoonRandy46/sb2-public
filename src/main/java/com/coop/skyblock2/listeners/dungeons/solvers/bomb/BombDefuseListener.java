package com.coop.skyblock2.listeners.dungeons.solvers.bomb;

import com.coop.skyblock2.listeners.dungeons.secrets.SecretUtils;
import com.coop.skyblock2.listeners.dungeons.solvers.bomb.chambers.BDChamber;
import com.coop.skyblock2.listeners.dungeons.solvers.bomb.chambers.BDChamberArrows;
import com.coop.skyblock2.listeners.dungeons.solvers.bomb.chambers.BDChamberBrokenMaze;
import com.coop.skyblock2.listeners.dungeons.solvers.bomb.chambers.BDChamberColorSwap;
import com.coop.skyblock2.listeners.dungeons.solvers.bomb.chambers.BDChamberCreepersweeper;
import com.coop.skyblock2.listeners.dungeons.solvers.bomb.chambers.BDChamberInvisibleMaze;
import com.coop.skyblock2.listeners.dungeons.solvers.bomb.chambers.BDChamberNumbers;
import com.coop.skyblock2.utils.Debug;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class BombDefuseListener {

	public static final int GROUND = 69;
	
	private static final int UNKNOWN = 0;
	private static final int POS_Z = 1;
	private static final int NEG_Z = 2;
	private static final int POS_X = 3;
	private static final int NEG_X = 4;
	
	private static int originX = -1;
	private static int originZ = -1;
	private static int centerX = -1;
	private static int centerZ = -1;
	private static int direction = UNKNOWN;
	private static boolean readyToRender = false;
	
	private static BDChamber[] chambers = new BDChamber[4];
	private static BDStatus status = BDStatus.SOLOABLE;
	
	public enum BDStatus {
		SOLOABLE, NEEDS_2P, NEEDS_CHEESE
	}
	
	@SubscribeEvent
	public void calibrateRoom(PlayerTickEvent e) {
		
		if (!(e.player instanceof EntityPlayerSP)) return;
		if (!Utils.isInDungeons()) return;
		if (readyToRender) return;
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
			
		Entity creeper = null;
		double closestDist = -1;
		
		for (Entity entity : p.world.getLoadedEntityList()) {
			
			if (!(entity instanceof EntityCreeper)) continue;
			if (Math.abs(entity.posY - 70) > 0.1) continue;
			if (entity.isInvisible()) continue;
			
			double dist = entity.getDistance(p);
			
			if (closestDist == -1 || dist < closestDist) {
				closestDist = dist;
				creeper = entity;
			}
			
		}
		
		if (creeper == null) return;
		Debug.sendDungeonsDebug("found bomb defuse!");
		
		int x = (int)creeper.posX;
		int z = (int)creeper.posZ;
		if (x < 0) x--;
		if (z < 0) z--;
		
		centerX = x;
		centerZ = z;
		
		Debug.sendDungeonsDebug("creeper xz: (" + x + ", " + z + ")");
		
		boolean xFacing = p.world.getBlockState(new BlockPos(x + 1, GROUND, z)).getBlock() == Blocks.OBSIDIAN;
		
		if (xFacing) direction = (p.world.getBlockState(new BlockPos(x - 12, GROUND, z + 3)).getBlock() == Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE ? POS_X : NEG_X);
		else direction = (p.world.getBlockState(new BlockPos(x + 3, GROUND, z - 12)).getBlock() == Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE ? POS_Z : NEG_Z);
		
		Debug.sendDungeonsDebug("direction: " + SecretUtils.directionToString(direction));
		
		int[] originXZ = translatedCoordinates(x, z, -13, -15);
		originX = originXZ[0];
		originZ = originXZ[1];
		
		Debug.sendDungeonsDebug("origin xz: (" + originX + ", " + originZ + ")");
		
		scanChambers();
	
		readyToRender = true;
		
	}
	
	@SubscribeEvent
	public void renderWorld(RenderWorldLastEvent e) {
		
		if (!readyToRender) return;
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		if (Math.abs(p.posX - centerX) > 16) return;
		if (Math.abs(p.posZ - centerZ) > 16) return;
		
		int chamberIndex = 0;
		
		for (int tz = 6; tz <= 27; tz += 7) {
			
			int[] doorXZ = translatedCoordinates(originX, originZ, 22, tz);
			Block door = p.world.getBlockState(new BlockPos(doorXZ[0], GROUND, doorXZ[1])).getBlock();
			
			if (door != Blocks.AIR && door != Blocks.BARRIER) break;
			chamberIndex++;
			
		}
		
		if (chamberIndex > 3) return;
		
		BDChamber chamber = chambers[chamberIndex];
		
		chamber.executeLogic(e);
		chamber.executeRender(e);
		
		displayStatus(e);
		
	}
	
	private static void displayStatus(RenderWorldLastEvent e) {
		
		BDChamber chamber = chambers[0];
		String text = "";
		
		switch (status) {
		case SOLOABLE:
			text = Utils.chat("&bSOLOABLE!");
			break;
		case NEEDS_2P:
			text = Utils.chat("&e2 PLAYERS");
			break;
		case NEEDS_CHEESE:
			text = Utils.chat("&cCHEESE");
			break;
		}
		
		double y = GROUND + 3.5;
		if (status != BDStatus.SOLOABLE) y = GROUND + 2;
		
		double[] xz = chamber.translatedCoordinatesD(chamber.xld, chamber.zld, 13.5, 6.5);
		chamber.render3Dtext(text, xz[0], y, xz[1], .1, e.getPartialTicks());
		
		if (status == BDStatus.SOLOABLE) return;
		String topText = status == BDStatus.NEEDS_2P ? Utils.chat("&eREQUIRES") : Utils.chat("&cREQUIRES");
		
		chamber.render3Dtext(topText, xz[0], GROUND + 3.5, xz[1], .1, e.getPartialTicks());
		
	}
	
	private static void scanChambers() {
		
		int rightX = 18;
		
		chambers[0] = new BDChamberNumbers(new int[] {originX, originZ}, translatedCoordinates(originX, originZ, rightX, 0), direction);
		
		for (int i = 1; i <= 3; i++) {
			
			int[] lxz = translatedCoordinates(originX, originZ, 0, 7 * i);
			int[] rxz = translatedCoordinates(originX, originZ, rightX, 7 * i);
			
			chambers[i] = getChamber(lxz, rxz);
			
		}
		
	}
	
	private static BDChamber getChamber(int[] lxz, int[] rxz) {
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		int xr = rxz[0];
		int zr = rxz[1];
		
		for (int tx = 0; tx < 9; tx++) {
			for (int tz = 0; tz < 6; tz++) {
				
				int[] xz = translatedCoordinates(xr, zr, tx, tz);
				Block b = p.world.getBlockState(new BlockPos(xz[0], GROUND, xz[1])).getBlock();
				
				if (b == Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE) return new BDChamberInvisibleMaze(lxz, rxz, direction);
				if (b == Blocks.STONE_PRESSURE_PLATE) {
					status = BDStatus.NEEDS_2P;
					xz = translatedCoordinates(lxz[0], lxz[1], 4, 0);
					b = p.world.getBlockState(new BlockPos(xz[0], GROUND, xz[1])).getBlock();
					if (b == Blocks.BARRIER) status = BDStatus.NEEDS_CHEESE;
					return new BDChamberBrokenMaze(lxz, rxz, direction);
				}
				if (b == Blocks.WOODEN_BUTTON) return new BDChamberColorSwap(lxz, rxz, direction);
				if (b == Blocks.PLANKS) return new BDChamberArrows(lxz, rxz, direction);
				
			}
		}
		
		return new BDChamberCreepersweeper(lxz, rxz, direction);
		
	}
	
	public static int[] translatedCoordinates(int tx, int tz, int xTranslation, int zTranslation) {
		
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
	public void worldLoad(WorldEvent.Load e) {
		reset();
	}
	
	public static void reset() {
		originX = -1;
		originZ = -1;
		centerX = -1;
		centerZ = -1;
		direction = UNKNOWN;
		chambers = new BDChamber[4];
		readyToRender = false;
		status = BDStatus.SOLOABLE;
	}
	
}
