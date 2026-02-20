package com.coop.skyblock2.listeners.dungeons.solvers.bomb.chambers;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class BDChamberColorSwap extends BDChamber {

	private ItemStack[] skulls = new ItemStack[3];
	
	private long[] startTimes = new long[3];
	
	private boolean logicComplete = false;
	
	public BDChamberColorSwap(int[] xzl, int[] xzr, int direction) {
		super(xzl, xzr, direction);
	}
	
	@Override
	public void executeLogic(RenderWorldLastEvent e) {
		
		if (!this.logicComplete) {
			
			boolean arrayFull = true;
			for (int i = 0; i < 3; i++) {
				if (this.skulls[i] != null) continue;
				arrayFull = false;
				break;
			}
			
			if (arrayFull) this.logicComplete = true;
			
		}
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		int[] xz = this.translatedCoordinatesI(this.xr, this.zr, 8, 1);
		Block b = p.world.getBlockState(new BlockPos(xz[0], GROUND + 2, xz[1])).getBlock();
		
		for (int i = 0; i < 3; i++) {
			
			int tx = 1 + (i * 3);
			
			xz = this.translatedCoordinatesI(this.xl, this.zl, tx, 4);
			
			Block currentBlock = p.world.getBlockState(new BlockPos(xz[0], GROUND, xz[1])).getBlock();
			
			if (currentBlock != b) {
				this.startTimes[i] = 0;
				continue;
			}
			
			if (this.startTimes[i] == 0) this.startTimes[i] = System.currentTimeMillis();
			long timeShown = System.currentTimeMillis() - this.startTimes[i];
			
			if (timeShown < 100 || timeShown > 600) continue;
			
			double x = xz[0];
			double y = i == 1 ? GROUND + 3 : GROUND + 2;
			double z = xz[1];
			
			this.skulls[i] = this.getSkull(x, y, z);
			
		}
		
	}
	
	@Override
	public void executeRender(RenderWorldLastEvent e) {
		
		if (!this.logicComplete) return;
		
		for (int i = 0; i < 3; i++) {
			
			double tx = 4 + i;
			this.renderSkullTXZ(this.skulls[i], this.xr, this.zr, tx, GROUND + 2, 4, e.getPartialTicks(), true);
			
		}
		
	}

}
