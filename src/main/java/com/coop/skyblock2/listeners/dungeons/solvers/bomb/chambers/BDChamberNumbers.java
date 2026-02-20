package com.coop.skyblock2.listeners.dungeons.solvers.bomb.chambers;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class BDChamberNumbers extends BDChamber {

	private ItemStack[] skulls = new ItemStack[4];
	private boolean logicComplete = false;
	
	public BDChamberNumbers(int[] xzl, int[] xzr, int direction) {
		super(xzl, xzr, direction);
	}
	
	@Override
	public void executeLogic(RenderWorldLastEvent e) {
		
		if (this.logicComplete) return;
		
		int[] txArray = new int[] {1, 2, 6, 7};
		
		for (int i = 0; i < 4; i++) this.skulls[i] = this.getSkullTXZ(this.xl, this.zl, txArray[i], GROUND + 1, 4);
		
		this.logicComplete = true;
		
	}
	
	@Override
	public void executeRender(RenderWorldLastEvent e) {
		
		if (!this.logicComplete) return;
		
		double[] txArray = new double[] {1, 2, 6, 7};
		
		for (int i = 0; i < 4; i++) this.renderSkullTXZ(this.skulls[i], this.xr, this.zr, txArray[i], GROUND + 2, 4, e.getPartialTicks(), true);
		
	}

}
