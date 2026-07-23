package edu.utsa.cs3443.fithubapp.controller;

import edu.utsa.cs3443.fithubapp.FitHubApplication;
import edu.utsa.cs3443.fithubapp.model.AccountStore;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;

public class CreateAccountController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField verifyPasswordField;
    @FXML private Label messageLabel;

    @FXML private void handleCreateAccount() {
        String u = usernameField.getText();
        String p = passwordField.getText();
        String v = verifyPasswordField.getText();

        if (u == null || u.isBlank()) { error("Choose a username."); return; }
        if (p == null || p.length() < 6) { error("Password must contain at least 6 characters."); return; }
        if (!p.equals(v)) { error("The passwords do not match."); return; }

        try {
            AccountStore.createAccount(u, p);
            success("Account created. You can now log in.");
        } catch (IOException e) {
            error("Could not save the account.");
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
