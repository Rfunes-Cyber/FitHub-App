package edu.utsa.cs3443.fithubapp.controller;

import edu.utsa.cs3443.fithubapp.FitHubApplication;
import edu.utsa.cs3443.fithubapp.model.AccountStore;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    @FXML private void handleLogin() {
        String u = usernameField.getText();
        String p = passwordField.getText();
        if (u == null || u.isBlank() || p == null || p.isBlank()) {
            error("Enter both your username and password."); return;
        }
        try {
            if (!AccountStore.accountExists()) error("No account exists yet. Select Create Account.");
            else if (AccountStore.authenticate(u, p)) success("Login successful.");
            else error("Incorrect username or password.");
        } catch (IOException e) {
            error("Could not read the local account.");
        }
    }

    @FXML private void openCreateAccount() throws IOException {
        FitHubApplication.showScreen("create-account.fxml");
    }

    @FXML private void openPasswordReset() throws IOException {
        FitHubApplication.showScreen("password-reset.fxml");
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
