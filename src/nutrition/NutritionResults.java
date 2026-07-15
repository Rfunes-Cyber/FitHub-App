package nutrition;

public class NutritionResults {
    private final double bmr;
    private final double tdee;
    private final double dailyCalories;
    private final double proteinGrams;
    private final double carbohydrateGrams;
    private final double fatGrams;

    public NutritionResults( double bmr,double tdee, double dailyCalories, double proteinGrams, double carbohydrateGrams, double fatGrams) {
        this.bmr = bmr;
        this.tdee = tdee;
        this.dailyCalories = dailyCalories;
        this.proteinGrams = proteinGrams;
        this.carbohydrateGrams = carbohydrateGrams;
        this.fatGrams = fatGrams;
    }
    public double getBmr() {
        return bmr;
    }
    public double getTdee() {
        return tdee;
    }
    public double getDailyCalories () {
        return dailyCalories;
    }
    public double getProteinGrams() {
        return proteinGrams;
    }
    public double getCarbohydrateGrams() {
        return fatGrams;
    }
}