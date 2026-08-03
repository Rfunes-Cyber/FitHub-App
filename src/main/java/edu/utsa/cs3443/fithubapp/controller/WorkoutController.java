package edu.utsa.cs3443.fithubapp.controller;

import edu.utsa.cs3443.fithubapp.model.Exercise;
import edu.utsa.cs3443.fithubapp.model.Workout;
import edu.utsa.cs3443.fithubapp.model.WorkoutSession;
import edu.utsa.cs3443.fithubapp.util.ExerciseFileLoader;
import edu.utsa.cs3443.fithubapp.util.WorkoutGenerator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

//This class controls the workout feature of FitHub DONT DELETE
//Controls and manages workout selection, excersices prefrence, time, and the workout screen
public class WorkoutController {

    //Deafult dropdown when the user does not care for a excercise
    private static final String NO_PREFERENCE = "No preference";

    //Main screen sections
    @FXML
    private VBox changeWorkoutPane;

    @FXML
    private VBox recommendedWorkoutPane;

    @FXML
    private VBox workoutProgressPane;

    @FXML
    private VBox exerciseInstructionPane;

    @FXML
    private VBox upcomingExercisesPane;

    // Equipment choices
    @FXML
    private ToggleGroup equipmentToggleGroup;

    @FXML
    private ToggleButton homeWorkoutButton;

    @FXML
    private ToggleButton gymAccessButton;

    @FXML
    private ComboBox<String> exerciseComboBox;

    // Muscle choices
    @FXML
    private CheckBox backCheckBox;

    @FXML
    private CheckBox chestCheckBox;

    @FXML
    private CheckBox armsCheckBox;

    @FXML
    private CheckBox legsCheckBox;

    @FXML
    private CheckBox shouldersCheckBox;

    @FXML
    private CheckBox coreCheckBox;

    // Recommended workout controls
    @FXML
    private Label workoutNameLabel;

    @FXML
    private Label workoutDurationLabel;

    @FXML
    private ListView<String> exerciseListView;

    @FXML
    private Button startWorkoutButton;

    // Workout progress controls
    @FXML
    private Label progressWorkoutNameLabel;

    @FXML
    private Label progressWorkoutDurationLabel;

    @FXML
    private Label elapsedTimeLabel;

    @FXML
    private Label progressExerciseCountLabel;

    @FXML
    private Label progressPercentLabel;

    @FXML
    private ProgressBar workoutProgressBar;

    @FXML
    private Label currentExerciseLabel;

    @FXML
    private Label currentSetLabel;

    @FXML
    private ListView<String> upcomingExerciseListView;

    @FXML
    private Button completeSetButton;

    @FXML
    private Button pauseWorkoutButton;

    @FXML
    private Button skipExerciseButton;

    //Stores the generated wrokout and the user current workout session
    private Workout generatedWorkout;
    private WorkoutSession activeSession;

    //This tracks the workout time and checks if has been pasued
    private Timeline workoutTimer;
    private int elapsedSeconds;
    private boolean workoutPaused;

    //Sets up the screen
    //clears dropdown choices of previous and restores the deafult selection
    @FXML
    private void initialize() {
        workoutPaused = false;
        elapsedSeconds = 0;

        setupWorkoutTimer();
        updateTimerLabel();
        setupPreferenceListeners();
        resetExercisePreferences();

        showChangeWorkoutPane();
    }

    //Updates the dropdown when equipment or muscles changes
    private void setupPreferenceListeners() {
        equipmentToggleGroup.selectedToggleProperty().addListener(
                (observable, oldValue, newValue) ->
                        updateExercisePreferences()
        );

        backCheckBox.selectedProperty().addListener(
                (observable, oldValue, newValue) ->
                        updateExercisePreferences()
        );

        chestCheckBox.selectedProperty().addListener(
                (observable, oldValue, newValue) ->
                        updateExercisePreferences()
        );

        armsCheckBox.selectedProperty().addListener(
                (observable, oldValue, newValue) ->
                        updateExercisePreferences()
        );

        legsCheckBox.selectedProperty().addListener(
                (observable, oldValue, newValue) ->
                        updateExercisePreferences()
        );

        shouldersCheckBox.selectedProperty().addListener(
                (observable, oldValue, newValue) ->
                        updateExercisePreferences()
        );

        coreCheckBox.selectedProperty().addListener(
                (observable, oldValue, newValue) ->
                        updateExercisePreferences()
        );
    }

    //Loads exercises matching the selected options
    private void updateExercisePreferences() {
        String equipmentType = getSelectedEquipmentType();

        ArrayList<String> selectedMuscleGroups =
                getSelectedMuscleGroups();

        resetExercisePreferences();

        if (equipmentType == null
                || selectedMuscleGroups.isEmpty()) {
            return;
        }

        try {
            ArrayList<String> matchingExerciseNames =
                    ExerciseFileLoader.loadExerciseNames(
                            selectedMuscleGroups,
                            equipmentType
                    );

            exerciseComboBox.getItems().addAll(
                    matchingExerciseNames
            );

        } catch (IllegalArgumentException
                 | IllegalStateException exception) {

            resetExercisePreferences();
        }
    }

    //Resets the dropdown to its default option
    private void resetExercisePreferences() {
        exerciseComboBox.getItems().clear();
        exerciseComboBox.getItems().add(NO_PREFERENCE);
        exerciseComboBox.setValue(NO_PREFERENCE);
    }

    //Creates a  workout timer
    private void setupWorkoutTimer() {
        workoutTimer = new Timeline(
                new KeyFrame(
                        Duration.seconds(1),
                        event -> {
                            elapsedSeconds++;
                            updateTimerLabel();
                        }
                )
        );

        workoutTimer.setCycleCount(Timeline.INDEFINITE);
    }

    //Displays time as minutes and seconds
    private void updateTimerLabel() {
        int minutes = elapsedSeconds / 60;
        int seconds = elapsedSeconds % 60;

        elapsedTimeLabel.setText(
                String.format(
                        "Elapsed Time: %02d:%02d",
                        minutes,
                        seconds
                )
        );
    }

    //Generates a workout from the selected options and opens the recommended workout screen
    @FXML
    private void handleGenerateWorkout() {
        ArrayList<String> selectedMuscleGroups =
                getSelectedMuscleGroups();

        String equipmentType = getSelectedEquipmentType();

        if (equipmentType == null) {
            showError(
                    "Select Home Workout or Gym Access."
            );
            return;
        }

        if (selectedMuscleGroups.isEmpty()) {
            showError(
                    "Select at least one muscle group."
            );
            return;
        }

        String preferredExercise =
                exerciseComboBox.getValue();

        if (preferredExercise == null
                || preferredExercise.isBlank()) {

            preferredExercise = NO_PREFERENCE;
        }

        try {
            generatedWorkout =
                    WorkoutGenerator.generateWorkout(
                            selectedMuscleGroups,
                            equipmentType,
                            preferredExercise
                    );

            activeSession = null;
            workoutPaused = false;

            workoutTimer.stop();
            elapsedSeconds = 0;
            updateTimerLabel();

            displayGeneratedWorkout();
            showRecommendedWorkoutPane();

        } catch (IllegalArgumentException
                 | IllegalStateException exception) {

            showError(exception.getMessage());
        }
    }

    //Starts the workout and timer
    @FXML
    private void handleStartWorkout() {
        if (generatedWorkout == null) {
            showError("Generate a workout first.");
            return;
        }

        if (generatedWorkout.getExercises().isEmpty()) {
            showError(
                    "The generated workout has no exercises."
            );
            return;
        }

        try {
            activeSession =
                    new WorkoutSession(generatedWorkout);

            workoutPaused = false;
            elapsedSeconds = 0;

            updateTimerLabel();
            workoutTimer.playFromStart();

            exerciseInstructionPane.setVisible(true);
            exerciseInstructionPane.setManaged(true);

            upcomingExercisesPane.setVisible(true);
            upcomingExercisesPane.setManaged(true);

            completeSetButton.setDisable(false);
            pauseWorkoutButton.setDisable(false);
            skipExerciseButton.setDisable(false);

            pauseWorkoutButton.setText("Pause");

            updateProgressWorkoutInformation();
            updateSessionDisplay();
            showWorkoutProgressPane();

        } catch (IllegalArgumentException exception) {
            showError(exception.getMessage());
        }
    }

    //Completes the current set
    @FXML
    private void handleCompleteSet() {
        if (activeSession == null) {
            showError("Start the workout first.");
            return;
        }

        if (workoutPaused) {
            showError(
                    "Resume the workout before completing a set."
            );
            return;
        }

        activeSession.completeSet();
        updateSessionDisplay();
    }

    //Skips the current exercise
    @FXML
    private void handleSkipExercise() {
        if (activeSession == null) {
            showError("Start the workout first.");
            return;
        }

        if (workoutPaused) {
            showError(
                    "Resume the workout before skipping an exercise."
            );
            return;
        }

        activeSession.skipExercise();
        updateSessionDisplay();
    }

    //Pauses or resumes the workout and timer
    @FXML
    private void handlePauseWorkout() {
        if (activeSession == null
                || activeSession.isWorkoutFinished()) {
            return;
        }

        workoutPaused = !workoutPaused;

        if (workoutPaused) {
            workoutTimer.pause();

            pauseWorkoutButton.setText("Resume");
            completeSetButton.setDisable(true);
            skipExerciseButton.setDisable(true);

        } else {
            workoutTimer.play();

            pauseWorkoutButton.setText("Pause");
            completeSetButton.setDisable(false);
            skipExerciseButton.setDisable(false);
        }
    }

    //Opens the Change Workout screen
    @FXML
    private void handleShowChangeWorkout() {
        showChangeWorkoutPane();
    }

    //Returns to the generated workout
    @FXML
    private void handleBackToWorkout() {
        if (generatedWorkout == null) {
            return;
        }

        showRecommendedWorkoutPane();
    }

    //Returns from the active workout
    @FXML
    private void handleBackToRecommended() {
        if (workoutTimer != null) {
            workoutTimer.pause();
        }

        if (activeSession != null
                && !activeSession.isWorkoutFinished()) {

            workoutPaused = true;
            pauseWorkoutButton.setText("Resume");
        }

        if (generatedWorkout == null) {
            showChangeWorkoutPane();
        } else {
            showRecommendedWorkoutPane();
        }
    }

    //Returns Home or Gym
    private String getSelectedEquipmentType() {
        if (homeWorkoutButton.isSelected()) {
            return "Home";
        }

        if (gymAccessButton.isSelected()) {
            return "Gym";
        }

        return null;
    }

    //Returns all selected muscle groups
    private ArrayList<String> getSelectedMuscleGroups() {
        ArrayList<String> muscleGroups =
                new ArrayList<>();

        if (backCheckBox.isSelected()) {
            muscleGroups.add("Back");
        }

        if (chestCheckBox.isSelected()) {
            muscleGroups.add("Chest");
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

    //Displays the generated workout
    private void displayGeneratedWorkout() {
        workoutNameLabel.setText(
                generatedWorkout.getName()
        );

        workoutDurationLabel.setText(
                "Time & Duration: "
                        + generatedWorkout.getDurationMin()
                        + " min"
        );

        exerciseListView.getItems().clear();

        for (Exercise exercise
                : generatedWorkout.getExercises()) {

            exerciseListView.getItems().add(
                    formatExercise(exercise)
            );
        }

        startWorkoutButton.setDisable(
                generatedWorkout.getExercises().isEmpty()
        );
    }

    //Displays workout information on the progress screen
    private void updateProgressWorkoutInformation() {
        progressWorkoutNameLabel.setText(
                generatedWorkout.getName()
        );

        progressWorkoutDurationLabel.setText(
                generatedWorkout.getDurationMin()
                        + " minute workout"
        );
    }

    //Updates the current workout session
    private void updateSessionDisplay() {
        if (activeSession.isWorkoutFinished()) {
            showCompletedWorkout();
            return;
        }

        Exercise currentExercise =
                activeSession.getCurrentExercise();

        currentExerciseLabel.setText(
                currentExercise.getName()
                        + "\n"
                        + currentExercise.getReps()
                        + " reps"
        );

        currentSetLabel.setText(
                "Set "
                        + activeSession.getCurrentSet()
                        + " of "
                        + currentExercise.getSets()
        );

        double progressPercent =
                activeSession.getProgressPercent();

        workoutProgressBar.setProgress(
                progressPercent / 100.0
        );

        progressPercentLabel.setText(
                Math.round(progressPercent) + "%"
        );

        updateExerciseCount(currentExercise);
        updateUpcomingExercises(currentExercise);
    }

    //Displays the completed workout state
    private void showCompletedWorkout() {
        workoutTimer.stop();

        currentExerciseLabel.setText(
                "Workout Completed"
        );

        currentSetLabel.setText(
                activeSession.getCompletedSets()
                        + " sets completed"
        );

        int exerciseCount =
                generatedWorkout.getExercises().size();

        progressExerciseCountLabel.setText(
                exerciseCount
                        + " of "
                        + exerciseCount
                        + " exercises"
        );

        progressPercentLabel.setText("100%");
        workoutProgressBar.setProgress(1.0);

        exerciseInstructionPane.setVisible(false);
        exerciseInstructionPane.setManaged(false);

        upcomingExercisesPane.setVisible(false);
        upcomingExercisesPane.setManaged(false);

        completeSetButton.setDisable(true);
        pauseWorkoutButton.setDisable(true);
        skipExerciseButton.setDisable(true);

        pauseWorkoutButton.setText("Pause");
        workoutPaused = false;
    }

    //Updates the current exercise number
    private void updateExerciseCount(
            Exercise currentExercise) {

        List<Exercise> exercises =
                generatedWorkout.getExercises();

        int currentIndex =
                exercises.indexOf(currentExercise);

        if (currentIndex < 0) {
            currentIndex = 0;
        }

        progressExerciseCountLabel.setText(
                "Exercise "
                        + (currentIndex + 1)
                        + " of "
                        + exercises.size()
        );
    }

    //Displays the remaining exercises
    private void updateUpcomingExercises(
            Exercise currentExercise) {

        upcomingExerciseListView.getItems().clear();

        List<Exercise> exercises =
                generatedWorkout.getExercises();

        int currentIndex =
                exercises.indexOf(currentExercise);

        if (currentIndex < 0) {
            currentIndex = 0;
        }

        for (int index = currentIndex + 1;
             index < exercises.size();
             index++) {

            upcomingExerciseListView.getItems().add(
                    formatExercise(exercises.get(index))
            );
        }

        if (upcomingExerciseListView
                .getItems()
                .isEmpty()) {

            upcomingExerciseListView.getItems().add(
                    "No upcoming exercises"
            );
        }
    }

    //Formats the exercise for a list
    private String formatExercise(Exercise exercise) {
        return exercise.getName()
                + "  •  "
                + exercise.getSets()
                + " sets × "
                + exercise.getReps()
                + " reps";
    }

    //Shows only the Change Workout screen
    private void showChangeWorkoutPane() {
        changeWorkoutPane.setVisible(true);
        changeWorkoutPane.setManaged(true);

        recommendedWorkoutPane.setVisible(false);
        recommendedWorkoutPane.setManaged(false);

        workoutProgressPane.setVisible(false);
        workoutProgressPane.setManaged(false);
    }

    //Shows only the Recommended Workout screen
    private void showRecommendedWorkoutPane() {
        changeWorkoutPane.setVisible(false);
        changeWorkoutPane.setManaged(false);

        recommendedWorkoutPane.setVisible(true);
        recommendedWorkoutPane.setManaged(true);

        workoutProgressPane.setVisible(false);
        workoutProgressPane.setManaged(false);
    }

    //Shows only the Workout In Progress screen
    private void showWorkoutProgressPane() {
        changeWorkoutPane.setVisible(false);
        changeWorkoutPane.setManaged(false);

        recommendedWorkoutPane.setVisible(false);
        recommendedWorkoutPane.setManaged(false);

        workoutProgressPane.setVisible(true);
        workoutProgressPane.setManaged(true);
    }

    //Displays an error message
    private void showError(String message) {
        Alert alert =
                new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Workout Error");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}