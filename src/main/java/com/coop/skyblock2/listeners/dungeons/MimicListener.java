package com.coop.skyblock2.listeners.dungeons;

import java.util.ArrayList;
import java.util.List;

import com.coop.skyblock2.utils.MagicRenderer;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.block.BlockChest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MimicListener {
	
	private static final ResourceLocation ALERT_TEXTURE = new ResourceLocation("sb2", "dungeons/chest_alert.png");
	
	@SubscribeEvent
	public void renderWorld(RenderWorldLastEvent e) {
		
		if (!Utils.isInDungeons()) return;
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		List<TileEntity> entities = p.world.loadedTileEntityList;
		ArrayList<double[]> chestLocations = new ArrayList<>();
		
		for (TileEntity entity : entities) {
			
			if (!(entity instanceof TileEntityChest)) continue;
			TileEntityChest chest = (TileEntityChest)entity;
			if (chest.getChestType() != BlockChest.Type.TRAP) continue;
			
			BlockPos pos = chest.getPos();
			chestLocations.add(new double[] {pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5});
			
		}
		
		if (chestLocations.size() == 0) return;
        
        MagicRenderer renderer = new MagicRenderer();
        renderer.autoTranslate(e.getPartialTicks());
        renderer.disableDepth();
        renderer.beginTex(ALERT_TEXTURE);
		renderer.phase(0);
        
		for (double[] loc : chestLocations) alertMimic(loc[0], loc[1], loc[2], renderer);
		
        renderer.draw();
        renderer.enableDepth();
        renderer.end();

	}
	
	public static void alertMimic(double x, double y, double z, MagicRenderer renderer) {
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		double width = 0.5;
		double height = 1.5;
        
        float yaw = p.rotationYaw;
        float pitch = p.rotationPitch;
		
        renderer.hlo((float)(width / 2), (float)height, 0f, (float)x, (float)y, (float)z, yaw, pitch);
        renderer.hlo((float)(-width / 2), (float)height, 0f, (float)x, (float)y, (float)z, yaw, pitch);
        renderer.hlo((float)(-width / 2), 0f, 0f, (float)x, (float)y, (float)z, yaw, pitch);
        renderer.hlo((float)(width / 2), 0f, 0f, (float)x, (float)y, (float)z, yaw, pitch);
        
	}
	
}
