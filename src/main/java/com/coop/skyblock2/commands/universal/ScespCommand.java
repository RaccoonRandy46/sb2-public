package com.coop.skyblock2.commands.universal;

import java.util.HashSet;
import java.util.Set;

import org.lwjgl.opengl.GL11;

import com.coop.skyblock2.Main;
import com.coop.skyblock2.utils.MagicRenderer;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ScespCommand {

	private static boolean enabled = false;
	
	private static Set<int[]> leverPositions = new HashSet<>();
	private static Set<int[]> ladderPositions = new HashSet<>();
	
	private static long lastTimeScanned = 0;
	
	@SubscribeEvent
	public void chatGo(ClientChatEvent e) {
		
		if (!Main.universal) return;
		
		String[] args = e.getMessage().split(" ");
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		if (args[0].equals("s>scesp")) {
			
			enabled = !enabled;
			p.sendMessage(new TextComponentString(Utils.chat(enabled ? "&asecret esp on" : "&csecret esp off")));
			
			leverPositions = new HashSet<>();
			ladderPositions = new HashSet<>();
			
		}
		
	}
	
	@SubscribeEvent
	public void renderWorld(RenderWorldLastEvent e) {
		
		if (!enabled) return;
		if (!Utils.isInDungeons()) return;
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		for (Entity entity : p.world.getLoadedEntityList()) {
			
			if (entity instanceof EntityBat) {
				drawBox(entity.posX - 0.5, entity.posY - 0.5, entity.posZ - 0.5, 0, 0, 255, e.getPartialTicks());
				continue;
			}
			
			if (!(entity instanceof EntityItem)) continue;
				
			drawBox(entity.posX - 0.5, entity.posY - 0.5, entity.posZ - 0.5, 255, 127, 0, e.getPartialTicks());
			
		}
		
		for (TileEntity entity : p.world.loadedTileEntityList) {
			
			BlockPos pos = entity.getPos();
			
			if (entity instanceof TileEntityChest) {
				drawBox(pos.getX(), pos.getY(), pos.getZ(), 0, 255, 0, e.getPartialTicks());
				continue;
			}
			
			if (!(entity instanceof TileEntitySkull)) continue;
			if (p.getDistance(pos.getX(), pos.getY(), pos.getZ()) > 10) continue;
			
			drawBox(pos.getX(), pos.getY(), pos.getZ(), 20, 20, 20, e.getPartialTicks());
			
		}
		
		for (int[] pos : leverPositions) drawBox(pos[0], pos[1], pos[2], 255, 0, 255, e.getPartialTicks());
		for (int[] pos : ladderPositions) drawBox(pos[0], pos[1], pos[2], 255, 255, 255, e.getPartialTicks());
		
		if (System.currentTimeMillis() - lastTimeScanned < 250) return;
		lastTimeScanned = System.currentTimeMillis();
		
		int px = (int)p.posX;
		int py = (int)p.posY;
		int pz = (int)p.posZ;
		
		if (px < 0) px--;
		if (pz < 0) pz--;
		
		for (int x = px - 10; x <= px + 10; x++) {
			for (int y = py - 10; y <= py + 10; y++) {
				for (int z = pz - 10; z <= pz + 10; z++) {
					
					Block b = p.world.getBlockState(new BlockPos(x, y, z)).getBlock();
					
					if (b == Blocks.LEVER) {
						leverPositions.add(new int[] {x, y, z});
						continue;
					}
					
					if (b != Blocks.LADDER) continue;
					
					ladderPositions.add(new int[] {x, y, z});
					
				}
			}
		}
		
	}
	
	private static void drawBox(double x, double y, double z, int r, int g, int b, float ticks) {
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		double px = (p.prevPosX + (p.posX - p.prevPosX) * ticks);
		double py = (p.prevPosY + (p.posY - p.prevPosY) * ticks);
		double pz = (p.prevPosZ + (p.posZ - p.prevPosZ) * ticks);
		
		MagicRenderer renderer = new MagicRenderer();
		renderer.autoTranslate(ticks);
		
		renderer.lineWidth(3);
		renderer.color(r, g, b);
		renderer.disableDepth();
		
		renderer.begin(GL11.GL_LINES);
		
		renderer.vc(px, py + 1, pz);
		renderer.vc(x + 0.5, y + 0.5, z + 0.5);
		
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
		
		renderer.vc(x, y, z + 1);
		renderer.vc(x, y + 1, z + 1);
		
		renderer.vc(x + 1, y, z + 1);
		renderer.vc(x + 1, y + 1, z + 1);
		
		renderer.draw();
		renderer.enableDepth();
		renderer.end();
		
	}
	
}
