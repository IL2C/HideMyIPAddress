package me.iliketocode.hmipa.velocity.listeners;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import me.iliketocode.hmipa.velocity.HMIPA;

public class ProxyListener {

    private final HMIPA instance;

    public ProxyListener(HMIPA instance) {
        this.instance = instance;
    }

    @Subscribe(order = PostOrder.EARLY)
    public void onProxyPing(ProxyPingEvent event) {
        instance.setAddress(event.getConnection());
    }
}
