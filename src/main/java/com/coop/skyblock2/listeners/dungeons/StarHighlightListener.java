package com.coop.skyblock2.listeners.dungeons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.coop.skyblock2.commands.dungeons.StarHighlightCommand;
import com.coop.skyblock2.listeners.dungeons.secrets.SecretSolutionCache;
import com.coop.skyblock2.listeners.dungeons.secrets.SecretUtils;
import com.coop.skyblock2.utils.FileManager;
import com.coop.skyblock2.utils.MagicRenderer;
import com.coop.skyblock2.utils.Num;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class StarHighlightListener {

	private static boolean updated = false;
	private static HashMap<String, List<int[]>> cellMap = new HashMap<>();
	
	@SubscribeEvent
	public void renderWorld(RenderWorldLastEvent e) {
		
		if (!updated) updateSettings();
		if (!Utils.isInDungeons()) return;
		
		boolean ctrl = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
		boolean shift = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
		boolean f = Keyboard.isKeyDown(Keyboard.KEY_F);
		
		if (ctrl && shift && f) {
			highlightAll(e.getPartialTicks());
			return;
		}
		
		if (!StarHighlightCommand.alwaysActive) {
			if (!ctrl || !f) return;
		}
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		int[] cellXZ = SecretUtils.getRoomCell();
		String cellCode = cellXZ[0] + "/" + cellXZ[1];
		
		if (!cellMap.containsKey(cellCode)) cellMap.put(cellCode, getCellLinks(cellXZ));
		List<int[]> cells = cellMap.get(cellCode);
		
		List<Entity> starredEnemies = new ArrayList<>();
		
		for (Entity entity : p.world.getLoadedEntityList()) {
			
			if (!(entity instanceof EntityArmorStand)) continue;
			
			String name = Utils.removeColorcodes(entity.getCustomNameTag());
			if (name.length() < 1) continue;
			int checkID = (int)name.charAt(0);
			if (checkID != 10031) continue;
			
			int[] enemyCell = SecretUtils.getRoomCell(entity);
			
			for (int[] cell : cells) {
				
				if (cell[0] != enemyCell[0] || cell[1] != enemyCell[1]) continue;
				starredEnemies.add(entity);
				break;
				
			}
			
		}
		
		for (Entity entity : starredEnemies) highlightEnemy(entity, e.getPartialTicks());
		
	}
	
	private static void highlightAll(float ticks) {
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		List<Entity> starredEnemies = new ArrayList<>();
		
		for (Entity entity : p.world.getLoadedEntityList()) {
			
			if (!(entity instanceof EntityArmorStand)) continue;
			
			String name = Utils.removeColorcodes(entity.getCustomNameTag());
			if (name.length() < 1) continue;
			int checkID = (int)name.charAt(0);
			if (checkID != 10031) continue;
			
			starredEnemies.add(entity);
			
		}
		
		for (Entity entity : starredEnemies) highlightEnemy(entity, ticks);
		
	}
	
	private static List<int[]> getCellLinks(int[] cellXZ) {
		
		List<int[]> cells = new ArrayList<>();
		cells.add(cellXZ);
		
		int cellX = cellXZ[0];
		int cellZ = cellXZ[1];
		
		String mastercode = SecretUtils.getRoomCode(cellX, cellZ);
		boolean redirectExists = SecretSolutionCache.redirectExists(mastercode);
		if (!SecretSolutionCache.solutionExists(mastercode) && !redirectExists) return cells;
		if (redirectExists) mastercode = SecretSolutionCache.getRedirect(mastercode);
		if (mastercode == null) return cells;
		
		for (int xOffset = -3; xOffset <= 3; xOffset++) {
			for (int zOffset = -3; zOffset <= 3; zOffset++) {
				
				if (xOffset == 0 && zOffset == 0) continue;
				
				int cx = cellX + xOffset;
				int cz = cellZ + zOffset;
				
				if (cx < 0 || cx > 10 || cz < 0 || cz > 10) continue;
				
				if (Math.abs(xOffset) > 1 || Math.abs(zOffset) > 1) {
					if (xOffset != 0 && zOffset != 0) continue;
				}
				
				String roomcode = SecretUtils.getRoomCode(cx, cz);
				if (SecretSolutionCache.redirectExists(roomcode)) roomcode = SecretSolutionCache.getRedirect(roomcode);
				if (roomcode == null) continue;
				if (roomcode.equals(mastercode)) cells.add(new int[] {cx, cz});
				
			}
		}
		
		return cells;
		
	}
	
	private static void highlightEnemy(Entity entity, float ticks) {
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		float px = (float)(p.prevPosX + (p.posX - p.prevPosX) * ticks);
		float py = (float)(p.prevPosY + (p.posY - p.prevPosY) * ticks);
		float pz = (float)(p.prevPosZ + (p.posZ - p.prevPosZ) * ticks);
	  
		float x = (float)(entity.prevPosX + (entity.posX - entity.prevPosX) * ticks);
		float y = (float)(entity.prevPosY + (entity.posY - entity.prevPosY) * ticks) - 1.8f;
		float z = (float)(entity.prevPosZ + (entity.posZ - entity.prevPosZ) * ticks);
		
		MagicRenderer renderer = new MagicRenderer();
		renderer.autoTranslate(ticks);
		
		renderer.lineWidth(3.5f);
		
		float hue = (float)Num.loop(0, 360, 777);
		float[] rgb = Utils.HSVtoRGB(hue, 100, 100);
		
		int r = (int)rgb[0];
		int g = (int)rgb[1];
		int b = (int)rgb[2];
		
		renderer.disableDepth();
		
		renderer.begin(GL11.GL_LINES);
		
		renderer.color(r, g, b, 77);
		renderer.vc(px, py + 1, pz);
		renderer.vc(x, y + 1, z);
		
		double size = 0.6;
		renderer.color(r, g, b, 255);
		renderer.hlo(-size, -size, -size, x, y + 1, z, hue, hue);
		renderer.hlo(size, -size, -size, x, y + 1, z, hue, hue);
		
		renderer.hlo(size, -size, -size, x, y + 1, z, hue, hue);
		renderer.hlo(size, -size, size, x, y + 1, z, hue, hue);
		
		renderer.hlo(size, -size, size, x, y + 1, z, hue, hue);
		renderer.hlo(-size, -size, size, x, y + 1, z, hue, hue);
		
		renderer.hlo(-size, -size, size, x, y + 1, z, hue, hue);
		renderer.hlo(-size, -size, -size, x, y + 1, z, hue, hue);
		
		renderer.hlo(-size, -size, -size, x, y + 1, z, hue, hue);
		renderer.hlo(-size, size, -size, x, y + 1, z, hue, hue);
		
		renderer.hlo(size, -size, -size, x, y + 1, z, hue, hue);
		renderer.hlo(size, size, -size, x, y + 1, z, hue, hue);
		
		renderer.hlo(-size, -size, size, x, y + 1, z, hue, hue);
		renderer.hlo(-size, size, size, x, y + 1, z, hue, hue);
		
		renderer.hlo(size, -size, size, x, y + 1, z, hue, hue);
		renderer.hlo(size, size, size, x, y + 1, z, hue, hue);
		
		renderer.hlo(-size, size, -size, x, y + 1, z, hue, hue);
		renderer.hlo(size, size, -size, x, y + 1, z, hue, hue);
		
		renderer.hlo(size, size, -size, x, y + 1, z, hue, hue);
		renderer.hlo(size, size, size, x, y + 1, z, hue, hue);
		
		renderer.hlo(size, size, size, x, y + 1, z, hue, hue);
		renderer.hlo(-size, size, size, x, y + 1, z, hue, hue);
		
		renderer.hlo(-size, size, size, x, y + 1, z, hue, hue);
		renderer.hlo(-size, size, -size, x, y + 1, z, hue, hue);
		
		renderer.draw();
		renderer.enableDepth();
		renderer.end();
		
	}
	
	@SubscribeEvent
	public void loadWorld(WorldEvent.Load e) {
		
		cellMap = new HashMap<>();
		
	}
	
	public static void updateSettings() {
		
		updated = true;
		
		try {
			
			StarHighlightCommand.alwaysActive = Boolean.parseBoolean(FileManager.readLine("mods/sb2/dungeons/starhighlight.cccm"));
			
		} catch (Exception E) {}
		
	}
	
}
