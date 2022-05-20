package com.example.multimedia2021;

import javafx.stage.Stage;

public class CreateSolution extends PlayHangman{
    public static void createSolution(Stage stage) {
        if(PlayHangman.getSet() != null) {
            PlayHangman.setWinner("computer");
            PlayHangman.createScene(stage, -1);
        }
    }
}
