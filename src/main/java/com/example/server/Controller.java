package com.example.server;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class Controller {

    @FXML
    TextArea textArea;

    public void appendText(String text) {
        textArea.appendText(text);
    }

}