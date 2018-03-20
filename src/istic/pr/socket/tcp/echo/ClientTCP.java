package istic.pr.socket.tcp.echo;

import java.io.*;
import java.net.*;

public class ClientTCP {

    public static void main(String[] args) {
        // Port et adresse du seveur
        int portServeur = 9999;
        String adresseServeur = "localhost";

        try (Socket socketVersLeServeur = new Socket(adresseServeur, portServeur)) {
            //créer reader et writer liés au socket
            BufferedReader reader = creerReader(socketVersLeServeur);
            PrintWriter printer = creerPrinter(socketVersLeServeur);

            // String contenant le message reçu
            String mot = recevoirMessage(reader);
            System.out.println(mot);

            // String lisant le message lui au clavier
            String motLu = lireMessageAuClavier();

            // Tant que la chaîne de caractères n'est pas "fin", on lit
            // l'entrée clavier
            while (!motLu.equals("fin")) {
                // On envoit le message au serveur
                envoyerMessage(printer, motLu);

                // On reçoit et affiche le message reçu du serveur en réponse
                mot = recevoirMessage(reader);
                System.out.println(mot);

                motLu = lireMessageAuClavier();
            }
            // On ferme le socket
            socketVersLeServeur.close();
        } catch (IOException e) {
            System.out.println("Erreur ->" + e.getMessage());
        }

    }

    public static String lireMessageAuClavier() throws IOException {
        // Lit un message saisit au clavier
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        return in.readLine();
    }


    public static BufferedReader creerReader(Socket socketVersUnClient) throws IOException {
        BufferedReader readerIn = new BufferedReader(new InputStreamReader(socketVersUnClient.getInputStream()));
        return readerIn;
    }

    public static PrintWriter creerPrinter(Socket socketVersUnClient) throws IOException {
        PrintWriter printerOut = new PrintWriter(new OutputStreamWriter(socketVersUnClient.getOutputStream()));
        return printerOut;
    }

    public static String recevoirMessage(BufferedReader reader) throws IOException {
        // On récupère la ligne courante
        String currentLine = reader.readLine();

        // Si elle est vide
        if(currentLine.equals(""))
            return "Ligne vide";

        // Sinon on la retourne
        return currentLine;
    }

    public static void envoyerMessage(PrintWriter p, String message) throws IOException {
        // On envoit le message au client
        p.println(message);
        p.flush();
    }
}