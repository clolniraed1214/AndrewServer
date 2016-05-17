package com.creamyrootbeer.AndrewServer;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.creamyrootbeer.AndrewServer.commands.GetAccelRod;
import com.creamyrootbeer.AndrewServer.commands.ReloadPlugin;
import com.creamyrootbeer.AndrewServer.events.player.BlockBreakHandler;
import com.creamyrootbeer.AndrewServer.events.player.RodClickHandler;
import com.creamyrootbeer.AndrewServer.util.Reloadable;

/**
 * Created by Collin on 5/7/2016.
 */
public class ServerPlugin extends JavaPlugin {

	// private static final String INSULT = "YOUR FACE!!! OOOHHHHH!!!";
	private Reloadable[] reloads = new Reloadable[] { new RodClickHandler(this) };

	private Logger logger;
	private PluginDescriptionFile pdfFile;

	public void onEnable() {

		pdfFile = getDescription();
		logger = getLogger();

		registerConfig();
		registerCommands();
		registerEvents();

		logger.log(Level.INFO, pdfFile.getFullName() + " plugin has been enabled. Version: " + pdfFile.getVersion());

	}

	public void onDisable() {
		logger.info(pdfFile.getName() + " has been disabled.");
	}

	private void registerCommands() {
		getCommand("accrod").setExecutor(new GetAccelRod());
		getCommand("server").setExecutor(new ReloadPlugin(this));
	}

	private void registerEvents() {
		PluginManager pm = Bukkit.getPluginManager();

		pm.registerEvents((Listener) reloads[0], this);
		pm.registerEvents(new BlockBreakHandler(), this);
	}

	private void registerConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	public static ServerPlugin getPl() {
		ServerPlugin pl = (ServerPlugin) Bukkit.getPluginManager().getPlugin("AndrewServer");

		return pl;
	}

	public void reloadConfigs() {
		for (Reloadable reload : reloads) {
			reload.reload();
		}
	}
}
