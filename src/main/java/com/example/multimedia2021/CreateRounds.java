package com.example.multimedia2021;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CreateRounds {
    public static void createRounds() {
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 730, 695);
        stage.setScene(scene);
        for(int i=PlayHangman.getGames()-1;i>PlayHangman.getGames()-6 && i>=0;i--) {
            Text winnerText = new Text(PlayHangman.getHistory()[i]);
            winnerText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            grid.add(winnerText, 0, PlayHangman.getGames()-i, 2, 1);
        }

        stage.show();
    }
}
