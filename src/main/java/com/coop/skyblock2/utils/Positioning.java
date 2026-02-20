package com.coop.skyblock2.utils;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;

public class Positioning {

	public static double[] zFunc(double yaw, double pitch, double multiplier) {
		
		yaw = (double)Math.toRadians(yaw);
		pitch = (double)Math.toRadians(pitch);
		
		double xzMult = Math.cos(pitch);
		
		double cx = (double)(-Math.sin(yaw) * xzMult) * multiplier;
		double cy = (double)-Math.sin(pitch) * multiplier;
		double cz = (double)(Math.cos(yaw) * xzMult) * multiplier;
		
		return new double[] {cx, cy, cz};
		
	}
	
	public static double[] HLO_XYZ(Entity player, double x_offset, double y_offset, double z_offset, double ticks) {
		
		double yaw = 0;
		
		if (player instanceof EntityPlayerSP) {
			
			player = (EntityPlayerSP)player;
			yaw = (double)(((EntityPlayerSP)player).prevRotationYawHead + (((EntityPlayerSP)player).rotationYawHead - ((EntityPlayerSP)player).prevRotationYawHead) * ticks);
			
		}
		
		if (player instanceof EntityOtherPlayerMP) {
			
			player = (EntityOtherPlayerMP)player;
			yaw = (double)(((EntityOtherPlayerMP)player).prevRotationYawHead + (((EntityOtherPlayerMP)player).rotationYawHead - ((EntityOtherPlayerMP)player).prevRotationYawHead) * ticks);
			
		}
		
		double x = (double)(player.prevPosX + (player.posX - player.prevPosX) * ticks);
		double y = (double)(player.prevPosY + (player.posY - player.prevPosY) * ticks) + 1.5f;
		double z = (double)(player.prevPosZ + (player.posZ - player.prevPosZ) * ticks);

		if (player.isSneaking()) y -= 0.3f;
		
		double pitch = player.rotationPitch;
		
		double[] tz = zFunc(yaw, pitch, z_offset);
		double[] ty = zFunc(yaw, pitch - 90, y_offset);
		double[] tx = zFunc(yaw + 90, 0, x_offset);
		
		x += tz[0] + ty[0] + tx[0];
		y += tz[1] + ty[1] + tx[1];
		z += tz[2] + ty[2] + tx[2];
		
		return new double[] {x, y, z};
		
	}
	
	public static double HLO_X(Entity player, double x_offset, double y_offset, double z_offset, double ticks) {
		
		return HLO_XYZ(player, x_offset, y_offset, z_offset, ticks)[0];
		
	}
	
	public static double HLO_Y(Entity player, double x_offset, double y_offset, double z_offset, double ticks) {
		
		return HLO_XYZ(player, x_offset, y_offset, z_offset, ticks)[1];
		
	}
	
	public static double HLO_Z(Entity player, double x_offset, double y_offset, double z_offset, double ticks) {
		
		return HLO_XYZ(player, x_offset, y_offset, z_offset, ticks)[2];
		
	}
	
	public static void HLO_vert(Entity player, double x_offset, double y_offset, double z_offset, double ticks) {
		double[] hlo = HLO_XYZ(player, x_offset, y_offset, z_offset, ticks);
		GL11.glVertex3d(hlo[0], hlo[1], hlo[2]);
	}
	
	public static double[] HLO_XYZ_ACCURATE(Entity player, double x_offset, double y_offset, double z_offset, double ticks) {
		
		double x = (double)(player.prevPosX + (player.posX - player.prevPosX) * ticks);
		double y = (double)(player.prevPosY + (player.posY - player.prevPosY) * ticks) + 1.5f;
		double z = (double)(player.prevPosZ + (player.posZ - player.prevPosZ) * ticks);
		
		if (player.isSneaking()) y -= 0.3f;
		
		double pitch = player.rotationPitch;
		double yaw = player.rotationYaw;
		
		double[] tz = zFunc(yaw, pitch, z_offset);
		double[] ty = zFunc(yaw, pitch - 90, y_offset);
		double[] tx = zFunc(yaw + 90, 0, x_offset);
		
		x += tz[0] + ty[0] + tx[0];
		y += tz[1] + ty[1] + tx[1];
		z += tz[2] + ty[2] + tx[2];
		
		return new double[] {x, y, z};
		
	}
	
	public static double HLO_X_ACCURATE(Entity player, double x_offset, double y_offset, double z_offset, double ticks) {
		
		return HLO_XYZ_ACCURATE(player, x_offset, y_offset, z_offset, ticks)[0];
		
	}
	
	public static double HLO_Y_ACCURATE(Entity player, double x_offset, double y_offset, double z_offset, double ticks) {
		
		return HLO_XYZ_ACCURATE(player, x_offset, y_offset, z_offset, ticks)[1];
		
	}
	
	public static double HLO_Z_ACCURATE(Entity player, double x_offset, double y_offset, double z_offset, double ticks) {
		
		return HLO_XYZ_ACCURATE(player, x_offset, y_offset, z_offset, ticks)[2];
		
	}
	
	public static double[] HLO_XYZ_CPY(Entity player, double x_offset, double y_offset, double z_offset, double yaw, double pitch, double ticks) {
		
		double x = (double)(player.prevPosX + (player.posX - player.prevPosX) * ticks);
		double y = (double)(player.prevPosY + (player.posY - player.prevPosY) * ticks) + 1f;
		double z = (double)(player.prevPosZ + (player.posZ - player.prevPosZ) * ticks);
		
		double[] tz = zFunc(yaw, pitch, z_offset);
		double[] ty = zFunc(yaw, pitch - 90, y_offset);
		double[] tx = zFunc(yaw + 90, 0, x_offset);
		
		x += tz[0] + ty[0] + tx[0];
		y += tz[1] + ty[1] + tx[1];
		z += tz[2] + ty[2] + tx[2];
		
		return new double[] {x, y, z};
		
	}
	
	public static double HLO_X_CPY(Entity player, double x_offset, double y_offset, double z_offset, double yaw, double pitch, double ticks) {
		
		return HLO_XYZ_CPY(player, x_offset, y_offset, z_offset, yaw, pitch, ticks)[0];
		
	}
	
	public static double HLO_Y_CPY(Entity player, double x_offset, double y_offset, double z_offset, double yaw, double pitch, double ticks) {
		
		return HLO_XYZ_CPY(player, x_offset, y_offset, z_offset, yaw, pitch, ticks)[1];
		
	}
	
	public static double HLO_Z_CPY(Entity player, double x_offset, double y_offset, double z_offset, double yaw, double pitch, double ticks) {
		
		return HLO_XYZ_CPY(player, x_offset, y_offset, z_offset, yaw, pitch, ticks)[2];
		
	}
	
	public static double[] HLO_XYZ_CXYZPY(double x_offset, double y_offset, double z_offset, double x, double y, double z, double yaw, double pitch) {
		
		double[] tz = zFunc(yaw, pitch, z_offset);
		double[] ty = zFunc(yaw, pitch - 90, y_offset);
		double[] tx = zFunc(yaw + 90, 0, x_offset);
		
		x += tz[0] + ty[0] + tx[0];
		y += tz[1] + ty[1] + tx[1];
		z += tz[2] + ty[2] + tx[2];
		
		return new double[] {x, y, z};
		
	}
	
	public static double HLO_X_CXYZPY(double x_offset, double y_offset, double z_offset, double x, double y, double z, double yaw, double pitch) {
		
		return HLO_XYZ_CXYZPY(x_offset, y_offset, z_offset, x, y, z, yaw, pitch)[0];
		
	}
	
	public static double HLO_Y_CXYZPY(double x_offset, double y_offset, double z_offset, double x, double y, double z, double yaw, double pitch) {
		
		return HLO_XYZ_CXYZPY(x_offset, y_offset, z_offset, x, y, z, yaw, pitch)[1];
		
	}
	
	public static double HLO_Z_CXYZPY(double x_offset, double y_offset, double z_offset, double x, double y, double z, double yaw, double pitch) {
		
		return HLO_XYZ_CXYZPY(x_offset, y_offset, z_offset, x, y, z, yaw, pitch)[2];
		
	}
	
	public static void HLO_CXYZPY_vert(double x_offset, double y_offset, double z_offset, double x, double y, double z, double yaw, double pitch) {
		double[] hlo = HLO_XYZ_CXYZPY(x_offset, y_offset, z_offset, x, y, z, yaw, pitch);
		GL11.glVertex3d(hlo[0], hlo[1], hlo[2]);
	}
	
	public static double[] HLO_XY_CXYY(double x_offset, double y_offset, double x, double y, double yaw) {
		
		yaw = (double)Math.toRadians(yaw);
		
		yaw += Math.PI / 2;
		
		y += Math.sin(yaw) * y_offset;
		x += Math.cos(yaw) * y_offset;
		
		yaw += Math.PI / 2;
		
		y += Math.sin(yaw) * x_offset;
		x += Math.cos(yaw) * x_offset;
		
		return new double[] {x, y};
		
	}
	
	public static double HLO_X_CXYY(double x_offset, double y_offset, double x, double y, double yaw) {
		
		return HLO_XY_CXYY(x_offset, y_offset, x, y, yaw)[0];
		
	}
	
	public static double HLO_Y_CXYY(double x_offset, double y_offset, double x, double y, double yaw) {
		
		return HLO_XY_CXYY(x_offset, y_offset, x, y, yaw)[1];
		
	}
	
}
