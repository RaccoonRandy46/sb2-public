package com.coop.skyblock2.listeners.dungeons.secrets.identifiers;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;

public class SecretChest extends Secret {
	
	private boolean chestSpawned = false;
	
	public SecretChest(int xIn, int yIn, int zIn) {
		super(xIn, yIn, zIn);
	}

	@Override
	public int[] getColor() {
		return new int[] {0, 255, 0};
	}
	
	@Override
	public boolean isActive(int[] xz, int direction) {
		
		if (super.isActiveCD(xz, direction, 10)) return true;
		
		Block b = this.getBlock(xz, direction);
		if (b != Blocks.AIR) this.chestSpawned = true;
		
		if (b == Blocks.AIR && this.chestSpawned) return false;
		
		int x = this.getActualX(xz, direction);
		int y = this.getY();
		int z = this.getActualZ(xz, direction);
		
		TileEntityChest chest = null;
		
		for (TileEntity entity : Minecraft.getMinecraft().player.world.loadedTileEntityList) {
			
			if (!(entity instanceof TileEntityChest)) continue;
			
			if (entity.getPos().getX() != x) continue;
			if (entity.getPos().getY() != y) continue;
			if (entity.getPos().getZ() != z) continue;
			
			chest = (TileEntityChest)entity;
			break;
			
		}
		
		if (chest == null) return true;
		
		return chest.lidAngle == 0;
		
	}

}
