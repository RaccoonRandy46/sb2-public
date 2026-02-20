package com.coop.skyblock2.listeners.dungeons.solvers;

import org.lwjgl.opengl.GL11;

import com.coop.skyblock2.utils.MagicRenderer;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.text.ChatType;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ThreeWeirdosListener {

	private static String correctName = null;
	
	@SubscribeEvent
	public void chatReceived(ClientChatReceivedEvent e) {
		
		if (!Utils.isInDungeons()) return;
		
		if (e.getType() != ChatType.CHAT) return;
		
		try {
			
			String message = Utils.removeColorcodes(e.getMessage().getFormattedText());
			
			if (!message.startsWith("[NPC] ")) return;

			if (!message.contains(":")) return;
			
			String name = message.split(" ")[1];
			name = name.split(":")[0];
			
			String phrase = message.split(": ")[1];
			
			if (!isCorrectPhrase(phrase)) return;
			
			correctName = name;
		
		} catch (Exception E) {}
		
	}
	
	@SubscribeEvent
	public void armorStandRendered(RenderWorldLastEvent e) {
		
		if (!Utils.isInDungeons()) return;
		
		if (correctName == null) return;
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		Entity entity = null;

		for (Entity currentEntity : p.world.getLoadedEntityList()) {
			
			if (!(currentEntity instanceof EntityArmorStand)) continue;
			if (!Utils.removeColorcodes(currentEntity.getName()).equals(correctName)) continue;
			
			if (Math.abs(p.posX - currentEntity.posX) > 6) continue;
			if (Math.abs(p.posY - currentEntity.posY) > 6) continue;
			if (Math.abs(p.posZ - currentEntity.posZ) > 6) continue;
			
			entity = currentEntity;
			break;
			
		}
		
		if (entity == null) return;
	  
		float x = (float)entity.posX;
		float y = (float)entity.posY;
		float z = (float)entity.posZ;
		
		MagicRenderer renderer = new MagicRenderer();
		renderer.autoTranslate(e.getPartialTicks());
		
		renderer.disableDepth();
		renderer.color(0, 255, 0);
		renderer.lineWidth(3);
		
		renderer.begin(GL11.GL_LINES);
		
		renderer.vc(x + -0.5f, y + 0, z + -0.5f);
		renderer.vc(x + -0.5f, y + 0, z + 0.5f);
		renderer.vc(x + -0.5f, y + 0, z + 0.5f);
		renderer.vc(x + 0.5f, y + 0, z + 0.5f);
		renderer.vc(x + 0.5f, y + 0, z + 0.5f);
		renderer.vc(x + 0.5f, y + 0, z + -0.5f);
		renderer.vc(x + 0.5f, y + 0, z + -0.5f);
		renderer.vc(x + -0.5f, y + 0, z + -0.5f);

		renderer.vc(x + -0.5f, y + 2, z + -0.5f);
		renderer.vc(x + -0.5f, y + 2, z + 0.5f);
		renderer.vc(x + -0.5f, y + 2, z + 0.5f);
		renderer.vc(x + 0.5f, y + 2, z + 0.5f);
		renderer.vc(x + 0.5f, y + 2, z + 0.5f);
		renderer.vc(x + 0.5f, y + 2, z + -0.5f);
		renderer.vc(x + 0.5f, y + 2, z + -0.5f);
		renderer.vc(x + -0.5f, y + 2, z + -0.5f);

		renderer.vc(x + -0.5f, y + 0, z + -0.5f);
		renderer.vc(x + -0.5f, y + 2, z + -0.5f);
		renderer.vc(x + -0.5f, y + 0, z + 0.5f);
		renderer.vc(x + -0.5f, y + 2, z + 0.5f);
		renderer.vc(x + 0.5f, y + 0, z + 0.5f);
		renderer.vc(x + 0.5f, y + 2, z + 0.5f);
		renderer.vc(x + 0.5f, y + 0, z + -0.5f);
		renderer.vc(x + 0.5f, y + 2, z + -0.5f);
		
		renderer.draw();
		renderer.enableDepth();
		renderer.end();
		
	}
	
	@SubscribeEvent
	public void loadWorld(WorldEvent.Load e) {
		reset();
	}
	
	public static void reset() {
		correctName = null;
	}
	
	private static boolean isCorrectPhrase(String s) {
		
		if (s.equals("The reward is not in my chest!")) return true;
		if (s.startsWith("At least one of them is lying, and the reward is not in ")) return true;
		if (s.equals("My chest doesn't have the reward. We are all telling the truth.")) return true;
		if (s.startsWith("Both of them are telling the truth. Also, ")) return true;
		if (s.equals("The reward isn't in any of our chests.")) return true;
		if (s.equals("My chest has the reward and I'm telling the truth!")) return true;
		
		return false;
		
	}
	
}
