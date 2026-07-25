package edu.utsa.cs3443.fithubapp.model;

/**
 * Model class for one day of calorie history.
 * The dashboard can use this data to display a weekly bar chart.
 */
public class CalorieHistoryEntry {
    private String dayLabel;
    private int caloriesConsumed;

    public CalorieHistoryEntry(String dayLabel, int caloriesConsumed) {
        this.dayLabel = dayLabel;
        this.caloriesConsumed = caloriesConsumed;
    }

    public String getDayLabel() {
        return dayLabel;
    }

    public void setDayLabel(String dayLabel) {
        this.dayLabel = dayLabel;
    }

    public int getCaloriesConsumed() {
        return caloriesConsumed;
    }

    public void setCaloriesConsumed(int caloriesConsumed) {
        this.caloriesConsumed = caloriesConsumed;
    }
}
