package com.coop.skyblock2.listeners.dungeons.secrets.identifiers;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class SecretMisc extends Secret {

	private boolean skullSpawned = false;
	
	public SecretMisc(int xIn, int yIn, int zIn) {
		super(xIn, yIn, zIn);
	}

	@Override
	public int[] getColor() {
		return new int[] {255, 100, 0};
	}
	
	@Override
	public boolean isActive(int[] xz, int direction) {
		
		Block b = this.getBlock(xz, direction);
		if (b == Blocks.SKULL) this.skullSpawned = true;
		
		if (b == Blocks.AIR && this.skullSpawned) return false;
		
		if (super.isActiveCD(xz, direction, 1.5)) return true;
		
		return this.getBlock(xz, direction) == Blocks.SKULL;
		
	}

}
