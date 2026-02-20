package com.coop.skyblock2.listeners.dungeons.secrets.identifiers;

import java.util.ArrayList;

import com.coop.skyblock2.listeners.dungeons.secrets.SecretUtils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.BlockPos;

public abstract class Secret {

	private int x;
	private int y;
	private int z;
	
	private ArrayList<Double[]> pathPoints = new ArrayList<>();
	
	public Secret(int xIn, int yIn, int zIn) {
		this.x = xIn;
		this.y = yIn;
		this.z = zIn;
	}
	
	public abstract int[] getColor();
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getZ() {
		return this.z;
	}
	
	public int getActualX(int[] xz, int direction) {
		int[] newXZ = SecretUtils.translatedCoordinates(xz[0], xz[1], this.x, this.z, direction);
		return newXZ[0];
	}
	
	public int getActualZ(int[] xz, int direction) {
		int[] newXZ = SecretUtils.translatedCoordinates(xz[0], xz[1], this.x, this.z, direction);
		return newXZ[1];
	}
	
	public void addPathPoint(double x, double y, double z) {
		this.pathPoints.add(new Double[] {x, y, z});
	}
	
	public ArrayList<Double[]> getPathPoints() {
		return this.pathPoints;
	}
	
	public boolean isActive(int[] xz, int direction) {
		
		return this.isActiveCD(xz, direction, 5);
		
	}
	
	public boolean isActiveCD(int[] xz, int direction, double distance) {
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		if (Math.abs(p.posX - this.getActualX(xz, direction)) > distance) return true;
		if (Math.abs(p.posY - this.getY()) > distance) return true;
		if (Math.abs(p.posZ - this.getActualZ(xz, direction)) > distance) return true;
		
		return false;
		
	}
	
	protected IBlockState getBlockState(int[] xz, int direction) {
		return Minecraft.getMinecraft().player.world.getBlockState(new BlockPos(this.getActualX(xz, direction), this.getY(), this.getActualZ(xz, direction)));
	}
	
	protected Block getBlock(int[] xz, int direction) {
		return this.getBlockState(xz, direction).getBlock();
	}
	
}
