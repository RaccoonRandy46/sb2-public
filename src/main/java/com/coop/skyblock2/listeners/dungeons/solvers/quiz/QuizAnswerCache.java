package com.coop.skyblock2.listeners.dungeons.solvers.quiz;

import java.util.HashMap;

import com.coop.skyblock2.utils.skyblock.SBTime;

public class QuizAnswerCache {

	private static final int[] GREEN = new int[] {0, 255, 0};
	private static final int[] YELLOW = new int[] {255, 255, 0};
	
	private static HashMap<String, QuizAnswer> answerMap = new HashMap<>();
	
	static {
		
		answerMap.put("what is the status of the watcher?", new QuizAnswer("stalker", GREEN));
		answerMap.put("what is the status of bonzo?", new QuizAnswer("new necromancer", GREEN));
		answerMap.put("what is the status of scarf?", new QuizAnswer("apprentice necromancer", GREEN));
		answerMap.put("what is the status of the professor?", new QuizAnswer("professor", GREEN));
		answerMap.put("what is the status of thorn?", new QuizAnswer("shaman necromancer", GREEN));
		answerMap.put("what is the status of livid?", new QuizAnswer("master necromancer", GREEN));
		answerMap.put("what is the status of sadan?", new QuizAnswer("necromancer lord", GREEN));
		answerMap.put("what is the status of maxor, storm, goldor, and necron?", new QuizAnswer("the wither lords", GREEN));
		answerMap.put("how many total fairy souls are there?", new QuizAnswer("247 fairy souls", YELLOW));
		answerMap.put("how many fairy souls are there in the end?", new QuizAnswer("12 fairy souls", GREEN));
		answerMap.put("how many fairy souls are there in spider's den?", new QuizAnswer("19 fairy souls", GREEN));
		answerMap.put("how many fairy souls are there in the farming islands?", new QuizAnswer("20 fairy souls", GREEN));
		answerMap.put("how many fairy souls are there in crimson isle?", new QuizAnswer("29 fairy souls", GREEN));
		answerMap.put("how many fairy souls are there in the park?", new QuizAnswer("11 fairy souls", GREEN));
		answerMap.put("how many fairy souls are there in jerry's workshop?", new QuizAnswer("5 fairy souls", GREEN));
		answerMap.put("how many fairy souls are there in the hub?", new QuizAnswer("80 fairy souls", GREEN));
		answerMap.put("how many fairy souls are there in deep caverns?", new QuizAnswer("21 fairy souls", GREEN));
		answerMap.put("how many fairy souls are there in gold mine?", new QuizAnswer("12 fairy souls", GREEN));
		answerMap.put("how many fairy souls are there in dungeon hub?", new QuizAnswer("7 fairy souls", GREEN));
		answerMap.put("how many fairy souls are there in dwarven mines?", new QuizAnswer("11 fairy souls", GREEN));
		answerMap.put("which brother is on the spider's den?", new QuizAnswer("rick", GREEN));
		answerMap.put("what is the name of the painter in the hub?", new QuizAnswer("marco", GREEN));
		answerMap.put("what is the name of the person that upgrades pets?", new QuizAnswer("kat", GREEN));
		answerMap.put("what is the name of the lady of the nether?", new QuizAnswer("elle", GREEN));
		answerMap.put("which villager in the village gives you a rogue sword?", new QuizAnswer("jamie", GREEN));
		answerMap.put("glass?", new QuizAnswer("woll weaver", GREEN));
		answerMap.put("how many unique minions are there?", new QuizAnswer("59", YELLOW));
		answerMap.put("which of these enemies does not spawn in the spider's den?", new QuizAnswer(new String[] {"zombie spider", "cave spider", "broodfather", "wither skeleton", "spooder"}, GREEN));
		answerMap.put("which of these monsters only spawns at night?", new QuizAnswer(new String[] {"zombie villager", "ghast"}, GREEN));
		answerMap.put("which of these is not a dragon in the end?", new QuizAnswer(new String[] {"zoomer dragon", "weak dragon", "stonk dragon", "holy dragon", "boomer dragon", "stable dragon", "booger dragon", "elder dragon", "older dragon", "professor dragon"}, GREEN));
		
	}
	
	public static QuizAnswer getAnswer(String question) {
		
		if (question.equals("what skyblock year is it?")) {
			return new QuizAnswer("year " + SBTime.getYear(), GREEN);
		}
		
		if (!answerMap.containsKey(question)) return null;
		return answerMap.get(question);
		
	}
	
}
