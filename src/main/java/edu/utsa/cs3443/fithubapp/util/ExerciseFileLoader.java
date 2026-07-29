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

    // Location of the CSV inside the application resources
    private static final String RESOURCE_PATH =
            "/workout/exercises.csv";

    private ExerciseFileLoader() {
    }

    // Loads exercises matching the selected muscles and equipment
    public static ArrayList<Exercise> loadExercises(
            ArrayList<String> selectedMuscleGroups,
            String selectedEquipmentType) {

        validateSelections(
                selectedMuscleGroups,
                selectedEquipmentType
        );

        ArrayList<Exercise> matchingExercises =
                new ArrayList<>();

        try (BufferedReader reader = openCsvReader()) {

            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                // Skip the CSV headings
                if (lineNumber == 1) {
                    continue;
                }

                // Skip empty rows
                if (line.isBlank()) {
                    continue;
                }

                String[] values = line.split(",", -1);

                if (values.length != 6) {
                    throw new IllegalArgumentException(
                            "Invalid CSV format on line "
                                    + lineNumber
                                    + "."
                    );
                }

                String workoutName = values[0].trim();
                String exerciseName = values[1].trim();
                String muscleGroup = values[2].trim();
                String equipmentType = values[3].trim();

                int sets;
                int reps;

                try {
                    sets = Integer.parseInt(
                            values[4].trim()
                    );

                    reps = Integer.parseInt(
                            values[5].trim()
                    );

                } catch (NumberFormatException exception) {
                    throw new IllegalArgumentException(
                            "Invalid sets or reps on CSV line "
                                    + lineNumber
                                    + "."
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
                                || equipmentType.equalsIgnoreCase(
                                "Both"
                        );

                if (muscleMatches && equipmentMatches) {
                    matchingExercises.add(
                            new Exercise(
                                    workoutName,
                                    exerciseName,
                                    muscleGroup,
                                    equipmentType,
                                    sets,
                                    reps
                            )
                    );
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

    // Returns only the names used by the preference dropdown
    public static ArrayList<String> loadExerciseNames(
            ArrayList<String> selectedMuscleGroups,
            String selectedEquipmentType) {

        ArrayList<Exercise> exercises =
                loadExercises(
                        selectedMuscleGroups,
                        selectedEquipmentType
                );

        ArrayList<String> exerciseNames =
                new ArrayList<>();

        for (Exercise exercise : exercises) {
            String exerciseName = exercise.getName();

            if (!containsNameIgnoreCase(
                    exerciseNames,
                    exerciseName
            )) {
                exerciseNames.add(exerciseName);
            }
        }

        return exerciseNames;
    }

    // Finds one matching exercise by its name
    public static Exercise findExerciseByName(
            ArrayList<Exercise> exercises,
            String exerciseName) {

        if (exercises == null
                || exerciseName == null
                || exerciseName.isBlank()) {

            return null;
        }

        for (Exercise exercise : exercises) {
            if (exercise.getName().equalsIgnoreCase(
                    exerciseName
            )) {
                return exercise;
            }
        }

        return null;
    }

    // Checks whether a selected muscle matches the CSV value
    private static boolean containsIgnoreCase(
            ArrayList<String> selectedMuscleGroups,
            String muscleGroup) {

        for (String selectedGroup : selectedMuscleGroups) {
            if (selectedGroup.equalsIgnoreCase(
                    muscleGroup
            )) {
                return true;
            }
        }

        return false;
    }

    // Prevents duplicate names in the preference dropdown
    private static boolean containsNameIgnoreCase(
            ArrayList<String> exerciseNames,
            String exerciseName) {

        for (String existingName : exerciseNames) {
            if (existingName.equalsIgnoreCase(
                    exerciseName
            )) {
                return true;
            }
        }

        return false;
    }

    // Validates the filter information
    private static void validateSelections(
            ArrayList<String> selectedMuscleGroups,
            String selectedEquipmentType) {

        if (selectedMuscleGroups == null
                || selectedMuscleGroups.isEmpty()) {

            throw new IllegalArgumentException(
                    "Select at least one muscle group."
            );
        }

        if (selectedEquipmentType == null
                || selectedEquipmentType.isBlank()) {

            throw new IllegalArgumentException(
                    "Select Home Workout or Gym Access."
            );
        }
    }

    // Tries the packaged resource first
    private static BufferedReader openCsvReader()
            throws IOException {

        InputStream inputStream =
                ExerciseFileLoader.class
                        .getResourceAsStream(
                                RESOURCE_PATH
                        );

        if (inputStream != null) {
            return new BufferedReader(
                    new InputStreamReader(inputStream)
            );
        }

        // Backup location while running from IntelliJ
        Path csvPath = Path.of(
                "src",
                "main",
                "resources",
                "workout",
                "exercises.csv"
        );

        return Files.newBufferedReader(csvPath);
    }
}