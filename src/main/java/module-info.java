module edu.utsa.cs3443.fithubapp {
    requires javafx.controls;
    requires javafx.fxml;

    opens edu.utsa.cs3443.fithubapp to javafx.fxml;
    opens edu.utsa.cs3443.fithubapp.controller to javafx.fxml;
    opens edu.utsa.cs3443.fithubapp.model to javafx.fxml;

    exports edu.utsa.cs3443.fithubapp;
}