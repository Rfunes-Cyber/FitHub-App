package edu.utsa.cs3443.fithubapp.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
/**
 * Controls the Settings screen.
 */
public class SettingsController {
    /**
     * Opens the Dashboard screen.
     */
    @FXML
    private void openDashboard(MouseEvent event) throws IOException {
        ScreenNavigator.open(event, "dashboard.fxml");
    }
    /**
     * Opens the Workout screen.
     */
    @FXML
    private void openWorkouts(MouseEvent event) throws IOException {
        ScreenNavigator.open(event, "workout.fxml");
    }
    /**
     * Opens the Profile screen.
     */
    @FXML
    private void openProfile(MouseEvent event) throws IOException {
        ScreenNavigator.open(event, "profile.fxml");
    }
}
