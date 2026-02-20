package com.coop.skyblock2.listeners.dungeons.solvers.quiz;

import org.lwjgl.opengl.GL11;

import com.coop.skyblock2.utils.Debug;
import com.coop.skyblock2.utils.MagicRenderer;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class QuizListener {

	private static QuizAnswer answer = null;
	private static boolean complete = false;
	
	@SubscribeEvent
	public void chatGet(ClientChatReceivedEvent e) {
		
		if (!Utils.isInDungeons()) return;
		if (complete) return;
		
		String msg = e.getMessage().getFormattedText().replace('\247', '&');
		
		if (msg.endsWith("&r&aanswered the final question correctly!&r")) {
			complete = true;
			Debug.sendDungeonsDebug("quiz complete");
			return;
		}
		
		boolean startsWithBool = msg.startsWith(" ") || msg.startsWith("&f ");
		if (startsWithBool && msg.contains("&r&6&lQuestion #")) {
			Debug.sendDungeonsDebug("reset answer");
			answer = null;
		}
		
		if (answer != null) return;
		
		if (!msg.endsWith("?&r")) return;
		if (!msg.contains("&r&6")) return;
		msg = msg.substring(msg.indexOf("&r&6") + 4, msg.length() - 2);
		msg = msg.toLowerCase();
		
		Debug.sendDungeonsDebug("question: [" + msg + "]");
		QuizAnswer currentAnswer = QuizAnswerCache.getAnswer(msg);
		if (currentAnswer == null) return;
		
		answer = currentAnswer;
		Debug.sendDungeonsDebug("saved answer: " + answer.toString());
		
	}
	
	@SubscribeEvent
	public void renderWorld(RenderWorldLastEvent e) {
		
		if (answer == null) return;
		if (complete) return;
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		EntityArmorStand armorStand = null;
		
		for (Entity entity : p.world.getLoadedEntityList()) {
			
			if (!(entity instanceof EntityArmorStand)) continue;
			if (Math.abs(entity.posX - p.posX) > 17) continue;
			if (Math.abs(entity.posY - p.posY) > 17) continue;
			if (Math.abs(entity.posZ - p.posZ) > 17) continue;
			
			String name = Utils.removeColorcodes(entity.getCustomNameTag());
			if (name.length() < 4) continue;
			
			int checkID = (int)name.charAt(1);
			if (checkID < 9424 || checkID > 9426) continue;
			
			name = name.substring(3);
			name = name.toLowerCase();
			name = name.replace(".", "");
			
			if (answer.equals(name)) {
				armorStand = (EntityArmorStand)entity;
				break;
			}
			
			if (!answer.contains(name)) continue;
			
			armorStand = (EntityArmorStand)entity;
			
		}
		
		if (armorStand == null) return;
		
		int[] color = answer.color;
		
		MagicRenderer renderer = new MagicRenderer();
		renderer.autoTranslate(e.getPartialTicks());
		
		renderer.disableDepth();
		renderer.lineWidth(3);
		renderer.color(color[0], color[1], color[2]);
		
		renderer.begin(GL11.GL_LINES);
		
		float x = (float)armorStand.posX;
		float y = (float)armorStand.posY + 2.5f;
		float z = (float)armorStand.posZ;
		
		double spinMult = System.currentTimeMillis() % 1000;
		spinMult /= 1000;
		float yaw = (float)spinMult * 360;
		spinMult *= Math.PI * 2;
		spinMult = Math.sin(spinMult);
		spinMult++;
		spinMult /= 4;
		y += spinMult;
		
		renderer.hlo(0, 0, 0, x, y, z, yaw, 0);
		renderer.hlo(0, 1.5f, 0, x, y, z, yaw, 0);
		
		renderer.hlo(0, 0, 0, x, y, z, yaw, 0);
		renderer.hlo(-0.5f, 0.5f, 0, x, y, z, yaw, 0);
		
		renderer.hlo(0, 0, 0, x, y, z, yaw, 0);
		renderer.hlo(0.5f, 0.5f, 0, x, y, z, yaw, 0);
		
		renderer.draw();
		renderer.enableDepth();
		renderer.end();
		
	}
	
	@SubscribeEvent
	public void loadWorld(WorldEvent.Load e) {
		reset();
	}
	
	public static void reset() {
		answer = null;
		complete = false;
	}
	
}
