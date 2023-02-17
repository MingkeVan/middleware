package com.mw.middleware.utils;

public class CommonUtil {
    // isValidIp
    public static boolean isValidIp(String ip) {
        if (ip == null || ip.length() == 0) {
            return false;
        }
        String[] ipArray = ip.split("\\.");
        if (ipArray.length != 4) {
            return false;
        }
        for (String ipPart : ipArray) {
            try {
                int ipPartInt = Integer.parseInt(ipPart);
                if (ipPartInt < 0 || ipPartInt > 255) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    // check port is valid
    public static boolean isValidPort(String port) {
        if (port == null || port.length() == 0) {
            return false;
        }
        try {
            int portInt = Integer.parseInt(port);
            if (portInt < 0 || portInt > 65535) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
