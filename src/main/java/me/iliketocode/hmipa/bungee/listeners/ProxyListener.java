package me.iliketocode.hmipa.bungee.listeners;

import me.iliketocode.hmipa.bungee.HMIPA;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyListener implements Listener {

    private final HMIPA instance;

    public ProxyListener(HMIPA instance) {
        this.instance = instance;
    }

    @EventHandler(priority = -65)
    public void onProxyPingStart(ProxyPingEvent event) {
        instance.setAddress(event.getConnection());
    }
}
