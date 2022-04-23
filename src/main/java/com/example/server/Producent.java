package com.example.server;

import java.util.concurrent.BlockingQueue;

// klasa, która będzie działać jako wątek bo implementuje runnable
public class Producent implements Runnable {
    private BlockingQueue<Odpowiedz> kolejka;

    public Producent(BlockingQueue<Odpowiedz> kolejka) {
        this.kolejka = kolejka;
    }
    @Override
    public void run() {
        try {
            kolejka.put(new Odpowiedz("test"));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
