package edu.utsa.cs3443.fithubapp.controller;

import edu.utsa.cs3443.fithubapp.FitHubApplication;
import edu.utsa.cs3443.fithubapp.model.AppSession;
import edu.utsa.cs3443.fithubapp.model.NutritionResults;
import edu.utsa.cs3443.fithubapp.model.UserInfo;
import edu.utsa.cs3443.fithubapp.util.NutritionCalc;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

/** Handles all three nutrition onboarding screens. */
public class NutritionController {
    @FXML private TextField ageField;
    @FXML private TextField feetField;
    @FXML private TextField inchesField;
    @FXML private TextField weightField;
    @FXML private RadioButton maleRadio;
    @FXML private RadioButton femaleRadio;

    @FXML private RadioButton littleActivityRadio;
    @FXML private RadioButton lightActivityRadio;
    @FXML private RadioButton moderateActivityRadio;
    @FXML private RadioButton veryActivityRadio;
    @FXML private RadioButton maintainRadio;
    @FXML private RadioButton lossRadio;
    @FXML private RadioButton extremeLossRadio;
    @FXML private RadioButton gainRadio;
    @FXML private RadioButton extremeGainRadio;

    @FXML private Label messageLabel;
    @FXML private Label caloriesLabel;
    @FXML private Label proteinLabel;
    @FXML private Label carbsLabel;
    @FXML private Label fatLabel;

    @FXML
    private void initialize() {
        NutritionResults results = AppSession.getNutritionResults();
        if (results != null && caloriesLabel != null) {
            caloriesLabel.setText(Math.round(results.getDailyCalories()) + " kcal");
            proteinLabel.setText(Math.round(results.getProteinGrams()) + " g");
            carbsLabel.setText(Math.round(results.getCarbohydrateGrams()) + " g");
            fatLabel.setText(Math.round(results.getFatGrams()) + " g");
        }
    }

    @FXML
    private void continueToGoals() {
        try {
            int age = Integer.parseInt(ageField.getText().trim());
            int feet = Integer.parseInt(feetField.getText().trim());
            int inches = Integer.parseInt(inchesField.getText().trim());
            double weightLb = Double.parseDouble(weightField.getText().trim());

            if (age < 1 || age > 100) throw new IllegalArgumentException("Age must be between 1 and 100.");
            if (feet < 3 || feet > 8 || inches < 0 || inches > 11) throw new IllegalArgumentException("Enter a valid height.");
            if (weightLb <= 0) throw new IllegalArgumentException("Weight must be greater than zero.");

            String gender = maleRadio.isSelected() ? "Male" : femaleRadio.isSelected() ? "Female" : null;
            if (gender == null) throw new IllegalArgumentException("Please select a gender.");

            double heightCm = ((feet * 12.0) + inches) * 2.54;
            double weightKg = weightLb * 0.45359237;
            AppSession.setUserInfo(new UserInfo(age, heightCm, weightKg, gender, null, null));
            FitHubApplication.showScreen("nutrition-goals.fxml");
        } catch (NumberFormatException e) {
            error("Please enter numbers in every field.");
        } catch (IllegalArgumentException | IOException e) {
            error(e.getMessage());
        }
    }

    @FXML
    private void calculateNutrition() {
        UserInfo info = AppSession.getUserInfo();
        if (info == null) {
            error("Please complete the first page.");
            return;
        }

        String activity = littleActivityRadio.isSelected() ? "Little to No Exercise"
                : lightActivityRadio.isSelected() ? "Lightly Active"
                : moderateActivityRadio.isSelected() ? "Moderately Active"
                : veryActivityRadio.isSelected() ? "Very Active" : null;

        String goal = maintainRadio.isSelected() ? "Maintain Weight"
                : lossRadio.isSelected() ? "Weight Loss"
                : extremeLossRadio.isSelected() ? "Extreme Weight Loss"
                : gainRadio.isSelected() ? "Gain Weight"
                : extremeGainRadio.isSelected() ? "Extreme Weight Gain" : null;

        if (activity == null || goal == null) {
            error("Please choose an activity level and fitness goal.");
            return;
        }

        info.setActivityLevel(activity);
        info.setFitnessGoal(goal);
        try {
            AppSession.setNutritionResults(NutritionCalc.calculate(
                    info.getAge(), info.getHeightCm(), info.getWeightKg(), info.getGender(), activity, goal));
            FitHubApplication.showScreen("nutrition-results.fxml");
        } catch (IllegalArgumentException | IOException e) {
            error(e.getMessage());
        }
    }

    @FXML private void backToPersonalInfo() throws IOException {
        FitHubApplication.showScreen("nutrition-input.fxml");
    }

    @FXML private void proceedToDashboard() throws IOException {
        FitHubApplication.showScreen("dashboard.fxml");
    }

    private void error(String message) {
        if (messageLabel != null) {
            messageLabel.getStyleClass().setAll("message-label", "error-message");
            messageLabel.setText(message == null ? "Unable to continue." : message);
        }
    }
}
