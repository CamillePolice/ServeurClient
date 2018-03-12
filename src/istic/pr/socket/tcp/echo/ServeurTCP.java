//...

package istic.pr.socket.tcp.echo;

import java.io.*;
import java.net.*;

public class ServeurTCP {

    public static void main(String[] args) throws IOException {

        // Port sur lequel on écoute
        int portEcoute = 9999;

        // Création d'un socket lié au port d'écoute défini ci-dessus
        ServerSocket socketServeur = new ServerSocket(portEcoute);

        while(true) {
            System.out.println("Attente des clients.");

            try (Socket socketClient = socketServeur.accept()) {
                System.out.println("Client -> " + socketClient.getInetAddress() + " connecté.");
                traiterSocketCliente(socketClient);
            } catch (IOException e) {
                System.out.println("Erreur (" + e.getMessage() + ")");
            }
        }
    }

    public static void traiterSocketCliente(Socket socketVersUnClient) throws IOException {
        // On crée un buffer
        BufferedReader reader = creerReader(socketVersUnClient);
        // On crée uun writer
        PrintWriter writer = creerPrinter(socketVersUnClient);

        String messageRecu = recevoirMessage(reader);

        while(messageRecu != null) {
            System.out.println(messageRecu);
            envoyerMessage(writer, messageRecu);
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