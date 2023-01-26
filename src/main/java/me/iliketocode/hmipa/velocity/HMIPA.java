package me.iliketocode.hmipa.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import me.iliketocode.hmipa.utils.InetSocketAddressUtil;
import me.iliketocode.hmipa.velocity.listeners.ConnectionListener;
import me.iliketocode.hmipa.velocity.listeners.ProxyListener;
import org.bstats.velocity.Metrics;

import java.lang.reflect.Field;
import java.net.InetSocketAddress;

@Plugin(id = "hidemyipaddress", name = "HideMyIPAddress", version = "5", description = "Prevents servers on the network getting a players connected IP address", authors = {"ILikeToCode"})
public class HMIPA {

    private final ProxyServer server;
    private final Metrics.Factory metricsFactory;

    @Inject
    public HMIPA(ProxyServer server, Metrics.Factory metricsFactory) {
        this.server = server;
        this.metricsFactory = metricsFactory;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        metricsFactory.make(this, 17536);

        EventManager eventManager = server.getEventManager();
        eventManager.register(this, new ProxyListener(this));
        eventManager.register(this, new ConnectionListener(this));
    }

    public void setAddress(Object connection) {
        if (connection == null) {
            return;
        }

        InetSocketAddress inetSocketAddress = InetSocketAddressUtil.create();

        if (inetSocketAddress == null) {
            return;
        }

        try {
            Field connectionField = connection.getClass().getDeclaredField("connection");
            connectionField.setAccessible(true);

            Object minecraftConnection = connectionField.get(connection);

            Field remoteAddressField = minecraftConnection.getClass().getDeclaredField("remoteAddress");
            remoteAddressField.setAccessible(true);
            remoteAddressField.set(minecraftConnection, inetSocketAddress);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
