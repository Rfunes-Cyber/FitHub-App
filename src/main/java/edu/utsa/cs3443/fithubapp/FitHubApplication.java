package edu.utsa.cs3443.fithubapp;

import javafx.application.Application;
import javafx.stage.Stage;

public class FitHubApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("FitHub");
        stage.show();
    }
}
