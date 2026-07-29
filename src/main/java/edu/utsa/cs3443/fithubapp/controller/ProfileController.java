package edu.utsa.cs3443.fithubapp.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class ProfileController {

    @FXML
    private void openDashboard(MouseEvent event) throws IOException {
        ScreenNavigator.open(event, "dashboard.fxml");
    }

    @FXML
    private void openWorkouts(MouseEvent event) throws IOException {
        ScreenNavigator.open(event, "workout.fxml");
    }

    @FXML
    private void openSettings(MouseEvent event) throws IOException {
        ScreenNavigator.open(event, "settings.fxml");
    }
}
