package com.example.multimedia2021;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.LinkedHashSet;

public class DictionaryDetails {
    public static void dictionaryDetails() {
        LinkedHashSet<String> set;
        set = PlayHangman.getSet();
        float six = 0, sevenNine = 0, tenPlus = 0;

        Stage stage = new Stage();
        stage.show();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        setGrid(stage, grid);

        if(PlayHangman.getSet() == null) {
            Text newGame = new Text("Start a new game first");
            newGame.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            grid.add(newGame, 0, 0, 2, 1);
        }
        else {
            for (String words : set) {
                if (words.length() == 6) {
                    six++;
                } else if (words.length() >= 10) {
                    tenPlus++;
                } else {
                    sevenNine++;
                }
            }
            Text sixText = new Text("6 letter words: " + six / (six + sevenNine + tenPlus));
            sixText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            grid.add(sixText, 0, 0, 2, 1);
            Text sevenNineText = new Text("7-9 letter words: " + sevenNine / (six + sevenNine + tenPlus));
            sevenNineText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            grid.add(sevenNineText, 0, 1, 2, 1);
            Text tenPlusText = new Text("10+ letter words: " + tenPlus / (six + sevenNine + tenPlus));
            tenPlusText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            grid.add(tenPlusText, 0, 2, 2, 1);
        }
    }

    static void setGrid(Stage stage, GridPane grid) {
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(grid, 300, 275);
        stage.setScene(scene);
    }
}
