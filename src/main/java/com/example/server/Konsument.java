package com.example.server;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class Konsument implements Runnable {
    BlockingQueue<Odpowiedz> kolejka;
    Controller controller;

    Map<String, String> pytanieOdpowiedz = preparePytanieOdpowiedzMap();

    public Konsument(BlockingQueue<Odpowiedz> kolejka, Controller controller) {
        this.kolejka = kolejka;
        this.controller = controller;
    }


    @Override
    public void run() {
        try {
            while (true) {
                controller.appendText(kolejka.take().getTresc());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    Map<String, String> preparePytanieOdpowiedzMap() {
        Map<String, String> result = new HashMap<>();
        result.put("Stolica Polski?", "Warszawa");
        result.put("Najlepszy jezyk programowania?", "Kotlin");
        return result;
    }
}
