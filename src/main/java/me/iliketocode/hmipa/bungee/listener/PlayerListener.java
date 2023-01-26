package me.iliketocode.hmipa.bungee.listener;

import me.iliketocode.hmipa.bungee.HMIPA;
import net.md_5.bungee.api.event.PlayerHandshakeEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.connection.InitialHandler;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.lang.reflect.Field;
import java.util.UUID;

public class PlayerListener implements Listener {

    private final HMIPA instance;

    public PlayerListener(HMIPA instance) {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerHandshake(PlayerHandshakeEvent event) {
        InitialHandler initialHandler = ((InitialHandler) event.getConnection());

        try {
            Field bungeeField = initialHandler.getClass().getDeclaredField("bungee");
            bungeeField.setAccessible(true);

            Object BungeeCord = bungeeField.get(initialHandler);

            Field connectionThrottleField = BungeeCord.getClass().getDeclaredField("connectionThrottle");
            connectionThrottleField.setAccessible(true);
            connectionThrottleField.set(BungeeCord, null);

            instance.setAddress(initialHandler, UUID.randomUUID(), null);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
    }
}
