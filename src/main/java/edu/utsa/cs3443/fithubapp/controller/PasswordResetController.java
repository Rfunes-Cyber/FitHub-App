package edu.utsa.cs3443.fithubapp.controller;

import edu.utsa.cs3443.fithubapp.FitHubApplication;
import edu.utsa.cs3443.fithubapp.model.AccountStore;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;

public class PasswordResetController {
    @FXML private TextField usernameField;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField verifyPasswordField;
    @FXML private Label messageLabel;

    @FXML private void handleResetPassword() {
        String u = usernameField.getText();
        String p = newPasswordField.getText();
        String v = verifyPasswordField.getText();

        if (!AccountStore.accountExists()) { error("No account exists yet."); return; }
        if (u == null || u.isBlank()) { error("Enter your username."); return; }
        if (p == null || p.length() < 6) { error("Password must contain at least 6 characters."); return; }
        if (!p.equals(v)) { error("The passwords do not match."); return; }

        try {
            if (AccountStore.changePassword(u, p)) success("Password changed successfully.");
            else error("That username was not found.");
        } catch (IOException e) {
            error("Could not update the account.");
        }
    }

    @FXML private void returnToLogin() throws IOException {
        FitHubApplication.showScreen("login.fxml");
    }

    private void error(String s) {
        messageLabel.getStyleClass().setAll("message-label", "error-message");
        messageLabel.setText(s);
    }

    private void success(String s) {
        messageLabel.getStyleClass().setAll("message-label", "success-message");
        messageLabel.setText(s);
    }
}
