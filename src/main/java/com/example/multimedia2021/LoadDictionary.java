package com.example.multimedia2021;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.Objects;

public class LoadDictionary {
    public static void loadDictionary() {
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        DictionaryDetails.setGrid(stage, grid);
        Text sceneTitle = new Text("Input dictionary ID");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label dictID = new Label("ID:");
        grid.add(dictID, 0, 1);

        TextField idTextField = new TextField();
        grid.add(idTextField, 1, 1);

        Button submit = new Button("submit");

        Text errorID = new Text("Invalid ID");

        submit.setOnAction(e -> {
            if(!Objects.equals(idTextField.getText(), "")) {
                String id = idTextField.getText();
                File file = new File("src\\main\\resources\\com\\example\\multimedia2021\\medialab\\hangman_" + id + ".txt");
                if(file.exists()) {
                    PlayHangman.setDictContent("hangman_" + id + ".txt");
                    stage.close();
                }
                else {
                    grid.add(errorID,1,3);
                }
            }
        });

        grid.add(submit,2,1);

        stage.show();
    }
}
