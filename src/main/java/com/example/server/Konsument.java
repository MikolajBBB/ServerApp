package com.example.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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
            Set<String> pytania = pytanieOdpowiedz.keySet();
            for (String pytanie : pytania) {
                controller.appendQuestion(pytanie);

                while (true){
                    Odpowiedz odpowiedz = kolejka.take();
                    String tresc = odpowiedz.getTresc();
                    String ipAddress = odpowiedz.getIpAddress();
                    if(tresc != null) {
                        String[] split = tresc.split(":");
                        if (split[1].equals(pytanieOdpowiedz.get(pytanie))) {
                            controller.appendOdpowiedzPoprawna(split[0], ipAddress);
                            break;
                        } else {
                            controller.appendOdpowiedzBledna();
                        }
                    }
                }
            }
            controller.appendFinalMessage();
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
