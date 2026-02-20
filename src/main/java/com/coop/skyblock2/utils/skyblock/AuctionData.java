package com.coop.skyblock2.utils.skyblock;

import java.util.ArrayList;

import com.coop.skyblock2.hypixel.api.reply.skyblock.SkyBlockAuctionsReply;
import com.coop.skyblock2.hypixel.api.util.HypixelApiAccessor;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class AuctionData {

	private static boolean fetchedData = false;
	public static long cooldown = 0;
	
	public static ArrayList<JsonArray> allAuctionPages = new ArrayList<>();
	public static ArrayList<JsonObject> allBIN = new ArrayList<>();
	public static ArrayList<JsonObject> allNonBIN = new ArrayList<>();
	
	public static void reloadData() {
		
		cooldown = System.currentTimeMillis();
		
		Thread t = new Thread() {
			public void run() {
			
				try {
					
					allAuctionPages.clear();
					allBIN.clear();
					allNonBIN.clear();
					
					SkyBlockAuctionsReply auctions = HypixelApiAccessor.API.getSkyBlockAuctions(0).get();
					allAuctionPages.add(auctions.getAuctions());
					
					int pages = auctions.getTotalPages();
					for (int i = 1; i < pages; i++) {
						allAuctionPages.add(HypixelApiAccessor.API.getSkyBlockAuctions(i).get().getAuctions());
					}
					
					for (JsonArray page : allAuctionPages) {
						for (int i = 0; i < page.size(); i++) {
							JsonObject object = page.get(i).getAsJsonObject();
							if (object.get("bin").getAsBoolean()) allBIN.add(object);
							else allNonBIN.add(object);
						}
					}
					
				} catch (Exception E) {}
				
			}
		};
		t.start();
		
	}
	
	@SubscribeEvent
	public void playerTick(PlayerTickEvent e) {
		
		if (fetchedData) return;
		
		fetchedData = true;
		reloadData();
		
	}
	
	public static long getLowestPrice(String name) {
		
		name = name.toLowerCase();
		
		ArrayList<JsonObject> binAuctions = allBIN;

		long lowestPrice = -1;
		
		for (int i = 0; i < binAuctions.size(); i++) {
			
			if (!(binAuctions.get(i).get("item_name").getAsString().toLowerCase().contains(name))) continue;
			
			long price = binAuctions.get(i).get("starting_bid").getAsLong();
			
			if (price < lowestPrice || lowestPrice == -1) lowestPrice = price;
			
		}
		
		return lowestPrice;
		
	}
	
}
