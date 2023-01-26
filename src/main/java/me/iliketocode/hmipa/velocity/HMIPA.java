package me.iliketocode.hmipa.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import me.iliketocode.hmipa.velocity.listener.Connection;
import me.iliketocode.hmipa.velocity.listener.Proxy;
import org.bstats.velocity.Metrics;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

@Plugin(id = "hidemyipaddress", name = "HideMyIPAddress", version = "5", description = "Prevents servers on the network getting a players connected IP address", authors = {"ILikeToCode"})
public class HMIPA {

    private final ProxyServer server;
    private final Logger logger;
    private final Metrics.Factory metricsFactory;

    private final InetSocketAddress inetSocketAddress;

    @Inject
    public HMIPA(ProxyServer server, Logger logger, Metrics.Factory metricsFactory) {
        this.server = server;
        this.logger = logger;
        this.metricsFactory = metricsFactory;

        try {
            this.inetSocketAddress = new InetSocketAddress(InetAddress.getByName("0.0.0.0"), 0);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        metricsFactory.make(this, 17536);

        EventManager eventManager = server.getEventManager();
        eventManager.register(this, new Proxy(this));
        eventManager.register(this, new Connection(this));
    }

    public boolean hasInetSocketAddress() {
        return inetSocketAddress != null;
    }

    public void setInetSocketAddress(Object connection) throws NoSuchFieldException, IllegalAccessException {
        if (inetSocketAddress == null || connection == null) {
            return;
        }

        Field connectionField = connection.getClass().getDeclaredField("connection");
        connectionField.setAccessible(true);

        Object minecraftConnection = connectionField.get(connection);

        Field remoteAddressField = minecraftConnection.getClass().getDeclaredField("remoteAddress");
        remoteAddressField.setAccessible(true);
        remoteAddressField.set(minecraftConnection, inetSocketAddress);
    }
}
