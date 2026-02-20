package com.coop.skyblock2.listeners.dungeons.secrets.identifiers;

import net.minecraft.init.Blocks;

public class SecretWall extends Secret {

	public SecretWall(int xIn, int yIn, int zIn) {
		super(xIn, yIn, zIn);
	}

	@Override
	public int[] getColor() {
		return new int[] {255, 0, 0};
	}
	
	@Override
	public boolean isActive(int[] xz, int direction) {
		
		if (super.isActiveCD(xz, direction, 10)) return true;
		
		return this.getBlock(xz, direction) != Blocks.AIR;
		
	}

}
