package com.coop.skyblock2.listeners.dungeons.secrets;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.coop.skyblock2.commands.dungeons.AutoCycleCommand;
import com.coop.skyblock2.listeners.dungeons.secrets.identifiers.Secret;
import com.coop.skyblock2.utils.Debug;
import com.coop.skyblock2.utils.MagicRenderer;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class SecretListener {

	private static HashMap<String, SecretSolution> solutions = new HashMap<>();
	private static HashMap<String, int[]> origins = new HashMap<>();
	private static HashMap<String, Integer> directions = new HashMap<>();
	
	private static boolean lastCSS = true;
	
	@SubscribeEvent
	public void getSolutions(PlayerTickEvent e) {
		
		if (!(e.player instanceof EntityPlayerSP)) return;
		if (!SecretManager.enabled) return;
		if (!Utils.isInDungeons()) return;
		
		int[] cellXZ = SecretUtils.getRoomCell();
		int cellX = cellXZ[0];
		int cellZ = cellXZ[1];
		
		String cellCode = cellX + "/" + cellZ;
		
		if (solutions.containsKey(cellCode)) return;
		
		Debug.sendSecretsDebug("-\n&d&l&oCELL CODE: &e" + cellCode);
		
		String redirect = null;
			
		String roomcode = SecretUtils.getRoomCode(cellX, cellZ);
		int direction = SecretUtils.getRoomDirection(cellX, cellZ);
		
		if (SecretSolutionCache.solutionExists(roomcode)) {
			solutions.put(cellCode, SecretSolutionCache.getSolution(roomcode));
			origins.put(cellCode, SecretUtils.getRoomOrigin(cellX, cellZ, direction));
			directions.put(cellCode, direction);
			Debug.sendSecretsDebug("&dTYPE: &eMASTER");
			Debug.sendSecretsDebug("&dDIRECTION: &e" + SecretUtils.directionToString(direction));
			Debug.sendSecretsDebug("&dROOMCODE:\n&7&o" + roomcode);
			return;
		}
		
		if (SecretSolutionCache.redirectExists(roomcode)) {
			redirect = SecretSolutionCache.getRedirect(roomcode);
			Debug.sendSecretsDebug("&dTYPE: &eREDIRECT");
			Debug.sendSecretsDebug("&dROOMCODE:\n&7&o" + roomcode);
		}
		
		if (redirect == null) {
			solutions.put(cellCode, new SecretSolution());
			Debug.sendSecretsDebug("&dTYPE: &cUNKNOWN");
			Debug.sendSecretsDebug("\n&7&oPOS_Z:\n&7&o" + SecretUtils.getUnfinalizedRoomCode(cellX, cellZ, 0) + "\n\n&7&oNEG_Z:\n&7&o" + SecretUtils.getUnfinalizedRoomCode(cellX, cellZ, 1) + "\n\n&7&oPOS_X:\n&7&o" + SecretUtils.getUnfinalizedRoomCode(cellX, cellZ, 2) + "\n\n&7&oNEG_X:\n&7&o" + SecretUtils.getUnfinalizedRoomCode(cellX, cellZ, 3));
			Debug.sendSecretsDebug("&c&ounable to identify cell! &4&oall four roomcodes have been printed above.");
			return;
		}
		
		for (int xOffset = -3; xOffset <= 3; xOffset++) {
			for (int zOffset = -3; zOffset <= 3; zOffset++) {
				
				if (xOffset == 0 && zOffset == 0) continue;
				
				int cx = cellX + xOffset;
				int cz = cellZ + zOffset;
				
				if (cx < 0 || cx > 10 || cz < 0 || cz > 10) continue;
				
				if (Math.abs(xOffset) > 1 || Math.abs(zOffset) > 1) {
					if (xOffset != 0 && zOffset != 0) continue;
				}
				
				String mastercode = SecretUtils.getRoomCode(cx, cz);
				int masterDirection = SecretUtils.getRoomDirection(cx, cz);
				
				if (mastercode.equals(redirect)) {
					solutions.put(cellCode, SecretSolutionCache.getSolution(redirect));
					directions.put(cellCode, masterDirection);
					origins.put(cellCode, SecretUtils.getRoomOrigin(cx, cz, masterDirection));
					return;
				}
				
			}
		}
		
		solutions.put(cellCode, new SecretSolution());
		Debug.sendSecretsDebug("&dTYPE: &eREDIRECT");
		Debug.sendSecretsDebug("&c&ounable to find master cell!");
		return;
		
	}
	
	@SubscribeEvent
	public void displaySolution(RenderWorldLastEvent e) {
		
		if (!SecretManager.enabled) return;
		
		int[] cellXZ = SecretUtils.getRoomCell();
		int cellX = cellXZ[0];
		int cellZ = cellXZ[1];
		
		String cellCode = cellX + "/" + cellZ;
		
		if (!solutions.containsKey(cellCode)) return;
		
		SecretSolution solution = solutions.get(cellCode);
		if (solution.getSecrets().size() == 0) return;
		
		int[] xz = origins.get(cellCode);
		int direction = directions.get(cellCode);
		
		handleManualCycle(solution);
		if (solution.getSecrets().size() == 0) return;
		
		Secret secret = AutoCycleCommand.enabled ? solution.getSecrets().get(0) : solution.getCurrentSecret();
		
		if (!secret.isActive(xz, direction) && AutoCycleCommand.enabled) {
			solution.popSecret();
			return;
		}
		
		MagicRenderer renderer = new MagicRenderer();
		renderer.autoTranslate(e.getPartialTicks());
		
		renderer.lineWidth(3);
		renderer.disableDepth();
		
		renderer.begin(GL11.GL_LINES);
		
		int[] color = secret.getColor();
		
		renderer.color(color[0], color[1], color[2]);
		
		drawPath(secret, xz, direction, renderer, e.getPartialTicks());
		drawBox(secret, xz, direction, renderer);
		
		highlightBats(renderer, e.getPartialTicks());
		
		renderer.draw();
		renderer.enableDepth();
		renderer.end();
		
	}
	
	private static void handleManualCycle(SecretSolution solution) {
		
		boolean css = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && Keyboard.isKeyDown(Keyboard.KEY_S);
		boolean prevCSS = lastCSS;
		lastCSS = css;
		
		if (!css || prevCSS) return;
		
		if (AutoCycleCommand.enabled) {
			if (solution.getSecretCount() == 0) return;
			solution.popSecret();
			return;
		}
		
		solution.cycleSecret();
		Minecraft.getMinecraft().player.sendMessage(new TextComponentString(Utils.chat("&acycling secrets &d(" + (solution.getSecretIndex() + 1) + "/" + solution.getSecretCount() + ")")));
		
	}
	
	private static void drawPath(Secret secret, int[] xz, int direction, MagicRenderer renderer, float ticks) {
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		double px = (p.prevPosX + (p.posX - p.prevPosX) * ticks);
		double py = (p.prevPosY + (p.posY - p.prevPosY) * ticks);
		double pz = (p.prevPosZ + (p.posZ - p.prevPosZ) * ticks);
		
		renderer.vc(px, py + 1, pz);
		
		ArrayList<Double[]> points = secret.getPathPoints();
		
		for (int i = 0; i < points.size(); i++) {
			
			Double[] point = points.get(i);
			int[] xz0 = SecretUtils.translatedCoordinates(xz[0], xz[1], (int)((double)point[0]), (int)((double)point[2]), direction);
			
			renderer.vc(xz0[0] + 0.5, point[1] + 0.5, xz0[1] + 0.5);
			renderer.vc(xz0[0] + 0.5, point[1] + 0.5, xz0[1] + 0.5);
			
		}
		
		renderer.vc(secret.getActualX(xz, direction) + 0.5, secret.getY() + 0.5, secret.getActualZ(xz, direction) + 0.5);
		
	}
	
	private static void drawBox(Secret secret, int[] xz, int direction, MagicRenderer renderer) {
		
		int x = secret.getX();
		int y = secret.getY();
		int z = secret.getZ();
		
		int[] newXZ = SecretUtils.translatedCoordinates(xz[0], xz[1], x, z, direction);
		x = newXZ[0];
		z = newXZ[1];
		
		drawBox(renderer, x, y, z);
		
	}
	
	private static void drawBox(MagicRenderer renderer, double x, double y, double z) {
		
		renderer.vc(x, y, z);
		renderer.vc(x + 1, y, z);
		
		renderer.vc(x + 1, y, z);
		renderer.vc(x + 1, y, z + 1);
		
		renderer.vc(x + 1, y, z + 1);
		renderer.vc(x, y, z + 1);
		
		renderer.vc(x, y, z + 1);
		renderer.vc(x, y, z);
		
		renderer.vc(x, y + 1, z);
		renderer.vc(x + 1, y + 1, z);
		
		renderer.vc(x + 1, y + 1, z);
		renderer.vc(x + 1, y + 1, z + 1);
		
		renderer.vc(x + 1, y + 1, z + 1);
		renderer.vc(x, y + 1, z + 1);
		
		renderer.vc(x, y + 1, z + 1);
		renderer.vc(x, y + 1, z);
		
		renderer.vc(x, y, z);
		renderer.vc(x, y + 1, z);
		
		renderer.vc(x + 1, y, z);
		renderer.vc(x + 1, y + 1, z);
		
		renderer.vc(x + 1, y, z + 1);
		renderer.vc(x + 1, y + 1, z + 1);
		
		renderer.vc(x, y, z + 1);
		renderer.vc(x, y + 1, z + 1);
		
	}
	
	private static void highlightBats(MagicRenderer renderer, float ticks) {
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		double px = (p.prevPosX + (p.posX - p.prevPosX) * ticks);
		double py = (p.prevPosY + (p.posY - p.prevPosY) * ticks);
		double pz = (p.prevPosZ + (p.posZ - p.prevPosZ) * ticks);
		
		renderer.color(0, 0, 255);
		
		for (Entity entity : p.world.getLoadedEntityList()) {
			
			if (!(entity instanceof EntityBat)) continue;
			
			if (Math.abs(p.posX - entity.posX) > 10) continue;
			if (Math.abs(p.posY - entity.posY) > 10) continue;
			if (Math.abs(p.posZ - entity.posZ) > 10) continue;
			
			double bx = (entity.prevPosX + (entity.posX - entity.prevPosX) * ticks);
			double by = (entity.prevPosY + (entity.posY - entity.prevPosY) * ticks);
			double bz = (entity.prevPosZ + (entity.posZ - entity.prevPosZ) * ticks);
			
			drawBox(renderer, bx - 0.5, by - 0.5, bz - 0.5);
			
			renderer.vc(px, py + 1, pz);
			renderer.vc(bx, by, bz);
			
		}
		
	}
	
	@SubscribeEvent
	public void worldLoad(WorldEvent.Load e) {
		
		SecretSolutionCache.registerAll();
		
		solutions = new HashMap<>();
		origins = new HashMap<>();
		directions = new HashMap<>();
		
	}
	
	public static void clearCell() {
		
		int[] cellXZ = SecretUtils.getRoomCell();
		int cellX = cellXZ[0];
		int cellZ = cellXZ[1];
		
		String cellCode = cellX + "/" + cellZ;
		
		solutions.remove(cellCode);
		origins.remove(cellCode);
		directions.remove(cellCode);
		
	}
	
}
