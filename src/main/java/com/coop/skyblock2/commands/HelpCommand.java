package com.coop.skyblock2.commands;

import java.util.ArrayList;
import java.util.List;

import com.coop.skyblock2.Main;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HelpCommand {

	private static List<String> helpList = new ArrayList<>();
	private static boolean init = false;
	
	@SubscribeEvent
	public void getChat(ClientChatEvent e) {
		
		if (e.getMessage().startsWith("s>")) {
			
			int page = 0;
			
			if (e.getMessage().split("s>").length > 1) {
				try {
					page = Integer.parseInt(e.getMessage().split("s>")[1])-1;
				} catch (Exception E) {return;}
			} else if (!e.getMessage().equals("s>")) return;
			if (page < 0) return;
			
			if (!init) initList();
			
			String out = "\n&8>&7+&8> [&6!&8] &6rpic help " + (page+1) + " &8[&6!&8] <&7+&8<";
			
			for (int i = page*8; i <= (page*8)+7; i++) {
				if (helpList.size() <= i) {break;}
				out += "\n&b" + helpList.get(i).split(";")[0] + " &7- " + helpList.get(i).split(";")[1];
			}
			
			if (helpList.size() <= (page+1)*8) out += "\n&d&oMore coming soon! &8&o(maybe idk lmao)\n&r";
			else out += "\n&6\"s>" + (page+2) + "\" for next page\n&r";
			
			out = Utils.chat(out);
			
			Minecraft.getMinecraft().player.sendMessage(new TextComponentString(out));
			
		}
		
	}
	
	private static void initList() {
		
		init = true;
		
		helpList.add("s>showfels;shows where fels in dungeons will spawn");
		helpList.add("s>showsecrets;shows the locations of some dungeons secrets");
		helpList.add("s>autocycle;toggles between automatic and manual secret cycling");
		helpList.add("s>dmap;toggles minimap for dungeons");
		helpList.add("s>chestscam;displays profits in dungeon reward chests");
		helpList.add("s>dclass;change ur dungeon class");
		helpList.add("s>iid;manually tell the mod that you are in dungeons");
		helpList.add("s>starhighlight;always highlight starred mobs in dungeons");
		helpList.add("s>abreload;reloads auction and bazaar data");
		helpList.add("s>sbdate;tells you the current skyblock date");
		helpList.add("s>update;updates &leverything");
		helpList.add("s>debug;enable/disable debug messages");
		//helpList.add("s>;");
		
		if (!Main.universal) return;
		
		helpList.add("s>fd;fd");
		helpList.add("s>sccb;sccb");
		helpList.add("s>scesp;scesp");
		helpList.add("s>scrc;scrc");
		helpList.add("s>scredo;scredo");
		
	}
	
}
