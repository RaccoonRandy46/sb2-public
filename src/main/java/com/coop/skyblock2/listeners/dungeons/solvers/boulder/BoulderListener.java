package com.coop.skyblock2.listeners.dungeons.solvers.boulder;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.coop.skyblock2.utils.Debug;
import com.coop.skyblock2.utils.MagicRenderer;
import com.coop.skyblock2.utils.Positioning;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class BoulderListener {

	private static final int UNKNOWN = 0;
	private static final int POS_Z = 1;
	private static final int NEG_Z = 2;
	private static final int POS_X = 3;
	private static final int NEG_X = 4;
	
	private static int originX = -1;
	private static int originZ = -1;
	
	private static int x = Integer.MIN_VALUE;
	private static int z = Integer.MIN_VALUE;
	
	private static int direction = UNKNOWN;
	
	private static String roomcode = null;
	
	private static int currentStep = -1;
	private static ArrayList<int[]> solution = new ArrayList<>();
	
	@SubscribeEvent
	public void calibrateRoom(PlayerTickEvent e) {
		
		if (!(e.player instanceof EntityPlayerSP)) return;
		
		if (!Utils.isInDungeons()) return;
		
		if (direction != UNKNOWN) return;
		
		EntityPlayerSP p = (EntityPlayerSP)e.player;
		
		if (p.posY < 64 || p.posY > 71) return;
		
		x = (int)p.posX;
		int y = 68;
		z = (int)p.posZ;
		
		if (x < 0) x--;
		if (z < 0) z--;
		
		IBlockState currentState = p.world.getBlockState(new BlockPos(x, y, z));
		Block current = currentState.getBlock();
		Block blockBelow = p.world.getBlockState(new BlockPos(x, y - 1, z)).getBlock();
		
		boolean isPlatform = current == Blocks.STAINED_GLASS && current.damageDropped(currentState) == 0;
		isPlatform = isPlatform || current == Blocks.BARRIER;
		if (blockBelow != Blocks.AIR) isPlatform = false;
		
		for (int i = 1; i < 11 && !isPlatform; i++) {
			
			double[] xyz = Positioning.HLO_XYZ_CXYZPY(0, 0, i, (float)p.posX, (float)p.posY, (float)p.posZ, p.rotationYaw, 0);
			
			x = (int)xyz[0];
			z = (int)xyz[2];
			
			if (x < 0) x--;
			if (z < 0) z--;
			
			currentState = p.world.getBlockState(new BlockPos(x, y, z));
			current = currentState.getBlock();
			p.world.getBlockState(new BlockPos(x, y - 1, z)).getBlock();
			
			isPlatform = current == Blocks.STAINED_GLASS && current.damageDropped(currentState) == 0;
			isPlatform = isPlatform || current == Blocks.BARRIER;
			if (blockBelow != Blocks.AIR) isPlatform = false;
			
		}
		
		if (!isPlatform) return;
		
		y = 65;
		
		boolean foundTorch = false;
		
		Debug.sendDungeonsDebug("bx: " + x + "\nbz: " + z);
		
		for (int bx = x - 10; bx <= x + 10; bx++) {
			for (int bz = z - 10; bz <= z + 10; bz++) {
				
				Block b = p.world.getBlockState(new BlockPos(bx, y, bz)).getBlock();
				if (b != Blocks.TORCH) continue;
				foundTorch = true;
				x = bx;
				z = bz;
				break;
				
			}
			if (foundTorch) break;
		}
		
		originX = x;
		originZ = z;
		
		Debug.sendDungeonsDebug("tx: " + originX + "\ntz: " + originZ);
		
		if (p.world.getBlockState(new BlockPos(x, 64, z)).getBlock() != Blocks.COBBLESTONE_WALL) return;
		
		if (p.world.getBlockState(new BlockPos(x - 1, 65, z)).getBlock() == Blocks.COBBLESTONE) {
			direction = POS_X;
		} else if (p.world.getBlockState(new BlockPos(x + 1, 65, z)).getBlock() == Blocks.COBBLESTONE) {
			direction = NEG_X;
		} else if (p.world.getBlockState(new BlockPos(x, 65, z - 1)).getBlock() == Blocks.COBBLESTONE) {
			direction = POS_Z;
		} else if (p.world.getBlockState(new BlockPos(x, 65, z + 1)).getBlock() == Blocks.COBBLESTONE) {
			direction = NEG_Z;
		}
		
		Debug.sendDungeonsDebug("direction: " + direction);
		
		if (direction == UNKNOWN) return;
		
		int[] txzCheck = translatedCoordinates(2, 3);
		if (p.world.getBlockState(new BlockPos(txzCheck[0], 67, txzCheck[1])).getBlock() != Blocks.COBBLESTONE_WALL) return;
		txzCheck = translatedCoordinates(-2, 3);
		if (p.world.getBlockState(new BlockPos(txzCheck[0], 67, txzCheck[1])).getBlock() != Blocks.COBBLESTONE_WALL) return;
		txzCheck = translatedCoordinates(6, 2);
		if (p.world.getBlockState(new BlockPos(txzCheck[0], 64, txzCheck[1])).getBlock() != Blocks.STONE_BRICK_STAIRS) return;
		txzCheck = translatedCoordinates(-6, 2);
		if (p.world.getBlockState(new BlockPos(txzCheck[0], 64, txzCheck[1])).getBlock() != Blocks.STONE_BRICK_STAIRS) return;
		
		int[] finalXZ = translatedCoordinates(-10, 4);
		originX = finalXZ[0];
		originZ = finalXZ[1];
		
		Debug.sendDungeonsDebug("x: " + originX + "\nz: " + originZ);
		
	}
	
	@SubscribeEvent
	public void getRoomCode(PlayerTickEvent e) {
		
		if (!(e.player instanceof EntityPlayerSP)) return;
		
		if (!Utils.isInDungeons()) return;
		
		if (roomcode != null || direction == UNKNOWN || originX == Integer.MIN_VALUE || originZ == Integer.MIN_VALUE) return;
		
		EntityPlayerSP p = (EntityPlayerSP)e.player;
		
		roomcode = "";
		for (int blockY = 0; blockY < 6; blockY++) {
			for (int blockX = 0; blockX < 7; blockX++) {
				
				int[] xz = translatedCoordinates(blockX * 3, blockY * 3);
				
				Block block = p.world.getBlockState(new BlockPos(xz[0], 65, xz[1])).getBlock();
				
				roomcode += (block == Blocks.PLANKS ? "1" : "0");
				
			}
		}
		
		Debug.sendDungeonsDebug("room code: " + roomcode);
		
		if (!BoulderSolutions.solutions.containsKey(roomcode)) return;
		
		solution = BoulderSolutions.solutions.get(roomcode);
		currentStep = 0;
		
	}
	
	@SubscribeEvent
	public void displaySolution(RenderWorldLastEvent e) {
		
		if (currentStep == -1 || currentStep >= solution.size()) return;
		
		int[] xz = translatedCoordinates(solution.get(currentStep)[0], solution.get(currentStep)[1]);
		int solutionX = xz[0];
		int solutionZ = xz[1];
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		Block block = p.world.getBlockState(new BlockPos(solutionX, 65, solutionZ)).getBlock();
		
		if (block != Blocks.STONE_BUTTON && block != Blocks.AIR) return;
		
		if (block == Blocks.AIR) {
			currentStep++;
			Debug.sendDungeonsDebug("step: " + currentStep);
			return;
		}
		
		if (Math.abs(solutionX - p.posX) > 30) return;
		if (Math.abs(solutionZ - p.posZ) > 30) return;
		
		MagicRenderer renderer = new MagicRenderer();
		renderer.autoTranslate(e.getPartialTicks());
		
		renderer.lineWidth(3);
		renderer.disableDepth();
		renderer.color(0, 255, 0);
		
		renderer.begin(GL11.GL_LINES);
		
		renderer.vc(solutionX, 65, solutionZ);
		renderer.vc(solutionX + 1, 65, solutionZ);
		
		renderer.vc(solutionX + 1, 65, solutionZ);
		renderer.vc(solutionX + 1, 65, solutionZ + 1);
		
		renderer.vc(solutionX + 1, 65, solutionZ + 1);
		renderer.vc(solutionX, 65, solutionZ + 1);
		
		renderer.vc(solutionX, 65, solutionZ + 1);
		renderer.vc(solutionX, 65, solutionZ);
		
		renderer.vc(solutionX, 65, solutionZ);
		renderer.vc(solutionX, 66, solutionZ);
		
		renderer.vc(solutionX + 1, 65, solutionZ);
		renderer.vc(solutionX + 1, 66, solutionZ);
		
		renderer.vc(solutionX + 1, 65, solutionZ + 1);
		renderer.vc(solutionX + 1, 66, solutionZ + 1);
		
		renderer.vc(solutionX, 65, solutionZ + 1);
		renderer.vc(solutionX, 66, solutionZ + 1);
		
		renderer.vc(solutionX, 66, solutionZ);
		renderer.vc(solutionX + 1, 66, solutionZ);
		
		renderer.vc(solutionX + 1, 66, solutionZ);
		renderer.vc(solutionX + 1, 66, solutionZ + 1);
		
		renderer.vc(solutionX + 1, 66, solutionZ + 1);
		renderer.vc(solutionX, 66, solutionZ + 1);
		
		renderer.vc(solutionX, 66, solutionZ + 1);
		renderer.vc(solutionX, 66, solutionZ);
		
		renderer.draw();
		renderer.enableDepth();
		renderer.end();
		
	}
	
	private static int[] translatedCoordinates(int xTranslation, int zTranslation) {
		
		int tx = originX;
		int tz = originZ;
		
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
		direction = UNKNOWN;
		originX = Integer.MIN_VALUE;
		originZ = Integer.MIN_VALUE;
		x = -1;
		z = -1;
		roomcode = null;
		currentStep = -1;
		solution = new ArrayList<>();
	}
	
}
