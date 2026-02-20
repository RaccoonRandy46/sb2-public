package com.coop.skyblock2.listeners.dungeons.solvers;

import org.lwjgl.input.Keyboard;

import com.coop.skyblock2.listeners.dungeons.solvers.bomb.BombDefuseListener;
import com.coop.skyblock2.listeners.dungeons.solvers.boulder.BoulderListener;
import com.coop.skyblock2.listeners.dungeons.solvers.creeper.CreeperListener;
import com.coop.skyblock2.listeners.dungeons.solvers.icefill.IceFillListener;
import com.coop.skyblock2.listeners.dungeons.solvers.quiz.QuizListener;
import com.coop.skyblock2.listeners.dungeons.solvers.water.WaterListener;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class ResetListener {

	private static long timeDraftUsed = 0;
	
	@SubscribeEvent
	public void playerTick(PlayerTickEvent e) {
		
		if (!(e.player instanceof EntityPlayerSP)) return;
		
		if (!Utils.isInDungeons()) return;
		
		if (System.currentTimeMillis() - timeDraftUsed <= 2000) resetAll();
		
		if (!Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) return;
		if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) return;
		if (!Keyboard.isKeyDown(Keyboard.KEY_R)) return;
		if (!Keyboard.isKeyDown(Keyboard.KEY_F12)) return;
		
		resetAll();
		
	}
	
	@SubscribeEvent
	public void chatGet(ClientChatReceivedEvent e) {
		
		if (!Utils.isInDungeons()) return;
		
		String message = Utils.removeColorcodes(e.getMessage().getFormattedText());
		
		if (message.startsWith("You used the Architect's First Draft to reset ")) timeDraftUsed = System.currentTimeMillis();
		
	}
	
	public static void resetAll() {
		
		BombDefuseListener.reset();
		BoulderListener.reset();
		CreeperListener.reset();
		IceFillListener.reset();
		QuizListener.reset();
		WaterListener.reset();
		BlazeListener.reset();
		ThreeWeirdosListener.reset();
		TicTacToeListener.reset();
		RatHockeyListener.reset();
		
	}
	
}
