package com.example.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

// klasa, która będzie działać jako wątek bo implementuje runnable
public class Producent implements Runnable {
    private BlockingQueue<Odpowiedz> kolejka;
    private Socket socket;

    public Producent(BlockingQueue<Odpowiedz> kolejka,Socket socket) {
        this.kolejka = kolejka;
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while (true){
                String s = bufferedReader.readLine();
                kolejka.put(new Odpowiedz(s));
            }
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
