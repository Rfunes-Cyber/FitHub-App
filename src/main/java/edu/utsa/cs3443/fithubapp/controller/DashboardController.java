package edu.utsa.cs3443.fithubapp.controller;

import java.util.ArrayList;
import java.util.List;

import edu.utsa.cs3443.fithubapp.model.CalorieHistoryEntry;
import edu.utsa.cs3443.fithubapp.model.DashboardSummary;
import edu.utsa.cs3443.fithubapp.model.MacroNutrient;

/**
 * Controller class for the FitHub dashboard screen.
 * This class coordinates dashboard data and sends it to the UI later.
 * The code is a skeleton for the design assignment, not the final working app.
 */
public class DashboardController {
    private DashboardSummary dashboardSummary;
    private List<MacroNutrient> macroNutrients;
    private List<CalorieHistoryEntry> calorieHistory;

    public DashboardController() {
        macroNutrients = new ArrayList<>();
        calorieHistory = new ArrayList<>();
    }

    /**
     * Loads sample dashboard data for the design stage.
     * Later this can be changed to load data from CSV files or a database.
     */
    public void loadSampleDashboardData() {
        dashboardSummary = new DashboardSummary("U001", "Abdulrahman", 2200, 1650, 3);

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
}
