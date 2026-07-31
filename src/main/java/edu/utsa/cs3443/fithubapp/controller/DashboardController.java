package edu.utsa.cs3443.fithubapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import edu.utsa.cs3443.fithubapp.model.CalorieHistoryEntry;
import edu.utsa.cs3443.fithubapp.model.DashboardSummary;
import edu.utsa.cs3443.fithubapp.model.MacroNutrient;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;

/**
 * Controls the FitHub dashboard screen.
 * It prepares dashboard data and displays it in dashboard.fxml.
 */
public class DashboardController {

    private DashboardSummary dashboardSummary;
    private final List<MacroNutrient> macroNutrients;
    private final List<CalorieHistoryEntry> calorieHistory;

    @FXML private Label welcomeLabel;
    @FXML private Label calorieGoalLabel;
    @FXML private Label caloriesConsumedLabel;
    @FXML private Label caloriesRemainingLabel;
    @FXML private Label streakLabel;
    @FXML private ProgressBar calorieProgressBar;

    @FXML private Label proteinAmountLabel;
    @FXML private Label carbsAmountLabel;
    @FXML private Label fatAmountLabel;
    @FXML private Label fiberAmountLabel;

    @FXML private ProgressBar proteinProgressBar;
    @FXML private ProgressBar carbsProgressBar;
    @FXML private ProgressBar fatProgressBar;
    @FXML private ProgressBar fiberProgressBar;

    @FXML private BarChart<String, Number> calorieHistoryChart;
    /**
     * Creates the lists used to store calorie and nutrition data.
     */
    public DashboardController() {
        macroNutrients = new ArrayList<>();
        calorieHistory = new ArrayList<>();
    }

    /**
     * JavaFX calls this method automatically after dashboard.fxml is loaded.
     */
    @FXML
    private void initialize() {
        loadSampleDashboardData();
        updateDashboardView();
    }

    /**
     * Loads sample data for the current project stage.
     * This can later be replaced with CSV or database loading.
     */
    public void loadSampleDashboardData() {
        dashboardSummary = new DashboardSummary(
                "U001", "Abdulrahman", 2200, 1650, 3);

        macroNutrients.clear();
        macroNutrients.add(new MacroNutrient("Protein", 120, 160));
        macroNutrients.add(new MacroNutrient("Carbs", 180, 250));
        macroNutrients.add(new MacroNutrient("Fat", 60, 80));
        macroNutrients.add(new MacroNutrient("Fiber", 25, 30));

        calorieHistory.clear();
        calorieHistory.add(new CalorieHistoryEntry("Mon", 1600));
        calorieHistory.add(new CalorieHistoryEntry("Tue", 2100));
        calorieHistory.add(new CalorieHistoryEntry("Wed", 2300));
        calorieHistory.add(new CalorieHistoryEntry("Thu", 2000));
        calorieHistory.add(new CalorieHistoryEntry("Fri", 1700));
        calorieHistory.add(new CalorieHistoryEntry("Sat", 1900));
    }

    /**
     * Updates the dashboard labels, progress bars, and calorie chart.
     */
    private void updateDashboardView() {
        if (dashboardSummary == null) {
            return;
        }

        welcomeLabel.setText("Good morning, " + dashboardSummary.getUserName() + "!");
        calorieGoalLabel.setText(String.format("%,d kcal", dashboardSummary.getCalorieGoal()));
        caloriesConsumedLabel.setText(String.format("%,d kcal", dashboardSummary.getCaloriesConsumed()));
        caloriesRemainingLabel.setText(String.format("%,d kcal", calculateCaloriesRemaining()));
        streakLabel.setText(dashboardSummary.getDayStreak() + " day streak");

        double calorieProgress = dashboardSummary.getCalorieGoal() == 0
                ? 0.0
                : (double) dashboardSummary.getCaloriesConsumed()
                        / dashboardSummary.getCalorieGoal();
        calorieProgressBar.setProgress(clampProgress(calorieProgress));

        updateMacroControls("Protein", proteinAmountLabel, proteinProgressBar);
        updateMacroControls("Carbs", carbsAmountLabel, carbsProgressBar);
        updateMacroControls("Fat", fatAmountLabel, fatProgressBar);
        updateMacroControls("Fiber", fiberAmountLabel, fiberProgressBar);

        updateCalorieHistoryChart();
    }

    private void updateMacroControls(
            String macroName, Label amountLabel, ProgressBar progressBar) {

        MacroNutrient macro = findMacro(macroName);
        if (macro == null) {
            amountLabel.setText("No data");
            progressBar.setProgress(0);
            return;
        }

        amountLabel.setText(String.format(
                "%dg / %dg",
                macro.getConsumedGrams(),
                macro.getTargetGrams()));

        double progress = macro.getTargetGrams() == 0
                ? 0.0
                : (double) macro.getConsumedGrams() / macro.getTargetGrams();

        progressBar.setProgress(clampProgress(progress));
    }

    private MacroNutrient findMacro(String name) {
        for (MacroNutrient macro : macroNutrients) {
            if (macro.getName().equalsIgnoreCase(name)) {
                return macro;
            }
        }
        return null;
    }

    private void updateCalorieHistoryChart() {
        calorieHistoryChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Calories");

        for (CalorieHistoryEntry entry : calorieHistory) {
            series.getData().add(
                    new XYChart.Data<>(
                            entry.getDayLabel(),
                            entry.getCaloriesConsumed()
                    ));
        }

        calorieHistoryChart.getData().add(series);
    }

    private double clampProgress(double progress) {
        return Math.max(0.0, Math.min(1.0, progress));
    }

    public DashboardSummary getDashboardSummary() {
        return dashboardSummary;
    }

    public List<MacroNutrient> getMacroNutrients() {
        return macroNutrients;
    }

    public List<CalorieHistoryEntry> getCalorieHistory() {
        return calorieHistory;
    }

    public int calculateCaloriesRemaining() {
        if (dashboardSummary == null) {
            return 0;
        }
        return dashboardSummary.getCaloriesRemaining();
    }
    /**
     * Opens the Profile screen.
     */
    @FXML
    private void openProfile(MouseEvent event) throws IOException {
        ScreenNavigator.open(event, "profile.fxml");
    }
    /**
     * Opens the Workout screen.
     */
    @FXML
    private void openWorkouts(MouseEvent event) throws IOException {
        ScreenNavigator.open(event, "workout.fxml");
    }
    /**
     * Opens the Settings screen.
     */
    @FXML
    private void openSettings(MouseEvent event) throws IOException {
        ScreenNavigator.open(event, "settings.fxml");
    }

}
