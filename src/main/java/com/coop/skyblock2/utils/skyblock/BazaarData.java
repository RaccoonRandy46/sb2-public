package com.coop.skyblock2.utils.skyblock;

import com.coop.skyblock2.hypixel.api.reply.skyblock.SkyBlockBazaarReply;
import com.coop.skyblock2.hypixel.api.util.HypixelApiAccessor;

import net.minecraft.item.ItemStack;

public class BazaarData {

	public static SkyBlockBazaarReply bazaar = null;
	
	public static void reloadData() {

		try {
			bazaar = HypixelApiAccessor.API.getSkyBlockBazaar().get();
		} catch (Exception E) {}
		
	}
	
	public static boolean isBazaarItem(ItemStack stack) {
		return bazaar.getProducts().containsKey(SBItemData.getID(stack));
	}
	
}
