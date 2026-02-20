package com.coop.skyblock2.listeners.dungeons.secrets;

import java.util.ArrayList;

import com.coop.skyblock2.listeners.dungeons.secrets.identifiers.Secret;

public class SecretSolution {

	private ArrayList<Secret> secrets;
	private int index = 0;
	
	public SecretSolution() {
		this.secrets = new ArrayList<>();
	}
	
	public void addSecret(Secret secret) {
		this.secrets.add(secret);
	}
	
	public ArrayList<Secret> getSecrets() {
		return this.secrets;
	}
	
	public void popSecret() {
		this.secrets.remove(0);
	}
	
	public void addPathPoint(double x, double y, double z) {
		if (this.secrets.size() == 0) return;
		this.secrets.get(this.secrets.size() - 1).addPathPoint(x, y, z);
	}
	
	public Secret getCurrentSecret() {
		if (this.index >= this.secrets.size()) this.index = 0;
		return this.secrets.get(this.index);
	}
	
	public void cycleSecret() {
		this.index++;
		if (this.index >= this.secrets.size()) this.index = 0;
	}
	
	public int getSecretIndex() {
		return this.index;
	}
	
	public int getSecretCount() {
		return this.secrets.size();
	}
	
}
