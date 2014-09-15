package com.summ.radio;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private Stage stage;

    private double clickX;
    private double clickY;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        Scene scene = new Scene(root, 400, 150);
        stage = primaryStage;

        final ContextMenu contextMenu = getContextMenu();

        root.setOnMousePressed(event -> {
            clickX = event.getSceneX();
            clickY = event.getSceneY();

            if (event.isPrimaryButtonDown()) {
                contextMenu.hide();
            } else if (event.isSecondaryButtonDown()) {
                contextMenu.hide();
                contextMenu.show(root, event.getScreenX(), event.getScreenY());
            }
        });

        root.setOnMouseDragged(this::move);

        primaryStage.setTitle("Summ Radio");
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }

    private void move(MouseEvent event) {
        if (!event.isPrimaryButtonDown()) return;
        stage.setX(event.getScreenX() - clickX);
        stage.setY(event.getScreenY() - clickY);
    }

    private ContextMenu getContextMenu() {
        ContextMenu contextMenu = new ContextMenu();

        Menu size = new Menu("Size");
        MenuItem separator = new SeparatorMenuItem();
        MenuItem options = new MenuItem("Options");
        MenuItem close = new MenuItem("Close");

        MenuItem verySmall = new MenuItem("Very small");
        MenuItem small = new MenuItem("Small");
        MenuItem medium = new MenuItem("Medium");
        MenuItem large = new MenuItem("Large");

        size.getItems().addAll(verySmall, small, medium, large);
        contextMenu.getItems().addAll(size, separator, options, close);

        close.setOnAction(event -> Platform.exit());

        return contextMenu;
    }
}