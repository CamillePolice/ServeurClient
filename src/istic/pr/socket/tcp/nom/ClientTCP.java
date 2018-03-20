package istic.pr.socket.tcp.nom;

import java.io.*;
import java.net.Socket;

public class ClientTCP {

    public static void main(String[] args) {
        //créer une socket client
        int portDuServeur = 9999;
        String adresseDuServeur = "localhost";

        try (Socket socketVersLeServeur = new Socket(adresseDuServeur, portDuServeur)) {

            //créer reader et writer associés au socket
            BufferedReader reader = creerReader(socketVersLeServeur);
            PrintWriter printer = creerPrinter(socketVersLeServeur);

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
        BufferedReader buffer = new BufferedReader(reader);
        return buffer.readLine();
    }


    public static BufferedReader creerReader(Socket socketVersUnClient) throws IOException {
        BufferedReader readerIn = new BufferedReader(new InputStreamReader(socketVersUnClient.getInputStream()));
        return readerIn;
    }

    public static PrintWriter creerPrinter(Socket socketVersUnClient) throws IOException {
        PrintWriter printerOut = new PrintWriter(new PrintStream(socketVersUnClient.getOutputStream()));
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

}