package com.coop.skyblock2.listeners.dungeons.solvers.quiz;

public class QuizAnswer {

	private String[] answers;
	public int[] color;
	
	public QuizAnswer(String answerIn, int[] colorIn) {
		this.answers = new String[] {answerIn};
		this.color = colorIn;
	}
	
	public QuizAnswer(String[] answersIn, int[] colorIn) {
		this.answers = answersIn;
		this.color = colorIn;
	}
	
	public boolean contains(String s) {
		
		for (String answer : this.answers) {
			if (s.contains(answer)) return true;
		}
		
		return false;
		
	}
	
	public boolean equals(String s) {
		
		for (String answer : this.answers) {
			if (answer.equals(s)) return true;
		}
		
		return false;
		
	}
	
	public String toString() {
		
		if (this.answers == null || this.answers.length == 0) return "undefined";
		
		if (this.answers.length == 1) return this.answers[0];
		
		String out = "[" + this.answers[0] + "]";
		for (int i = 1; i < this.answers.length; i++) out += ", [" + this.answers[i] + "]";
		
		return out;
		
	}
	
}
