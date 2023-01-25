package me.iliketocode.hmipa.bukkit;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

public class HMIPA extends JavaPlugin {

	public void onEnable() {
		getServer().getLogger().log(Level.WARNING, "[" + getDescription().getName()
				+ "] This is a BungeeCord plugin. It cannot be loaded onto this server. Disabling plugin!");
		getServer().getPluginManager().disablePlugin(this);
	}
}
