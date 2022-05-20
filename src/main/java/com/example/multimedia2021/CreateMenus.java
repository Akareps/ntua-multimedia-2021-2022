package com.example.multimedia2021;

import javafx.scene.Group;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.multimedia2021.PlayHangman.initializeGame;

public class CreateMenus {
    private static Menu menu1 = new Menu("Application");
    private static Menu menu2 = new Menu("Details");
    private static Stage stage1;

    /**
     * This method creates the application menu and the details menu
     * which are shown in the top left corner of the screen.
     * Calls the corresponding private methods in order to create each
     * option in the corresponding menu.
     * @param root the group of the stage, passed as a parameter for ease of access
     * @param stage the main stage. The menus are shown on it
     */
    public static void createMenus(Group root, Stage stage) {
        menu1 = new Menu("Application");
        menu2 = new Menu("Details");
        stage1 = stage;

        menu1.setGraphic(new ImageView("file:src\\main\\resources\\com\\example\\multimedia2021\\images\\hung.png"));

        MenuBar menuBar1 = new MenuBar();
        menuBar1.getMenus().add(menu1);

        MenuBar menuBar2 = new MenuBar();
        menuBar2.getMenus().add(menu2);

        HBox hbox3 = new HBox();
        hbox3.getChildren().add(menuBar1);
        hbox3.getChildren().add(menuBar2);
        root.getChildren().add(hbox3);

        createStart();
        createCreate();
        createDictionary();
        createLoad();
        createRounds();
        createSolution();
        createExit();
    }

    private static void createStart() {
        MenuItem start = new MenuItem("Start");
        start.setOnAction(e -> {
            try {
                initializeGame(stage1);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        menu1.getItems().add(start);
    }

    private static void createLoad() {
        MenuItem load = new MenuItem("Load");
        load.setOnAction(e -> LoadDictionary.loadDictionary());
        menu1.getItems().add(load);
    }

    private static void createCreate() {
        MenuItem create = new MenuItem("Create");
        create.setOnAction(e -> CreateDictionary.loadURL());
        menu1.getItems().add(create);
    }

    private static void createExit() {
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e -> stage1.close());
        menu1.getItems().add(exit);
    }

    private static void createDictionary() {
        MenuItem dictionary = new MenuItem("Dictionary");
        dictionary.setOnAction(e -> DictionaryDetails.dictionaryDetails());
        menu2.getItems().add(dictionary);
    }

    private static void createRounds() {
        MenuItem rounds = new MenuItem("Rounds");
        rounds.setOnAction(e -> CreateRounds.createRounds());
        menu2.getItems().add(rounds);
    }

    private static void createSolution() {
        MenuItem solution = new MenuItem("Solution");
        solution.setOnAction(e -> CreateSolution.createSolution(stage1));
        menu2.getItems().add(solution);
    }
}
