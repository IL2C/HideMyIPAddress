package me.iliketocode.hmipa.velocity.listener;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.proxy.InboundConnection;
import me.iliketocode.hmipa.velocity.HMIPA;

public class Proxy {

    private final HMIPA instance;

    public Proxy(HMIPA instance) {
        this.instance = instance;
    }

    @Subscribe(order = PostOrder.EARLY)
    public void onProxyPing(ProxyPingEvent event) {
        InboundConnection inboundConnection = event.getConnection();

        if (!instance.hasInetSocketAddress()) {
            return;
        }

        try {
            instance.setInetSocketAddress(inboundConnection);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
