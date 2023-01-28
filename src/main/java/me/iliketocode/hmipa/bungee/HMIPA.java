package me.iliketocode.hmipa.bungee;

import me.iliketocode.hmipa.bungee.listeners.LoginListener;
import me.iliketocode.hmipa.bungee.listeners.PlayerListener;
import me.iliketocode.hmipa.bungee.listeners.ProxyListener;
import me.iliketocode.hmipa.utils.InetSocketAddressUtil;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
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

    public void setAddress(Object initialHandler) {
        InetSocketAddress inetSocketAddress = InetSocketAddressUtil.create();

        if (inetSocketAddress == null) {
            return;
        }

        try {
            Class<?> initialHandlerClass = Class.forName("net.md_5.bungee.connection.InitialHandler");

            Field chField = initialHandlerClass.getDeclaredField("ch");
            chField.setAccessible(true);

            Class<?> channelWrapperClass = Class.forName("net.md_5.bungee.netty.ChannelWrapper");

            Object channelWrapper = chField.get(initialHandler);

            Field remoteAddressField = channelWrapperClass.getDeclaredField("remoteAddress");
            remoteAddressField.setAccessible(true);
            remoteAddressField.set(channelWrapper, inetSocketAddress);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException |
                 ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
