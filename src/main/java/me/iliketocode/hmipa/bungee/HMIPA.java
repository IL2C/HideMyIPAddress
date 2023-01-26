package me.iliketocode.hmipa.bungee;

import me.iliketocode.hmipa.bungee.listeners.LoginListener;
import me.iliketocode.hmipa.bungee.listeners.PlayerListener;
import me.iliketocode.hmipa.bungee.listeners.ProxyListener;
import me.iliketocode.hmipa.utils.InetSocketAddressUtil;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.connection.InitialHandler;
import net.md_5.bungee.netty.ChannelWrapper;
import org.bstats.bungeecord.Metrics;

import java.lang.reflect.Field;
import java.net.InetSocketAddress;

public class HMIPA extends Plugin {

    public void onEnable() {
        new Metrics(this, 7037);

        PluginManager pluginManager = getProxy().getPluginManager();
        pluginManager.registerListener(this, new LoginListener(this));
        pluginManager.registerListener(this, new PlayerListener(this));
        pluginManager.registerListener(this, new ProxyListener(this));
    }

    public void setAddress(InitialHandler initialHandler) {
        InetSocketAddress inetSocketAddress = InetSocketAddressUtil.create();

        if (inetSocketAddress == null) {
            return;
        }

        try {
            Field chField = initialHandler.getClass().getDeclaredField("ch");
            chField.setAccessible(true);

            ChannelWrapper channelWrapper = (ChannelWrapper) chField.get(initialHandler);

            Field remoteAddressField = channelWrapper.getClass().getDeclaredField("remoteAddress");
            remoteAddressField.setAccessible(true);
            remoteAddressField.set(channelWrapper, inetSocketAddress);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
    }
}
