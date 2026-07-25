package edu.utsa.cs3443.fithubapp.model;

/**
 * Model class for one macronutrient shown on the dashboard.
 * Examples: Protein, Carbs, Fat, and Fiber.
 */
public class MacroNutrient {
    private String name;
    private int consumedGrams;
    private int targetGrams;

    public MacroNutrient(String name, int consumedGrams, int targetGrams) {
        this.name = name;
        this.consumedGrams = consumedGrams;
        this.targetGrams = targetGrams;
    }

    public double getProgressPercent() {
        if (targetGrams == 0) {
            return 0.0;
        }
        return (consumedGrams * 100.0) / targetGrams;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getConsumedGrams() {
        return consumedGrams;
    }

    public void setConsumedGrams(int consumedGrams) {
        this.consumedGrams = consumedGrams;
    }

    public int getTargetGrams() {
        return targetGrams;
    }

    public void setTargetGrams(int targetGrams) {
        this.targetGrams = targetGrams;
    }
}
