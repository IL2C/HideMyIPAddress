package me.iliketocode.hmipa.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class HMIPA extends JavaPlugin {

    public void onEnable() {
        getServer().getLogger().log(Level.WARNING, "[" + getDescription().getName() + "] This is a BungeeCord plugin. It cannot be loaded onto this server. Disabling plugin!");
        getServer().getPluginManager().disablePlugin(this);
    }
}
