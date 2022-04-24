package com.example.server;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

public class Konsument implements Runnable {
    BlockingQueue<Odpowiedz> kolejka;
    Controller controller;


    String[] subs;

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
                        if (split[1].equals(pytanieOdpowiedz.get(pytanie))){
                            kolejka.clear();
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
        try {
            FileReader fr = new FileReader("src/main/resources/com/example/server/pytania.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                result.put(split[0], split[1]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("The problem with reading a file occurred");
        }


        return result;
    }
}


