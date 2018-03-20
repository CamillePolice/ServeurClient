package istic.pr.socket.tcp.chat;

import istic.pr.socket.tcp.thread.ServeurTCP;

import java.io.IOException;
import java.net.Socket;

public class TraiteUnClient implements Runnable {

    public Socket socketVersUnClient;
    private String charset;

    public TraiteUnClient(Socket socketVersUnClient, String charset) {
        this.socketVersUnClient = socketVersUnClient;
        this.charset = charset;
    }
    @Override
    public void run() {
        try {
            ServeurTCP.traiterSocketCliente(socketVersUnClient, charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
