package com.coop.skyblock2.utils.skyblock;

import java.util.Set;

import com.coop.skyblock2.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;

public class SBItemData {

	public static boolean isSkyblockItem(ItemStack itemStack) {
		
		NBTTagCompound nbt = itemStack.getTagCompound();
		
		if (nbt == null) return false;
		
		return (nbt.hasKey("ExtraAttributes"));
		
	}
	
	public static NBTTagCompound getItemData(ItemStack itemStack) {
		
		NBTTagCompound nbt = itemStack.getTagCompound();
		
		if (nbt == null) return null;
		if (!nbt.hasKey("ExtraAttributes")) return null;
		
		return nbt.getCompoundTag("ExtraAttributes");
		
	}
	
	public static void printItemData(ItemStack itemStack) {
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		if (!isSkyblockItem(itemStack)) {
			p.sendMessage(new TextComponentString(Utils.chat("&a&oItem data:\n&c&onot a skyblock item!")));
			return;
		}
		
		String data = "";
		NBTTagCompound tags = getItemData(itemStack);
		
		if (tags == null) return;
		
		for (String key : tags.getKeySet()) {
			
			byte tagId = tags.getTagId(key);
			String newLine = Utils.chat("&7&o" + key + " -> " + NBTBase.getTagTypeName(tagId));
			if (data.equals("")) data = newLine;
			else data += "\n" + newLine;
			
		}
		
		p.sendMessage(new TextComponentString(Utils.chat("&a&oItem data:\n" + data)));
		
	}
	
	public static String getID(ItemStack itemStack) {
		
		if (!isSkyblockItem(itemStack)) return "UNKNOWN";
		
		NBTTagCompound tags = getItemData(itemStack);
		if (itemStack.getItem() != Items.ENCHANTED_BOOK) return tags.getString("id");
		
		NBTTagCompound enchantments = tags.getCompoundTag("enchantments");
		if (enchantments.getSize() > 1) return tags.getString("id");
		
		String enchantmentName = "???";
		Set<String> enchantmentSet = enchantments.getKeySet();
		for (String enchantment : enchantmentSet) enchantmentName = enchantment;
		
		int level = enchantments.getInteger(enchantmentName);
		
		return "ENCHANTMENT_" + enchantmentName.toUpperCase() + "_" + level;
		
	}
	
}
