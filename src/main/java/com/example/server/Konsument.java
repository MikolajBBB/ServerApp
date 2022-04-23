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

//    Map<String, String> pytanieOdpowiedz = preparePytanieOdpowiedzMap();

    public Konsument(BlockingQueue<Odpowiedz> kolejka, Controller controller) {
        this.kolejka = kolejka;
        this.controller = controller;
    }


    @Override
    public void run() {
        try {
//            Set<String> pytania = pytanieOdpowiedz.keySet();
            String s = readFile();
            String [] subs = s.split(",");
            int k = 1;
            int j = 0;
            for (int i=0;i<subs.length-2;i++) {
                controller.appendQuestion(subs[j]);
                while (true){
                    Odpowiedz odpowiedz = kolejka.take();
                    String tresc = odpowiedz.getTresc();
                    String ipAddress = odpowiedz.getIpAddress();
                    if(tresc != null) {
                        String[] split = tresc.split(":");
                        if (split[1].equals(subs[k])){
                            if(k+2<= subs.length && j+2<= subs.length) {
                                j = j + 2;
                                k = k + 2;
                            }
                            controller.appendOdpowiedzPoprawna(split[0], ipAddress);
                            break;
                        } else {
                            controller.appendOdpowiedzBledna();
                        }
                    }
                }
            }
            controller.appendFinalMessage();
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

//    Map<String, String> preparePytanieOdpowiedzMap() {
//        Map<String, String> result = new HashMap<>();
//        result.put("Stolica Polski?", "Warszawa");
//        result.put("Najlepszy jezyk programowania?", "Kotlin");
//        return result;
//    }

    public String readFile() throws IOException {
        // Using the Java 7 "try with resource syntax".
        try (FileReader fr = new FileReader("src/main/java/com/example/server/pytania.txt")) {
            BufferedReader br = new BufferedReader(fr);
            StringBuilder content = new StringBuilder();
            int c;
            while ((c = br.read()) != -1) {
                content.append((char)c);
            }
            return content.toString();
        }
    }

}


