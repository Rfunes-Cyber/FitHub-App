package edu.utsa.cs3443.fithubapp.model;

/**
 * Model class that stores the main numbers shown on the dashboard screen.
 * This class does not control the UI. It only stores and calculates dashboard data.
 */
public class DashboardSummary {
    private String userId;
    private String userName;
    private int calorieGoal;
    private int caloriesConsumed;
    private int dayStreak;

    public DashboardSummary(String userId, String userName, int calorieGoal, int caloriesConsumed, int dayStreak) {
        this.userId = userId;
        this.userName = userName;
        this.calorieGoal = calorieGoal;
        this.caloriesConsumed = caloriesConsumed;
        this.dayStreak = dayStreak;
    }

    public int getCaloriesRemaining() {
        return calorieGoal - caloriesConsumed;
    }

    public double getDailyGoalPercent() {
        if (calorieGoal == 0) {
            return 0.0;
        }
        return (caloriesConsumed * 100.0) / calorieGoal;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCalorieGoal() {
        return calorieGoal;
    }

    public void setCalorieGoal(int calorieGoal) {
        this.calorieGoal = calorieGoal;
    }

    public int getCaloriesConsumed() {
        return caloriesConsumed;
    }

    public void setCaloriesConsumed(int caloriesConsumed) {
        this.caloriesConsumed = caloriesConsumed;
    }

    public int getDayStreak() {
        return dayStreak;
    }

    public void setDayStreak(int dayStreak) {
        this.dayStreak = dayStreak;
    }
}
