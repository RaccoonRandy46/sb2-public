package com.coop.skyblock2.listeners.dungeons.secrets.identifiers;

public class SecretBat extends Secret {
	
	public SecretBat(int xIn, int yIn, int zIn) {
		super(xIn, yIn, zIn);
	}

	@Override
	public int[] getColor() {
		return new int[] {0, 0, 255};
	}
	
}
