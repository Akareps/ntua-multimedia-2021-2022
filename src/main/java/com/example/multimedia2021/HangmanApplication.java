package com.example.multimedia2021;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;

public class HangmanApplication extends Application {
    private final Image image = new Image(new File("src\\main\\resources\\com\\example\\multimedia2021\\images\\homescreen.jpg").toURI().toString());
    @Override
    public void start(Stage stage) {

        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(1030);
        imageView.setFitWidth(1030);
        imageView.setPreserveRatio(true);
        stage.setTitle("Home");

        Group root = new Group(imageView);
        CreateMenus.createMenus(root, stage);

        Scene scene = new Scene(root, 1030, 1030);
        stage.setTitle("Loading");
        stage.setScene(scene);
        stage.setMaxHeight(1030);
        stage.setMaxWidth(1030);
        stage.setMinHeight(1030);
        stage.setMinWidth(1030);
        stage.show();
        stage.setTitle("MediaLab Hangman");

    }

    public static void main(String[] args) {
        launch();
    }
}