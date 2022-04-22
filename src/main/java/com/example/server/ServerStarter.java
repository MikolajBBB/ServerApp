package com.example.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.*;

import java.io.IOException;

public class ServerStarter extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ServerStarter.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Server");
        stage.setScene(scene);
        stage.show();
        ServerSocket serverSocket = new ServerSocket(6868);
        new Thread() { //oddzielny wątek ,żeby watek okienka z acceptem nie wchodziły sb w glowe
            @Override
            public void run() {

                for (int i=0;i<5;i++) {
                    System.out.println("Connected");
                    try {

                        Socket socket = serverSocket.accept();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }.start();
    }

    public static void main(String[] args) {
        launch();
    }
}