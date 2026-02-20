package com.coop.skyblock2.utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

import com.coop.skyblock2.commands.dungeons.IIDCommand;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;

public class Utils {

	public static String chat(String s) {
		return s.replace("&", "\247");
	}
	
	public static String removeColorcodes(String s) {
		  
		String newString = "";
		  
		for (int i = 0; i < s.length(); i++) {
			  
			if (((int)s.charAt(i)) == 167) {
				i++;
				continue;
			}
			  
			newString += s.charAt(i);
			  
		}
		  
		return newString;
		  
	}
	
	public static boolean isInDungeons() {return isInDungeons(Utils.getScoreboard());}
	private static boolean isInDungeons(List<String> scoreboard) {

		if (IIDCommand.bool) return true;
		
		if (scoreboard == null || scoreboard.size() < 5) return false;
		if (!isInSkyblock(scoreboard)) return false;
		
		if (Utils.removeColorcodes(scoreboard.get(5)).contains("The Catac")) return true;
		return Utils.removeColorcodes(scoreboard.get(4)).contains("The Catac");
		
	}
	
	public static boolean isInSkyblock() {return isInSkyblock(Utils.getScoreboard());}
	private static boolean isInSkyblock(List<String> scoreboard) {
		
		if (IIDCommand.bool) return true;
		
		if (scoreboard == null || scoreboard.size() < 1) return false;
		if (!isInHypixel(scoreboard)) return false;
		
		return Utils.removeColorcodes(scoreboard.get(0)).contains("SKYBLOCK");
		
	}
	
	public static boolean isInHypixel() {return isInHypixel(Utils.getScoreboard());}
	private static boolean isInHypixel(List<String> scoreboard) {
		
		if (IIDCommand.bool) return true;
		
		if (scoreboard == null || scoreboard.size() < 1) return false;
		
		return Utils.removeColorcodes(scoreboard.get(scoreboard.size() - 1)).contains("hypixel");
		
	}
	
	public static String commafy(long number) {
		
		boolean negative = number < 0;
		if (negative) number *= -1;
		
		String numberString = number + "";
		
		String out = "";
		
		for (int i = numberString.length() - 1; i >= 0; i--) {
			
			out = numberString.charAt(i) + out;
			if ((numberString.length() - i) % 3 == 0 && i > 0) out = "," + out;
			
		}
		
		if (negative) out = "-" + out;
		
		return out;
		
	}
	
	public static List<String> getScoreboard() {
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		if (p == null) return null;
		
		Scoreboard scoreboard = Minecraft.getMinecraft().world.getScoreboard();
        ScoreObjective scoreobjective = null;
        ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(p.getName());

        if (scoreplayerteam != null) {
        	
            int i1 = scoreplayerteam.getColor().getColorIndex();
            if (i1 >= 0) scoreobjective = scoreboard.getObjectiveInDisplaySlot(3 + i1);
            
        }

        ScoreObjective objective = scoreobjective != null ? scoreobjective : scoreboard.getObjectiveInDisplaySlot(1);

        if (objective == null) return null;
        
        scoreboard = objective.getScoreboard();
        Collection<Score> collection = scoreboard.getSortedScores(objective);
        
        List<Score> list = Lists.newArrayList(Iterables.filter(collection, new Predicate<Score>() {
            public boolean apply(@Nullable Score p_apply_1_) {
                return p_apply_1_.getPlayerName() != null && !p_apply_1_.getPlayerName().startsWith("#");
            }
        }));

        if (list.size() > 15) collection = Lists.newArrayList(Iterables.skip(list, collection.size() - 15));
        else collection = list;

        List<String> reversed = new ArrayList<>();
        
        for (Score score : collection) {
        	
            ScorePlayerTeam scoreplayerteam1 = scoreboard.getPlayersTeam(score.getPlayerName());
            String text = ScorePlayerTeam.formatPlayerName(scoreplayerteam1, score.getPlayerName());
            
            reversed.add(text);
            
        }
        
        List<String> out = new ArrayList<String>();
        out.add(objective.getDisplayName());
        
        for (int i = reversed.size() - 1; i >= 0; i--) out.add(reversed.get(i));
        
        return out;
		
	}
	
	public static float[] RGBtoHSV(float r, float g, float b) {
		  
		float[] hsvDecimal = Color.RGBtoHSB((int)r, (int)g, (int)b, null);
			
		float[] hsv = {hsvDecimal[0]*360, hsvDecimal[1]*100, hsvDecimal[2]*100};
		  
		return hsv;
		  
	}
	  
	public static float[] HSVtoRGB(float h, float s, float v) {
	      
		h /= 360;
		s /= 100;
		v /= 100;
		  
		Color rgbColor = Color.decode(String.valueOf(Color.HSBtoRGB(h, s, v)));
		  
		float[] rgb = {(float)rgbColor.getRed(), (float)rgbColor.getGreen(), (float)rgbColor.getBlue()};
			
		return rgb;

	}
	
}
