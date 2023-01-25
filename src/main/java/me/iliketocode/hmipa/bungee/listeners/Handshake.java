package me.iliketocode.hmipa.bungee.listeners;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import net.md_5.bungee.api.event.PlayerHandshakeEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.connection.InitialHandler;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import net.md_5.bungee.netty.ChannelWrapper;

public class Handshake implements Listener {

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

			setAddress(initialHandler, UUID.randomUUID(), null);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
	}

	@EventHandler(priority = -65)
	public void onPreLoginEvent(PreLoginEvent event) {
		try {
			setAddress(((InitialHandler) event.getConnection()), null, InetAddress.getByName("0.0.0.0"));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	@EventHandler(priority = -65)
	public void onProxyPingEventStart(ProxyPingEvent event) {
		try {
			setAddress(((InitialHandler) event.getConnection()), null, InetAddress.getByName("0.0.0.0"));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	@EventHandler(priority = 65)
	public void onProxyPingEventEnd(ProxyPingEvent event) {
		setAddress(((InitialHandler) event.getConnection()), UUID.randomUUID(), null);
	}

	private void setAddress(InitialHandler initialHandler, UUID uuid, InetAddress InetAddress) {
		try {
			Field chField = initialHandler.getClass().getDeclaredField("ch");
			chField.setAccessible(true);

			ChannelWrapper channelWrapper = (ChannelWrapper) chField.get(initialHandler);

			Field remoteAddressField = channelWrapper.getClass().getDeclaredField("remoteAddress");
			remoteAddressField.setAccessible(true);

			InetSocketAddress InetSocketAddress = (java.net.InetSocketAddress) remoteAddressField.get(channelWrapper);

			Field holderField = InetSocketAddress.getClass().getDeclaredField("holder");
			holderField.setAccessible(true);

			Object InetSocketAddressHolder = holderField.get(InetSocketAddress);

			if (uuid != null) {
				Field hostnameField = InetSocketAddressHolder.getClass().getDeclaredField("hostname");
				hostnameField.setAccessible(true);
				hostnameField.set(InetSocketAddressHolder, uuid.toString().replace("-", ""));
			}

			Field addrField = InetSocketAddressHolder.getClass().getDeclaredField("addr");
			addrField.setAccessible(true);
			addrField.set(InetSocketAddressHolder, InetAddress);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
	}
}
