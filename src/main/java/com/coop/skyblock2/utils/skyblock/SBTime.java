package com.coop.skyblock2.utils.skyblock;

public class SBTime {

	public static final long BEGINNING_OF_TIME = 1560275700000L;
	
	public static final long YEAR_LENGTH = 446400000L;
	public static final long MONTH_LENGTH = 37200000L;
	public static final long DAY_LENGTH = 1200000L;
	
	public static long getTimePassedMillis() {
		return System.currentTimeMillis() - BEGINNING_OF_TIME;
	}
	
	public static int getYear() {
		return (int)(getTimePassedMillis() / YEAR_LENGTH + 1);
	}
	
	public static int getMonth() {
		return (int)(getTimePassedMillis() % YEAR_LENGTH / MONTH_LENGTH);
	}
	
	public static int getDay() {
		
		long time = getTimePassedMillis() % YEAR_LENGTH;
		time -= MONTH_LENGTH * getMonth();
		
		return (int)(time / DAY_LENGTH + 1);
		
	}
	
	public static String getSeason() {
		
		int month = getMonth();
		
		int season = month / 3;
		int progress = month % 3;
		
		String progressString = "Early";
		if (progress != 0) progressString = (progress == 1 ? "Mid" : "Late");
		
		String seasonString = "Spring";
		switch (season) {
		case 1:
			seasonString = "Summer";
			break;
		case 2:
			seasonString = "Autumn";
			break;
		case 3:
			seasonString = "Winter";
			break;
		}
		
		return progressString + " " + seasonString;
		
	}
	
	public static String getDate() {
		
		int day = getDay();
		return day + getDaySuffix(day) + " of " + getSeason() + ", Year " + getYear();
		
	}
	
	private static String getDaySuffix(int day) {
		
		if (day / 10 % 10 == 1) return "th";
		
		if (day % 10 == 1) return "st";
		if (day % 10 == 2) return "nd";
		if (day % 10 == 3) return "rd";
		
		return "th";
		
	}
	
}
