package istic.pr.socket.tcp.nom;

import java.io.*;
import java.net.Socket;

public class ClientTCP {

    public static void main(String[] args) {
        //créer une socket client
        int portServeur = 9999;
        String adresseServeur = "localhost";

        // Nom
        String nom = "";
        // On vérifie si il y a bien un argument
        if (args.length >= 1)
            nom = args[0];
        else
            System.out.println("Pas de nom trouvé.");

        try (Socket socketVersLeServeur = new Socket(adresseServeur, portServeur)) {

            //créer reader et writer associés au socket
            BufferedReader reader = creerReader(socketVersLeServeur);
            PrintWriter printer = creerPrinter(socketVersLeServeur);

            // On envoit le nom au serveur
            envoyerNom(printer, nom);

            String mot = recevoirMessage(reader);
            System.out.println(mot);

            String motLu = lireMessageAuClavier();

            while (!motLu.equals("fin")) {
                envoyerMessage(printer, motLu);
                mot = recevoirMessage(reader);
                System.out.println(mot);
                motLu = lireMessageAuClavier();
            }
            socketVersLeServeur.close();
        } catch (IOException e) {
            System.out.println("Erreur ->" + e.getMessage());
        }

    }

    public static String lireMessageAuClavier() throws IOException {
        // Lit un message saisit au clavier (System.in)
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
        String currentLine = reader.readLine();

        if(currentLine.equals(""))
            return "Ligne vide";

        return currentLine;
    }

    public static void envoyerMessage(PrintWriter p, String message) throws IOException {
        p.println(message);
        p.flush();
    }

    public static void envoyerNom(PrintWriter printer, String nom) throws IOException {
        // envoie < NAME: NOM > au serveur
        // On utilise envoyerMessage
        envoyerMessage(printer, nom);
    }


}