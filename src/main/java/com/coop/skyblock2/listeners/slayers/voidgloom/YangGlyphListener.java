package com.coop.skyblock2.listeners.slayers.voidgloom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class YangGlyphListener {

	private static HashMap<UUID, Integer[]> fallingBlockPosMap = new HashMap<>();
	private static HashMap<UUID, Long> fallingBlockAgeMap = new HashMap<>();
	
	private static ArrayList<Integer[]> yangPosArray = new ArrayList<>();
	
	@SubscribeEvent
	public void findFallingBlock(PlayerTickEvent e) {
		
		if (!(e.player instanceof EntityPlayerSP)) return;
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		for (Entity entity : p.world.loadedEntityList) {
			
			if (!(entity instanceof EntityArmorStand)) continue;
			
			if (Math.abs(p.posX - entity.posX) > 25) continue;
			if (Math.abs(p.posY - entity.posY) > 25) continue;
			if (Math.abs(p.posZ - entity.posZ) > 25) continue;
			
			for (ItemStack item : entity.getArmorInventoryList()) {
					
				try {
				
					if (item.getItem() == Item.getItemFromBlock(Blocks.BEACON)) {
						
						int x = (int)entity.posX;
						int y = (int)entity.posY;
						int z = (int)entity.posZ;
						if (x < 0) x--;
						if (z < 0) z--;
						
						fallingBlockPosMap.put(entity.getUniqueID(), new Integer[] {x, y, z});
						fallingBlockAgeMap.put(entity.getUniqueID(), System.currentTimeMillis());
						
					}
				
				} catch (Exception E) {}
					
			}
			
		}
		
		if (fallingBlockPosMap.size() == 0) return;
		
		ArrayList<Integer[]> newArray = new ArrayList<>();
		
		HashSet<UUID> keysToRemove = new HashSet<>();
		for (UUID uuid : fallingBlockPosMap.keySet()) {
			
			if (System.currentTimeMillis() - fallingBlockAgeMap.get(uuid) >= 6000) {
				keysToRemove.add(uuid);
				continue;
			}
			
			Integer[] xyz = fallingBlockPosMap.get(uuid);
			
			boolean added = false;
			for (int x = xyz[0] - 5; x <= xyz[0] + 5 && !added; x++) {
				for (int y = xyz[1] - 5; y <= xyz[1] + 5 && !added; y++) {
					for (int z = xyz[2] - 5; z <= xyz[2] + 5; z++) {
						
						Block block = p.world.getBlockState(new BlockPos(x, y, z)).getBlock();
						if (block != Blocks.BEACON) continue;
						
						newArray.add(new Integer[] {x, y, z});
						
						added = true;
						break;
						
					}
				}
			}
		
		}
		
		for (UUID key : keysToRemove) {
			fallingBlockPosMap.remove(key);
			fallingBlockAgeMap.remove(key);
		}
		
		yangPosArray = newArray;
		
	}
	
	@SubscribeEvent
	public void drawGlyph(RenderWorldLastEvent e) {
		
		if (yangPosArray.size() == 0) return;
		
		ArrayList<Integer[]> yangPosArrayClone = cloneArray(yangPosArray);
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		float translatex = (float)(p.prevPosX + (p.posX - p.prevPosX) * e.getPartialTicks());
		float translatey = (float)(p.prevPosY + (p.posY - p.prevPosY) * e.getPartialTicks());
		float translatez = (float)(p.prevPosZ + (p.posZ - p.prevPosZ) * e.getPartialTicks());
		
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		GL11.glTranslated(-translatex, -translatey, -translatez);
		
		GL11.glLineWidth(3);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glHint( GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
		
		for (Integer[] xyz : yangPosArrayClone) {
			
			try {
				
				GL11.glColor4ub((byte)67, (byte)64, (byte)255, (byte)120);
				
				GL11.glBegin(GL11.GL_QUADS);
			
				float x = xyz[0];
				float y = xyz[1];
				float z = xyz[2];
	        
				GL11.glVertex3f(x, y, z);
				GL11.glVertex3f(x, y, z + 1);
				GL11.glVertex3f(x, y + 1, z + 1);
				GL11.glVertex3f(x, y + 1, z);
				
				GL11.glVertex3f(x + 1, y, z);
				GL11.glVertex3f(x, y, z);
				GL11.glVertex3f(x, y + 1, z);
				GL11.glVertex3f(x + 1, y + 1, z);
				
				GL11.glVertex3f(x + 1, y, z + 1);
				GL11.glVertex3f(x + 1, y, z);
				GL11.glVertex3f(x + 1, y + 1, z);
				GL11.glVertex3f(x + 1, y + 1, z + 1);
				
				GL11.glVertex3f(x, y, z + 1);
				GL11.glVertex3f(x + 1, y, z + 1);
				GL11.glVertex3f(x + 1, y + 1, z + 1);
				GL11.glVertex3f(x, y + 1, z + 1);
				
				GL11.glVertex3f(x, y + 1, z + 1);
				GL11.glVertex3f(x + 1, y + 1, z + 1);
				GL11.glVertex3f(x + 1, y + 1, z);
				GL11.glVertex3f(x, y + 1, z);
				
				GL11.glVertex3f(x, y, z);
				GL11.glVertex3f(x + 1, y, z);
				GL11.glVertex3f(x + 1, y, z + 1);
				GL11.glVertex3f(x, y, z + 1);
	        
				GL11.glEnd();
				
				GL11.glColor4ub((byte)67, (byte)64, (byte)255, (byte)255);
				
				GL11.glBegin(GL11.GL_LINES);
				
				GL11.glVertex3f(x + 0.5f, y + 0.5f, z + 0.5f);
				GL11.glVertex3f(translatex, translatey + 1, translatez);
				
				GL11.glEnd();
				
			} catch (Exception E) {}
        
		}
		
		GL11.glColor4ub((byte)255, (byte)255, (byte)255, (byte)255);
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
			
		GL11.glPopMatrix();
		
	}
	
	private static ArrayList<Integer[]> cloneArray(ArrayList<Integer[]> array) {
		
		ArrayList<Integer[]> out = new ArrayList<>();
		
		for (int i = 0; i < array.size(); i++) {
			out.add(array.get(0));
		}
		
		return out;
		
	}
	
}
