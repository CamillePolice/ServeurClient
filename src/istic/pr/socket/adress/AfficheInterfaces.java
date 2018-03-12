package istic.pr.socket.adress;

import java.net.*;
import java.util.*;

public class AfficheInterfaces {

    public static void main(String[] args) {
        try {
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface netint : Collections.list(nets))
                affichageInformationInter(netint);
        } catch (SocketException e) {
            System.err.println("Erreur : getNetworkInterfaces");
            e.printStackTrace();
        }

    }

    static void affichageInformationInter(NetworkInterface netint){
        System.out.printf("%s\n", netint.getDisplayName());
        System.out.printf("%s\n", netint.getName());
        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
            System.out.printf("->%s\n", inetAddress);
        }
        System.out.printf("\n");
    }

}
