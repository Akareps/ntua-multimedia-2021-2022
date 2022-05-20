package com.example.multimedia2021;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class PlayHangman {

    public static void setDictContent(String dictContent) {
        PlayHangman.dictContent = dictContent;
    }

    private static String content="", filePath="", given, dictContent="hangman_001.txt", winner="no one";
    private static final char[][] excluded = new char[60][6];

    public static LinkedHashSet<String> getSet() {
        return set;
    }

    private static LinkedHashSet<String> set;
    private static int mistakes = 0, corrects = 0, points=0;
    private static char[] word;
    private static Boolean[] found;
    private static char[] wordFilled;
    private static Integer pos;

    public static void setWinner(String winner) {
        PlayHangman.winner = winner;
    }

    public static Integer getGames() {
        return games;
    }

    private static Integer games=-1;

    public static String[] getHistory() {
        return history;
    }

    private static final String[] history = new String[10000];

    public static void setContent(String activeDictionary) throws IOException {
        content = Files.readString(Path.of("src\\main\\resources\\com\\example\\multimedia2021\\medialab\\" + activeDictionary), StandardCharsets.US_ASCII);
        filePath = "src\\main\\resources\\com\\example\\multimedia2021\\medialab\\" + activeDictionary;
    }

    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    private static Map<Character, Integer> odds(int position, Boolean[] found, String word) {
        Map<Character, Integer> probs = new LinkedHashMap<>();
        int val;

        for(String words: set) {
            boolean flag = true;
            if(word.length()==words.length()) {
                for (int i = 0; i < found.length; i++) {
                    if (found[i]) {
                        if (!(word.charAt(i) == words.charAt(i))) {
                            flag = false;
                            break;
                        }
                    }
                    for(int j=0;j<6;j++) {
                        if (words.charAt(i) == excluded[i][j]) {
                            flag = false;
                            break;
                        }
                    }
                }
                if(flag) {
                    if(!probs.containsKey(words.charAt(position)))
                        probs.put(words.charAt(position),1);
                    else {
                        val = probs.get(words.charAt(position));
                        probs.put(words.charAt(position),val+1);
                    }
                }
            }
        }
        return sortByValue(probs);
    }

    protected static void createScene(Stage stage, int charToShow) {
        history[games] = "Word: " + Arrays.toString(word) + ", Attempts: " + (corrects+mistakes) + ", Won: " + winner;
        Image image1 = new Image(new File("src\\main\\resources\\com\\example\\multimedia2021\\images\\screen1.jpg").toURI().toString());
        ImageView imageView1 = new ImageView(image1);
        imageView1.setFitHeight(1030);
        imageView1.setFitWidth(1030);
        imageView1.setPreserveRatio(true);

        Image image2 = new Image(new File("src\\main\\resources\\com\\example\\multimedia2021\\images\\hung" + mistakes + ".png").toURI().toString());
        ImageView imageView2 = new ImageView(image2);
        imageView2.setFitHeight(150);
        imageView2.setFitWidth(150);
        imageView2.setX(50);
        imageView2.setY(350);
        imageView2.setBlendMode(BlendMode.MULTIPLY);
        imageView2.setPreserveRatio(true);
        Group root = new Group(imageView1, imageView2);

        if(Objects.equals(winner, "no one")) {

            StackPane stackPane = new StackPane();

            Text text = new Text(Arrays.toString(wordFilled));
            text.setBlendMode(BlendMode.MULTIPLY);
            text.minHeight(100);
            text.minWidth(100);
            text.setStyle("-fx-font: 24 arial;");
            stackPane.getChildren().addAll(text);
            stackPane.setLayoutY(500);

            HBox hbox = new HBox();

            for (int i = 1; i <= wordFilled.length; i++) {
                Button button = new Button("" + i);

                int finalI = i;
                button.setOnAction(e -> createScene(stage, finalI));

                if (!found[i - 1]) {
                    hbox.getChildren().addAll(button);
                }
            }
            hbox.setLayoutY(550);
            hbox.setLayoutX(650);

            if (charToShow > 0) {
                HBox hbox2 = new HBox();
                hbox2.setLayoutY(520);
                hbox2.setLayoutX(650);

                Text text2 = new Text(odds(charToShow - 1, found, String.valueOf(word)).keySet().toString());
                text2.setBlendMode(BlendMode.MULTIPLY);
                text2.minHeight(100);
                text2.minWidth(100);
                text2.setStyle("-fx-font: 15 arial;");

                hbox2.getChildren().addAll(text2);
                root.getChildren().add(hbox2);
            }

            root.getChildren().add(stackPane);
            root.getChildren().add(hbox);

            ComboBox<String> comboBox = new ComboBox();
            comboBox.getItems().addAll(
                    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
            );
            comboBox.setLayoutY(950);
            comboBox.setLayoutX(100);
            root.getChildren().add(comboBox);

            ObservableList<String> options = FXCollections.observableArrayList();
            for (int i = 1; i <= found.length; i++) {
                if (!found[i - 1]) {
                    options.add("" + i);
                }
            }
            ComboBox<String> comboBox2 = new ComboBox();
            comboBox2.getItems().addAll(
                    options
            );

            comboBox2.setLayoutY(950);
            comboBox2.setLayoutX(40);
            root.getChildren().add(comboBox2);

            Button choose = new Button("choose");
            choose.setOnAction(e -> {
                if (comboBox.getValue() != null && comboBox2.getValue() != null) {
                    given = comboBox.getValue();
                    pos = Integer.parseInt(comboBox2.getValue()) - 1;
                    try {
                        playHangman(stage, false);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            choose.setLayoutX(160);
            choose.setLayoutY(950);
            root.getChildren().add(choose);

            CreateMenus.createMenus(root, stage);
        }
        else if(Objects.equals(winner, "computer")) {
            StackPane stackPane = new StackPane();
            Text text = new Text("You lost... the word was: " + Arrays.toString(word));
            text.setBlendMode(BlendMode.MULTIPLY);
            text.minHeight(100);
            text.minWidth(100);
            text.setStyle("-fx-font: 28 arial;");
            stackPane.getChildren().addAll(text);
            stackPane.setLayoutY(500);
            stackPane.setLayoutX(250);
            root.getChildren().add(stackPane);
            CreateMenus.createMenus(root, stage);
        }
        else {
            StackPane stackPane = new StackPane();
            Text text = new Text("You Won!!!");
            text.setBlendMode(BlendMode.MULTIPLY);
            text.minHeight(100);
            text.minWidth(100);
            text.setStyle("-fx-font: 48 arial;");
            stackPane.getChildren().addAll(text);
            stackPane.setLayoutY(500);
            stackPane.setLayoutX(400);
            root.getChildren().add(stackPane);
            CreateMenus.createMenus(root, stage);
        }

        Text pointsText = new Text("Current points: " + points);
        pointsText.minHeight(100);
        pointsText.minWidth(100);
        pointsText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
        pointsText.setFill(Color.WHITE);
        pointsText.setStrokeWidth(2);
        pointsText.setStroke(Color.BLACK);
        pointsText.setLayoutY(100);
        pointsText.setLayoutX(250);
        root.getChildren().add(pointsText);

        Text wordsText = new Text("Words in dictionary: " + set.size());
        customizeText(wordsText);
        wordsText.setLayoutY(40);
        wordsText.setLayoutX(250);
        root.getChildren().add(wordsText);

        float percentage = 1;
        if(corrects!=0) {
            percentage = (float) corrects/ (float) (corrects+mistakes);
        }

        Text percentageText = new Text("Successful guesses: " + percentage * 100 + "%");
        customizeText(percentageText);
        percentageText.setLayoutY(150);
        percentageText.setLayoutX(250);
        root.getChildren().add(percentageText);

        Scene scene = new Scene(root, 1000, 1000);

        stage.setTitle("Loading");
        stage.setScene(scene);
        stage.show();
        stage.setTitle("MediaLab Hangman");
    }

    private static void customizeText(Text wordsText) {
        wordsText.minHeight(100);
        wordsText.minWidth(100);
        wordsText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        wordsText.setFill(Color.WHITE);
        wordsText.setStrokeWidth(2);
        wordsText.setStroke(Color.BLACK);
    }

    public static void initializeGame(Stage stage) throws IOException {
        setContent(dictContent);
        set = new LinkedHashSet<>(List.of(content.replaceAll("[^a-zA-Z ]", " ").split(" ")));

        for(int i=0;i<60;i++) {
            for(int j=0;j<6;j++) {
                excluded[i][j] = '_';
            }
        }

        int c=0;
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;

        while (br.readLine() != null) {
            c++;
        }

        Random random = new Random();
        int r = random.nextInt(c);
        c = 0;
        br = new BufferedReader(new FileReader(filePath));

        while ((line = br.readLine()) != null) {
            if(c==r) {
                break;
            }
            c++;
        }

        word = line.toCharArray();
        found = new Boolean[word.length];
        wordFilled = new char[word.length];

        for(int i=0;i< word.length;i++) {
            wordFilled[i] = '_';
        }

        for(int i=0;i<word.length;i++)
            found[i] = false;

        mistakes = 0;
        corrects = 0;
        points=0;
        winner="no one";
        games++;

        createScene(stage, -1);
    }

    public static void playHangman(Stage stage, Boolean init) throws IOException {
        if(init)
            initializeGame(stage);
        else {
            Media sound;
            if(word[pos]==given.charAt(0)) {
                sound = new Media(new File("src\\main\\resources\\com\\example\\multimedia2021\\sound\\success.mp3").toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
                float numerator = odds(pos, found, String.valueOf(word)).get(given.charAt(0)), denominator=0;
                for (Map.Entry<Character, Integer> entry : odds(pos, found, String.valueOf(word)).entrySet())
                    denominator += entry.getValue();
                if(numerator/denominator>=0.6)
                    points += 5;
                else if(numerator/denominator>=0.4)
                    points += 10;
                else if(numerator/denominator>=0.25)
                    points += 15;
                else
                    points += 30;
                found[pos] = true;
                wordFilled[pos] = given.charAt(0);
                corrects++;
                if(corrects==found.length)
                    winner = "player";
            }
            else {
                sound = new Media(new File("src\\main\\resources\\com\\example\\multimedia2021\\sound\\failure.wav").toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
                excluded[pos][mistakes] = given.charAt(0);
                mistakes++;
                if(points>=15)
                    points -= 15;
                else
                    points = 0;
                if(mistakes==6)
                    winner = "computer";
            }
            createScene(stage, -1);
        }
    }
}
