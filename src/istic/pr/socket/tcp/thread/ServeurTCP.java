//...

package istic.pr.socket.tcp.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ServeurTCP {

    public static void main(String[] args) throws IOException {

        // Port sur lequel on écoute
        int portEcoute = 9999;

        // Création d'un socket lié au port d'écoute défini ci-dessus
        ServerSocket socketServeur = new ServerSocket(portEcoute);

        // Charset
        String charset = "UTF-8";
        // On vérifie qu'il y a l'argument
        if(args.length >= 1)
            charset = args[0];
        else
            System.out.println("Pas de charset détecté");

        Executor service = Executors.newFixedThreadPool(5);

        while(true) {
            System.out.println("Attente des clients.");

            try (Socket socketClient = socketServeur.accept()) {
                System.out.println("Client -> " + socketClient.getInetAddress() + " connecté.");
                service.execute(new TraiteUnClient(socketClient, charset));
            } catch (IOException e) {
                System.out.println("Erreur (" + e.getMessage() + ")");
            }
        }
    }

    public static void traiterSocketCliente(Socket socketVersUnClient, String charset) throws IOException {
        // On crée un buffer
        BufferedReader reader = creerReader(socketVersUnClient, charset);
        // On crée uun writer
        PrintWriter writer = creerPrinter(socketVersUnClient, charset);

        // Nom
        String nom = avoirNom(reader);
        // Si le nom est null
        if(nom == null || nom.equals("")) {
            System.out.println("Erreur, pas de nom détecté.");
            nom = "NomParDéfaut";
        }
        else {
            envoyerMessage(writer, "Nom : " + nom);
        }

        String messageRecu = recevoirMessage(reader);

        while(messageRecu != null) {
            System.out.println(messageRecu);
            envoyerMessage(writer, nom + ">" + messageRecu);
            messageRecu = recevoirMessage(reader);
        }
        // On ferme le socket
        socketVersUnClient.close();
    }

    public static BufferedReader creerReader(Socket socketVersUnClient, String charset) throws IOException {
        return ClientTCP.creerReader(socketVersUnClient, charset);
    }

    public static PrintWriter creerPrinter(Socket socketVersUnClient, String charset) throws IOException {
        return ClientTCP.creerPrinter(socketVersUnClient, charset);
    }

    public static String recevoirMessage(BufferedReader reader) throws IOException {
        return ClientTCP.recevoirMessage(reader);
    }


    public static void envoyerMessage(PrintWriter printer, String message) throws IOException {
        ClientTCP.envoyerMessage(printer, message);
    }

    public static String avoirNom(BufferedReader reader) throws IOException {
        //retourne le nom du client (en utilisant split de la classe String par exemple)
        // On utilise recevoirMessage
        return recevoirMessage(reader);
    }

}