package edu.utsa.cs3443.fithubapp.util;

import edu.utsa.cs3443.fithubapp.model.Exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class ExerciseFileLoader {

    // Location of the CSV when it is included with the application
    private static final String RESOURCE_PATH =
            "/workout/exercises.csv";

    private ExerciseFileLoader() {
    }

    // Loads exercises that match the selected muscles and equipment
    public static ArrayList<Exercise> loadExercises(
            ArrayList<String> selectedMuscleGroups,
            String selectedEquipmentType) {

        ArrayList<Exercise> matchingExercises = new ArrayList<>();

        try (BufferedReader reader = openCsvReader()) {

            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                // Skips the column headings
                if (lineNumber == 1) {
                    continue;
                }

                // Skips blank lines
                if (line.isBlank()) {
                    continue;
                }

                String[] values = line.split(",", -1);

                // Every row must contain six values
                if (values.length != 6) {
                    throw new IllegalArgumentException(
                            "Invalid CSV format on line " + lineNumber + "."
                    );
                }

                String workoutName = values[0].trim();
                String exerciseName = values[1].trim();
                String muscleGroup = values[2].trim();
                String equipmentType = values[3].trim();

                int sets;
                int reps;

                try {
                    sets = Integer.parseInt(values[4].trim());
                    reps = Integer.parseInt(values[5].trim());
                } catch (NumberFormatException exception) {
                    throw new IllegalArgumentException(
                            "Invalid sets or reps on CSV line "
                                    + lineNumber + "."
                    );
                }

                boolean muscleMatches =
                        containsIgnoreCase(
                                selectedMuscleGroups,
                                muscleGroup
                        );

                boolean equipmentMatches =
                        equipmentType.equalsIgnoreCase(
                                selectedEquipmentType
                        )
                                || equipmentType.equalsIgnoreCase("Both");

                if (muscleMatches && equipmentMatches) {
                    Exercise exercise = new Exercise(
                            workoutName,
                            exerciseName,
                            muscleGroup,
                            equipmentType,
                            sets,
                            reps
                    );

                    matchingExercises.add(exercise);
                }
            }

        } catch (IOException exception) {
            throw new IllegalStateException(
                    "The exercise CSV file could not be read.",
                    exception
            );
        }

        return matchingExercises;
    }

    // Checks a muscle group without requiring matching capitalization
    private static boolean containsIgnoreCase(
            ArrayList<String> selectedMuscleGroups,
            String muscleGroup) {

        for (String selectedGroup : selectedMuscleGroups) {
            if (selectedGroup.equalsIgnoreCase(muscleGroup)) {
                return true;
            }
        }

        return false;
    }

    // Attempts to load the CSV from the application resources first
    private static BufferedReader openCsvReader() throws IOException {

        InputStream inputStream =
                ExerciseFileLoader.class.getResourceAsStream(
                        RESOURCE_PATH
                );

        if (inputStream != null) {
            return new BufferedReader(
                    new InputStreamReader(inputStream)
            );
        }

        // Backup location used while running directly from IntelliJ
        Path csvPath = Path.of(
                "src",
                "workout",
                "exercises.csv"
        );

        return Files.newBufferedReader(csvPath);
    }
}