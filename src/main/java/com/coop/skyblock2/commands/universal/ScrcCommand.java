package com.coop.skyblock2.commands.universal;

import com.coop.skyblock2.Main;
import com.coop.skyblock2.listeners.dungeons.secrets.SecretUtils;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ScrcCommand {

	public static int originX = -1;
	public static int originZ = -1;
	public static int direction = -1;
	
	@SubscribeEvent
	public void chatGo(ClientChatEvent e) {
		
		if (!Main.universal) return;
		
		String[] args = e.getMessage().split(" ");
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		if (args[0].equals("s>scrc")) {
			
			if (!Utils.isInDungeons()) {
				p.sendMessage(new TextComponentString(Utils.chat("&cyou are not in dungeons")));
				return;
			}
			
			int dir = 1;
			
			if (args.length > 1) {
				
				try {
					dir = Integer.parseInt(args[1]);
				} catch (Exception E) {
					p.sendMessage(new TextComponentString(Utils.chat("&cinvalid direction")));
					return;
				}
				
			}
			
			if (dir < 1 || dir > 4) {
				p.sendMessage(new TextComponentString(Utils.chat("&cinvalid direction")));
				return;
			}
			
			direction = dir;
			
			int[] cellXZ = SecretUtils.getRoomCell();
			int[] originXZ = SecretUtils.getRoomOrigin(cellXZ[0], cellXZ[1], dir);
			originX = originXZ[0];
			originZ = originXZ[1];
			
			String roomcode = SecretUtils.getUnfinalizedRoomCode(cellXZ[0], cellXZ[1], dir);
			
			GuiScreen.setClipboardString(roomcode);
			p.sendMessage(new TextComponentString(Utils.chat("&b&lroom code (" + SecretUtils.directionToString(dir) + ")\n&d" + roomcode + "\n&8&o(copied to clipboard)")));
			
		}
		
	}
	
}
