
package com.thecodinginterface.sorting;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        var loader = new FXMLLoader(getClass().getResource("App.fxml"));
        var anchorPane = (AnchorPane) loader.load();
        primaryStage.setScene(new Scene(anchorPane));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
