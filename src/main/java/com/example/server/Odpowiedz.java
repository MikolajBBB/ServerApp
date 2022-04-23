package com.example.server;

public class Odpowiedz {
    private final String tresc;
    private final String ipAddress;

    public Odpowiedz(String tresc, String ipAddress) {
        this.tresc = tresc;
        this.ipAddress = ipAddress;
    }

    public String getTresc() {
        return tresc;
    }

    public String getIpAddress() {
        return ipAddress;
    }
}
