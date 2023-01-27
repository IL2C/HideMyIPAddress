package me.iliketocode.hmipa.bungee.listeners;

import me.iliketocode.hmipa.bungee.HMIPA;
import net.md_5.bungee.api.event.PlayerHandshakeEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class PlayerListener implements Listener {

    private final HMIPA instance;

    public PlayerListener(HMIPA instance) {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerHandshake(PlayerHandshakeEvent event) {
        instance.setAddress(event.getConnection());
    }
}
