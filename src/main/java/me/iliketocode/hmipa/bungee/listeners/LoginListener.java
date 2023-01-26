package me.iliketocode.hmipa.bungee.listeners;

import me.iliketocode.hmipa.bungee.HMIPA;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.connection.InitialHandler;
import net.md_5.bungee.event.EventHandler;

public class LoginListener implements Listener {

    private final HMIPA instance;

    public LoginListener(HMIPA instance) {
        this.instance = instance;
    }

    @EventHandler(priority = -65)
    public void onPreLogin(PreLoginEvent event) {
        instance.setAddress((InitialHandler) event.getConnection());
    }
}
