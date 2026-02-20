package com.coop.skyblock2.commands.universal;

import com.coop.skyblock2.Main;
import com.coop.skyblock2.listeners.dungeons.secrets.SecretUtils;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SccbCommand {

	@SubscribeEvent
	public void chatGo(ClientChatEvent e) {
		
		if (!Main.universal) return;
		
		String[] args = e.getMessage().split(" ");
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		if (args[0].equals("s>sccb")) {
			
			if (!Utils.isInDungeons()) {
				p.sendMessage(new TextComponentString(Utils.chat("&cyou are not in dungeons")));
				return;
			}
			
			if (ScrcCommand.originX == -1 || ScrcCommand.originZ == -1) {
				p.sendMessage(new TextComponentString(Utils.chat("&cmaster cell has not been selected.\n&cselect a master cell with s>scrc")));
				return;
			}
			
			if (Minecraft.getMinecraft().objectMouseOver.typeOfHit != RayTraceResult.Type.BLOCK) {
				p.sendMessage(new TextComponentString(Utils.chat("&cno block selected")));
				return;
			}
			
			BlockPos pos = Minecraft.getMinecraft().objectMouseOver.getBlockPos();
			int[] txz = getTranslatedOffsetXZ(pos.getX(), pos.getZ(), ScrcCommand.direction);
			
			String coordinates = txz[0] + ", " + pos.getY() + ", " + txz[1];
			GuiScreen.setClipboardString(coordinates);
			
			p.sendMessage(new TextComponentString(Utils.chat("&b&otranslated coordinates\n&d" + coordinates + "\n&8&o(copied to clipboard)")));
			
		}
		
	}
	
	private static int[] getTranslatedOffsetXZ(int x, int z, int direction) {
		
		switch (direction) {
		case SecretUtils.POS_Z:
			return new int[] {ScrcCommand.originX - x, z - ScrcCommand.originZ};
		case SecretUtils.NEG_Z:
			return new int[] {x - ScrcCommand.originX, ScrcCommand.originZ - z};
		case SecretUtils.POS_X:
			return new int[] {z - ScrcCommand.originZ, x - ScrcCommand.originX};
		case SecretUtils.NEG_X:
			return new int[] {ScrcCommand.originZ - z, ScrcCommand.originX - x};
		}
		
		return new int[] {0, 0};
		
	}
	
}
