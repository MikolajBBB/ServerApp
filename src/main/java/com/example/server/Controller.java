package com.example.server;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class Controller {

    int questionCounter = 1;

    @FXML
    TextArea textArea;

    public void appendQuestion(String pytanie) {
        StringBuilder builder = new StringBuilder();
        builder.append("Nr ");
        builder.append(questionCounter);
        builder.append(") ");
        builder.append(pytanie);
        builder.append("\n");
        textArea.appendText(builder.toString());
    }

    public void appendOdpowiedzPoprawna(String name, String ipAddress) {
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        builder.append(" (");
        builder.append(ipAddress);
        builder.append(") ");
        builder.append(" odpowiedział poprawnie :)");
        builder.append("\n");
        builder.append("\n");
        textArea.appendText(builder.toString());
        questionCounter++;
    }

    public void appendOdpowiedzBledna() {
        StringBuilder builder = new StringBuilder();
        builder.append("Nadeszła odpowiedź błędna :(");
        builder.append("\n");
        textArea.appendText(builder.toString());
    }

    public void appendFinalMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        builder.append("Odpowiedziano już na wszystkie pytania. Koniec zabawy!");
        textArea.appendText(builder.toString());
    }
}