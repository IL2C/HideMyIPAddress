package me.iliketocode.hmipa.utils;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.UUID;

public class InetSocketAddressUtil {

    private static final Random random = new Random();
    private static boolean randomIPSupport = false;

    static {
        try {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getByName("0.0.0.0"), random.nextInt(65535));

            Field holderField = inetSocketAddress.getClass().getDeclaredField("holder");
            holderField.setAccessible(true);

            randomIPSupport = true;
        } catch (Exception e) {
            randomIPSupport = false;
        }
    }

    public static InetSocketAddress create() {
        try {
            InetAddress inetAddress = InetAddress.getByName("0.0.0.0");
            InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress, random.nextInt(65535));

            if (!randomIPSupport) {
                return inetSocketAddress;
            }

            Field holderField = inetSocketAddress.getClass().getDeclaredField("holder");
            holderField.setAccessible(true);

            Object InetSocketAddressHolder = holderField.get(inetSocketAddress);

            UUID uuid = UUID.randomUUID();

            Field hostnameField = InetSocketAddressHolder.getClass().getDeclaredField("hostname");
            hostnameField.setAccessible(true);
            hostnameField.set(InetSocketAddressHolder, uuid.toString().replace("-", ""));

            Field addrField = InetSocketAddressHolder.getClass().getDeclaredField("addr");
            addrField.setAccessible(true);
            addrField.set(InetSocketAddressHolder, inetAddress);

            if (inetAddress == null) {
                return inetSocketAddress;
            }

            holderField = InetAddress.class.getDeclaredField("holder");
            holderField.setAccessible(true);

            Object inetAddressHolder = holderField.get(inetAddress);

            Class<?> inetAddressHolderClass = inetAddressHolder.getClass();

            Field originalHostNameField = inetAddressHolderClass.getDeclaredField("originalHostName");
            originalHostNameField.setAccessible(true);
            originalHostNameField.set(inetAddressHolder, uuid.toString().replace("-", ""));

            Field hostNameField = inetAddressHolderClass.getDeclaredField("hostName");
            hostNameField.setAccessible(true);
            hostNameField.set(inetAddressHolder, uuid.toString().replace("-", ""));

            Field addressField = inetAddressHolderClass.getDeclaredField("address");
            addressField.setAccessible(true);
            addressField.set(inetAddressHolder, random.nextInt(Integer.MAX_VALUE));

            return inetSocketAddress;
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException |
                 UnknownHostException e) {
            e.printStackTrace();
        }

        return null;
    }
}
