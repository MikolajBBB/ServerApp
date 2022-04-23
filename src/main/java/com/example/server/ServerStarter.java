package com.example.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.*;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ServerStarter extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ServerStarter.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Server");
        stage.setScene(scene);
        stage.show();
        ServerSocket serverSocket = new ServerSocket(6868);
        BlockingQueue<Odpowiedz> kolejka = new ArrayBlockingQueue<>(50);
        Konsument konsument = new Konsument(kolejka, fxmlLoader.getController());
        new Thread(konsument).start();
        new Thread() { //oddzielny wątek ,żeby watek okienka z acceptem nie wchodziły sb w glowe
            @Override
            public void run() {

                while (true){
                    try {
                        Socket socket = serverSocket.accept();
                        System.out.println("Connected new client");
                        Producent producent = new Producent(kolejka,socket);
                        new Thread(producent).start();
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