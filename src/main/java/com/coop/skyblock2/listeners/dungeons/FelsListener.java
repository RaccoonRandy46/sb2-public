package com.coop.skyblock2.listeners.dungeons;

import java.util.ArrayList;
import java.util.List;

import com.coop.skyblock2.commands.dungeons.FelsCommand;
import com.coop.skyblock2.utils.FileManager;
import com.coop.skyblock2.utils.MagicRenderer;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FelsListener {

	private static boolean updated = false;
	
	private static final ResourceLocation ALERT_TEXTURE = new ResourceLocation("sb2", "dungeons/fels_alert.png");
	
	@SubscribeEvent
	public void renderWorld(RenderWorldLastEvent e) {
		
		if (!updated) updateSettings();
		if (!FelsCommand.enabled) return;
		if (!Utils.isInDungeons()) return;
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		List<Entity> entities = p.world.loadedEntityList;
		ArrayList<double[]> felsLocations = new ArrayList<>();
		
		for (Entity entity : entities) {
			
			if (!(entity instanceof EntityArmorStand)) continue;
			
			EntityArmorStand armorStand = (EntityArmorStand)entity;
			boolean isFels = false;
			
			for (ItemStack item : armorStand.getArmorInventoryList()) {
				
				String key = "dbSGvkxW0KP5zN07KG6DHEwj4uG/jsvykgeKyodC9vs5wjBB9lKeBcm97XUKxqCJaEy1POhHgZjAs3uBYZWX1QXMDhvZwMK1lJBdS9xEPSuzYHLbPiPNk7JQ/w+/bv/WYOBPh/iUjC7XTk5O6iEt/e4YgrNiDO10A7cE9iwf59bYnE0hWg/s3wIe28BxdC8qmOj6oNO6SCtzQpemsCJyJDXapglFD8RV6XPc60yto0Y2ITrcYHA4KXAA3qYBQC1Gu7ttu+UyPwXrCXYHbJlSucVf1+Wq+mwo638Fn0pZtDV4od7//UNuhTrXbgLE4GxXSPIXh22z70I4n4qrJtMF+hjLqil4gm71NNreuaewqnhjKjFwyhoomHHHkIqRehTu+Xb7rDuzJcooXEpLDMAFss4PJXrOw9NMYLtHlzaE7Ugnv/nRNpUXKwX+LiwjaH8vifH75uFu92eoZcFpUZJtj7hlrqSU+uIH9SEImbPBVdKpuabk+oHuPdCbdhuG0kED3w+3DnvoToFGadIMZgL9Ywh4m9bA+9NdPZ3XWqgK2RCVMLbYgwXOxpg75BvE5qNIv9C62W74+z3DLb+k72eyNza3Z5dHfcNiFXZNC4JrIfye061aWw8pmXqqqieUscaQBsTywcYBnsqvNQ9rgSZowxKUXrJ0RG/PwxEGtLzWDcE=\",Value:\"ewogICJ0aW1lc3RhbXAiIDogMTcyMDAyNTQ4Njg2MywKICAicHJvZmlsZUlkIiA6ICIzZDIxZTYyMTk2NzQ0Y2QwYjM3NjNkNTU3MWNlNGJlZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJTcl83MUJsYWNrYmlyZCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9jMjg2ZGFjYjBmMjE0NGQ3YTQxODdiZTM2YmJhYmU4YTk4ODI4ZjdjNzlkZmY1Y2UwMTM2OGI2MzAwMTU1NjYzIiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=";
				
				if (item.getItem().equals(Items.SKULL)) {
					
					try {
						if (item.getTagCompound().getTag("SkullOwner").toString().contains(key)) {
							isFels = true;
							break;
						}
					} catch (Exception E) {}
					
				}
				
			}
			
			if (isFels) felsLocations.add(new double[] {armorStand.posX, armorStand.posY + 2.25, armorStand.posZ});
			
		}
		
		if (felsLocations.size() == 0) return;
        
        MagicRenderer renderer = new MagicRenderer();
        renderer.autoTranslate(e.getPartialTicks());
        renderer.disableDepth();
        renderer.beginTex(ALERT_TEXTURE);
		renderer.phase(0);
        
		for (double[] loc : felsLocations) alertFel(loc[0], loc[1], loc[2], renderer);
		
        renderer.draw();
        renderer.enableDepth();
        renderer.end();

	}
	
	public static void alertFel(double x, double y, double z, MagicRenderer renderer) {
		
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
	
	public static void updateSettings() {
		
		updated = true;
		
		try {
			
			FelsCommand.enabled = Boolean.parseBoolean(FileManager.readLine("mods/sb2/dungeons/fels.cccm"));
			
		} catch (Exception E) {}
		
	}
	
}
