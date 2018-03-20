//...

package istic.pr.socket.tcp.echo;

import java.io.*;
import java.net.*;

public class ServeurTCP {

    public static void main(String[] args) throws IOException {

        // Port sur lequel le serveur est branché
        int portServeur = 9999;

        // Création d'un socket lié au port d'écoute défini ci-dessus
        ServerSocket socketServeur = new ServerSocket(portServeur);

        while(true) {
            // Message d'information
            System.out.println("Attente des clients.");

            try (Socket socketClient = socketServeur.accept()) {
                System.out.println("Client -> " + socketClient.getInetAddress() + " connecté.");
                traiterSocketCliente(socketClient);
            } catch (IOException e) {
                System.out.println("Erreur ->" + e.getMessage());
            }
        }
    }

    public static void traiterSocketCliente(Socket socketVersUnClient) throws IOException {
        // Création du reader et printer
        BufferedReader reader = creerReader(socketVersUnClient);
        PrintWriter writer = creerPrinter(socketVersUnClient);


        String messageRecu = recevoirMessage(reader);

        // Tant qu'il y a des un message à recevoir on boucle
        while(messageRecu != null) {
            // On affiche le message lu
            System.out.println(messageRecu);

            // On envoie le message au client
            envoyerMessage(writer, "Message" + ">" + messageRecu);

            // On check le prochain message
            messageRecu = recevoirMessage(reader);
        }
        // On ferme le socket
        socketVersUnClient.close();
    }

    public static BufferedReader creerReader(Socket socketVersUnClient) throws IOException {
        return ClientTCP.creerReader(socketVersUnClient);
    }

    public static PrintWriter creerPrinter(Socket socketVersUnClient) throws IOException {
        return ClientTCP.creerPrinter(socketVersUnClient);
    }

    public static String recevoirMessage(BufferedReader reader) throws IOException {
        return ClientTCP.recevoirMessage(reader);
    }


    public static void envoyerMessage(PrintWriter printer, String message) throws IOException {
        ClientTCP.envoyerMessage(printer, message);
    }
}