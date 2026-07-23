package edu.utsa.cs3443.fithubapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class FitHubApplication extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        primaryStage.setTitle("FitHub");
        primaryStage.setResizable(false);
        showScreen("login.fxml");
        primaryStage.show();
    }

    public static void showScreen(String file) throws IOException {
        FXMLLoader loader = new FXMLLoader(FitHubApplication.class.getResource("/edu/utsa/cs3443/fithubapp/fxml/" + file));
        Parent root = loader.load();
        Scene scene = new Scene(root, 540, 960);
        scene.getStylesheets().add(Objects.requireNonNull(
                FitHubApplication.class.getResource("/edu/utsa/cs3443/fithubapp/css/styles.css")).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
