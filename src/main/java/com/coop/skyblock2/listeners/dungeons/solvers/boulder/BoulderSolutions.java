package com.coop.skyblock2.listeners.dungeons.solvers.boulder;

import java.util.ArrayList;
import java.util.HashMap;

public class BoulderSolutions {

	public static HashMap<String, ArrayList<int[]>> solutions = new HashMap<>();
	
	static {
		
		ArrayList<int[]> solution0 = new ArrayList<>();
		solution0.add(new int[] {2, 13});
		solutions.put("010001000101000101010001010001000101001001", solution0);
		
		ArrayList<int[]> solution1 = new ArrayList<>();
		solution1.add(new int[] {1, 11});
		solutions.put("000000000010100110100010101010101110000000", solution1);
		
		ArrayList<int[]> solution2 = new ArrayList<>();
		solution2.add(new int[] {10, 2});
		solution2.add(new int[] {10, 5});
		solution2.add(new int[] {7, 11});
		solution2.add(new int[] {6, 13});
		solutions.put("000000011111110100010010001001111100000000", solution2);
		
		ArrayList<int[]> solution3 = new ArrayList<>();
		solution3.add(new int[] {5, 4});
		solution3.add(new int[] {7, 5});
		solution3.add(new int[] {8, 7});
		solution3.add(new int[] {11, 7});
		solution3.add(new int[] {13, 11});
		solution3.add(new int[] {14, 13});
		solutions.put("000011100101110111000100101010111100100000", solution3);
		
		ArrayList<int[]> solution4 = new ArrayList<>();
		solution4.add(new int[] {13, 5});
		solution4.add(new int[] {12, 7});
		solution4.add(new int[] {10, 11});
		solution4.add(new int[] {9, 13});
		solutions.put("100000111100011001111101000110111011100011", solution4);
		
		ArrayList<int[]> solution5 = new ArrayList<>();
		solution5.add(new int[] {10, 2});
		solution5.add(new int[] {9, 4});
		solution5.add(new int[] {8, 7});
		solution5.add(new int[] {10, 8});
		solution5.add(new int[] {11, 10});
		solution5.add(new int[] {9, 10});
		solution5.add(new int[] {13, 11});
		solution5.add(new int[] {7, 11});
		solution5.add(new int[] {8, 13});
		solutions.put("000000010111011100011101110111101111000001", solution5);
		
		ArrayList<int[]> solution6 = new ArrayList<>();
		solution6.add(new int[] {5, 4});
		solution6.add(new int[] {7, 5});
		solution6.add(new int[] {8, 7});
		solution6.add(new int[] {11, 7});
		solution6.add(new int[] {14, 10});
		solution6.add(new int[] {16, 11});
		solutions.put("000011000101110111000100101011111100000000", solution6);
		
		ArrayList<int[]> solution7 = new ArrayList<>();
		solution7.add(new int[] {4, 2});
		solution7.add(new int[] {2, 13});
		solutions.put("101010101010100011111001010101000001001010", solution7);
		
	}
	
}
