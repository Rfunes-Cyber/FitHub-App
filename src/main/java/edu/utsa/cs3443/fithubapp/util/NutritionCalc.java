package edu.utsa.cs3443.fithubapp.util;

import edu.utsa.cs3443.fithubapp.model.NutritionResults;

public class NutritionCalc {
    private NutritionCalc() {
    }
    public static NutritionResults calculate(int age, double heightCm, double weightKg, String gender, String activityLevel, String fitnessGoal) {
        validateInputs(age, heightCm, weightKg, gender, activityLevel, fitnessGoal);

        double bmr = calculateBmr(age, heightCm, weightKg, gender);
        double tdee = bmr * getActivityMultiplier(activityLevel);
        double dailyCalories = adjustCaloriesForGoal(tdee, fitnessGoal);
        double proteinGrams = dailyCalories * 0.30 / 4.0;
        double carbohydrateGrams = dailyCalories * 0.40 / 4.0;
        double fatGrams = dailyCalories * 0.30 / 9.0;

        return new NutritionResults(bmr, tdee, dailyCalories, proteinGrams, carbohydrateGrams, fatGrams);
    }

    private static double calculateBmr(int age, double heightCm, double weightKg, String gender) {
        double baseBmr = (10 * weightKg) + (6.25 * heightCm) - (5 * age);

        if (gender.equalsIgnoreCase("Male")) {
            return baseBmr + 5;
        }
        if (gender.equalsIgnoreCase("Female")) {
            return baseBmr - 161;
        }
        return baseBmr - 78;
    }

    private static double getActivityMultiplier(String activityLevel) {

        switch (activityLevel) {
            case "Little to No Exercise":
                return 1.20;
            case "Lightly Active":
                return 1.375;
            case "Moderately Active":
                return 1.55;
            case "Very Active":
                return 1.725;
            default:
                throw new IllegalArgumentException("Invalid activity level.");
        }
    }

    private static double adjustCaloriesForGoal(double tdee, String fitnessGoal) {

        switch (fitnessGoal) {
            case "Maintain Weight":
                return tdee;
            case "Weight Loss":
                return tdee - 500;
            case "Extreme Weight Loss":
                return tdee - 1000;
            case "Gain Weight":
                return tdee + 500;
            case "Extreme Weight Gain":
                return tdee + 1000;
            default:
                throw new IllegalArgumentException("Invalid fitness goal.");
        }
    }

    private static void validateInputs(int age, double heightCm, double weightKg, String gender, String activityLevel, String fitnessGoal) {

        if (age < 1 || age > 100) {
            throw new IllegalArgumentException(
                    "Age must be between 1 and 100.");
        }

        if (heightCm <= 0) {
            throw new IllegalArgumentException(
                    "Height must be greater than zero.");
        }

        if (weightKg <= 0) {
            throw new IllegalArgumentException(
                    "Weight must be greater than zero.");
        }

        if (gender == null || activityLevel == null || fitnessGoal == null) {
            throw new IllegalArgumentException(
                    "Please complete every field.");
        }
    }
}