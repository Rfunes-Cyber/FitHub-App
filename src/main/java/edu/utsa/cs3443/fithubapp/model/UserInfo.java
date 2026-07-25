package edu.utsa.cs3443.fithubapp.model;

/** Stores the information entered during nutrition onboarding. */
public class UserInfo {
    private int age;
    private double heightCm;
    private double weightKg;
    private String gender;
    private String activityLevel;
    private String fitnessGoal;

    public UserInfo() {
    }

    public UserInfo(int age, double heightCm, double weightKg, String gender,
                    String activityLevel, String fitnessGoal) {
        this.age = age;
        this.heightCm = heightCm;
        this.weightKg = weightKg;
        this.gender = gender;
        this.activityLevel = activityLevel;
        this.fitnessGoal = fitnessGoal;
    }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public double getHeightCm() { return heightCm; }
    public void setHeightCm(double heightCm) { this.heightCm = heightCm; }
    public double getWeightKg() { return weightKg; }
    public void setWeightKg(double weightKg) { this.weightKg = weightKg; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getActivityLevel() { return activityLevel; }
    public void setActivityLevel(String activityLevel) { this.activityLevel = activityLevel; }
    public String getFitnessGoal() { return fitnessGoal; }
    public void setFitnessGoal(String fitnessGoal) { this.fitnessGoal = fitnessGoal; }
}
