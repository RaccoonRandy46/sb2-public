package com.coop.skyblock2.listeners.slayers.voidgloom;

import org.lwjgl.opengl.GL11;

import com.coop.skyblock2.utils.MagicRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HeadFindListener {
	
	@SubscribeEvent
	public void trackHeads(RenderWorldLastEvent e) {

		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		MagicRenderer renderer = new MagicRenderer();

		renderer.autoTranslate(e.getPartialTicks());
		renderer.lineWidth(3);
		renderer.disableDepth();
		
		for (Entity entity : p.world.loadedEntityList) {
			
			if (entity instanceof EntityPlayer) continue; 
			if (Math.abs(entity.posX - p.posX) > 20) continue;
			if (Math.abs(entity.posY - p.posY) > 20) continue;
			if (Math.abs(entity.posZ - p.posZ) > 20) continue;
			
			boolean hasHead = false;
			String key = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWIwNzU5NGUyZGYyNzM5MjFhNzdjMTAxZDBiZmRmYTExMTVhYmVkNWI5YjIwMjllYjQ5NmNlYmE5YmRiYjRiMyJ9fX0=";
			for (ItemStack item : entity.getArmorInventoryList()) {
				if (item.getItem().equals(Items.SKULL)) {
					
					try {
					
						if (item.getTagCompound().getTag("SkullOwner").toString().contains(key)) {
							hasHead = true;
							break;
						}
					
					} catch (Exception E) {}
					
				}
			}
			if (!hasHead) continue;
			
			drawHeadBox(entity, renderer, e.getPartialTicks());
			
		}
		
		renderer.enableDepth();
        renderer.end();
		
	}
	
	private static void drawHeadBox(Entity entity, MagicRenderer renderer, float ticks) {
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		float translatex = (float)(p.prevPosX + (p.posX - p.prevPosX) * ticks);
		float translatey = (float)(p.prevPosY + (p.posY - p.prevPosY) * ticks);
		float translatez = (float)(p.prevPosZ + (p.posZ - p.prevPosZ) * ticks);
		
		float x = (float)(entity.prevPosX + (entity.posX - entity.prevPosX) * ticks);
		float y = (float)(entity.prevPosY + (entity.posY - entity.prevPosY) * ticks);
		float z = (float)(entity.prevPosZ + (entity.posZ - entity.prevPosZ) * ticks);
		y += entity.getEyeHeight();
		y += 1f;
		
		float width = 0.923f;
		
		x -= width / 2;
		y -= width / 2;
		z -= width / 2;
		
		renderer.begin(GL11.GL_LINES);

		renderer.color(255, 38, 38);
		
		renderer.vc(translatex, translatey + 1, translatez);
		renderer.vc(x + width / 2, y + width / 2, z + width / 2);
		
		renderer.draw();
		
		renderer.begin(GL11.GL_QUADS);
		
		renderer.color(255, 38, 38, 160);
		
		renderer.vc(x, y, z);
		renderer.vc(x, y, z + width);
		renderer.vc(x, y + width, z + width);
		renderer.vc(x, y + width, z);
		
		renderer.vc(x + width, y, z);
		renderer.vc(x, y, z);
		renderer.vc(x, y + width, z);
		renderer.vc(x + width, y + width, z);
		
		renderer.vc(x + width, y, z + width);
		renderer.vc(x + width, y, z);
		renderer.vc(x + width, y + width, z);
		renderer.vc(x + width, y + width, z + width);
		
		renderer.vc(x, y, z + width);
		renderer.vc(x + width, y, z + width);
		renderer.vc(x + width, y + width, z + width);
		renderer.vc(x, y + width, z + width);
		
		renderer.vc(x, y + width, z + width);
		renderer.vc(x + width, y + width, z + width);
		renderer.vc(x + width, y + width, z);
		renderer.vc(x, y + width, z);
		
		renderer.vc(x, y, z);
		renderer.vc(x + width, y, z);
		renderer.vc(x + width, y, z + width);
		renderer.vc(x, y, z + width);
		
		renderer.draw();
		
	}
	
}
