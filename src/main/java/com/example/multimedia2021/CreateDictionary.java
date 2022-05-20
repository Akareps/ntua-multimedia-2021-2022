package com.example.multimedia2021;

import com.example.multimedia2021.exceptions.HangmanException;
import com.example.multimedia2021.exceptions.UnbalancedException;
import com.example.multimedia2021.exceptions.UndersizeException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

public class CreateDictionary {

    private static String url = "", id="";

    private static float percentage(LinkedHashSet<String> set) {
        float ninePlus = 0;
        for(String word: set) {
            if(word.length()>=9)
                ninePlus++;
        }
        return ninePlus/set.size();
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        }
    }

    public static void createDictionary() throws IOException, JSONException, HangmanException {
        JSONObject json = readJsonFromUrl(url);
        String description = "";

        //Many description JSONs contained only a description field, not a nested description field with value. The following segment covers both cases
        try {
            description = (String) json.get("description");
        }
        catch (Exception ignored) {}
        if(Objects.equals(description, ""))
            description = (String) json.getJSONObject("description").get("value");

        description = Arrays.toString(description.replaceAll("[^a-zA-Z ]", " ").toUpperCase().replaceAll("\\b\\w{1,5}\\b\\s?", "").split("\\s+"));
        description = description.replaceAll(",|\\[|\\]", "");
        StringBuilder result = new StringBuilder();
        String[] allWords;
        allWords = description.split(" ");
        LinkedHashSet<String> set = new LinkedHashSet<>(List.of(allWords));

        for(String word: set) {
            result.append(word).append(" ");
        }

        String dictionary = (String.valueOf(result)).replaceAll(" $","").replaceAll(" ", "\n");

        if(set.size()<20)
            throw new UndersizeException(String.valueOf(result.length()));

        if(percentage(set)<0.2)
            throw new UnbalancedException(String.valueOf(percentage(set)));

        try (PrintWriter out = new PrintWriter("src\\main\\resources\\com\\example\\multimedia2021\\medialab\\hangman_" + id + ".txt")) {
            out.println(dictionary);
        }
    }

    public static void loadURL() {
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 400, 375);
        stage.setScene(scene);
        Text sceneTitle = new Text("Input dictionary ID and Open Library ID");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label dictID = new Label("Dictionary ID:");
        grid.add(dictID, 0, 1);

        TextField idTextField = new TextField();
        grid.add(idTextField, 1, 1);

        Label libraryID = new Label("Library ID:");
        grid.add(libraryID, 0, 2);

        TextField idTextField2 = new TextField();
        grid.add(idTextField2, 1, 2);

        Button submit = new Button("submit");

        submit.setOnAction(e -> {
            if(!Objects.equals(idTextField.getText(), "") && !Objects.equals(idTextField2.getText(), "")) {
                url = "https://openlibrary.org/books/" + idTextField2.getText() +".json";
                id = idTextField.getText();
                stage.close();
                try {
                    createDictionary();
                } catch (IOException | JSONException | HangmanException ex) {
                    ex.printStackTrace();
                }
            }
        });

        grid.add(submit,2,1);

        stage.show();
    }
}
