package com.summ.radio;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private Stage stage;

    private double clickX;
    private double clickY;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        Scene scene = new Scene(root, 400, 150);
        stage = primaryStage;

        final ContextMenu contextMenu = new ContextMenu();
        MenuItem exit = new MenuItem("Exit");
        contextMenu.getItems().addAll(exit);
        exit.setOnAction(event -> Platform.exit());

        root.setOnMousePressed(event -> {
            clickX = event.getSceneX();
            clickY = event.getSceneY();

            if (event.isPrimaryButtonDown()) {
                contextMenu.hide();
            } else if (event.isSecondaryButtonDown()) {
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
        stage.setX(event.getScreenX() - clickX);
        stage.setY(event.getScreenY() - clickY);
    }

    public static void main(String[] args) {
        launch(args);
    }
}