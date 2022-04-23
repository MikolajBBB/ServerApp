package com.example.server;

import java.util.concurrent.BlockingQueue;

public class Konsument implements Runnable {
    BlockingQueue<Odpowiedz> kolejka;

    public Konsument(BlockingQueue<Odpowiedz> kolejka) {
        this.kolejka = kolejka;
    }


    @Override
    public void run() {
        try {
            Thread.sleep(500);
            while (!kolejka.isEmpty()) {
                System.out.println(kolejka.take().getTresc());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
