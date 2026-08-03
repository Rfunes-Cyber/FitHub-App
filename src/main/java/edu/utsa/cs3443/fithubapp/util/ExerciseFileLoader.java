package edu.utsa.cs3443.fithubapp.util;

import edu.utsa.cs3443.fithubapp.model.Exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

//Loads exercise info from the exercise CSV file
//Filters the exercise by selected muscle group and equipment, exercise names for the prefrence bar, and searches for a preferred exercise by name
public class ExerciseFileLoader {

    // Location of the CSV inside the application resources
    private static final String RESOURCE_PATH =
            "/workout/exercises.csv";

    //Prevents objects from being created from this fileLoader and as well do not edit
    private ExerciseFileLoader() {
    }

    //Loads exercises matching the selected muscles and equipment
    //selectedEquipmentType selected equipment category & list of matching Exercise objects
    //IllegalArgumentException when the selections or CSV data are invalid
    //IllegalStateException when the CSV file cannot be read
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

                //Skip the CSV headings
                if (lineNumber == 1) {
                    continue;
                }

                //Skip empty rows
                if (line.isBlank()) {
                    continue;
                }

                //Keeps empty CSV values so every row can be validated
                String[] values = line.split(",", -1);

                //Every exercise row must contain exactly six columns
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

                //Exercises marked Both can be used for Home or Gym workouts
                boolean equipmentMatches =
                        equipmentType.equalsIgnoreCase(
                                selectedEquipmentType
                        )
                                || equipmentType.equalsIgnoreCase(
                                "Both"
                        );

                //Creates an Exercise only when both filters match
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
    //Duplicates name are removed
    //selectedMuscleGroups muscle groups selected by the user & selectedEquipmentType selected equipment category
    //returns list of matching exercise names
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
    //exercises available for the workout
    //exerciseName exercise name to find
    //returns matching exercise or null
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
    //selectedMuscleGroups selected muscle-group names & muscleGroup muscle group read from the CSV
    //returns true when the muscle group matches
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
    //exerciseNames exercise names already added & exerciseName exercise name being checked
    //returns true when the name already exists
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
    //selectedMuscleGroups muscle groups selected by the user & selectedEquipmentType selected equipment category
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

    //Opens the CSV file
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