package com.coop.skyblock2.listeners.dungeons.solvers.bomb.chambers;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.coop.skyblock2.utils.MagicRenderer;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class BDChamberBrokenMaze extends BDChamber {

	private boolean logicComplete = false;
	private boolean isBroken = false;
	private static HashMap<Block, double[]> plateCoordinates = new HashMap<>();
	
	public BDChamberBrokenMaze(int[] xzl, int[] xzr, int direction) {
		super(xzl, xzr, direction);
	}
	
	@Override
	public void executeLogic(RenderWorldLastEvent e) {
		
		if (this.logicComplete) return;
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		int[][] txzArray = new int[][] {{1, 1}, {7, 1}, {3, 3}, {5, 3}, {1, 4}, {7, 4}};
		
		for (int[] txz : txzArray) {
			
			int[] xz = this.translatedCoordinatesI(this.xr, this.zr, txz[0], txz[1]);
			Block b = p.world.getBlockState(new BlockPos(xz[0], GROUND - 1, xz[1])).getBlock();
			
			plateCoordinates.put(b, new double[] {xz[0] + 0.5, xz[1] + 0.5});
			
		}
		
		this.logicComplete = true;
		
	}
	
	@Override
	public void executeRender(RenderWorldLastEvent e) {
		
		if (!this.logicComplete) return;
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		Color[] colors = new Color[54];
		this.isBroken = false;
		
		for (int tz = 0; tz < 6; tz++) {
			for (int tx = 0; tx < 9; tx++) {
				
				int[] xz = this.translatedCoordinatesI(this.xl, this.zl, tx, tz);
				Block b = p.world.getBlockState(new BlockPos(xz[0], GROUND, xz[1])).getBlock();
				colors[tz * 9 + tx] = this.getColor(b);
				
			}
		}
		
		if (this.isBroken) {
			
			double[] xz = this.translatedCoordinatesD(this.xrd, this.zrd, 4.5, 5.5);
			double y1 = GROUND + 2;
			double y2 = GROUND + 5;
			
			this.render3Dtext(Utils.chat("&cit broke"), xz[0], y2, xz[1], .2, e.getPartialTicks());
			this.render3Dtext(Utils.chat("&clmfao"), xz[0], y1, xz[1], .2, e.getPartialTicks());
			
			return;
			
		}
		
		for (double y = GROUND; y <= GROUND + 5; y++) {
			for (double tx = 0; tx < 9; tx++) {
				
				Color color = colors[(int)((y - GROUND) * 9 + tx)];
				this.drawSquare(tx, y, color, e.getPartialTicks());
				
			}
		}
		
		this.markPlayer(e.getPartialTicks());
		
	}
	
	private void drawSquare(double tx, double y, Color color, float ticks) {
		
		double tz = 5.8;
		
		double[] xz1 = this.translatedCoordinatesD(this.xrd, this.zrd, tx, tz);
		double[] xz2 = this.translatedCoordinatesD(this.xrd, this.zrd, tx + 1, tz);
		
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		
		MagicRenderer renderer = new MagicRenderer();
		renderer.autoTranslate(ticks);
		
		renderer.color(r, g, b);
		
		renderer.begin(GL11.GL_QUADS);
		
		renderer.vc(xz1[0], y, xz1[1]);
		renderer.vc(xz2[0], y, xz2[1]);
		renderer.vc(xz2[0], y + 1, xz2[1]);
		renderer.vc(xz1[0], y + 1, xz1[1]);
		
		renderer.draw();
		renderer.end();
		
	}
	
	private void markPlayer(float ticks) {
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		double[] topRight = this.translatedCoordinatesD(this.xld, this.zld, 9, 6);
		
		double minX = Math.min(this.xld, topRight[0]);
		double maxX = Math.max(this.xld, topRight[0]);
		double minZ = Math.min(this.zld, topRight[1]);
		double maxZ = Math.max(this.zld, topRight[1]);
		
		for (EntityPlayer u : p.world.playerEntities) {
			
			if (u.posX < minX || u.posX > maxX || u.posZ < minZ || u.posZ > maxZ) continue;
			
			this.markPlates(u, ticks);
			
			double size = 0.3;
			
			double ux = (u.prevPosX + (u.posX - u.prevPosX) * ticks);
			double uz = (u.prevPosZ + (u.posZ - u.prevPosZ) * ticks);
			
			double[] txz = this.translatedCoordinatesInvD(this.xld, this.zld, ux, uz);
			double[] xz1 = this.translatedCoordinatesD(this.xrd, this.zrd, txz[0] - size, 5.6);
			double[] xz2 = this.translatedCoordinatesD(this.xrd, this.zrd, txz[0] + size, 5.6);
			double y = GROUND + txz[1];
			
			MagicRenderer renderer = new MagicRenderer();
			renderer.autoTranslate(ticks);
			
			renderer.color(0, 255, 0);
			
			renderer.begin(GL11.GL_QUADS);
			
			renderer.vc(xz1[0], y - size, xz1[1]);
			renderer.vc(xz2[0], y - size, xz2[1]);
			renderer.vc(xz2[0], y + size, xz2[1]);
			renderer.vc(xz1[0], y + size, xz1[1]);
			
			renderer.draw();
			renderer.end();
			
		}
		
	}
	
	private void markPlates(EntityPlayer u, float ticks) {
		
		int x = (int)u.posX;
		int y = GROUND;
		int z = (int)u.posZ;
		if (x < 0) x--;
		if (z < 0) z--;
		
		List<double[]> laserCoordinates = new ArrayList<>();
		List<int[]> laserColors = new ArrayList<>();
		
		for (int i = 0; i < 4; i++) {
			
			int add = i % 2 * 2 - 1;
			BlockPos pos = i < 2 ? new BlockPos(x + add, y, z) : new BlockPos(x, y, z + add);
			Block b = u.world.getBlockState(pos).getBlock();
			
			if (!this.isDoor(b)) continue;
			
			laserCoordinates.add(plateCoordinates.get(b));
			
			Color color = this.getColor(b);
			laserColors.add(new int[] {color.getRed(), color.getGreen(), color.getBlue()});
			
		}
		
		if (laserCoordinates.size() == 0) return;
		
		MagicRenderer renderer = new MagicRenderer();
		renderer.autoTranslate(ticks);
		
		renderer.begin(GL11.GL_QUADS);
		
		for (int i = 0; i < laserCoordinates.size(); i++) {
			
			double[] xz = laserCoordinates.get(i);
			int[] color = laserColors.get(i);
			
			double dx = xz[0];
			double dz = xz[1];
			
			renderer.color(color[0], color[1], color[2], 100);
			
			renderer.vc(dx + .25, 0, dz + .25);
			renderer.vc(dx + .25, 0, dz - .25);
			renderer.vc(dx + .25, 255, dz - .25);
			renderer.vc(dx + .25, 255, dz + .25);
			
			renderer.vc(dx + .25, 0, dz - .25);
			renderer.vc(dx - .25, 0, dz - .25);
			renderer.vc(dx - .25, 255, dz - .25);
			renderer.vc(dx + .25, 255, dz - .25);
			
			renderer.vc(dx - .25, 0, dz - .25);
			renderer.vc(dx - .25, 0, dz + .25);
			renderer.vc(dx - .25, 255, dz + .25);
			renderer.vc(dx - .25, 255, dz - .25);
			
			renderer.vc(dx - .25, 0, dz + .25);
			renderer.vc(dx + .25, 0, dz + .25);
			renderer.vc(dx + .25, 255, dz + .25);
			renderer.vc(dx - .25, 255, dz + .25);
			
		}
		
		renderer.draw();
		renderer.end();
		
	}
	
	private Color getColor(Block b) {
		
		if (b == Blocks.BARRIER) this.isBroken = true;
		if (b == Blocks.AIR) return new Color(127, 127, 127);
		if (b == Blocks.IRON_BLOCK) return new Color(255, 255, 255);
		if (b == Blocks.BEDROCK) return new Color(0, 0, 0);
		if (b == Blocks.REDSTONE_BLOCK) return new Color(255, 0, 0);
		if (b == Blocks.GOLD_BLOCK) return new Color(255, 255, 0);
		if (b == Blocks.LAPIS_BLOCK) return new Color(0, 0, 255);
		if (b == Blocks.DIAMOND_BLOCK) return new Color(0, 255, 255);
		return new Color(50, 50, 50);
		
	}
	
	private boolean isDoor(Block b) {
		
		if (b == Blocks.IRON_BLOCK) return true;
		if (b == Blocks.BEDROCK) return true;
		if (b == Blocks.REDSTONE_BLOCK) return true;
		if (b == Blocks.GOLD_BLOCK) return true;
		if (b == Blocks.LAPIS_BLOCK) return true;
		if (b == Blocks.DIAMOND_BLOCK) return true;
		
		return false;
		
	}

}
