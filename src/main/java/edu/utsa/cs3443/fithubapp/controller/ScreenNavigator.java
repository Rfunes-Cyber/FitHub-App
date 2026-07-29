package edu.utsa.cs3443.fithubapp.controller;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public final class ScreenNavigator {

    private ScreenNavigator() {
    }

    public static void open(MouseEvent event, String fxmlFile) throws IOException {
        var resource = ScreenNavigator.class.getResource(
                "/edu/utsa/cs3443/fithubapp/fxml/" + fxmlFile);

        if (resource == null) {
            throw new IOException("FXML file not found: " + fxmlFile);
        }

        Parent root = FXMLLoader.load(resource);

        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();

        stage.setScene(new Scene(root, 390, 844));
        stage.setWidth(390);
        stage.setHeight(844);
        stage.setResizable(false);
        stage.centerOnScreen();
    }
}
