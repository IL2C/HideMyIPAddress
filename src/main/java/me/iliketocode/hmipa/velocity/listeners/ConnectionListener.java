package me.iliketocode.hmipa.velocity.listeners;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PreLoginEvent;
import com.velocitypowered.api.proxy.InboundConnection;
import me.iliketocode.hmipa.velocity.HMIPA;

import java.lang.reflect.Field;

public class ConnectionListener {

    private final HMIPA instance;

    public ConnectionListener(HMIPA instance) {
        this.instance = instance;
    }

    @Subscribe(order = PostOrder.EARLY)
    public void onPreLogin(PreLoginEvent event) {
        InboundConnection inboundConnection = event.getConnection();

        try {
            Field delegateField = inboundConnection.getClass().getDeclaredField("delegate");
            delegateField.setAccessible(true);

            instance.setAddress(delegateField.get(inboundConnection));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
