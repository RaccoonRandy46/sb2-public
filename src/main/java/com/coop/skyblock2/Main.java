package com.coop.skyblock2;

import com.coop.skyblock2.commands.ABReloadCommand;
import com.coop.skyblock2.commands.DateCommand;
import com.coop.skyblock2.commands.DebugCommand;
import com.coop.skyblock2.commands.HelpCommand;
import com.coop.skyblock2.commands.TempCommand;
import com.coop.skyblock2.commands.UpdateCommand;
import com.coop.skyblock2.commands.dungeons.AutoCycleCommand;
import com.coop.skyblock2.commands.dungeons.ChestScamCommand;
import com.coop.skyblock2.commands.dungeons.DungeonClassCommand;
import com.coop.skyblock2.commands.dungeons.FelsCommand;
import com.coop.skyblock2.commands.dungeons.IIDCommand;
import com.coop.skyblock2.commands.dungeons.MinimapCommand;
import com.coop.skyblock2.commands.dungeons.SecretsCommand;
import com.coop.skyblock2.commands.dungeons.StarHighlightCommand;
import com.coop.skyblock2.commands.universal.FDCommand;
import com.coop.skyblock2.commands.universal.SccbCommand;
import com.coop.skyblock2.commands.universal.ScespCommand;
import com.coop.skyblock2.commands.universal.ScrcCommand;
import com.coop.skyblock2.commands.universal.ScredoCommand;
import com.coop.skyblock2.listeners.CrashPatchListener;
import com.coop.skyblock2.listeners.TempListener;
import com.coop.skyblock2.listeners.dungeons.ChestScamListener;
import com.coop.skyblock2.listeners.dungeons.FelsListener;
import com.coop.skyblock2.listeners.dungeons.FinderFilterListener;
import com.coop.skyblock2.listeners.dungeons.LividFindListener;
import com.coop.skyblock2.listeners.dungeons.MimicListener;
import com.coop.skyblock2.listeners.dungeons.MinimapListener;
import com.coop.skyblock2.listeners.dungeons.SpiritBearFindListener;
import com.coop.skyblock2.listeners.dungeons.StarHighlightListener;
import com.coop.skyblock2.listeners.dungeons.secrets.SecretManager;
import com.coop.skyblock2.listeners.dungeons.solvers.SolverManager;
import com.coop.skyblock2.listeners.dungeons.solvers.terminals.TerminalColorFind;
import com.coop.skyblock2.listeners.dungeons.solvers.terminals.TerminalColorMatch;
import com.coop.skyblock2.listeners.dungeons.solvers.terminals.TerminalCount;
import com.coop.skyblock2.listeners.dungeons.solvers.terminals.TerminalStartsWithLetter;
import com.coop.skyblock2.listeners.experiments.ExperimentManager;
import com.coop.skyblock2.listeners.slayers.SlayerManager;
import com.coop.skyblock2.proxy.CommonProxy;
import com.coop.skyblock2.utils.Reference;
import com.coop.skyblock2.utils.skyblock.AuctionData;
import com.coop.skyblock2.utils.skyblock.SkyblockData;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class Main {
	
	public static String UUID = "b5e0dfc2521645878ae36c21dff5774b";
	public static boolean universal = false;
	
	@Instance
	public static Main instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.COMMON_PROXY)
	public static CommonProxy proxy;
	
	@EventHandler
	public static void PreInit(FMLPreInitializationEvent e) {
		
		UpdateCommand.updateAll();
		SkyblockData.reloadData();
		
	}

	@EventHandler
	public static void init(FMLInitializationEvent e) {}
	
	@EventHandler
	public static void PostInit(FMLPostInitializationEvent e) {
		
		MinecraftForge.EVENT_BUS.register(new HelpCommand());
		MinecraftForge.EVENT_BUS.register(new FelsCommand());
		MinecraftForge.EVENT_BUS.register(new FelsListener());
		MinecraftForge.EVENT_BUS.register(new MimicListener());
		MinecraftForge.EVENT_BUS.register(new MinimapCommand());
		MinecraftForge.EVENT_BUS.register(new MinimapListener());
		MinecraftForge.EVENT_BUS.register(new ChestScamCommand());
		MinecraftForge.EVENT_BUS.register(new ChestScamListener());
		MinecraftForge.EVENT_BUS.register(new DateCommand());
		MinecraftForge.EVENT_BUS.register(new DebugCommand());
		MinecraftForge.EVENT_BUS.register(new IIDCommand());
		MinecraftForge.EVENT_BUS.register(new ABReloadCommand());
		SolverManager.registerSolvers();
		SlayerManager.registerSlayerHelpers();
		MinecraftForge.EVENT_BUS.register(new LividFindListener());
		MinecraftForge.EVENT_BUS.register(new SpiritBearFindListener());
		MinecraftForge.EVENT_BUS.register(new StarHighlightCommand());
		MinecraftForge.EVENT_BUS.register(new StarHighlightListener());
		MinecraftForge.EVENT_BUS.register(new UpdateCommand());
		MinecraftForge.EVENT_BUS.register(new SecretsCommand());
		MinecraftForge.EVENT_BUS.register(new FinderFilterListener());
		MinecraftForge.EVENT_BUS.register(new AutoCycleCommand());
		MinecraftForge.EVENT_BUS.register(new DungeonClassCommand());
		MinecraftForge.EVENT_BUS.register(new ScrcCommand());
		MinecraftForge.EVENT_BUS.register(new SccbCommand());
		MinecraftForge.EVENT_BUS.register(new ScredoCommand());
		MinecraftForge.EVENT_BUS.register(new ScespCommand());
		MinecraftForge.EVENT_BUS.register(new TerminalStartsWithLetter());
		MinecraftForge.EVENT_BUS.register(new TerminalCount());
		MinecraftForge.EVENT_BUS.register(new TerminalColorMatch());
		MinecraftForge.EVENT_BUS.register(new TerminalColorFind());
		MinecraftForge.EVENT_BUS.register(new CrashPatchListener());
		MinecraftForge.EVENT_BUS.register(new FDCommand());
		SecretManager.registerFinders();
		ExperimentManager.registerExperiments();
		MinecraftForge.EVENT_BUS.register(new TempCommand());
		MinecraftForge.EVENT_BUS.register(new TempListener());
		MinecraftForge.EVENT_BUS.register(new AuctionData());
		MinecraftForge.EVENT_BUS.register(new Listener());
		
	}

}
