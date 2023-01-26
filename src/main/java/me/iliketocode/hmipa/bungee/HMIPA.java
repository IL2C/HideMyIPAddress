package me.iliketocode.hmipa.bungee;

import me.iliketocode.hmipa.bungee.listeners.Handshake;
import net.md_5.bungee.api.plugin.Plugin;
import org.bstats.bungeecord.Metrics;

public class HMIPA extends Plugin {

    public void onEnable() {
        new Metrics(this, 7037);

        getProxy().getPluginManager().registerListener(this, new Handshake());
    }
}
