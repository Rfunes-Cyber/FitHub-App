package edu.utsa.cs3443.fithubapp.util;

import edu.utsa.cs3443.fithubapp.model.Exercise;
import edu.utsa.cs3443.fithubapp.model.Workout;

import java.util.ArrayList;

// DONT DELETE
//Create Workout objects based on muscle group equipment, and preferred exercise selected by the user
//Also loads matching exercises from the CSV file and places it first when chosen
public class WorkoutGenerator {

    private static final String NO_PREFERENCE =
            "No preference";

    //Prevents WorkoutGenerator objects from being created
    private WorkoutGenerator() {
    }

    //Original method preserved for WorkoutTest and other code
    public static Workout generateWorkout(
            ArrayList<String> selectedMuscleGroups,
            String equipmentType) {

        return generateWorkout(
                selectedMuscleGroups,
                equipmentType,
                NO_PREFERENCE
        );
    }

    //Generates a workout with an optional preferred exercise
    public static Workout generateWorkout(
            ArrayList<String> selectedMuscleGroups,
            String equipmentType,
            String preferredExerciseName) {

        validateSelections(
                selectedMuscleGroups,
                equipmentType
        );

        //Creates name example like "Legs, back, Arm workout
        String workoutName =
                String.join(
                        ", ",
                        selectedMuscleGroups
                )
                        + " Workout";

        //Estimates twenty minutes per muscle group
        int durationMin =
                selectedMuscleGroups.size() * 20;

        Workout workout =
                new Workout(
                        workoutName,
                        durationMin
                );
        //Stores each selected muscle group in the workout
        for (String muscleGroup
                : selectedMuscleGroups) {

            workout.addMuscleGroup(
                    muscleGroup
            );
        }

        // This loads the exercises that match what the user selected so muscles and equipment
        ArrayList<Exercise> matchingExercises =
                ExerciseFileLoader.loadExercises(
                        selectedMuscleGroups,
                        equipmentType
                );

        if (matchingExercises.isEmpty()) {
            throw new IllegalArgumentException(
                    "No matching exercises were found."
            );
        }

        Exercise preferredExercise =
                getPreferredExercise(
                        matchingExercises,
                        preferredExerciseName
                );

        //Adds the preferred exercise first
        if (preferredExercise != null) {
            workout.addExercise(
                    preferredExercise
            );
        }

        //Add all remaining exercises to workout
        for (Exercise exercise : matchingExercises) {

            if (exercise != preferredExercise) {
                workout.addExercise(exercise);
            }
        }

        return workout;
    }

    //Returns the preferred exercise when one was selected
    private static Exercise getPreferredExercise(
            ArrayList<Exercise> matchingExercises,
            String preferredExerciseName) {

        if (preferredExerciseName == null
                || preferredExerciseName.isBlank()
                || preferredExerciseName.equalsIgnoreCase(
                NO_PREFERENCE
        )) {

            return null;
        }

        Exercise preferredExercise =
                ExerciseFileLoader.findExerciseByName(
                        matchingExercises,
                        preferredExerciseName
                );

        if (preferredExercise == null) {
            throw new IllegalArgumentException(
                    "The preferred exercise does not match "
                            + "the selected workout options."
            );
        }

        return preferredExercise;
    }

    //Validates the workout selections
    //As well selectedMuscleGroups muscle groups selected by the user
    //equipmentType selected equipment category
    //IllegalArgumentException when a required selection is missing or invalid value
    private static void validateSelections(
            ArrayList<String> selectedMuscleGroups,
            String equipmentType) {

        if (selectedMuscleGroups == null
                || selectedMuscleGroups.isEmpty()) {

            throw new IllegalArgumentException(
                    "Select at least one muscle group."
            );
        }

        if (equipmentType == null
                || equipmentType.isBlank()) {

            throw new IllegalArgumentException(
                    "Equipment type cannot be empty."
            );
        }

        if (!equipmentType.equalsIgnoreCase("Home")
                && !equipmentType.equalsIgnoreCase("Gym")) {

            throw new IllegalArgumentException(
                    "Equipment type must be Home or Gym."
            );
        }

        for (String muscleGroup
                : selectedMuscleGroups) {

            if (muscleGroup == null
                    || muscleGroup.isBlank()) {

                throw new IllegalArgumentException(
                        "Muscle groups cannot be empty."
                );
            }
        }
    }
}