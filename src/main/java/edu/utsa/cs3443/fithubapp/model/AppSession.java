package edu.utsa.cs3443.fithubapp.model;

/** Holds temporary data while the user moves between FitHub screens. */
public final class AppSession {
    private static String username;
    private static UserInfo userInfo;
    private static NutritionResults nutritionResults;

    private AppSession() { }

    public static String getUsername() { return username; }
    public static void setUsername(String username) { AppSession.username = username; }
    public static UserInfo getUserInfo() { return userInfo; }
    public static void setUserInfo(UserInfo userInfo) { AppSession.userInfo = userInfo; }
    public static NutritionResults getNutritionResults() { return nutritionResults; }
    public static void setNutritionResults(NutritionResults results) { nutritionResults = results; }

    public static void clearNutrition() {
        userInfo = null;
        nutritionResults = null;
    }
}
