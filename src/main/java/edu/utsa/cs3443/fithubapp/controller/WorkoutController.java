package edu.utsa.cs3443.fithubapp.controller;

import edu.utsa.cs3443.fithubapp.model.Exercise;
import edu.utsa.cs3443.fithubapp.model.Workout;
import edu.utsa.cs3443.fithubapp.model.WorkoutSession;
import edu.utsa.cs3443.fithubapp.util.WorkoutGenerator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;

import java.util.ArrayList;

public class WorkoutController {

    // Muscle group checkboxes
    @FXML
    private CheckBox chestCheckBox;

    @FXML
    private CheckBox backCheckBox;

    @FXML
    private CheckBox armsCheckBox;

    @FXML
    private CheckBox legsCheckBox;

    @FXML
    private CheckBox shouldersCheckBox;

    @FXML
    private CheckBox coreCheckBox;

    // Workout screen controls
    @FXML
    private ComboBox<String> equipmentComboBox;

    @FXML
    private Label workoutNameLabel;

    @FXML
    private ListView<String> exerciseListView;

    @FXML
    private Button startWorkoutButton;

    @FXML
    private Label currentExerciseLabel;

    @FXML
    private Label currentSetLabel;

    @FXML
    private ProgressBar workoutProgressBar;

    @FXML
    private Button completeSetButton;

    @FXML
    private Button skipExerciseButton;

    private Workout generatedWorkout;
    private WorkoutSession activeSession;

    // Sets up the equipment choices
    @FXML
    private void initialize() {
        equipmentComboBox.getItems().addAll(
                "Home",
                "Gym"
        );
    }

    // Generates a workout from the selected options
    @FXML
    private void handleGenerateWorkout() {

        ArrayList<String> selectedMuscleGroups =
                getSelectedMuscleGroups();

        String equipmentType =
                equipmentComboBox.getValue();

        if (selectedMuscleGroups.isEmpty()) {
            showError("Select at least one muscle group.");
            return;
        }

        if (equipmentType == null) {
            showError("Select Home or Gym equipment.");
            return;
        }

        try {
            generatedWorkout = WorkoutGenerator.generateWorkout(
                    selectedMuscleGroups,
                    equipmentType
            );

            displayWorkout();

            startWorkoutButton.setDisable(false);
            completeSetButton.setDisable(true);
            skipExerciseButton.setDisable(true);

            currentExerciseLabel.setText("Current Exercise");
            currentSetLabel.setText("Set 0 of 0");
            workoutProgressBar.setProgress(0.0);

        } catch (IllegalArgumentException exception) {
            showError(exception.getMessage());
        }
    }

    // Starts the generated workout
    @FXML
    private void handleStartWorkout() {

        if (generatedWorkout == null) {
            showError("Generate a workout first.");
            return;
        }

        try {
            activeSession = new WorkoutSession(generatedWorkout);

            startWorkoutButton.setDisable(true);
            completeSetButton.setDisable(false);
            skipExerciseButton.setDisable(false);

            updateSessionDisplay();

        } catch (IllegalArgumentException exception) {
            showError(exception.getMessage());
        }
    }

    // Completes the current set
    @FXML
    private void handleCompleteSet() {

        if (activeSession == null) {
            showError("Start the workout first.");
            return;
        }

        activeSession.completeSet();
        updateSessionDisplay();
    }

    // Skips the current exercise
    @FXML
    private void handleSkipExercise() {

        if (activeSession == null) {
            showError("Start the workout first.");
            return;
        }

        activeSession.skipExercise();
        updateSessionDisplay();
    }

    // Returns all selected muscle groups
    private ArrayList<String> getSelectedMuscleGroups() {

        ArrayList<String> muscleGroups = new ArrayList<>();

        if (chestCheckBox.isSelected()) {
            muscleGroups.add("Chest");
        }

        if (backCheckBox.isSelected()) {
            muscleGroups.add("Back");
        }

        if (armsCheckBox.isSelected()) {
            muscleGroups.add("Arms");
        }

        if (legsCheckBox.isSelected()) {
            muscleGroups.add("Legs");
        }

        if (shouldersCheckBox.isSelected()) {
            muscleGroups.add("Shoulders");
        }

        if (coreCheckBox.isSelected()) {
            muscleGroups.add("Core");
        }

        return muscleGroups;
    }

    // Displays the generated workout
    private void displayWorkout() {

        workoutNameLabel.setText(
                generatedWorkout.getName()
                        + " - "
                        + generatedWorkout.getDurationMin()
                        + " minutes"
        );

        exerciseListView.getItems().clear();

        for (Exercise exercise : generatedWorkout.getExercises()) {
            exerciseListView.getItems().add(
                    exercise.toString()
            );
        }
    }

    // Updates the current exercise and progress
    private void updateSessionDisplay() {

        if (activeSession.isWorkoutFinished()) {
            currentExerciseLabel.setText("Workout Completed");
            currentSetLabel.setText(
                    activeSession.getCompletedSets()
                            + " sets completed"
            );

            workoutProgressBar.setProgress(1.0);

            completeSetButton.setDisable(true);
            skipExerciseButton.setDisable(true);
            return;
        }

        Exercise currentExercise =
                activeSession.getCurrentExercise();

        currentExerciseLabel.setText(
                currentExercise.getName()
                        + " - "
                        + currentExercise.getReps()
                        + " reps"
        );

        currentSetLabel.setText(
                "Set "
                        + activeSession.getCurrentSet()
                        + " of "
                        + currentExercise.getSets()
        );

        workoutProgressBar.setProgress(
                activeSession.getProgressPercent() / 100.0
        );
    }

    // Displays an error message
    private void showError(String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Workout Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}