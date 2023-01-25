package me.iliketocode.hmipa.bungee;

import me.iliketocode.hmipa.bungee.listeners.Handshake;
import me.iliketocode.hmipa.bungee.utils.Metrics;
import net.md_5.bungee.api.plugin.Plugin;

public class HMIPA extends Plugin {

    public void onEnable() {
        new Metrics(this, 7037);

        getProxy().getPluginManager().registerListener(this, new Handshake());
    }
}
