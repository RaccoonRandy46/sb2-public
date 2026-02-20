package com.coop.skyblock2.listeners.dungeons.solvers;

import com.coop.skyblock2.listeners.dungeons.solvers.bomb.BombDefuseListener;
import com.coop.skyblock2.listeners.dungeons.solvers.boulder.BoulderListener;
import com.coop.skyblock2.listeners.dungeons.solvers.creeper.CreeperListener;
import com.coop.skyblock2.listeners.dungeons.solvers.icefill.IceFillListener;
import com.coop.skyblock2.listeners.dungeons.solvers.quiz.QuizListener;
import com.coop.skyblock2.listeners.dungeons.solvers.water.WaterListener;

import net.minecraftforge.common.MinecraftForge;

public class SolverManager {

	public static void registerSolvers() {
		
		MinecraftForge.EVENT_BUS.register(new ThreeWeirdosListener());
		MinecraftForge.EVENT_BUS.register(new BlazeListener());
		MinecraftForge.EVENT_BUS.register(new BoulderListener());
		MinecraftForge.EVENT_BUS.register(new CreeperListener());
		MinecraftForge.EVENT_BUS.register(new IceFillListener());
		MinecraftForge.EVENT_BUS.register(new WaterListener());
		MinecraftForge.EVENT_BUS.register(new RatHockeyListener());
		MinecraftForge.EVENT_BUS.register(new TicTacToeListener());
		MinecraftForge.EVENT_BUS.register(new BombDefuseListener());
		MinecraftForge.EVENT_BUS.register(new QuizListener());
		MinecraftForge.EVENT_BUS.register(new ResetListener());
		
	}
	
}
