package me.iliketocode.hmipa.velocity.listener;

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

        if (!instance.hasInetSocketAddress()) {
            return;
        }

        try {
            Field delegateField = inboundConnection.getClass().getDeclaredField("delegate");
            delegateField.setAccessible(true);

            Object initialInboundConnection = delegateField.get(inboundConnection);

            instance.setInetSocketAddress(initialInboundConnection);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
