package me.iliketocode.hmipa.bungee.listener;

import me.iliketocode.hmipa.bungee.HMIPA;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.connection.InitialHandler;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

public class ProxyListener implements Listener {

    private final HMIPA instance;

    public ProxyListener(HMIPA instance) {
        this.instance = instance;
    }

    @EventHandler(priority = -65)
    public void onProxyPingStart(ProxyPingEvent event) {
        instance.setAddress(((InitialHandler) event.getConnection()), null);
    }

    @EventHandler(priority = 65)
    public void onProxyPingEnd(ProxyPingEvent event) {
        instance.setAddress(((InitialHandler) event.getConnection()), UUID.randomUUID(), null);
    }
}
