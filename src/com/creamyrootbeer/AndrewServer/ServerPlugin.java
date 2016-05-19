package com.creamyrootbeer.AndrewServer;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.creamyrootbeer.AndrewServer.commands.GetAccRod;
import com.creamyrootbeer.AndrewServer.commands.GetCustomItem;
import com.creamyrootbeer.AndrewServer.events.entity.SnowballHitEvent;
import com.creamyrootbeer.AndrewServer.events.player.BlockBreakHandler;
import com.creamyrootbeer.AndrewServer.events.player.PlayerClickHandler;
import com.creamyrootbeer.AndrewServer.items.ClickableVars;

/**
 * Created by Collin on 5/7/2016.
 */
public class ServerPlugin extends JavaPlugin {

	// private static final String INSULT = "YOUR FACE!!! OOOHHHHH!!!";
	private Logger logger;
	private PluginDescriptionFile pdfFile;
	private final ClickableVars vars = new ClickableVars();

	public void onEnable() {

		pdfFile = getDescription();
		logger = getLogger();

		registerConfig();
		registerCommands();
		registerEvents();

		logger.log(Level.INFO, pdfFile.getFullName() + " has been enabled.");

	}

	public void onDisable() {
		logger.info(pdfFile.getName() + " has been disabled.");
	}

	private void registerCommands() {
		getCommand("getitem").setExecutor(new GetCustomItem());
		getCommand("accrod").setExecutor(new GetAccRod());
	}

	private void registerEvents() {
		PluginManager pm = Bukkit.getPluginManager();

		pm.registerEvents(new PlayerClickHandler(this), this);
		pm.registerEvents(new BlockBreakHandler(), this);
		pm.registerEvents(new SnowballHitEvent(), this);
	}

	private void registerConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	public static ServerPlugin getPl() {
		ServerPlugin pl = (ServerPlugin) Bukkit.getPluginManager().getPlugin("AndrewServer");

		return pl;
	}
	
	public static ClickableVars getVars() {
		return getPl().vars;
	}
}
