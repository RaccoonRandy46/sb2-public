package com.coop.skyblock2.listeners.dungeons.solvers.bomb.chambers;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class BDChamberArrows extends BDChamber {
	
	private ItemStack[] skulls = new ItemStack[9];
	private boolean logicComplete = false;
	
	public BDChamberArrows(int[] xzl, int[] xzr, int direction) {
		super(xzl, xzr, direction);
	}
	
	@Override
	public void executeLogic(RenderWorldLastEvent e) {
		
		if (this.logicComplete) return;
		
		for (int i = 0; i < 9; i++) {
			
			int y = (GROUND + 2) - (i / 3);
			int tz = 1 + (i % 3);
			
			this.skulls[i] = this.getSkullTXZ(this.xl, this.zl, 1, y, tz);
			
		}
		
		this.logicComplete = true;
		
	}
	
	@Override
	public void executeRender(RenderWorldLastEvent e) {
		
		if (!this.logicComplete) return;
		
		for (int i = 0; i < 9; i++) {
			
			int y = (GROUND + 2) - (i / 3);
			double tx = 4 + (i % 3);
			
			this.renderSkullTXZ(this.skulls[i], this.xr, this.zr, tx, y, 4, e.getPartialTicks(), true);
			
		}
		
	}

}
