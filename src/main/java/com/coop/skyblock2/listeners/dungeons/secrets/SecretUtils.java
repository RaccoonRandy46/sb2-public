package com.coop.skyblock2.listeners.dungeons.secrets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class SecretUtils {
	
	public static final int POS_Z = 1;
	public static final int NEG_Z = 2;
	public static final int POS_X = 3;
	public static final int NEG_X = 4;
	
	public static int[] getRoomCell() {
		
		return getRoomCell(Minecraft.getMinecraft().player);
		
	}
	
	public static int[] getRoomCell(Entity entity) {
		
		int x = (int)entity.posX + 8;
		int z = (int)entity.posZ + 8;
		x *= -1;
		z *= -1;
		
		int cellX = x / 32;
		int cellZ = z / 32;
		
		return new int[] {cellX, cellZ};
		
	}
	
	public static int[] getRoomOrigin(int cellX, int cellZ) {
		
		int x = -9;
		int z = -9;
		
		x -= (cellX * 32);
		z -= (cellZ * 32);
		
		x--;
		z--;
		
		return new int[] {x, z};
		
	}
	
	public static int[] getRoomOrigin(int cellX, int cellZ, int direction) {
		
		int[] xz = getRoomOrigin(cellX, cellZ);
		int x = xz[0] - 8;
		int z = xz[1] - 8;
		
		if (direction == NEG_Z || direction == POS_X) x -= 14;
		
		if (direction == POS_Z || direction == POS_X) z -= 14;
		
		return new int[] {x, z};
		
	}
	
	public static String getRoomCode(int cellX, int cellZ) {
		return (String)getRoomInformation(cellX, cellZ)[0];
	}
	
	public static int getRoomDirection(int cellX, int cellZ) {
		return (int)getRoomInformation(cellX, cellZ)[1];
	}
	
	private static Object[] getRoomInformation(int cellX, int cellZ) {
		
		String bestRoomcode = "";
		int bestScore = -1;
		int finalDirection = 1;
		
		for (int i = 1; i <= 4; i++) {
			
			String currentCode = getUnfinalizedRoomCode(cellX, cellZ, i);
			int currentScore = getRoomcodeScore(currentCode);
			
			if (currentScore == -1) return new Object[] {finalizeRoomcode(currentCode), i};
			
			if (currentScore <= bestScore && bestScore != -1) continue;
			
			bestScore = currentScore;
			bestRoomcode = currentCode;
			finalDirection = i;
			
		}
		
		return new Object[] {finalizeRoomcode(bestRoomcode), finalDirection};
		
	}
	
	public static String getUnfinalizedRoomCode(int cellX, int cellZ, int direction) {
		
		int[] xz = getRoomOrigin(cellX, cellZ, direction);
		int x = xz[0];
		int z = xz[1];
		
		ArrayList<Integer> idArray = new ArrayList<>();
		
		for (int tz = 0; tz <= 15; tz++) {
			for (int tx = 0; tx < 15; tx += 14) {
				
				int[] xz0 = translatedCoordinates(x, z, tx, tz, direction);
				
				int newID = 0;
				
				for (int y = 50; y <= 80; y++) {
				
					IBlockState state = Minecraft.getMinecraft().player.world.getBlockState(new BlockPos(xz0[0], y, xz0[1]));
					Block b = state.getBlock();
					int damage = b.damageDropped(state);
					
					if (b == Blocks.AIR) continue;
					if (b == Blocks.STONEBRICK && damage == 2) continue;
					if (b == Blocks.LEVER) continue;
					if (b == Blocks.STONE && damage == 2) continue;
					if (b == Blocks.LOG || b == Blocks.LOG2) continue;
					if (b == Blocks.PLANKS) continue;
					if (b == Blocks.CHEST || b == Blocks.TRAPPED_CHEST) continue;
					if (b == Blocks.TRIPWIRE) continue;
					if (b == Blocks.SKULL) continue;
					if (b == Blocks.STONE_SLAB) continue;
					if (b == Blocks.STONE_BRICK_STAIRS) continue;
					
					newID += Block.getIdFromBlock(b);
					
					if (damage != 0) newID *= damage;
				
				}
				
				idArray.add(newID);
			
			}
		}
		
		String roomcode = "";
		int currentID = 0;
		int chainLength = 0;
		
		for (int i = 0; i <= idArray.size(); i++) {
			
			if (i == idArray.size()) {
				roomcode += (currentID + "" + chainLength);
				break;
			}
			
			int id = idArray.get(i);
			
			if (chainLength == 0) currentID = id;
			
			if (id == currentID) {
				chainLength++;
				continue;
			}
			
			roomcode += (currentID + "" + chainLength);
			currentID = id;
			chainLength = 1;
			
		}
		
		return roomcode;
		
	}
	
	private static String finalizeRoomcode(String roomcode) {
		return (String)runFinalization(roomcode, false);
	}
	
	private static int getRoomcodeScore(String roomcode) {
		return (int)runFinalization(roomcode, true);
	}
	
	private static Object runFinalization(String roomcode, boolean returnScore) {
		
		if (SecretSolutionCache.solutionExists(roomcode) || SecretSolutionCache.redirectExists(roomcode)) return returnScore ? -1 : roomcode;
		
		Set<String> roomcodes = new HashSet<>();
		roomcodes.addAll(SecretSolutionCache.solutions.keySet());
		roomcodes.addAll(SecretSolutionCache.redirects.keySet());
		
		int bestSimilarityScore = 0;
		String bestRoomcode = "";
		
		for (String newRoomcode : roomcodes) {
			
			int similarity = getRoomcodeSimilarity(newRoomcode, roomcode);
			if (similarity <= bestSimilarityScore) continue;
			
			bestSimilarityScore = similarity;
			bestRoomcode = newRoomcode;
			
		}
		
		if (bestRoomcode.equals("")) return returnScore ? 0 : roomcode;
		if (bestSimilarityScore < 10) return returnScore ? bestSimilarityScore : roomcode;
		return returnScore ? bestSimilarityScore : bestRoomcode;
		
	}
	
	private static int getRoomcodeSimilarity(String original, String generated) {
		
		int shortestLength = Math.min(original.length(), generated.length());
		int score = 0;
		
		for (int i = 0; i < shortestLength; i++) {
			if (original.charAt(i) != generated.charAt(i)) break;
			score++;
		}
		
		for (int i = 0; i < shortestLength; i++) {
			if (original.charAt(original.length() - 1 - i) != generated.charAt(generated.length() - 1 - i)) break;
			score++;
		}
		
		return score;
		
	}
	
	public static int[] translatedCoordinates(int tx, int tz, int xTranslation, int zTranslation, int direction) {
		
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
	
	public static String directionToString(int direction) {
		
		switch (direction) {
		case POS_Z:
			return "POS Z";
		case NEG_Z:
			return "NEG Z";
		case POS_X:
			return "POS X";
		case NEG_X:
			return "NEG X";
		}
		
		return "UNKNOWN";
		
	}
	
}
