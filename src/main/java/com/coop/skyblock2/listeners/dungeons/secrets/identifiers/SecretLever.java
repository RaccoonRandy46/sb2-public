package com.coop.skyblock2.listeners.dungeons.secrets.identifiers;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

public class SecretLever extends Secret {

	public SecretLever(int xIn, int yIn, int zIn) {
		super(xIn, yIn, zIn);
	}

	@Override
	public int[] getColor() {
		return new int[] {255, 0, 255};
	}
	
	@Override
	public boolean isActive(int[] xz, int direction) {
		
		if (super.isActive(xz, direction)) return true;
		
		RayTraceResult rayTrace = Minecraft.getMinecraft().objectMouseOver;
		if (rayTrace == null) return true;
		
		if (rayTrace.typeOfHit != RayTraceResult.Type.BLOCK) return true;
		
		BlockPos pos = rayTrace.getBlockPos();
        
		if (pos.getX() != this.getActualX(xz, direction)) return true;
		if (pos.getY() != this.getY()) return true;
		if (pos.getZ() != this.getActualZ(xz, direction)) return true;
		
		return false;
		
	}

}
