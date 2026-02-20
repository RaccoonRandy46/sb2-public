package com.coop.skyblock2.utils;

public class Num {
	
	public static double ran(double min, double max) {
		double diff = Math.abs(min - max);
		diff = (diff + 1);
		double rng = Math.floor(Math.random() * Math.floor(diff));
		rng = (rng + min);
		return rng;
	}
	
	public static double loop(double min, double max, int time) {
		if (time < 1 || min > max) return -1;
		double diff = max-min;
		double multiplier = System.currentTimeMillis()%time;
		multiplier/=(float)time;
		double add = multiplier*diff;
		return min+add;
	}
	
	public static double xoop(double min, double max, int time) {
		if (time < 1 || min > max) return -1;
		time*=2;
		double diff = max-min;
		double multiplier = System.currentTimeMillis()%time;
		multiplier-=((float)time/2f);
		multiplier = Math.abs(multiplier);
		multiplier/=((float)time/2f);
		double add = multiplier*diff;
		return min+add;
	}
    
    public static boolean approx(double a, double b, double range) {
    	return Math.abs(a - b) <= range;
    }
	
}
