package com.coop.skyblock2.commands.dungeons;

import java.io.File;
import java.io.IOException;

import com.coop.skyblock2.utils.FileManager;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DungeonClassCommand {

	public enum EnumClass {
		HEALER,
		TANK,
		ARCHER,
		BERSERK,
		MAGE
	}
	
	public static EnumClass dungeonClass = EnumClass.HEALER;
	
	@SubscribeEvent
	public void chatGo(ClientChatEvent e) {
		
		String[] args = e.getMessage().split(" ");
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		if (args[0].equals("s>dclass")) {
			
			if (args.length < 2) {
				p.sendMessage(new TextComponentString(Utils.chat("&5Your current class is set to &d&l" + classToString(dungeonClass) + "\n&8&oto change your class, use: s>dclass <class>")));
				return;
			}
			
			String clazz = args[1];
			
			EnumClass newClass = classFromString(clazz);
			
			if (newClass == null) {
				p.sendMessage(new TextComponentString(Utils.chat("&cunknown class")));
				return;
			}
			
			dungeonClass = newClass;
			
			p.sendMessage(new TextComponentString(Utils.chat("&aSet class to &d" + classToString(dungeonClass) + "&a!")));
			
			try {
				FileManager.writeFile("dungeons", "dungeonclass", classToString(dungeonClass));
			} catch (IOException E) {}

		}
		
	}
	
	private static EnumClass classFromString(String s) {
		
		s = s.toLowerCase();
		
		if (s.equals("healer") || s.equals("heal") || s.equals("h")) return EnumClass.HEALER;
		
		if (s.equals("tank") || s.equals("t")) return EnumClass.TANK;
		
		if (s.equals("archer") || s.equals("arch") || s.equals("a")) return EnumClass.ARCHER;
		
		if (s.equals("berzerk") || s.equals("berz") || s.equals("berserk") || s.equals("bers") || s.equals("b")) return EnumClass.BERSERK;
		
		if (s.equals("mage") || s.equals("m")) return EnumClass.MAGE;
		
		return null;
		
	}
	
	public static String classToString(EnumClass clazz) {
		
		switch (clazz) {
		case HEALER:
			return "Healer";
		case TANK:
			return "Tank";
		case ARCHER:
			return "Archer";
		case BERSERK:
			return "Berserk";
		case MAGE:
			return "Mage";
		}
		
		return null;
		
	}
	
	@SubscribeEvent
	public void loadWorld(WorldEvent.Load e) {
		
		try {
			
			File dclass = new File("mods/sb2/dungeons/dungeonclass.cccm");
			if (dclass.exists()) dungeonClass = classFromString(FileManager.readLine("mods/sb2/dungeons/dungeonclass.cccm"));
			
		} catch (Exception E) {}
		
	}
	
}
