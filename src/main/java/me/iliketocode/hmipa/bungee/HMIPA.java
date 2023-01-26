package me.iliketocode.hmipa.bungee;

import me.iliketocode.hmipa.bungee.listener.LoginListener;
import me.iliketocode.hmipa.bungee.listener.PlayerListener;
import me.iliketocode.hmipa.bungee.listener.ProxyListener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.connection.InitialHandler;
import net.md_5.bungee.netty.ChannelWrapper;
import org.bstats.bungeecord.Metrics;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.UUID;

public class HMIPA extends Plugin {

    private InetAddress inetAddress;

    public void onEnable() {
        try {
            this.inetAddress = InetAddress.getByName("0.0.0.0");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        new Metrics(this, 7037);

        PluginManager pluginManager = getProxy().getPluginManager();
        pluginManager.registerListener(this, new LoginListener(this));
        pluginManager.registerListener(this, new PlayerListener(this));
        pluginManager.registerListener(this, new ProxyListener(this));
    }

    public void setAddress(InitialHandler initialHandler, UUID uuid) {
        setAddress(initialHandler, uuid, inetAddress);
    }

    public void setAddress(InitialHandler initialHandler, UUID uuid, InetAddress inetAddress) {
        try {
            Field chField = initialHandler.getClass().getDeclaredField("ch");
            chField.setAccessible(true);

            ChannelWrapper channelWrapper = (ChannelWrapper) chField.get(initialHandler);

            Field remoteAddressField = channelWrapper.getClass().getDeclaredField("remoteAddress");
            remoteAddressField.setAccessible(true);

            InetSocketAddress inetSocketAddress = (java.net.InetSocketAddress) remoteAddressField.get(channelWrapper);

            Field holderField = inetSocketAddress.getClass().getDeclaredField("holder");
            holderField.setAccessible(true);

            Object InetSocketAddressHolder = holderField.get(inetSocketAddress);

            if (uuid != null) {
                Field hostnameField = InetSocketAddressHolder.getClass().getDeclaredField("hostname");
                hostnameField.setAccessible(true);
                hostnameField.set(InetSocketAddressHolder, uuid.toString().replace("-", ""));
            }

            Field addrField = InetSocketAddressHolder.getClass().getDeclaredField("addr");
            addrField.setAccessible(true);
            addrField.set(InetSocketAddressHolder, inetAddress);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
    }
}
