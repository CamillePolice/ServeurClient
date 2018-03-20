package istic.pr.socket.udp;

import istic.pr.socket.tcp.chat.ClientTCP;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class ChatMulticast {


    public static void main(String[] args) {

        // Nom
        String nom = "";

        // On vérifie si on a un argument
        if (args.length >= 1) {
            nom = args[0];
        }

        try {

            // Adresse où s'échangent les messages
            InetAddress groupeIP = InetAddress.getByName("225.0.4.50");
            // Port d'écoute
            int port = 9999;
            MulticastSocket multicastSocket = new MulticastSocket(port);

            // On met le socket sur l'ip
            multicastSocket.joinGroup(groupeIP);


            ChatMulticast.Recevoir recevoir = new ChatMulticast.Recevoir(multicastSocket);
            Thread thread = new Thread(recevoir);
            thread.start();

            String motLu = lireMessageAuClavier();

            while (!motLu.equals("fin")) {
                motLu = nom + "> "+motLu;
                envoyerMessage(multicastSocket, motLu, groupeIP, port);
                motLu = lireMessageAuClavier();
            }

            recevoir.boucleTourne = false;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void envoyerMessage(MulticastSocket socket, String message, InetAddress groupeIP, int portEcoute) throws IOException {
        // Datagram Packet
        DatagramPacket packet = new DatagramPacket(message.getBytes(), message.getBytes().length, groupeIP, portEcoute);
        // On envoit sur le socket
        socket.send(packet);
    }

    public static String recevoirMessage(MulticastSocket socket) throws IOException {
        // Message
        byte[] messageLu = new byte[2048];
        DatagramPacket message = new DatagramPacket(messageLu, messageLu.length);
        socket.receive(message);
        return new String(messageLu);
    }

    public static String lireMessageAuClavier() throws IOException {
        // Comme les autres
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        return in.readLine();
    }

    private static class Recevoir implements Runnable {

        private MulticastSocket multicastSocket;
        public boolean boucleTourne = true;

        public Recevoir(MulticastSocket multicastSocket) {
            this.multicastSocket = multicastSocket;
        }

        @Override
        public void run() {
            while (boucleTourne) {
                try {
                    String res = recevoirMessage(multicastSocket);
                    System.out.println(res);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
