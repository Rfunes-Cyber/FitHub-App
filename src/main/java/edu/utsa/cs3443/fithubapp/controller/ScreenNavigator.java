package edu.utsa.cs3443.fithubapp.controller;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
/**
 * Handles navigation between the application's FXML screens.
 */
public final class ScreenNavigator {
    /**
     * Prevents this utility class from being instantiated.
     */
    private ScreenNavigator() {
    }
    /**
     * Opens the selected FXML screen in the current application window.
     *
     * @param event the mouse event used to locate the current window
     * @param fxmlFile the name of the FXML file to open
     * @throws IOException if the FXML file cannot be found or loaded
     */
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
