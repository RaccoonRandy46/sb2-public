package com.coop.skyblock2.listeners.dungeons.solvers.bomb.chambers;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.coop.skyblock2.utils.MagicRenderer;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class BDChamberCreepersweeper extends BDChamber {

	private List<int[]> locations = new ArrayList<>();
	private boolean logicComplete = false;
	
	public BDChamberCreepersweeper(int[] xzl, int[] xzr, int direction) {
		super(xzl, xzr, direction);
	}

	@Override
	public void executeLogic(RenderWorldLastEvent e) {
		
		if (this.logicComplete) return;
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		for (int tx = 3; tx <= 5; tx++) {
			for (int tz = 1; tz <= 3; tz++) {
				
				int[] xz = this.translatedCoordinatesI(this.xl, this.zl, tx, tz);
				Block b = p.world.getBlockState(new BlockPos(xz[0], GROUND, xz[1])).getBlock();
				
				if (b == Blocks.AIR) continue;
				
				xz = this.translatedCoordinatesI(this.xr, this.zr, tx, tz);
				this.locations.add(new int[] {xz[0], xz[1]});
				
			}
		}
		
		this.logicComplete = true;
		
	}
	
	@Override
	public void executeRender(RenderWorldLastEvent e) {
		
		if (!this.logicComplete) return;
		
		for (int[] location : this.locations) {
			
			int x = location[0];
			double y = GROUND + 1.1;
			int z = location[1];
			
			Block b = Minecraft.getMinecraft().player.world.getBlockState(new BlockPos(x, GROUND, z)).getBlock();
			if (b == Blocks.AIR) continue;
			
			MagicRenderer renderer = new MagicRenderer();
			renderer.autoTranslate(e.getPartialTicks());
			
			renderer.color(0, 255, 0, 127);
			
			renderer.begin(GL11.GL_QUADS);
			
			renderer.vc(x, y, z + 1);
			renderer.vc(x + 1, y, z + 1);
			renderer.vc(x + 1, y, z);
			renderer.vc(x, y, z);
			
			renderer.draw();
			renderer.end();
			
		}
		
	}
	
}
