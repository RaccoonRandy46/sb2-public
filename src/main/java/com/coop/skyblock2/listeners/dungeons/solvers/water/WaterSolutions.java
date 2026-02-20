package com.coop.skyblock2.listeners.dungeons.solvers.water;

import java.util.HashMap;

public class WaterSolutions {

	public static final char ALWAYS_ON = 'y';
	public static final char ALWAYS_OFF = 'n';
	public static final char ON_AFTER_WATER = 'a';
	public static final char ON_BEFORE_WATER = 'b';
	
	public static final int QUARTZ = 0;
	public static final int GOLD = 1;
	public static final int COAL = 2;
	public static final int DIAMOND = 3;
	public static final int EMERALD = 4;
	public static final int CLAY = 5;
	
	public static HashMap<String, HashMap<Integer, String>> solutions = new HashMap<>();
	
	static {
		
		//ORDER: QUARTZ, GOLD, COAL, DIAMOND, EMERALD, CLAY
		
		HashMap<Integer, String> solution0 = new HashMap<>();
		solution0.put(WaterListener.RED, "nynnyn");
		solution0.put(WaterListener.GREEN, "nnnnnn");
		solution0.put(WaterListener.BLUE, "ynnynn");
		solution0.put(WaterListener.ORANGE, "nnnnyn");
		solution0.put(WaterListener.PURPLE, "yynynn");
		solutions.put("011101111011110111000010111101111011101101011100010000000110101110101011111011010000010101111101101101101010000000000000110101111110101111011010000001010000101001111110101111010101100000000000000010110111111100111111011011100000000000000001110111001101111110000011100110011111011111110000101000000001000011010101111110101110000000000000000111", solution0);

		HashMap<Integer, String> solution1 = new HashMap<>();
		solution1.put(WaterListener.RED, "ynynyn");
		solution1.put(WaterListener.GREEN, "yynnyn");
		solution1.put(WaterListener.BLUE, "ynnynn");
		solution1.put(WaterListener.ORANGE, "yynnyy");
		solution1.put(WaterListener.PURPLE, "nnynnn");
		solutions.put("011100001011110111001111110101111000000111111010111001110000000001000001000011011110101110101111101111000110010000000100001111011111001110110111100000100100011000011111010010111111100111101001011111111000000100100000000111110110011111111011111010000000000101111101011111101010111110001111110000000111011111111010111011101111111100000000000111", solution1);
		          
		HashMap<Integer, String> solution2 = new HashMap<>();
		solution2.put(WaterListener.RED, "nynyyy");
		solution2.put(WaterListener.GREEN, "nynnyn");
		solution2.put(WaterListener.BLUE, "yyynyy");
		solution2.put(WaterListener.ORANGE, "yynnny");
		solution2.put(WaterListener.PURPLE, "yynnyy");
		solutions.put("011101111011110111001000100001111011100101110110111100000000000000000000111011011111101111111100101000000000000010010101111111111101011010111111111110100001011111100000000011101111110110111000000000000010011101101111101011011110110111110101101111000000000010000000000101111011111101111010100000000000111101010111111111011110000000000000001111", solution2);
		
		HashMap<Integer, String> solution3 = new HashMap<>();
		solution3.put(WaterListener.RED, "nnnnnn");
		solution3.put(WaterListener.GREEN, "nnnnyn");
		solution3.put(WaterListener.BLUE, "nnynny");
		solution3.put(WaterListener.ORANGE, "nyynnn");
		solution3.put(WaterListener.PURPLE, "yynyny");
		solutions.put("011101110011110111001110111011111011100111000000000001110011101110111110000001110111011111111100000011001111100000111011101111110111011101110000000000001110111010111110101111011101011111010110000000100000101011011111011111010001100000101000001011111111010001111101111111101110111110111111110111011111011111111000000000001111111111110111111111", solution3);
		
	}
	
	public static char getLeverRequirement(HashMap<Integer, String> solution, int color, int lever) {
		
		return solution.get(color).charAt(lever);
		
	}
	
}
