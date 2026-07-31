FitHub Application
FitHub is a Java-based fitness application built with JavaFX and organized using the Model-View-Controller (MVC) design pattern. The application allows users to create a local account, log in, enter personal nutrition information, calculate daily calorie and macronutrient targets, generate workouts, and view dashboard, profile, and settings screens.
Main Features:
Local account creation, login, and password reset
Nutrition onboarding using age, gender, height, weight, activity level, and fitness goal
Estimated daily calorie, protein, carbohydrate, and fat targets
Workout generation based on selected muscle groups and available equipment
Dashboard, profile, and settings screens
CSV-based application data

Project Structure:
src/main/java/edu/utsa/cs3443/fithubapp/model — application data and model classes
src/main/java/edu/utsa/cs3443/fithubapp/controller — JavaFX controllers and screen behavior
src/main/java/edu/utsa/cs3443/fithubapp/view — view-related Java classes
src/main/java/edu/utsa/cs3443/fithubapp/util — calculation and file-loading utilities
src/main/resources/edu/utsa/cs3443/fithubapp/fxml — FXML user-interface files
src/main/resources/edu/utsa/cs3443/fithubapp/css — application styling
src/main/resources/workout/exercises.csv — exercise data used by the workout feature

How to get started with Application:
git clone <TEAM-REPOSITORY-URL>
cd FitHub-App
Replace <TEAM-REPOSITORY-URL> with the HTTPS URL shown under the green Code button on the GitHub repository.

How to Run Application:
Open the FitHub-App folder in IntelliJ IDEA.
Confirm that IntelliJ is using JDK 21.
If necessary, allow Maven to import and download the project dependencies.
Open FitHubApplication.java
Run the main method in FitHubApplication.

Windows Terminal:
From the repository root, run -
.\mvnw.cmd clean javafx:run

For MacOS or Linux Terminal:
From the repository root, run -
chmod +x mvnw
./mvnw clean javafx:run

After all this the application should open in a fixed JavaFX window.

UML Diagram: The UML diagram will be located inside the docs folder - docs/FitHub_Project_UML-FitHub_Application_UML_Class_Diagram.png

Known Issues:
The interface uses a fixed 540 × 960 window and is not responsive to resizing.
Account information is stored locally and supports one local account file per computer.
The application should be launched from the repository root so relative data-file paths resolve correctly.
Nutrition values are estimates and are intended for demonstration purposes only.
Dashboard currently displays placeholder information and does not dynamically update all user nutrition values.

Authors/Contributors: 
Raymond Funes
Brayden Dominic Dinh
Ryoma M Herd
Abdulrahman Aladadi







